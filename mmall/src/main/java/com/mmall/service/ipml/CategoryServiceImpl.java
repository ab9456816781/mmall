package com.mmall.service.ipml;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mmall.commons.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.pojo.Category;
import com.mmall.service.ICategoryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CategoryServiceImpl implements ICategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryMapper categoryMapper ;

    @Override
    public ServerResponse addCategory(String categoryName, Integer parentId) {
        if (parentId == null || StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMessage("添加分类管理参数错误");
        }
        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(true);
        int count = categoryMapper.insert(category);
        if (count > 0){
            return ServerResponse.createBySuccess("添加分类成功");
        }
        return ServerResponse.createByErrorMessage("添加失败");
    }

    @Override
    public ServerResponse setCategoryName(Integer categoryId, String categoryName) {
        if (categoryId == null || StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMessage("更新分类管理参数错误");
        }
        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);

        int count = categoryMapper.updateByPrimaryKeySelective(category);
        if (count>0){
            return ServerResponse.createBySuccess("更新分类名称成功");
        }
        return ServerResponse.createByErrorMessage("更新失败");
    }

    @Override
    public ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId) {
        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        if (CollectionUtils.isEmpty(categoryList)){
            //打个日志
            logger.info("未查到当前分类的子分类");
        }
        return ServerResponse.createBySuccess(categoryList);
    }

    @Override
    public ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId) {
        Set<Category> categorySet = Sets.newHashSet();
        findChildCategory(categorySet , categoryId);

        List<Integer> categoryList = Lists.newArrayList();
        for (Category categoryItem : categorySet){
            categoryList.add(categoryItem.getId());
        }
        return ServerResponse.createBySuccess(categoryList);
    }

    private Set<Category> findChildCategory(Set<Category> categorySet , Integer categoryId){
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category != null){
            categorySet.add(category);
        }
        //递归算法， 查询子节点  一定有个退出 条件
        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        //mybatis 将list 转换
        if (categoryList != null){
            for (Category categoryItem : categoryList){
                findChildCategory(categorySet , categoryItem.getId());
            }
        }
        return categorySet;
    }

}
