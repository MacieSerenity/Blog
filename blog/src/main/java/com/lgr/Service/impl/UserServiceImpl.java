package com.lgr.service.impl;

import com.lgr.dao.UserRepository;
import com.lgr.service.UserService;
import com.lgr.po.User;
import com.util.MD5Utils;
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
        User user=userRepository.findUserByUsernameAndPassword(username,MD5Utils.code(password));
        return user;
    }
}
