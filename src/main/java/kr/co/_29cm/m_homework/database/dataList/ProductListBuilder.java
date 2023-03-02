package kr.co._29cm.m_homework.database.dataList;

import kr.co._29cm.m_homework.database.DataListBuilder;
import kr.co._29cm.m_homework.entity.BaseEntity;
import kr.co._29cm.m_homework.entity.Product;
import kr.co._29cm.m_homework.entity.ProductColumn;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 상품 데이터가 메모리 저장소에 올라갈 형태를 결정해주는 클래스
 */
@Component
public class ProductListBuilder implements DataListBuilder {
    @Override
    public String getTopic() {
        return Product.class.getSimpleName();
    }

    @Override
    public List<BaseEntity> buildList(List<String[]> rows) {
        // 정렬은 상품번호 기준 내림차순으로 한다.(최신 상품을 위로)
        return rows.stream().skip(1).map(row -> Product.builder()
                .id(row[ProductColumn.ID.ordinal()])
                .name(row[ProductColumn.NAME.ordinal()])
                .price(Integer.parseInt(row[ProductColumn.PRICE.ordinal()]))
                .stockAmt(Integer.parseInt(row[ProductColumn.STOCK_AMT.ordinal()]))
                .build()
        ).sorted(Comparator.comparing(Product::getId).reversed()).collect(Collectors.toList());
    }
}
