package com.amos.utils;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Amos
 * @date 8/5/2020 6:18 PM
 */
public class DateUtil {

    /**
     * date to time(d 2 t)
     * @param date
     * @return
     */
    public static Timestamp d2t(Date date){
        if(date == null){
            return null;
        }
        //getTime 方法返回一个整数值，这个整数代表了从 1970 年 1 月 1 日开始计算到 Date 对象中的时间之间的毫秒数。
        return new Timestamp(date.getTime());
        //java.sql.Timestamp 年月日 时分秒经过 SimpleDateFormat  定制格式化之后.  都可展示对应的年月日时分秒.
    }

    /**
     * time to date (t 2 d)
     * @param timestamp
     * @return
     */
    public static Date t2d(Timestamp timestamp){
        if(timestamp == null){
            return null;
        }
        return new Date(timestamp.getTime());
    }

    /**
     * 获得系统的时间，单位为毫秒
     * @return Timestamp
     */
    public static Timestamp nowTimestamp(){
        return new Timestamp(System.currentTimeMillis());
    }
}
