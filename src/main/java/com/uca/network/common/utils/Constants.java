package com.uca.network.common.utils;

public enum Constants {
    /**
     * http head 常量
     */
    HTTP_HEAD_X_AUTH_TOKEN("X-Auth-Token"), HTTP_HEAD_X_SUBJECT_TOKEN("X-Subject-Token"), HTTP_MSG_TYPE("application/json;charset=UTF-8"), DEFAULT_DOMAIN_ID("default");

    private String value;

    private Constants(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

    /**
     * 用户登录验证相关常量
     */
    public static final int LOGIN_LOCK_EXPIRED_TIME = 15;
    public static final int LOGIN_ERROR_SLIDE_TIME = 5;
    public static final int LOGIN_ERROR_LOCK_TIME = 10;
    public static final String LOGIN_ERROR_KEY_PREFIX = "login_err_key_prefix";

    public static final int TIME_OUT_SECONDS = 50;

    public static final String UNKNOWN = "unknown";

    public static final String IPV6_CIDR = "0:0:0:0:0:0:0:1";

    public static final int FOUR = 4;

    public static final int TWENTY_FOUR = 24;

    public static final int WEIGHT = 255;

    public static final int EIGHT = 8;

    public static final int THREE = 3;

    public static final int H255 = 255;

    public static final int TWO = 2;

    public static final String VERTICAL_LINE = "|";

    //license begin

    public static final String CLOUD_BASE = "CloudBase";

    public static final String MAX_AM_NUM = "MaxAmNum";

    public static final String D_BAA_S = "DBaaS";

    public static final String AAA_S = "AaaS";

    public static final String STR_32 = "32";

    public static final String IPV4 = "IPv4";

    public static final String IPV6 = "IPv6";

    public static final String STR_53 = "53";

    public static final String STR_80 = "80";

    public static final String STR_443 = "443";

    public static final String STR_143 = "143";

    public static final String STR_993 = "993";

    public static final String STR_1433 = "1433";

    public static final String STR_3306 = "3306";

    public static final String STR_110 = "110";

    public static final String STR_995 = "995";

    public static final String STR_3389 = "3389";

    public static final String STR_25 = "25";

    public static final String STR_465 = "465";

    public static final String STR_22 = "22";

    public static final String ICMP_IPV6 = "icmpv6";

    public static final String ICMP = "icmp";

    public static final String ALL = "any";

    public static final Integer NUM_0 = 0;

    public static final String DEFAULT = "DEFAULT";


    //license end

    public static final String SPLITPAGEENTITY = "{\n" +
            "  \"draw\" : 0,\n" +
            "  \"startIndex\" : 0,\n" +
            "  \"length\" : 10,\n" +
            "  \"ascendingOrder\" : \"desc\",\n" +
            "  \"search\" : \"\",\n" +
            "  \"paging\" : false,\n" +
            "  \"filterMap\" : {\n" +
            "\n" +
            "  }\n" +
            "}\n";
}
