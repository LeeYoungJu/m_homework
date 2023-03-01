package kr.co._29cm.m_homework.service;

import kr.co._29cm.m_homework.database.DataVirtualStorage;
import kr.co._29cm.m_homework.entity.BaseEntity;
import kr.co._29cm.m_homework.entity.Product;
import kr.co._29cm.m_homework.repository.DataRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {
    private DataVirtualStorage dataVirtualStorage;
    private DataRepository dataRepository;
    private ProductService productService;

    @BeforeAll
    void setup() {
        List<String> colNames = Arrays.asList(
                "상품번호", "상품명", "가격", "재고"
        );
        List<BaseEntity> dataList = Arrays.asList(
                Product.builder().id("748943").name("디오디너리").price(19000).stockAmt(89).build(),
                Product.builder().id("759928").name("마스크 스트랩").price(2800).stockAmt(85).build(),
                Product.builder().id("744775").name("SHUT UP [TK00112]").price(28000).stockAmt(35).build()
        );

        dataVirtualStorage = new DataVirtualStorage();
        dataVirtualStorage.setColumnMap(Product.class.getSimpleName(), colNames);
        dataVirtualStorage.setDataMap(Product.class.getSimpleName(), dataList);

        dataRepository = new DataRepository(dataVirtualStorage);

        productService = new ProductService(dataRepository);
    }



}