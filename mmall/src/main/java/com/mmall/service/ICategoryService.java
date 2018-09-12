package com.mmall.service;

import java.util.List;

import com.mmall.commons.ServerResponse;

public interface ICategoryService {

    ServerResponse addCategory(String categoryName , Integer parentId);

    ServerResponse setCategoryName(Integer categoryId, String categoryName);

    ServerResponse getChildrenParallelCategory(Integer parentId);

    ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId);
}
