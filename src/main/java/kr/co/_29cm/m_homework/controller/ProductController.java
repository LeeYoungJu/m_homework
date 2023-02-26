package kr.co._29cm.m_homework.controller;

import kr.co._29cm.m_homework.database.exception.SoldOutException;
import kr.co._29cm.m_homework.entity.Order;
import kr.co._29cm.m_homework.entity.Product;
import kr.co._29cm.m_homework.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    public List<String> getColNames() {
        return productService.getColNames();
    }

    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    public boolean isProductIdValidate(String id) {
        return productService.isProductIdValidate(id);
    }

    public boolean isStockAmtOk(Order order) throws SoldOutException {
        return productService.isStockAmtOk(order);
    }

    public void pay() {

    }
}
