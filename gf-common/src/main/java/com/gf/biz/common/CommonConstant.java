package com.gf.biz.common;

/*
 *    Copyright (c) 2018-2025, Gf All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the XX科技 developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: Gf (1414798079@qq.com)
 */


/**
 * @author Gf
 * @date 2017/10/29
 */
public interface CommonConstant {
    /**
     * token请求头名称
     */
    String REQ_HEADER = "Authorization";

    /**
     * token分割符
     */
    String TOKEN_SPLIT = "Bearer ";

    /**
     * jwt签名
     */
    String SIGN_KEY = "PIG";

    /**
     * 删除
     */
    String STATUS_DEL = "1";

    /**
     * 删除
     */
    String STATUS_UN_DEL = "0";

    /**
     * 删除
     */
    Integer INT_STATUS_DEL = 1;

    /**
     * 正常
     */
    String STATUS_NORMAL = "1";

    /**
     * 正常
     */
    String STATUS_UN_NORMAL = "0";

    /**
     * 正常
     */
    Integer INT_STATUS_NORMAL = 0;

    /**
     * 锁定
     */
    String STATUS_LOCK = "9";

    /**
     * 菜单
     */
    String MENU = "0";

    /**
     * 按钮
     */
    String BUTTON = "1";

    /**
     * 删除标记
     */
    String COLUMN_DEL_FLAG = "deleted_flag";

    String COLUMN_ID = "id";

    /**
     * 是否删除标记（0否 1是）
     */
    String IS_DELETE = "is_delete";

    /**
     * 编码
     */
    String UTF8 = "UTF-8";

    /**
     * JSON 资源
     */
    String CONTENT_TYPE = "application/json; charset=utf-8";

    /**
     * 阿里大鱼
     */
    String ALIYUN_SMS = "aliyun_sms";

    /**
     * 路由信息Redis保存的key
     */
    String ROUTE_KEY = "_ROUTE_KEY";

    /**
     * 数组符号
     */
    String ARRAY_SYMBOL = "[]";

    String DEFAULT_OPT_USER="SYS";

    String DATASOURCE_BIZ_1= "biz_1";

    String DATASOURCE_MASTER= "master";
}
