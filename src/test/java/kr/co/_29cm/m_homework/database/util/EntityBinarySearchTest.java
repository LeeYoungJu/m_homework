package kr.co._29cm.m_homework.database.util;

import kr.co._29cm.m_homework.entity.BaseEntity;
import kr.co._29cm.m_homework.entity.Product;
import kr.co._29cm.m_homework.exception.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("데이터 이진탐색 클래스 테스트")
class EntityBinarySearchTest {

    @Test
    @DisplayName("BinarySearch class 기능 테스트")
    void binarySearchMultiTargetTest() {
        List<BaseEntity> entities = Arrays.asList(
                Product.builder().id("111").name("product1").price(3000).stockAmt(10).build(),
                Product.builder().id("333").name("product3").price(3000).stockAmt(10).build(),
                Product.builder().id("222").name("product2").price(3000).stockAmt(10).build(),
                Product.builder().id("888").name("product8").price(3000).stockAmt(10).build(),
                Product.builder().id("777").name("product7").price(3000).stockAmt(10).build()
        );

        // id를 기준으로 내림차순으로 정렬
        Comparator<BaseEntity> comparator = Comparator.comparing(BaseEntity::getId);
        List<BaseEntity> sortedProducts = entities.stream()
                .sorted(comparator.reversed()).collect(Collectors.toList());

        // 찾을 id 리스트
        List<String> ids = Arrays.asList("111", "222", "777");

        EntityBinarySearch entityBinarySearch = new EntityBinarySearch(sortedProducts);
        List<Integer> indexes = entityBinarySearch.binarySearchMultiTarget(ids);
        assertEquals(4, indexes.get(0));
        assertEquals(3, indexes.get(1));
        assertEquals(1, indexes.get(2));

        List<String> id = Arrays.asList("333");
        indexes = entityBinarySearch.binarySearchMultiTarget(id);
        assertEquals(2, indexes.get(0));

        List<String> errorId = Arrays.asList("111", "333", "98989");
        assertThrows(EntityNotFoundException.class
                , () -> entityBinarySearch.binarySearchMultiTarget(errorId));
    }
}