package com.lgr.service;

import com.lgr.po.User;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: MacieSerenity
 * Date: 2020/05/07 22:54
 * Description:
 * Version: V1.0
 */
@Service
public interface UserService {
    User checkUser(String username,String password);
}
