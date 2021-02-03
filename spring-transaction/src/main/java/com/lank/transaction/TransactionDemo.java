package com.lank.transaction;

import com.lank.mapper.UserMapper;
import com.lank.model.UserModel;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.function.Consumer;

/**
 * @author lank
 * @since 2021/2/2 12:02
 * 声明式事务
 */
@Component
public class TransactionDemo {

    @Autowired
    private TransactionTemplate transactionTemplate;

    /**
     * 平台事务管理器，spring事务管理的核心。
     */
    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private UserMapper userMapper;


    public void testTransactionTemplate() {

        transactionTemplate.executeWithoutResult(new Consumer<TransactionStatus>() {
            @Override
            public void accept(TransactionStatus transactionStatus) {
                try {
                    // 业务逻辑
                    UserModel userModel1 = userMapper.selectById(1);
                    UserModel userModel2 = userMapper.selectById(2);
                    //用户1转账100给用户2
                    userModel1.setMoney(userModel1.getMoney() - 100);
                    userMapper.updateById(userModel1);
                    // 模拟异常
                    int i = 1 / 0;
                    userModel2.setMoney(userModel2.getMoney() + 100);
                    userMapper.updateById(userModel2);

                } catch (Exception e) {
                    e.printStackTrace();
                    transactionStatus.setRollbackOnly();
                }
            }
        });
    }

    public void testTransactionManager() {

        //事务属性
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        // 事务运行状态
        TransactionStatus transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);
        try {
            // 业务逻辑
            UserModel userModel1 = userMapper.selectById(1);
            UserModel userModel2 = userMapper.selectById(2);
            //用户1转账100给用户2
            userModel1.setMoney(userModel1.getMoney() - 100);
            userMapper.updateById(userModel1);
            // 模拟异常
            // int i = 1 / 0;
            userModel2.setMoney(userModel2.getMoney() + 100);
            userMapper.updateById(userModel2);
            //提交事务
            transactionManager.commit(transactionStatus);
        } catch (Exception e) {
            e.printStackTrace();
            transactionManager.rollback(transactionStatus);
        }

    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void testPropagation() {
        // 业务逻辑
        UserModel userModel1 = userMapper.selectById(1);
        UserModel userModel2 = userMapper.selectById(2);
        //用户1转账100给用户2
        userModel1.setMoney(userModel1.getMoney() - 100);
        userMapper.updateById(userModel1);
        // 模拟异常
        int i = 1 / 0;
        userModel2.setMoney(userModel2.getMoney() + 100);
        userMapper.updateById(userModel2);
    }


    public void withoutTransaction() {
        UserModel userModel1 = userMapper.selectById(1);
        UserModel userModel2 = userMapper.selectById(2);
        //用户1转账100给用户2
        userModel1.setMoney(userModel1.getMoney() - 100);
        userMapper.updateById(userModel1);
        // 模拟异常
        int i = 1 / 0;
        userModel2.setMoney(userModel1.getMoney() + 100);
        userMapper.updateById(userModel1);
    }


}
