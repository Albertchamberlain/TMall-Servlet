package com.amos.dao;

import com.amos.bean.Category;

import java.util.List;

/**
 * @author Amos
 * @date 8/5/2020 8:56 PM
 */
public interface CategoryDao {
    /**
     * get total category
     * @return
     */
    int getTotal();

    /**
     * add category
     * @param category
     */
    void add(Category category);

    /**
     * update category
     * @param category
     */
    void update(Category category);

    /**
     * delete category by id
     * @param id
     */
    void delete(Integer id);

    /**
     * get category by id
     * @param id
     * @return
     */
    Category get(Integer id);

    /**
     * get some category with limit
     * @param start
     * @param count
     * @return  List<Category>
     */
    List<Category> list(Integer start , Integer count);
}
