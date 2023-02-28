package kr.co._29cm.m_homework.service;

import kr.co._29cm.m_homework.entity.DeliveryInfo;
import kr.co._29cm.m_homework.repository.DataRepository;
import kr.co._29cm.m_homework.service.consts.DeliveryType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryInfoService {
    private final DataRepository dataRepository;

    public DeliveryInfo getDeliveryCharge(String deliveryType) {
        return dataRepository.selectOneById(DeliveryInfo.class, deliveryType);
    }
}
