<%@ page import="ua.org.oa.nedvygav.dao.DaoFacade" %>
<%@ page import="ua.org.oa.nedvygav.data.Artist" %>
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
  <title>Artists</title>
</head>
<body>
<jsp:include page="/header.jsp" />
<%
  DaoFacade facade = new DaoFacade(request.getServletContext());
  String view = request.getParameter("genre_id");
  List<Artist> artists;
  if (view!=null){
    artists = facade.getArtistDao().loadAllbyValue(Long.parseLong(view));
  } else {
    artists = facade.getArtistDao().loadAll();
  }
%>
<table>
  <tr>
    <th>Id</th>
    <th>Name</th>
    <th>Country</th>
    <% if (session.getAttribute("role")!=null&&session.getAttribute("role").equals("admin")) {%>
    <th>Update</th>
    <th>Delete</th>
    <% }%>
  </tr>
  <% for (Artist artist: artists) {%>
  <tr>
    <td><%=artist.getId()%></td>
    <td><%=artist.getName()%></td>
    <td><%=artist.getOriginCountry()%></td>
    <% if (session.getAttribute("role")!=null&&session.getAttribute("role").equals("admin")) {%>
    <td><a href="artists_edit.jsp?id=<%=artist.getId()%>">Update</a></td>
    <td><a href="artists?method=delete&id=<%=artist.getId()%>">Delete</a></td>
    <% }%>
    <td><a href="albums.jsp?artist_id=<%=artist.getId()%>">View albums</a></td>
  </tr>
  <% }%>
</table>
<% if (session.getAttribute("role")!=null&&session.getAttribute("role").equals("admin")) {%>
<button onclick="location.href='artists_edit.jsp'">Create artist</button>
<% } %>
<% facade.closeSqlConnection(); %>
</body>
</html>
