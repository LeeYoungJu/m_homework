package kr.co._29cm.m_homework.service;

import kr.co._29cm.m_homework.repository.DataRepository;
import kr.co._29cm.m_homework.database.DataTopic;
import kr.co._29cm.m_homework.database.exception.IllegalTopicException;
import kr.co._29cm.m_homework.database.exception.NoDataException;
import kr.co._29cm.m_homework.entity.Order;
import kr.co._29cm.m_homework.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductService {

    private String topic = DataTopic.PRODUCT;

    private final DataRepository dataRepository;

    public List<String> getColNames() {
        try {
            return dataRepository.getColNames(Product.class);
        } catch (IllegalTopicException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Product> getAllProducts() {
        try {
            return dataRepository.getAll(Product.class);
        } catch (IllegalTopicException e) {
            throw new RuntimeException(e);
        }
    }

    public Product getProductById(String id) {
        try {
            return dataRepository.getObjectById(Product.class, id);
        } catch (NoDataException e) {
            throw new RuntimeException(e);
        } catch (IllegalTopicException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isProductIdValidate(String id) {
        try {
            dataRepository.getObjectById(Product.class, id);
            return true;
        } catch (NoDataException e) {
            return false;
        } catch (IllegalTopicException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isStockAmtOk(Order order) {
        return order.getOrderProducts().stream().map(orderProduct -> {
            Product product = getProductById(orderProduct.getProductId());
            return product.getStockAmt() > orderProduct.getOrderAmt();
        }).allMatch(isOk -> isOk);
    }
}
