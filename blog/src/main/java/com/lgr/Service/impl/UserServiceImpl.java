package com.lgr.Service.impl;

import com.lgr.Dao.UserRepository;
import com.lgr.Service.UserService;
import com.lgr.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: MacieSerenity
 * Date: 2020/05/07 22:56
 * Description:0
 * .
 * Version: V1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public User checkUser(String username, String password) {
        User user=userRepository.findUserByUsernameAndPassword(username,password);
        return user;
    }
}
