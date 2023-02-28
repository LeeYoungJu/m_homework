package kr.co._29cm.m_homework.entity;

import kr.co._29cm.m_homework.exception.SoldOutException;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Product implements BaseEntity {
    private String id;
    private String name;
    private int price;
    private int stockAmt;

    public BaseEntity decreaseStockAmt(int decreaseAmt) throws SoldOutException {
        if(this.stockAmt < decreaseAmt) {
            throw new SoldOutException();
        }
        this.stockAmt -= decreaseAmt;

        return this;
    }
}
