<%@ page import="ua.org.oa.nedvygav.dao.DaoFacade" %>
<%@ page import="ua.org.oa.nedvygav.dao.AbstractDao" %>
<%@ page import="ua.org.oa.nedvygav.data.Genre" %>
<%--
  Created by IntelliJ IDEA.
  User: Веталь
  Date: 27.06.2015
  Time: 21:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Genre edit</title>
</head>
<body>
<jsp:include page="/header.jsp" />
<form action="genres" method="POST">
  <%
    String id = request.getParameter("id");
    if (id==null) {
  %>
  <input type="hidden" name="method" value="create" />
  <div><h4>Create genre: </h4></div>
  Name: <input type="text" name="name">
  <% } else {
    DaoFacade facade = new DaoFacade(request.getServletContext());
    Genre genre = facade.getGenreDao().findById(Long.parseLong(id));
  %>
  <input type="hidden" name="method" value="update" />
  <input type="hidden" name="id" value="<%=id %>" />
  <div><h4>Update genre: </h4></div>
  Name: <input type="text" name="name" value="<%= genre.getName() %>" />
  <%  facade.closeSqlConnection(); } %>
  <input type="submit" value="Save" />
</form>
</body>
</html>
