# 상품 주문 프로그램

## 1. 프로젝트 환경 구성
### 자바 버전
자바 버전 11을 선택하였다.\
자바 8 이상을 선택 한 이유는 함수형 인터페이스와 람다 표현식 그리고 스트림 API를 적극적으로 사용해서 코드 가독성을 높이고 side effect를 줄이기 위해서 이다.\
자바8을 사용해도 되는데 굳이 자바11을 선택한 이유는 LTS 버전이며, 자바8과 비교하여 가비지 컬렉터가 변경되는 등 성능 향상 측면의 변화도 있었기 때문이다.

### 사용한 외부 라이브러리  
### `spring boot`  
웹 프로젝트가 아니기 때문에 서블릿 컨테이너는 필요 없지만 스프링 컨테이너가 제공하는 기능을 사용해 DI를 보다 손쉽게 하기 위해 선택하였다. 자바 11 버전을 지원하는 버전인 2.7.9을 선택하였으며 최신 버전인 3 이상을 선택하지 않은 이유는 버전 3 이상은 자바 17 이상만 지원하기 때문이다.
### `lombok`
entity 클래스 작성 시 getter 메서드를 좀 더 편리하게 작성하고 builder 패턴을 보다 쉽게 구현하여 사용하기 위해 선택하였다.
### `opencsv`
csv 파일에 들어있는 상품 데이터를 손쉽게 ArrayList 구조로 메모리에 load 하기 위해 선택하였다.
### `junit5`
테스트 코드를 쉽고 빠르게 작성하기 위해 선택하였다.

## 2. 프로젝트 구조

### 애플리케이션 시작점
Main 클래스의 main 메서드가 앱을 실행시키는 시작 함수이다.\
시작 함수가 실행되면 스프링 컨테이너에 Annotation을 기반으로 Bean들을 등록하고 의존성 관계를 설정한다.\
스프링 컨테이너 설정이 끝나면 데이터를 로드하고 사용자에게 보여질 화면을 표시한다.

### 계층 분리  
프로젝트 계층을 다음과 같이 나눴다.

### `view`
사용자와 직접적으로 소통하는 계층이다. 사용자에게 몇 가지 선택 옵션을 주고 선택에 따라 task가 실행된다. task를 실행하는 통로는 오로지 controller 뿐이다.
### `controller`
사용자가 발생 시키는 event들과 비즈니스 로직을 구현한 서비스 계층 사이를 이어주는 매핑 역할을 하는 계층이다. 사용자의 요청을 받아서 서비스 계층에 작업을 위임한 후 결과를 받아서 view 계층에 결과값을 리턴해준다.
### `service`
실질적인 비즈니스 로직이 작성되는 계층이다. 스프링 컨테이너로부터 주입 받은repository 객체를 활용해서 데이터를 가져온 후 task에 맞는 작업을 실행한다. 그리고 그 결과값을 controller 계층에 넘겨준다.
### `repository`
스프링 컨테이너로부터 주입 받은 database 계층의 storage 객체를 활용해서 데이터를 가져온 후 service 계층에 데이터를 넘겨준다. 실제 데이터가 저장되어 있는 메모리 저장소와 맞닿아 있는 계층이며 저장소의 데이터를 가져온 후 그 복사본을 service 계층에 넘겨주도록 하여 의도치 않은 원본 데이터의 손상을 막는다. 특정 데이터를 검색할 때는 이진탐색을 활용해서 데이터 탐색 시 시간 복잡도를 O(log n)으로 만들었다. 그리고 BaseRepository 인터페이스를 활용해서 언제든 다른 구현체를 만들어서 쉽게 교체할 수 있도록 하였다.
### `database`
앱의 시작 시점에 csv 파일의 데이터가 로드되는 메모리 저장소가 존재하는 계층이다. 메모리 저장소는 Storage 클래스의 클래스 변수로 존재한다. 앱의 시작과 동시에 생성되고 종료 시 사라진다. 메모리 저장소는 private으로 정의되어서 외부에서 직접적으로 접근할 수 없으며 Storage 클래스가 구현한 메서드를 통해서만 데이터를 다룰 수 있다. 오로지 repository 계층만 이 계층에 접근한다.

## 3. 데이터 load 방식
### CSV 파일  
원본 데이터는 csv 파일로 제공된다. 이 파일을 resources 폴더에 저장하여 활용할 수 있도록 하였다.
### 데이터 메모리 저장소
데이터가 필요할 때 마다 파일을 읽으면 Disk I/O가 빈번하게 일어나 성능 저하가 오기 때문에 App 시작 시점에 DataLoader를 이용해 파일의 데이터를 메모리 저장소에 load 하고 데이터 활용 시 메모리 저장소에 접근하도록 하였다. 여기서 말하는 메모리 저장소는 Storage 클래스의 클래스 변수다.
```java
/**
 * Storage 클래스
 */
@Component
public class DataVirtualStorage {
    /*
     * 메모리 저장소
     */
    private static Map<String, List<String>> tableColumnMap = new HashMap<>();
    private static Map<String, List<BaseEntity>> tableDataMap = new HashMap<>();
}
```
### 데이터 로더
DataLoader라는 인터페이스를 작성하고 이를 구현한 CSVDataLoader 구현체를 만들어서 csv 파일의 데이터를 load 할 수 있도록 하였다.  그리고 application.properties 설정 파일에 파일 확장자를 지정할 수 있게 하였고 DataLoaderFactory 클래스를 만들어 설정 파일의 값에 따라 자동으로 구현체가 교체되어 데이터를 load할 수 있도록 하였다.
```java
public interface DataLoader {
    /**
     * 원본 데이터가 저장된 파일의 확장자 반환
     * @return 파일 확장자
     */
    String getFileExt();

    /**
     * 파일에 저장된 데이터를 메모리 저장소에 올리는 함수
     */
    void loadDataOnMemory();
}

@Component
@RequiredArgsConstructor
public class DataLoaderFactory {
    private final List<DataLoader> dataLoaders;
    private final PropertyManager propertyManager;

    /**
     * 설정파일에 저장된 파일 확장자를 읽어서 이 확장자를 다루는 DataLoader 클래스를 찾아서 반환한다.
     * @return DataLoader 인터페이스를 구현한 클래스
     */
    public DataLoader getLoader() {
        String fileExt = propertyManager.getProperty("data.file.ext", DataFileExt.CSV);

        Optional<DataLoader> opt = dataLoaders.stream()
                .filter(loader -> loader.getFileExt().equals(fileExt))
                .findFirst();

        if(opt.isEmpty()) {
            throw new DataLoaderNotFoundException();
        }

        return opt.get();
    }
}
```
### Entity Class
파일에 있던 데이터가 메모리 저장소에 올라갈 때 데이터 주제에 따라 저장되는 형태가 달라야 하는데 이를 entity 계층에 class 형태로 작성하였다. 그리고 BaseEntity 인터페이스를 작성하고 메모리 저장소에 올라갈 데이터 클래스는 모두 이 인터페이스를 구현하여 database 계층에서 데이터 주제가 무엇이든 상관없이 동일하게 데이터를 다룰 수 있게 하였다. (이 프로젝트에서는 데이터 주제를 topic이란 명칭으로 통일하겠다.)
```java
public interface BaseEntity {
    String getId();
}

@Getter
public class Product implements BaseEntity {
    // 상품번호
    private String id;

    // 상품명
    private String name;

    // 상품가격
    private int price;

    // 상품 재고량
    private int stockAmt;
}
```
### DataListBuilder
topic이 달라지면 파일로부터 읽은 데이터를 메모리 저장소에 load 할 때 그 방식 또한 다 달라야 한다. 파일로부터 데이터를 읽으면 String 배열 형태의 데이터를 얻을 수 있는데 이를 entity 객체로 변환한 후 entity객체의 List 형태로 load해야 하기 때문이다. 그래서 이를 수행하는 함수를 지닌 DataListBuilder 인터페이스를 작성하였고 데이터 topic마다 이 인터페이스를 다 다르게 구현해서 메모리 저장소에 데이터를 load 할 때 이 구현체들을 활용할 수 있도록 하였다. 데이터 topic 리스트는 설정 파일에 저장하였고 이 설정 값을 읽어서 topic에 맞는 DataListBuilder 구현체를 각각 불러와서 load하는 식이다.
```java
public interface DataListBuilder {
    String getTopic();
    List<BaseEntity> buildList(List<String[]> rows);
}

@Component
@RequiredArgsConstructor
public class DataListBuilderFactory {
    private final List<DataListBuilder> dataListBuilders;

    public DataListBuilder getBuilder(String topic) {
        Optional<DataListBuilder> opt = dataListBuilders.stream()
                .filter(builder -> builder.getTopic().equals(topic))
                .findFirst();

        if(opt.isEmpty()) {
            throw new DataListBuilderNotFoundException();
        }

        return opt.get();
    }
}

@Component
public class ProductListBuilder implements DataListBuilder {
    @Override
    public String getTopic() {
        return Product.class.getSimpleName();
    }

    @Override
    public List<BaseEntity> buildList(List<String[]> rows) {
        // 정렬은 상품번호 기준 내림차순으로 한다.(최신 상품을 위로)
        return rows.stream().skip(1).map(row -> Product.builder()
                .id(row[ProductColumn.ID.ordinal()])
                .name(row[ProductColumn.NAME.ordinal()])
                .price(Integer.parseInt(row[ProductColumn.PRICE.ordinal()]))
                .stockAmt(Integer.parseInt(row[ProductColumn.STOCK_AMT.ordinal()]))
                .build()
        ).sorted(Comparator.comparing(Product::getId).reversed()).collect(Collectors.toList());
    }
}
```

## 4. 데이터 활용 방식

### 특정 데이터 검색
List 형태로 entity 객체가 저장되고 관리되는데 이 List 중 필요한 객체를 찾을 필요가 있다. 이 때 하나씩 돌면서 체크하면 시간복잡도가 O(n)이 되기 때문에 데이터 수가 증가할수록 탐색 소요 시간도 일정한 비율로 같이 증가하게 된다. 그래서 이를 개선하기 위해 entity 객체 List를 이진탐색으로 검색할 수 있는 클래스를 만들었다. 이를 활용하면 시간복잡도가 O(log n)으로 개선된다. (List는 메모리에 로드 될 때 id를 기준으로 정렬해서 올라가며 검색할 때 id를 기준으로 검색한다.)
### 데이터 복사본 활용  
데이터가 메모리 저장소로부터 사용자에 가까운 계층으로 넘어갈 때 원본 데이터 객체를 넘기게 되면 의도치 않게 원본 데이터가 손상 될 위험이 있다. 데이터 조작은 반드시 view → controller → service → repository 순서로 거쳐서 처리되어야 하는데 누군가의 실수로 view 혹은 controller 계층에서 데이터를 수정/삭제 해버려서 데이터 무결성이 깨지고 앱 전체에 영향을 줄 수 있다는 뜻이다.

## 5. 개발 방향성

### SOLID 원칙
개인적으로 SOLID 원칙 중 단일 책임 원칙과 개방 폐쇄 원칙을 제일 중요하게 생각하는데, 이번 프로젝트를 구현함에 있어서 어떻게 하면 이 원칙들을 잘 지킬 수 있을지에 대한 고민을 많이 하였다. 앞으로 추가될 가능성이 있거나 교체될 가능성이 있는 컴퍼넌트는 모두 인터페이스를 작성하여 이를 의존하는 쪽에서 실제 구현체가 아닌 인터페이스에 의존하도록 하여서 구현체가 변경/추가 되더라도 이를 활용하는 쪽에서는 변경이 일어나지 않도록 개발하였다. 그리고 필요 시 팩토리 클래스를 작성하고 팩토리 메서드가 참조할만한 값들은 모두 설정파일에 저장하여 사용자가 설정값만 변경하더라도 동적으로 사용되는 구현체가 변경되도록 하였다.\
최대한 기능별로 클래스를 나누고 의존하는 구현체가 어떤 것인지 컴파일단계에선 알지 못하게 하고 동적으로 어떤 구현체를 쓸 지 결정되도록 개발함으로서 유연성을 확보할 수 있었다.
### Stream API 적극 활용
앱 특성상 List 자료구조를 적극적으로 사용하는데 이 데이터를 조작할 때 Stream 형태로 변환 후 map, filter, match 등의 기능을 적극적으로 활용 후 다시 List로 반환해주는 방식을 많이 사용했다. 그 결과 코드 가독성이 올라가고 기능 구현이 쉬워졌다.
### 함수형 인터페이스 활용
view 계층은 사용자에게 선택권을 주고 어떤 것을 선택했는지에 따라 그에 맞는 작업이 controller에 요청되어야 한다. 그리고 이러한 패턴의 반복으로 이뤄진다. 그래서 사용자에게 옵션을 주고 답변을 받는 클래스를 따로 작성하였고 그 답변에 따라 실행되어야 할 함수를 콜백함수로 외부에서 받아와서 실행하는 식으로 개발하였다. 그 결과 사용자와 직접 소통하는 부분과 controller에게 작업을 요청하는 부분을 확실하게 분리할 수 있었다.
```java
// 사용자와 소통하는 클래스
@Component
public class OrderOrQuitQuestion {    
    public void ask(Runnable orderChooseCallback, Runnable quitChooseCallback) {
        System.out.print(Question.ORDER_OR_QUIT);
        String answer = UserInterface.scanner.nextLine();
        if(isAnswerOrder(answer)) {
            orderChooseCallback.run();
        } else if (isAnswerQuit(answer)) {
            quitChooseCallback.run();
        } else {
            System.out.println(Warning.WRONG_ANSWER_O_OR_Q);
        }
    }
}

// 사용자의 답변에 따라 적절한 콜백함수를 실행하는 클래스
@Component
@RequiredArgsConstructor
public class UserInterface {
    private final OrderOrQuitQuestion orderOrQuitQuestion;

    public void render() {
        while(true) {
            orderOrQuitQuestion.ask(                    
                    (() -> chooseOrder())
                  , (() -> chooseQuit())
            );
 
            if(isQuit()) {
                break;
            }
        }
    }
}
```

## 6. 남은 과제

앞으로 해결해야 할 과제는❓

	앞으로 데이터 규모가 더 커진다면?
	다양한 topic의 데이터가 계속해서 추가된다면?
	다양한 topic들간의 관계를 다양한 방식으로 맺어줘야 한다면?
	사용자 인터페이스가 터미널창에서 벗어나 GUI 형태로 되어야 한다면?

결국 이러한 과제를 해결하려면 오로지 메모리로 대용량 데이터를 처리할 순 없으니 OS 파일 시스템 및 가상 메모리, 페이지 캐시를 활용하고 table 단위로 다양한 topic들을 추가할 수 있으며 그들의 관계를 맺은 후 자유롭게 데이터를 가져올 수 있는 RDBMS를 도입해야 한다. 동시성 문제와 transaction을 매우 훌륭히 처리하기 때문에 더더욱 필요하다.  
그리고 앱이 성장하면 자연스럽게 사용자 친화적인 UI가 필요하고 이를 위해 웹 화면 개발이 들어갈 것이며 웹 화면의 요청을 받을 수 있는 웹 서버가 구축될 것이다. 이를 스프링으로 개발한다면 이번 프로젝트에서 사용하지 않은 스프링 서블릿 컨테이너를 활용해서 웹 요청을 받아서 처리할 수 있도록 개발해야 할 것이다.