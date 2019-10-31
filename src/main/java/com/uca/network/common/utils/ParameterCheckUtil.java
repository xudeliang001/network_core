package com.uca.network.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by y15527 on 2017/12/27.
 *
 * @author y15527
 */
public class ParameterCheckUtil {

    public static final Integer NUM_2 = 2;

    public static final Integer NUM_15 = 15;

    public static final Integer NUM_85 = 85;

    public static final Integer NUM_32 = 32;

    /**
     * 校验名称
     * 规则：名称包含2-15位，以字母为首位，由大小写字母、数字和下划线组成组成
     *
     * @param name 名称
     * @return boolean
     */
    public static boolean checkName(String name) {
        if (null != name) {
            if (name.length() >= NUM_2 && name.length() <= NUM_15) {
                String reg = "^[a-zA-Z]+[0-9a-zA-Z]+$";
                Pattern pattern = Pattern.compile(reg);
                Matcher matcher = pattern.matcher(name);
                return matcher.matches();
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 校验数字
     * 规则：校验为数字，且大于1
     *
     * @param num 名称
     * @return boolean
     */
    public static boolean checkNum(String num) {
        if (null != num) {
            String reg = "^[1-9]+[0-9]*$";
            Pattern pattern = Pattern.compile(reg);
            Matcher matcher = pattern.matcher(num);
            return matcher.matches();
        } else {
            return false;
        }
    }

    /**
     * 校验描述性字段长度
     * 规则：校验小于85个字符
     *
     * @param dec 描述
     * @return boolean
     */
    public static boolean checkDescriptionLength(String dec) {
        if (null != dec) {
            if (dec.length() <= NUM_85) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean checkNetworkName(String name) {
        if (null != name) {
            if (name.length() >= NUM_2 && name.length() <= NUM_32) {
                String reg = "^[a-zA-Z]+[0-9a-zA-Z]+$";
                Pattern pattern = Pattern.compile(reg);
                Matcher matcher = pattern.matcher(name);
                return matcher.matches();
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean checkFirewallRuleName(String name) {
        if (null != name) {
            if (name.length() >= NUM_2 && name.length() <= NUM_32) {
                String reg = "^[a-zA-Z]+[0-9a-zA-Z]+$";
                Pattern pattern = Pattern.compile(reg);
                Matcher matcher = pattern.matcher(name);
                return matcher.matches();
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean checkCidrBlock(String cidr) {
        Pattern p = Pattern.compile("((0{0,2}[0-9])|(0?[1-9][0-9])|([1][0-9][0-9])|(2[0-4][0-9])|(25[0-5]))\\." +
                "((0{0,2}[0-9])|(0?[1-9][0-9])|([1][0-9][0-9])|(2[0-4][0-9])|(25[0-5]))\\." +
                "((0{0,2}[0-9])|(0?[1-9][0-9])|([1][0-9][0-9])|(2[0-4][0-9])|(25[0-5]))\\." +
                "((0{0,2}[0-9])|(0?[1-9][0-9])|([1][0-9][0-9])|(2[0-4][0-9])|(25[0-5]))" +
                "/((0?[1-9])|(1[0-9])|(2[0-9])|(3[0-1]))");
        Matcher m = p.matcher(cidr);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkIpAddress(String ipAddress) {
        Pattern p = Pattern.compile("((0{0,2}[0-9])|(0?[1-9][0-9])|([1][0-9][0-9])|(2[0-4][0-9])|(25[0-5]))\\." +
                "((0{0,2}[0-9])|(0?[1-9][0-9])|([1][0-9][0-9])|(2[0-4][0-9])|(25[0-5]))\\." +
                "((0{0,2}[0-9])|(0?[1-9][0-9])|([1][0-9][0-9])|(2[0-4][0-9])|(25[0-5]))\\." +
                "((0{0,2}[0-9])|(0?[1-9][0-9])|([1][0-9][0-9])|(2[0-4][0-9])|(25[0-5]))");
        Matcher m = p.matcher(ipAddress);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }
}
