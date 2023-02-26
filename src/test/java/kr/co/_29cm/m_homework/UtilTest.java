package kr.co._29cm.m_homework;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import kr.co._29cm.m_homework.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/*
 * 비즈니스 로직과 상관없는 유틸성 라이브러리/함수 검증 및 테스트
 */
public class UtilTest {

    @Test
    @DisplayName("Stream skip 함수 테스트(첫번째열 제외)")
    void streamSkipTest() {
        List<String> list = new ArrayList<>();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");
        list.add("ddd");

        List<String> newList = list.stream().skip(1).collect(Collectors.toList());
        assertEquals(3, newList.size());
        for(int i=0; i<newList.size(); i++) {
            assertEquals(list.get(i+1), newList.get(i));
        }
    }

    @Test
    @DisplayName("lombok getter 테스트")
    void productEntityTest() {
        Product product = Product.builder()
                .id("123")
                .name("테스트상품")
                .price(2000)
                .stockAmt(10)
                .build();
        assertEquals("123", product.getId());
        assertEquals("테스트상품", product.getName());
        assertEquals(2000, product.getPrice());
        assertEquals(10, product.getStockAmt());
    }

    @Test
    @DisplayName("opencsv 콤마 split 테스트")
    void splitRowTest() {
        final int NUM_ROWS = 2;
        final int NUM_COLS = 4;

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("123,테스트 상품,2000,10");
        stringBuilder.append("\n");
        stringBuilder.append("124,\"특수 상품, 올리브\",3520,2");

        CSVReader csvReader = new CSVReader(new StringReader(stringBuilder.toString()));
        try {
            List<String[]> rows = csvReader.readAll();
            assertAll(() -> {
                assertEquals(NUM_ROWS, rows.size());
                assertEquals(NUM_COLS, rows.get(0).length);
                assertEquals(NUM_COLS, rows.get(1).length);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("stream().filter 사용 테스트")
    void streamFilterTest() {
        List<String> data = new ArrayList<>();
        data.add("a");
        data.add("b");
        data.add("c");

        List<String> newData = data.stream()
                .filter(item -> item.equals("b")).collect(Collectors.toList());
        List<String> noData = data.stream()
                .filter(item -> item.equals("z")).collect(Collectors.toList());

        List<String> nullData = new ArrayList<>();
        List<String> noData2 = nullData.stream()
                .filter(item -> item.equals("c")).collect(Collectors.toList());

        assertEquals(1, newData.size());
        assertEquals(0, noData.size());
        assertEquals(0, noData2.size());
    }

    @Test
    @DisplayName("stream().allMatch 사용 테스트")
    void streamAllMatchTest() {
        List<Integer> data = new ArrayList<>();
        data.add(1);
        data.add(2);
        data.add(3);

        boolean res1 = data.stream().map(num -> num > 2).allMatch(isOk -> isOk);
        boolean res2 = data.stream().map(num -> num > 0).allMatch(isOk -> isOk);

        assertFalse(res1);
        assertTrue(res2);
    }

    @Test
    @DisplayName("Integer.parseInt Exception 테스트")
    void stringToIntTest() {
        String data = "a";
        assertThrows(NumberFormatException.class, () -> Integer.parseInt(data));

        String data2 = " ";
        assertThrows(NumberFormatException.class, () -> Integer.parseInt(data2));
    }

    @Test
    @DisplayName("Collections.binarySearch 사용 테스트")
    void binarySearchTest() {
        Product p1 = Product.builder().id("111").name("product1").price(3000).stockAmt(10).build();
        Product p3 = Product.builder().id("333").name("product3").price(3000).stockAmt(10).build();
        Product p2 = Product.builder().id("222").name("product2").price(3000).stockAmt(10).build();
        Product p8 = Product.builder().id("888").name("product8").price(3000).stockAmt(10).build();
        Product p7 = Product.builder().id("777").name("product7").price(3000).stockAmt(10).build();

        Product searchP1 = Product.builder().id("111").build();
        Product searchP2 = Product.builder().id("777").build();
        Product searchP3 = Product.builder().id("1212").build();

        List<Product> products = new ArrayList<>();
        products.add(p1);
        products.add(p3);
        products.add(p2);
        products.add(p8);
        products.add(p7);

        Comparator<Product> comparator = Comparator.comparing(Product::getId);

        List<Product> sortedProducts = products.stream().sorted(comparator).collect(Collectors.toList());

        int idx1 = Collections.binarySearch(sortedProducts, searchP1, comparator);
        int idx2 = Collections.binarySearch(sortedProducts, searchP2, comparator);
        int idx3 = Collections.binarySearch(sortedProducts, searchP3, comparator);

        assertEquals(0, idx1);
        assertEquals(3, idx2);
        assertTrue(idx3 < 0);
    }
}
