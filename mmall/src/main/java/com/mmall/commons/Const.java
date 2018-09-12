package com.mmall.commons;

import java.util.Set;

import com.google.common.collect.Sets;

public class Const {
    public static final String CURRENT_USER = "currentUser";

    public static final String EMAIL = "email";

    public static final String USERNAME = "username";

    public interface Role{
        int ROLE_CUSTOMER = 0;//普通用户、
        int ROLE_ADMIN = 1;//管理员
    }

    public enum ProductStatusEnum{
        NO_SALE(1,"在线")
        ;
        private int code;
        private String msg;

        ProductStatusEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }
        public int getCode() {
            return code;
        }
        public String getMsg() {
            return msg;
        }
    }

    public interface ProductListOrderBy{
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc" , "price_asc");
    }




}
