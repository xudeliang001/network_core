/***********************************************************************
 * ResourceStatus.java H3C所有，受到法律的保护，任何公司或个人，未经授权不得擅自拷贝。
 *
 * @copyright Copyright: 2015-2020
 * @creator likewei<li.kewei@h3c.com>
 * @create-time 2018/5/8 13:54
 * @revision $Id: *
 ***********************************************************************/
package com.uca.network.common.utils;

public enum ResourceStatus {
    INACTIVE("INACTIVE"), ACTIVE("ACTIVE"), PENDING_UPDATE("PENDING_UPDATE"),DOWN("DOWN");

    private String val;

    ResourceStatus(String val) {

        this.val = val;
    }

    public String getVal() {
        return val;
    }
}
