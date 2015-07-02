<%@ page import="ua.org.oa.nedvygav.dao.DaoFacade" %>
<%@ page import="ua.org.oa.nedvygav.data.Album" %>
<%@ page import="ua.org.oa.nedvygav.data.Artist" %>
<%@ page import="java.util.List" %>
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
  <title>Album edit</title>
</head>
<body>
<jsp:include page="/header.jsp" />
<%
  DaoFacade facade = new DaoFacade(request.getServletContext());
%>
<form action="albums" method="POST">
  <%
    String id = request.getParameter("id");
    if (id==null) {
  %>
  <input type="hidden" name="method" value="create" />
  <div><h4>Create album: </h4></div>
  Name: <input type="text" name="name">
  Year: <input type="text" name="year">
  Artist: <select name="artist_id">
  <% List<Artist> artists = facade.getArtistDao().loadAll();
    for (Artist artist : artists) { %>
  <option value="<%= artist.getId() %>">
    <%= artist.getName() %>
  </option>
  <% } %>
</select>
  <% } else {
    Album album = facade.getAlbumDao().findById(Long.parseLong(id));
  %>
  <input type="hidden" name="method" value="update" />
  <input type="hidden" name="id" value="<%=id %>" />
  <div><h4>Update album: </h4></div>
  Name: <input type="text" name="name" value="<%= album.getName() %>" />
  Year: <input type="text" name="year" value="<%= album.getYear() %>" />
  Artist: <select name="artist_id">
  <% List<Artist> artists = facade.getArtistDao().loadAll();
    for (Artist artist : artists) { %>
  <option value="<%= artist.getId() %>">
    <%= artist.getName() %>
  </option>
  <% } %>
</select>
  <% }
  facade.closeSqlConnection();
  %>
  <input type="submit" value="Save" />
</form>
</body>
</html>
