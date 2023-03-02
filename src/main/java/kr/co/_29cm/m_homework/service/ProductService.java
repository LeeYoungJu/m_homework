package kr.co._29cm.m_homework.service;

import kr.co._29cm.m_homework.entity.Product;
import kr.co._29cm.m_homework.exception.EntityNotFoundException;
import kr.co._29cm.m_homework.repository.BaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * 상품과 관련된 비즈니스 로직이 구현되는 클래스
 */
@Component
@RequiredArgsConstructor
public class ProductService {
    private final BaseRepository baseRepository;

    /**
     * 책임 : 상품 토픽의 컬림명 리스트 반환
     * @return 상품 컬림명 리스트
     */
    public List<String> getProductColNames() {
        return baseRepository.selectColNames(Product.class);
    }

    /**
     * 책임 : 상품 토픽의 모든 데이터 리스트 조회
     * @return 상품 전체 데이터 리스트
     */
    public List<Product> getAllProducts() {
        return baseRepository.selectAll(Product.class);
    }

    /**
     * 책임 : id를 이용해 상품객체 한개를 찾아서 반환
     * @param id 상품번호(String)
     * @return 상품객체
     */
    public Product getProductById(String id) throws EntityNotFoundException {

        return baseRepository.selectOneById(Product.class, id);
    }

    /**
     * 책임 : 상품번호가 유효한 번호인지 체크
     * @param id 상품번호(String)
     * @return 유효하면 true 아니면 false
     */
    public boolean isProductIdValidate(String id) {
        try {
            baseRepository.selectOneById(Product.class, id);
            return true;
        } catch (EntityNotFoundException e) {
            return false;
        }
    }
}
