<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: Веталь
  Date: 26.06.2015
  Time: 21:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
  <h1><a style="color: black; text-decoration: none" href="index.jsp">AUDIO SHOP</a></h1>
</div>
<div>
  <form style="display: inline-block;" action="login" method="POST">
    <input type="submit" value="admin" name="role">
    <input type="submit" value="user" name="role">
  </form>
  <button onclick="location.href='cart.jsp'"> Shopping cart
    <% List cart = (List) session.getAttribute("cart");
      if (cart!=null&&cart.size()>0) { %> (<%=cart.size() %>) <% } %>
  </button>
  <% if (session.getAttribute("role")!=null&&session.getAttribute("role").equals("admin")) {%>
  <button onclick="location.href='orders.jsp'">Orders</button>
  <% }%>
</div>
<div>
  <tr>
    <td><a href="genres.jsp">Genres</a></td>
    <td><a href="artists.jsp">Artist</a></td>
    <td><a href="albums.jsp">Albums</a></td>
    <td><a href="audios.jsp">Audios</a></td>
  </tr>
  <br>
  <br>
</div>

