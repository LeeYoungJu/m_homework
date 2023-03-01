package kr.co._29cm.m_homework.service;

import kr.co._29cm.m_homework.entity.DeliveryInfo;
import kr.co._29cm.m_homework.repository.DataRepository;
import kr.co._29cm.m_homework.service.consts.DeliveryType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 배달정보와 관련된 비즈니스 로직이 구현되는 클래스
 */
@Component
@RequiredArgsConstructor
public class DeliveryInfoService {
    private final DataRepository dataRepository;

    /**
     * 배송비, 배송면제금액 등의 배송 정보를 반환한다.
     * @param deliveryType 배송 종류(general: 일반배송, special: 특별배송)
     * @return 배송정보 객체
     */
    public DeliveryInfo getDeliveryCharge(String deliveryType) {
        return dataRepository.selectOneById(DeliveryInfo.class, deliveryType);
    }
}
