package kr.co._29cm.m_homework.database;

import kr.co._29cm.m_homework.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataVirtualStorage {

    private static List<String> productColNames;
    private static List<Product> products;

    public void setProductColNames(List<String> colNames) {
        DataVirtualStorage.productColNames = colNames;
    }

    public void setProducts(List<Product> products) {
        DataVirtualStorage.products = products;
    }

    public List<String> getProductColNames() {
        return DataVirtualStorage.productColNames;
    }

    public List<Product> getProducts() {
        return DataVirtualStorage.products;
    }
}
