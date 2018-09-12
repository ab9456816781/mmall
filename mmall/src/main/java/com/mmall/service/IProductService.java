package com.mmall.service;

import com.mmall.commons.ServerResponse;
import com.mmall.pojo.Product;

public interface IProductService {
    ServerResponse saveOrUpdateProduct(Product product);

    ServerResponse setSaleStatus(Integer productId, Integer status);

    ServerResponse getDetail(Integer productId);

    ServerResponse getProductList(Integer pageNum, Integer pageSize);

    ServerResponse searchProduct(String productName, Integer productId, Integer pageNum, Integer pageSize);

    ServerResponse getProductDetail(Integer productId);

    ServerResponse getProductByKeyWord(Integer categoryId, String keyword, Integer pageNum, Integer pageSize, String orderBy);
}
