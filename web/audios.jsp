<%@ page import="ua.org.oa.nedvygav.dao.DaoFacade" %>
<%@ page import="ua.org.oa.nedvygav.data.Audio" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: Веталь
  Date: 27.06.2015
  Time: 17:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Audios</title>
</head>
<body>
<jsp:include page="/header.jsp" />
<%
  DaoFacade facade = new DaoFacade(request.getServletContext());
  String view = request.getParameter("album_id");
  List<Audio> audios;
  if (view!=null){
    audios = facade.getAudioDao().loadAllbyValue(Long.parseLong(view));
  } else {
    audios = facade.getAudioDao().loadAll();
  }
%>
<table>
  <tr>
    <th>Id</th>
    <th>Name</th>
    <th>Duration</th>
    <th>Price</th>
    <% if (session.getAttribute("role")!=null&&session.getAttribute("role").equals("admin")) {%>
    <th>Update</th>
    <th>Delete</th>
    <% }%>
  </tr>
  <% for (Audio audio: audios) {%>
  <tr>
    <td><%=audio.getId()%></td>
    <td><%=audio.getName()%></td>
    <td><%=audio.getDuration()%></td>
    <td><%=audio.getPrice()%></td>
    <% if (session.getAttribute("role")!=null&&session.getAttribute("role").equals("admin")) {%>
    <td><a href="audios_edit.jsp?id=<%=audio.getId()%>">Update</a></td>
    <td><a href="audios?method=delete&id=<%=audio.getId()%>">Delete</a></td>
    <% }%>
    <td><a href="cart?method=add&id=<%=audio.getId()%>">Add to cart</a></td>
  </tr>
  <% }%>
</table>
<% if (session.getAttribute("role")!=null&&session.getAttribute("role").equals("admin")) {%>
<button onclick="location.href='audios_edit.jsp'">Create audio</button>
<% } %>
<% facade.closeSqlConnection(); %>
</body>
</html>
