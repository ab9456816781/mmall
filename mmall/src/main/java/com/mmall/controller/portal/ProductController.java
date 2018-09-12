package com.mmall.controller.portal;

import com.mmall.commons.ServerResponse;
import com.mmall.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService productService;

    /**
     * 产品搜索及动态排序List
     * @param categoryId
     * @param keyword
     * @param orderBy
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse list(@RequestParam(value = "categoryId" , required = false) Integer categoryId,
                               @RequestParam(value = "keyword" , required = false) String keyword,
                               @RequestParam(value = "orderBy" ,defaultValue = "")String orderBy ,
                               @RequestParam(value = "pageNum" ,defaultValue = "1")Integer pageNum ,
                               @RequestParam(value = "pageSize" ,defaultValue = "10")Integer pageSize){
        return productService.getProductByKeyWord(categoryId , keyword , pageNum , pageSize , orderBy);
    }


    /**
     * 查看详情
     * @param productId
     * @return
     */
    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse detail(Integer productId){
        return productService.getProductDetail(productId);
    }


}
