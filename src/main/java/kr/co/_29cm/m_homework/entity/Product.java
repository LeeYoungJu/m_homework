package kr.co._29cm.m_homework.entity;

import kr.co._29cm.m_homework.exception.SoldOutException;
import lombok.Builder;
import lombok.Getter;

/**
 * <pre>
 * 상품 엔티티
 * 메모리 저장소에 상품 데이터가 저장되는 형태
 * </pre>
 */
@Getter
@Builder
public class Product implements BaseEntity {

    // 상품번호
    private String id;

    // 상품명
    private String name;

    // 상품가격
    private int price;

    // 상품 재고량
    private int stockAmt;

    /**
     * <pre>
     * 주문이 체결될 떄 상품 재고량을 감소시키는 함수
     * 만약 재고량이 부족하면 SoldOutException 예외를 발생시킨다.
     * </pre>
     * @param decreaseAmt 감소할 재고량
     * @return 재고량이 감소된 상품 객체
     * @throws SoldOutException
     */
    public BaseEntity decreaseStockAmt(int decreaseAmt) throws SoldOutException {
        if(this.stockAmt < decreaseAmt) {
            throw new SoldOutException();
        }
        this.stockAmt -= decreaseAmt;

        return this;
    }
}
