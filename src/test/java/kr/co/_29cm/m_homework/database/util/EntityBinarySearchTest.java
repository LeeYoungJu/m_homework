package kr.co._29cm.m_homework.database.util;

import kr.co._29cm.m_homework.entity.BaseEntity;
import kr.co._29cm.m_homework.entity.Product;
import kr.co._29cm.m_homework.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class EntityBinarySearchTest {

    @Test
    void binarySearchMultiTargetTest() {
        BaseEntity e1 = Product.builder().id("111").name("product1").price(3000).stockAmt(10).build();
        BaseEntity e3 = Product.builder().id("333").name("product3").price(3000).stockAmt(10).build();
        BaseEntity e2 = Product.builder().id("222").name("product2").price(3000).stockAmt(10).build();
        BaseEntity e8 = Product.builder().id("888").name("product8").price(3000).stockAmt(10).build();
        BaseEntity e7 = Product.builder().id("777").name("product7").price(3000).stockAmt(10).build();

        List<BaseEntity> entities = new ArrayList<>();
        entities.add(e1);
        entities.add(e3);
        entities.add(e2);
        entities.add(e8);
        entities.add(e7);

        Comparator<BaseEntity> comparator = Comparator.comparing(BaseEntity::getId);

        List<BaseEntity> sortedProducts = entities.stream().sorted(comparator).collect(Collectors.toList());

        List<String> ids = Arrays.asList("111", "222", "777");

        EntityBinarySearch entityBinarySearch = new EntityBinarySearch(sortedProducts);
        List<Integer> indexes = entityBinarySearch.binarySearchMultiTarget(entities, ids);
        assertEquals(0, indexes.get(0));
        assertEquals(1, indexes.get(1));
        assertEquals(3, indexes.get(2));

        List<String> id = Arrays.asList("333");
        indexes = entityBinarySearch.binarySearchMultiTarget(entities, id);
        assertEquals(2, indexes.get(0));

        List<String> errorId = Arrays.asList("111", "333", "98989");
        assertThrows(EntityNotFoundException.class
                , () -> entityBinarySearch.binarySearchMultiTarget(entities, errorId));
    }
}