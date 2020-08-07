package com.amos.service;

import com.amos.bean.User;
import com.amos.dao.UserDao;
import com.amos.dao.impl.UserDaoImpl;

import java.util.List;

/**
 * @author Amos
 * @date 8/7/2020 9:28 PM
 */
public class UserService {
    private UserDao dao = new UserDaoImpl();
    public int getTotal() {return dao.getTotal();}
    public void add(User user) {
        dao.add(user);
    }
    public void update(User user) {
        dao.update(user);
    }
    public void delete(int id) {
        dao.delete(id);
    }
    public User get(int id){
        return dao.get(id);
    }
    public User get(String name){
        return dao.get(name);
    }
    public User get(String name,String password){
        return dao.get(name,password);
    }
    public boolean passwordIsRight(String id,String password){
        return dao.get(id, password) != null;
    }
    public boolean isExist(String name){
        return dao.get(name) != null;
    }
    public List<User> list(int start , int count){
        return dao.list(start,count);
    }
    public List<User> list(){
        return dao.list(0, (int) Short.MAX_VALUE);
    }
}
