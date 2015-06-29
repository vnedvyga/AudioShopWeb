<%@ page import="ua.org.oa.nedvygav.data.Audio" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: Веталь
  Date: 28.06.2015
  Time: 23:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shopping cart</title>
</head>
<body>
<jsp:include page="/header.jsp" />
  <% List<Audio> cart = (List<Audio>) session.getAttribute("cart");
  if (cart!=null) { %>
  <table>
    <tr>
      <th>Name</th>
      <th>Duration</th>
      <th>Price</th>
    </tr>
    <% for (Audio audio: cart) {%>
  <tr>
    <td><%=audio.getName()%></td>
    <td><%=audio.getDuration()%></td>
    <td><%=audio.getPrice()%></td>
    <td><a href="cart?method=remove&id=<%=audio.getId()%>">Remove from cart</a></td>
  </tr>
  <% } %>
  </table>
  <button onclick="location.href='cart?method=buy'">Buy</button>
  <button onclick="location.href='cart?method=clear'">Clear cart</button>
  <% } else {%> No Items to Show <% } %>

</body>
</html>
