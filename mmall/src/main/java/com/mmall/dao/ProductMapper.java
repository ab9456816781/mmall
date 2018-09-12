package com.mmall.dao;

import java.util.List;

import com.mmall.pojo.Product;
import org.apache.ibatis.annotations.Param;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> selectListByPage();

    List<Product> selectByNameAndProductId(@Param("productName") String productName, @Param("productId") Integer productId);


    List<Product> selectByNameAndCategoryIds(@Param("productName") String productName, @Param("categoryList") List<Integer> categoryList);
}