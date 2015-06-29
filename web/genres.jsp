<%@ page import="ua.org.oa.nedvygav.dao.DaoFacade" %>
<%@ page import="ua.org.oa.nedvygav.data.Genre" %>
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
    <title>Genres</title>
</head>
<body>
<jsp:include page="/header.jsp" />
<%
  DaoFacade facade = new DaoFacade(request.getServletContext());
  List<Genre> genres = facade.getGenreDao().loadAll();
%>
<table>
  <tr>
    <th>Id</th>
    <th>Name</th>
    <% if (session.getAttribute("role")!=null&&session.getAttribute("role").equals("admin")) {%>
    <th>Update</th>
    <th>Delete</th>
    <% }%>
  </tr>
  <% for (Genre genre: genres) {%>
  <tr>
    <td><%=genre.getId()%></td>
    <td><%=genre.getName()%></td>
    <% if (session.getAttribute("role")!=null&&session.getAttribute("role").equals("admin")) {%>
    <td><a href="genres_edit.jsp?id=<%=genre.getId()%>">Update</a></td>
    <td><a href="genres?method=delete&id=<%=genre.getId()%>">Delete</a></td>
    <% }%>
    <td><a href="artists.jsp?genre_id=<%=genre.getId()%>">View Artists</a></td>
  </tr>
  <% }%>
</table>
<% if (session.getAttribute("role")!=null&&session.getAttribute("role").equals("admin")) {%>
<button onclick="location.href='genres_edit.jsp'">Create genre</button>
<% } %>
<% facade.closeSqlConnection(); %>
</body>
</html>
