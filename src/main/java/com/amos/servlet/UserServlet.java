package com.amos.servlet;

import com.amos.bean.User;
import com.amos.service.UserService;
import com.amos.utils.Pagination;
import com.amos.utils.PaginationUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Amos
 * @date 8/8/2020 1:44 PM
 */
public class UserServlet extends  BaseServlet{
    private UserService service = new UserService();
    public String list(HttpServletRequest request, HttpServletResponse response){
        Pagination pagination= PaginationUtil.createPagination(request,service.getTotal());
        List<User> users = service.list(pagination.getStart(),pagination.getCount());
        request.setAttribute("users",users);
        request.setAttribute("pagination",pagination);
        return "jsp/admin/listUser.jsp";
    }
}
