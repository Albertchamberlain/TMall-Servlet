package com.amos.utils;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
/**
 * @author Amos
 * @date 8/5/2020 7:03 PM
 */
public class PaginationUtil {
    public static Pagination createPagination(HttpServletRequest request,int total){
        //获取Pagination本身的两个参数
        int start = 0;
        try{
            start = Integer.parseInt(request.getParameter("pageStart"));
        }catch (Exception ignored){

        }
        int count = 5;
        try{
            count = Integer.parseInt(request.getParameter("pageCount"));
        }catch (Exception ignored){

        }

        StringBuilder param = new StringBuilder();
        Enumeration enu=request.getParameterNames();
        while(enu.hasMoreElements()){
            String paraName=(String)enu.nextElement();
            if(!"pageStart".equals(paraName)) {
                param.append('&').append(paraName).append("=").append(request.getParameter(paraName));
            }
        }
        Pagination pagination = new Pagination(start,count,total);
        pagination.setParam(param.toString());
        return pagination;
    }
}
