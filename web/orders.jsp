<%@ page import="ua.org.oa.nedvygav.dao.DaoFacade" %>
<%@ page import="ua.org.oa.nedvygav.data.Order" %>
<%@ page import="java.util.List" %>
<%@ page import="ua.org.oa.nedvygav.data.Audio" %>
<%@ page import="java.io.PrintWriter" %>
<%--
  Created by IntelliJ IDEA.
  User: Веталь
  Date: 29.06.2015
  Time: 13:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Orders</title>
</head>
<body>
<jsp:include page="/header.jsp" />
<% DaoFacade facade = new DaoFacade(request.getServletContext());
  List<Order> orders = facade.getOrderDao().loadAll();
  %>
<table cellspacing="10">
  <tr>
    <th>Id</th>
    <th>Date</th>
    <th>Items</th>
    <th>Total Price</th>
  </tr>
  <% for (Order order: orders) {%>
  <tr>
    <td valign="top"><%=order.getId()%></td>
    <td valign="top"><%=order.getDateTime()%></td>
    <td><% List<Audio> items = order.getItems();
    for (Audio audio : items){%>
    <%=audio.getName()%> <%=audio.getDuration()%> <%=audio.getPrice()%> <br>
    <% } %>
    </td>
    <td valign="top"><%=order.getTotalPrice()%></td>
    <td valign="top"><a href="orders.jsp?del=<%=order.getId()%>">Delete record</a></td>
  </tr>
  <% }%>
</table>
<% String orderId = request.getParameter("del");
  if (orderId!=null) {
    boolean wasDeleted = facade.getOrderDao().delete(Long.parseLong(orderId));
    try (PrintWriter pw = response.getWriter()) {
      RequestDispatcher requestDispatcher = request.getRequestDispatcher("orders.jsp");
      if (wasDeleted) {
        response.setStatus(HttpServletResponse.SC_OK);
        pw.print("{\"response\":\"Deleted\"}");
        //requestDispatcher.include(request,response);
      } else {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        pw.print("{\"error\":\"Failed to delete order\"}");
        //requestDispatcher.include(request,response);
      }
    }
  }%>
<% facade.closeSqlConnection(); %>
</body>
</html>
