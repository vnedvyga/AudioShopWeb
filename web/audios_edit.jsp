<%@ page import="ua.org.oa.nedvygav.dao.DaoFacade" %>
<%@ page import="ua.org.oa.nedvygav.data.Album" %>
<%@ page import="ua.org.oa.nedvygav.data.Artist" %>
<%@ page import="java.util.List" %>
<%@ page import="ua.org.oa.nedvygav.data.Audio" %>
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
  <title>Audio edit</title>
  <script src="js/jquery-1.11.3.js" type="text/javascript"></script>
  <script>
    $(document).ready(function() {

      $('#artist_id').change(function(event) {
        var artistId = $("select#artist_id").val();
        $.getJSON('albums', { method : "getAlbums",
          artist_id : artistId
        }, function(response) {
          var select = $('#album_id');
          select.find('option').remove();
          $.each(response, function(index, value) {
            $('<option>').val(value.id).text(value.name).appendTo(select);
          });
        });
      });
    });
  </script>
</head>
<body>
<jsp:include page="/header.jsp" />
<%
  DaoFacade facade = new DaoFacade(request.getServletContext());
%>
<form action="audios" method="POST">
  <%
    String id = request.getParameter("id");
    if (id==null) {
  %>
  <input type="hidden" name="method" value="create" />
  <div><h4>Create audio: </h4></div>
  Name: <input type="text" name="name">
  Duration: <input type="text" name="duration">
  Price: <input type="text" name="price">
  Artist: <select id="artist_id" name="artist_id">
  <option>Select artist</option>
  <% List<Artist> artists = facade.getArtistDao().loadAll();
    for (Artist artist : artists) { %>
  <option value="<%= artist.getId() %>">
    <%= artist.getName() %>
  </option>
  <% } %>
</select>
  Album: <select id="album_id" name="album_id">
  <option>Select artist first</option>
</select>
  <% } else {
    Audio audio = facade.getAudioDao().findById(Long.parseLong(id));
    Artist artist = facade.getArtistDao().findById(audio.getArtistId());
    Album album = facade.getAlbumDao().findById(audio.getAlbumId());
  %>
  <input type="hidden" name="method" value="update" />
  <input type="hidden" name="id" value="<%=id %>" />
  <div><h4>Update audio: </h4></div>
  Name: <input type="text" name="name" value="<%= audio.getName() %>" />
  Duration: <input type="text" name="duration" value="<%= audio.getDuration() %>" />
  Price: <input type="text" name="price" value="<%= audio.getPrice() %>" />
  Artist: <select id="artist_id" name="artist_id">
  <option value="<%= artist.getId() %>"><%= artist.getName() %></option>
  <% List<Artist> artists = facade.getArtistDao().loadAll();
    for (Artist artistInst : artists) { %>
  <option value="<%= artistInst.getId() %>">
    <%= artistInst.getName() %>
  </option>
  <% } %>
</select>
  Album: <select id="album_id" name="album_id">
  <option value="<%= album.getId() %>"><%= album.getName() %></option>
</select>

  <% } %>
  <input type="submit" value="Save" />
</form>
<% facade.closeSqlConnection(); %>
</body>
</html>
