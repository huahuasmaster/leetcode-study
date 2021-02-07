package com.zyz.study.es;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author 执零
 * @version 1.0
 * @date 2021/2/6 23:45
 */
@SpringBootTest
class OrderAppTest {
    @Autowired
    private OrderApp orderApp;

    @Test
    void newOrder() {
        orderApp.newOrder();
    }

    @Test
    void updateOrder() {
        orderApp.updateOrder();
    }

    @Test
    void deleteOrder() {
        orderApp.deleteOrder();
    }
}