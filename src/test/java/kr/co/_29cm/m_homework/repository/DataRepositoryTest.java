package kr.co._29cm.m_homework.repository;

import kr.co._29cm.m_homework.Main;
import kr.co._29cm.m_homework.database.DataLoaderFactory;
import kr.co._29cm.m_homework.entity.Order;
import kr.co._29cm.m_homework.entity.Product;
import kr.co._29cm.m_homework.exception.EntityNotFoundException;
import kr.co._29cm.m_homework.exception.TopicClassNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("데이터 repository 테스트")
class DataRepositoryTest {

    private static DataRepository dataRepository;

    @BeforeAll
    static void setup() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(Main.class);
        applicationContext.refresh();

        DataLoaderFactory dataLoaderFactory = applicationContext.getBean(DataLoaderFactory.class);
        dataLoaderFactory.getLoader().loadDataOnMemory();

        dataRepository = applicationContext.getBean(DataRepository.class);
    }

    @Test
    @DisplayName("데이터 컬럼명 조회 함수 테스트")
    void selectColumnNamesTest() {
        Class<Product> productClass = Product.class;
        Class<?> noClass = Order.class;
        Class<?> nullClass = null;

        List<String> colNames = dataRepository.selectColNames(productClass);
        assertTrue(colNames.size() > 0);
        assertThrows(TopicClassNotFoundException.class
                , () -> dataRepository.selectColNames(noClass));
        assertThrows(TopicClassNotFoundException.class
                , () -> dataRepository.selectColNames(nullClass));
    }

    @Test
    @DisplayName("전체 데이터 조회 함수 테스트")
    void selectAllTest() {
        Class<Product> productClass = Product.class;
        Class<?> noEntityClass = Order.class;
        Class<?> nullClass = null;

        List<Product> products = dataRepository.selectAll(productClass);
        assertTrue(products.size() > 0);
        assertTrue(products.get(0).getId().length() > 0);
        assertTrue(products.get(0).getName().length() > 0);
        assertTrue(products.get(0).getPrice() > 0);
        assertThrows(TopicClassNotFoundException.class
                , () -> dataRepository.selectAll(noEntityClass));
        assertThrows(TopicClassNotFoundException.class
                , () -> dataRepository.selectAll(nullClass));
    }

    @Test
    @DisplayName("특정 데이터 조회 함수 테스트")
    void selectOneByIdTest() {
        Class<Product> productClass = Product.class;
        Class<?> noEntityClass = Order.class;
        Class<?> nullClass = null;

        String id = "213341";
        String noId = "999999";
        String emptyId = "";
        String nullId = null;

        Product product = dataRepository.selectOneById(productClass, id);
        assertTrue(product != null);
        assertTrue(product.getId().length() > 0);
        assertTrue(product.getName().length() > 0);
        assertTrue(product.getPrice() > 0);

        assertThrows(TopicClassNotFoundException.class
                , () -> dataRepository.selectOneById(noEntityClass, id));
        assertThrows(TopicClassNotFoundException.class
                , () -> dataRepository.selectOneById(nullClass, id));

        assertThrows(EntityNotFoundException.class
                , () -> dataRepository.selectOneById(productClass, noId));
        assertThrows(EntityNotFoundException.class
                , () -> dataRepository.selectOneById(productClass, emptyId));
        assertThrows(EntityNotFoundException.class
                , () -> dataRepository.selectOneById(productClass, nullId));

        assertThrows(TopicClassNotFoundException.class
                , () -> dataRepository.selectOneById(noEntityClass, nullId));
    }

    @Test
    @DisplayName("일부 데이터 조회 함수 테스트")
    void selectByIdsTest() {
        Class<Product> productClass = Product.class;
        Class<?> noEntityClass = Order.class;
        Class<?> nullClass = null;

        List<String> ids = Arrays.asList("213341", "778422");
        List<String> noIds = Arrays.asList("213341", "999999");
        List<String> emptyIds = new ArrayList<>();
        List<String> nullIds = null;

        List<Product> products = dataRepository.selectByIds(productClass, ids);
        assertTrue(products != null && products.size() == ids.size());
        products.forEach(product -> {
            assertTrue(product.getId().length() > 0);
            assertTrue(product.getName().length() > 0);
            assertTrue(product.getPrice() > 0);
        });

        List<Product> emptyRes = dataRepository.selectByIds(productClass, emptyIds);
        assertTrue(emptyRes != null && emptyRes.size() == 0);

        assertThrows(TopicClassNotFoundException.class
                , () -> dataRepository.selectByIds(noEntityClass, ids));
        assertThrows(TopicClassNotFoundException.class
                , () -> dataRepository.selectByIds(nullClass, ids));

        assertThrows(EntityNotFoundException.class
                , () -> dataRepository.selectByIds(productClass, noIds));
        assertThrows(EntityNotFoundException.class
                , () -> dataRepository.selectByIds(productClass, nullIds));

        assertThrows(TopicClassNotFoundException.class
                , () -> dataRepository.selectByIds(nullClass, emptyIds));
        assertThrows(TopicClassNotFoundException.class
                , () -> dataRepository.selectByIds(nullClass, noIds));
        assertThrows(TopicClassNotFoundException.class
                , () -> dataRepository.selectByIds(nullClass, nullIds));
    }

}