package kr.co._29cm.m_homework.service;

import kr.co._29cm.m_homework.Main;
import kr.co._29cm.m_homework.database.DataLoaderFactory;
import kr.co._29cm.m_homework.entity.Product;
import kr.co._29cm.m_homework.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("상품 서비스 테스트")
class ProductServiceTest {
    private static ProductService productService;
    @BeforeAll
    static void setup() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(Main.class);
        applicationContext.refresh();

        DataLoaderFactory dataLoaderFactory = applicationContext.getBean(DataLoaderFactory.class);
        dataLoaderFactory.getLoader().loadDataOnMemory();

        productService = applicationContext.getBean(ProductService.class);
    }

    @Test
    @DisplayName("상품 컬럼명 조회 함수 테스트")
    void getProductColNamesTest() {
        List<String> productColNames = productService.getProductColNames();
        assertTrue(productColNames != null);
        assertTrue(productColNames.size() > 0);
    }

    @Test
    @DisplayName("상품 전체 조회 함수 테스트")
    void getAllProductsTest() {
        List<Product> allProducts = productService.getAllProducts();
        assertTrue(allProducts != null);
        assertTrue(allProducts.size() > 0);
        assertTrue(allProducts.get(0).getId().length() > 0);
        assertTrue(allProducts.get(0).getName().length() > 0);
        assertTrue(allProducts.get(0).getPrice() > 0);
    }

    @Test
    @DisplayName("특정 상품 조회 함수 테스트")
    void getProductByIdTest() {
        String id = "213341";
        String noId = "999999999";
        String emptyId = "";
        String nullId = null;

        Product product = productService.getProductById(id);
        assertTrue(product != null);
        assertTrue(product.getName().equals("20SS 오픈 카라/투 버튼 피케 티셔츠 (6color)"));

        assertThrows(EntityNotFoundException.class, () -> productService.getProductById(noId));
        assertThrows(EntityNotFoundException.class, () -> productService.getProductById(emptyId));
        assertThrows(EntityNotFoundException.class, () -> productService.getProductById(nullId));
    }

    @Test
    @DisplayName("상품번호 유효성 체크 함수 테스트")
    void productIdValidateTest() {
        String id = "213341";
        String noId = "999999999";
        String emptyId = "";
        String nullId = null;

        boolean normalRes = productService.isProductIdValidate(id);
        boolean noIdRes = productService.isProductIdValidate(noId);
        boolean emptyIdRes = productService.isProductIdValidate(emptyId);
        boolean nullIdRes = productService.isProductIdValidate(nullId);

        assertTrue(normalRes);
        assertFalse(noIdRes);
        assertFalse(emptyIdRes);
        assertFalse(nullIdRes);
    }

}