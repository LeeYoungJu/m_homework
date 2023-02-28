package kr.co._29cm.m_homework.database.dataList;

import kr.co._29cm.m_homework.database.DataListBuilder;
import kr.co._29cm.m_homework.entity.BaseEntity;
import kr.co._29cm.m_homework.entity.Product;
import kr.co._29cm.m_homework.entity.ProductColumn;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductListBuilder implements DataListBuilder {
    @Override
    public String getTopic() {
        return Product.class.getSimpleName();
    }

    @Override
    public List<BaseEntity> buildList(List<String[]> rows) {
        return rows.stream().skip(1).map(row -> Product.builder()
                .id(row[ProductColumn.ID.ordinal()])
                .name(row[ProductColumn.NAME.ordinal()])
                .price(Integer.parseInt(row[ProductColumn.PRICE.ordinal()]))
                .stockAmt(Integer.parseInt(row[ProductColumn.STOCK_AMT.ordinal()]))
                .build()
        ).sorted(Comparator.comparing(Product::getId).reversed()).collect(Collectors.toList());
    }
}
