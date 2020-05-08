package com.lgr.dao;

import com.lgr.po.User;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Created with IntelliJ IDEA.
 * User: MacieSerenity
 * Date: 2020/05/07 22:57
 * Description:
 * Version: V1.0
 */
//@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findUserByUsernameAndPassword(String username,String password);
}
