package com.lank.transaction;

import com.lank.service.UserService;
import com.lank.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author lank
 * @since 2021/2/2 17:39
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TransactionDemoTest {

    @Autowired
    private TransactionDemo transactionDemo;

    @Autowired
    private UserService userService;

    @Test
    public void testTransactionTemplate() {
        transactionDemo.testTransactionTemplate();
    }

    @Test
    public void testTransactionManager() {
        transactionDemo.testTransactionManager();
    }

    @Test
    public void testWithoutTransaction() {
        transactionDemo.withoutTransaction();
    }

    @Test
    public void testUserService() {
        //userService.aMethod();
        transactionDemo.testPropagation();
    }
}