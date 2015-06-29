<%@ page import="ua.org.oa.nedvygav.dao.DaoFacade" %>
<%@ page import="ua.org.oa.nedvygav.data.Artist" %>
<%@ page import="ua.org.oa.nedvygav.data.Genre" %>
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
  <title>Artist edit</title>
</head>
<body>
<jsp:include page="/header.jsp" />
<%
  DaoFacade facade = new DaoFacade(request.getServletContext());
%>
<form action="artists" method="POST">
  <%
    String id = request.getParameter("id");
    if (id==null) {
  %>
  <input type="hidden" name="method" value="create" />
  <div><h4>Create artist: </h4></div>
  Name: <input type="text" name="name">
  Country: <input type="text" name="country">
  Genre: <select name="genre_id">
  <% List<Genre> genres = facade.getGenreDao().loadAll();
    for (Genre genre : genres) { %>
  <option value="<%= genre.getId() %>">
    <%= genre.getName() %>
  </option>
  <% } %>
</select>
  <% } else {
    Artist artist = facade.getArtistDao().findById(Long.parseLong(id));
  %>
  <input type="hidden" name="method" value="update" />
  <input type="hidden" name="id" value="<%=id %>" />
  <div><h4>Update artist: </h4></div>
  Name: <input type="text" name="name" value="<%= artist.getName() %>" />
  Country: <input type="text" name="country" value="<%= artist.getOriginCountry() %>" />
  Genre: <select name="genre_id">
  <% List<Genre> genres = facade.getGenreDao().loadAll();
    for (Genre genre : genres) { %>
  <option value="<%= genre.getId() %>">
    <%= genre.getName() %>
  </option>
  <% } %>
</select>
  <% } %>
  <input type="submit" value="Save" />
</form>
<% facade.closeSqlConnection(); %>
</body>
</html>
