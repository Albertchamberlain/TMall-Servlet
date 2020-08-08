package com.amos.servlet;

import com.amos.bean.Order;
import com.amos.service.OrderService;
import com.amos.utils.Pagination;
import com.amos.utils.PaginationUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @author Amos
 * @date 8/8/2020 12:15 PM
 */
public class OrderServlet  extends BaseServlet{
    private OrderService service = new OrderService();
    public String list(HttpServletRequest request, HttpServletResponse response){
        Pagination pagination= PaginationUtil.createPagination(request,service.getTotal());
        List<Order> orders = service.list(pagination.getStart(),pagination.getCount());
        request.setAttribute("orders",orders);
        request.setAttribute("pagination",pagination);
        return "jsp/admin/listOrder.jsp";
    }
    public String delivery(HttpServletRequest request, HttpServletResponse response){
        int oid = Integer.parseInt(request.getParameter("oid"));
        Order order = service.get(oid);
        order.setStatus(OrderService.OrderType.WAIT_CONFIRM);
        order.setDeliverDate(new Date());
        service.update(order);
        return "@/admin/order_list";
    }
}
