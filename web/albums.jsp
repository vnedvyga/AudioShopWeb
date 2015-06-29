<%@ page import="ua.org.oa.nedvygav.dao.DaoFacade" %>
<%@ page import="ua.org.oa.nedvygav.data.Album" %>
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
  <title>Albums</title>
</head>
<body>
<jsp:include page="/header.jsp" />
<%
  DaoFacade facade = new DaoFacade(request.getServletContext());
  String view = request.getParameter("artist_id");
  List<Album> albums;
  if (view!=null){
    albums = facade.getAlbumDao().loadAllbyValue(Long.parseLong(view));
  } else {
    albums = facade.getAlbumDao().loadAll();
  }
%>
<table>
  <tr>
    <th>Id</th>
    <th>Name</th>
    <th>Year</th>
    <% if (session.getAttribute("role")!=null&&session.getAttribute("role").equals("admin")) {%>
    <th>Update</th>
    <th>Delete</th>
    <% }%>
  </tr>
  <% for (Album album: albums) {%>
  <tr>
    <td><%=album.getId()%></td>
    <td><%=album.getName()%></td>
    <td><%=album.getYear()%></td>
    <% if (session.getAttribute("role")!=null&&session.getAttribute("role").equals("admin")) {%>
    <td><a href="albums_edit.jsp?id=<%=album.getId()%>">Update</a></td>
    <td><a href="albums?method=delete&id=<%=album.getId()%>">Delete</a></td>
    <% }%>
    <td><a href="audios.jsp?album_id=<%=album.getId()%>">View songs</a></td>
    <td><a href="cart?method=add_album&id=<%=album.getId()%>">Add to cart</a></td>
  </tr>
  <% }%>
</table>
<% if (session.getAttribute("role")!=null&&session.getAttribute("role").equals("admin")) {%>
<button onclick="location.href='albums_edit.jsp'">Create album</button>
<% } %>
<% facade.closeSqlConnection();%>
</body>
</html>
