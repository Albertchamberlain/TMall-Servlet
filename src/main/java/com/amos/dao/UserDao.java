package com.amos.dao;

import com.amos.bean.User;

import java.util.List;

/**
 * @author Amos
 * @date 8/5/2020 8:40 PM
 */
public interface UserDao {
    /**
     * get total user
     * @return
     */
   int getTotal();

    /**
     * add user
     * @param user
     */
    void add(User user);

    /**
     * update user
     * @param user
     */
    void update(User user);

    /**
     * delete user by id
     * @param id
     */
    void delete(Integer id);

    /**
     * query user with page
     * @param start
     * @param count
     * @return List<User>
     */
    List<User> list(Integer start , Integer  count);

    /**
     * get user by id
     * @param id
     * @return user
     */
    User get(Integer  id);

    /**
     * get user by name
     * @param name
     * @return user
     */
    User get(String name);

    /**
     * get user by name&password
     * @param name
     * @param password
     * @return user
     */
    User get(String name, String password);
}
