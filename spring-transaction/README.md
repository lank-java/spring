## Spring事务

Spring事务机制是通过统一的机制来处理不同数据访问技术的事务处理。Spring事务机制提供了一个platformTransactionManager接口，不同的数据库有不同的实现。jdbc的实现为DataSourceTransactionManager。

Spring支持两种方式的事务管理：

### 编程式事务

通过 TransactionTemplate或者TransactionManager手动管理事务，不常用。代码实现如下：

```java
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
```

### 声明式事务

Spring事务的传播行为（七种）- Propagation：

- REQUIRED：使用当前事务，如果没有事务，则新建一个，子方法必须运行在一个事务中。如果当前存在一个事务则加入该事务，称为一个整体。（**默认传播行为**）
- SUPPORTS：如果当前有事务，则使用当前事务。没有则不使用事务。
- MANDATORY：支持当前事务，如果不存在事务则抛出一个异常。
- REQUIREDS_NEW：创建一个新事务，如果当前存在事务则挂起当前事务。当前不存在事务则同REQUIRED。
- NOT_SUPPORTED：以非事务的方式执行，如果当前存在事务，则挂起当前事务。
- NEVER：以非事务的方式执行，如果当前存在事务，则抛出异常。
- NESTED：如果当前存在事务，则开启子事务（嵌套事务）。嵌套事务是独立提交或者回滚的，如果当前没有事务，则同REQUIRED。如果主事务提交，则会携带子事务一起提交。如果主事务回滚，则会携带子事务一起回滚。相反，子事务回滚，主事务则可以回滚，也可以不回滚。

```java
//在方法上加@Transactional注解
    @Transactional(propagation = Propagation.REQUIRED)
    public void testPropagation(){
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
```

> 数据库的事务的提交和回滚是通过binlog和redo log实现。