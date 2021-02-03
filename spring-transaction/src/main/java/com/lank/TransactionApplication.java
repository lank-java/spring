package com.lank;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author lank
 * @since 2021/2/2 17:24
 */
@SpringBootApplication
@MapperScan("com.lank.mapper")
@EnableTransactionManagement
public class TransactionApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransactionApplication.class);
    }
}
