package com.zoey.goods.comment.utils;

import com.dashuai.commons.utils.TimeUtils;
import com.dashuai.commons.utils.UniqueIdUtils;

import java.util.Date;

/**
 * Created in 2019.12.25
 *
 * @author Zoey
 */
public class OrderNoCreator {

    /**
     * 生成订单号规则
     *
     * @param userId the user id
     * @return order no
     */
    public static String getOrderNo(String userId) {
        String date = TimeUtils.YYYYMMDDHHMMSS.format(new Date());
        return date + "0" + userId + "0" + UniqueIdUtils.getUUIdStr();
    }
}
