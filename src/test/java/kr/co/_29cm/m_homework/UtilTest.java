package kr.co._29cm.m_homework;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import kr.co._29cm.m_homework.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 비즈니스 로직과 상관없는 유틸성 라이브러리/함수 검증 및 테스트
 */
@DisplayName("유틸성 함수 테스트")
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
    @DisplayName("숫자 format 테스트")
    void addCommaToNumberTest() {
        String numStr = "34200";
        String numStr2 = "20546534200";
        DecimalFormat formatter = new DecimalFormat("#,###");
        assertEquals("34,200", formatter.format(Integer.parseInt(numStr)));
        assertEquals("20,546,534,200", formatter.format(Long.parseLong(numStr2)));
    }

    static class MyCounter {
        private int count;
        public synchronized void increment() {
            int temp = count;
            count = temp + 1;
        }

        public int getCount() {
            return count;
        }
    }

    @Test
    @DisplayName("멀티스레드 사용 테스트")
    void multiThreadTest() throws InterruptedException {
        MyCounter myCounter = new MyCounter();
        int numOfThreads = 5;
        ExecutorService service = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(numOfThreads);
        for(int i=0; i<numOfThreads; i++) {
            service.submit(() -> {
                myCounter.increment();
                latch.countDown();
            });
        }
        latch.await();
        assertEquals(numOfThreads, myCounter.getCount());
    }
}
