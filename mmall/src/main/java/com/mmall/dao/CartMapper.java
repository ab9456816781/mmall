package com.mmall.dao;

import com.mmall.pojo.Cart;

import java.util.List;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    /**判断字段是否为空，不为空则set**/
    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    /**字段 空判断**/
    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    List<Cart> selectByUserId(Integer userId);
}