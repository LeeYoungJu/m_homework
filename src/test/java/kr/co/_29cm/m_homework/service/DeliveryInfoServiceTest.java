package kr.co._29cm.m_homework.service;

import kr.co._29cm.m_homework.Main;
import kr.co._29cm.m_homework.database.DataLoaderFactory;
import kr.co._29cm.m_homework.entity.DeliveryInfo;
import kr.co._29cm.m_homework.exception.EntityNotFoundException;
import kr.co._29cm.m_homework.service.consts.DeliveryType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("배달정보 서비스 테스트")
class DeliveryInfoServiceTest {
    private static DeliveryInfoService deliveryInfoService;

    @BeforeAll
    static void setup() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(Main.class);
        applicationContext.refresh();

        DataLoaderFactory dataLoaderFactory = applicationContext.getBean(DataLoaderFactory.class);
        dataLoaderFactory.getLoader().loadDataOnMemory();

        deliveryInfoService = applicationContext.getBean(DeliveryInfoService.class);
    }

    @Test
    @DisplayName("배달정보 조회 함수 테스트")
    void getDeliveryInfoTest() {
        String general = DeliveryType.GENERAL;
        String emptyType = "";
        String nullType = null;

        DeliveryInfo generalDelivery = deliveryInfoService.getDeliveryInfo(general);
        assertEquals(DeliveryType.GENERAL, generalDelivery.getId());
        assertEquals(2500, generalDelivery.getCharge());
        assertEquals(50000, generalDelivery.getChargeFreePrice());

        assertThrows(EntityNotFoundException.class
                , () -> deliveryInfoService.getDeliveryInfo(emptyType));
        assertThrows(EntityNotFoundException.class
                , () -> deliveryInfoService.getDeliveryInfo(nullType));

    }

}