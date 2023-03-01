package kr.co._29cm.m_homework.controller;

import kr.co._29cm.m_homework.entity.Product;
import kr.co._29cm.m_homework.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 사용자 화면과 ProductService 기능을 중간에서 매핑해주는 Controller 클래스
 */
@Component
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    public List<String> getProductColNames() {
        return productService.getProductColNames();
    }

    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    public Product getProductById(String id) {
        return productService.getProductById(id);
    }

    public boolean isProductIdValidate(String id) {
        return productService.isProductIdValidate(id);
    }
}
