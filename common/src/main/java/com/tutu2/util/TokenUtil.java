package com.tutu2.util;

import com.tutu2.enums.ResultEnum;
import com.tutu2.pub.ServiceException;
import org.apache.commons.lang3.StringUtils;

/**
 * token相关工具
 */
public class TokenUtil {

    public static Long parseUserId(String token) {
        if (StringUtils.isBlank(token)) {
            throw new ServiceException(ResultEnum.NULL_TOKEN);
        }

        Long userId = Long.valueOf(Des3.urlDecode(token).split("@")[1]);
        if (userId.longValue() < 10000000L) {
            throw new ServiceException(ResultEnum.NULL_TOKEN);
        }
        return userId;
    }

}
