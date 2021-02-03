package com.lank.service.impl;

import com.lank.model.UserModel;
import com.lank.mapper.UserMapper;
import com.lank.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lank
 * @since 2021-02-02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserModel> implements UserService {


    @Autowired
    private UserMapper userMapper;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void aMethod(){

        UserModel userModel1 = userMapper.selectById(1);
        //用户1转账100给用户2
        userModel1.setMoney(userModel1.getMoney() - 100);
        userMapper.updateById(userModel1);

        bMethod();



    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void bMethod(){

        UserModel userModel2 = userMapper.selectById(2);
        userModel2.setMoney(userModel2.getMoney() + 100);
        userMapper.updateById(userModel2);
        int i = 1 /0;

    }

}
