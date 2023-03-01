package kr.co._29cm.m_homework.database.dataList;

import kr.co._29cm.m_homework.database.DataListBuilder;
import kr.co._29cm.m_homework.entity.BaseEntity;
import kr.co._29cm.m_homework.entity.DeliveryInfo;
import kr.co._29cm.m_homework.entity.DeliveryInfoColumn;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 배달 정보 데이터가 메모리 저장소에 올라갈 형태를 결정해주는 클래스
 */
@Component
public class DeliveryInfoListBuilder implements DataListBuilder {
    @Override
    public String getTopic() {
        return DeliveryInfo.class.getSimpleName();
    }

    @Override
    public List<BaseEntity> buildList(List<String[]> rows) {
        return rows.stream().skip(1).map(row -> DeliveryInfo.builder()
                .id(row[DeliveryInfoColumn.ID.ordinal()])
                .charge(Integer.parseInt(row[DeliveryInfoColumn.CHARGE.ordinal()]))
                .chargeFreePrice(Integer.parseInt(row[DeliveryInfoColumn.CHARGE_FREE_PRICE.ordinal()]))
                .build()
        ).collect(Collectors.toList());
    }
}
