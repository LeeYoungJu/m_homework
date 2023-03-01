package kr.co._29cm.m_homework.service;

import kr.co._29cm.m_homework.Main;
import kr.co._29cm.m_homework.controller.OrderController;
import kr.co._29cm.m_homework.controller.ProductController;
import kr.co._29cm.m_homework.database.DataLoaderFactory;
import kr.co._29cm.m_homework.entity.Order;
import kr.co._29cm.m_homework.entity.OrderProduct;
import kr.co._29cm.m_homework.entity.Product;
import kr.co._29cm.m_homework.exception.SoldOutException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;


class OrderServiceTest {

    private static AnnotationConfigApplicationContext applicationContext;
    private static OrderService orderService;
    private static ProductService productService;

    @BeforeAll
    static void setup() {
        applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(Main.class);
        applicationContext.refresh();

        DataLoaderFactory dataLoaderFactory = applicationContext.getBean(DataLoaderFactory.class);
        dataLoaderFactory.getLoader().loadDataOnMemory();

        orderService = applicationContext.getBean(OrderService.class);
        productService = applicationContext.getBean(ProductService.class);
    }

    @Test
    @DisplayName("멀티유저 동시결제 테스트")
    void multiThreadPayTest() throws InterruptedException {
        String productId = "778422";     // 수량이 7개인 상품을
        int numOfThreads = 2;            // 유저 2명이 동시에
        int orderAmt = 4;                // 4개를 주문/결제하면 한명은 SoldOutException 예외 발생
        int remainedAmt = 3;             // 결과적으로 3개의 상품이 남아있어야함.

        Product product1 = productService.getProductById(productId);
        OrderProduct orderProduct = OrderProduct.builder()
                .product(product1)
                .orderAmt(orderAmt)
                .build();

        Order order = orderService.createOrder();
        order.addProduct(orderProduct);

        ExecutorService service = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(numOfThreads);
        final List<Boolean> soldOutYn = Arrays.asList(false);
        for(int i=0; i<numOfThreads; i++) {
            service.submit(() -> {
                try {
                    orderService.payFor(order);
                } catch (SoldOutException e) {
                    soldOutYn.set(0, true);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        assertTrue(soldOutYn.get(0));

        Product afterOrderProduct = productService.getProductById(productId);
        assertEquals(remainedAmt, afterOrderProduct.getStockAmt());
    }
}