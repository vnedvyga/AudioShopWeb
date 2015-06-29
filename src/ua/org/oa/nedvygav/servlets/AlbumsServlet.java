package ua.org.oa.nedvygav.servlets;

import com.google.gson.Gson;
import ua.org.oa.nedvygav.dao.DaoFacade;
import ua.org.oa.nedvygav.data.Album;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


public class AlbumsServlet extends HttpServlet {
    private static final String PARAMETER_METOD = "method";
    private static final String PARAMETER_ID = "id";
    private static final String PARAMETER_NAME = "name";
    private static final String PARAMETER_YEAR = "year";
    private static final String PARAMETER_ARTIST_ID = "artist_id";

    private static final String CREATE_METHOD = "create";
    private static final String UPDATE_METHOD = "update";
    private static final String DELETE_METHOD = "delete";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestMethod = request.getParameter(PARAMETER_METOD);
        response.setContentType("application/json;charset=UTF-8");
        DaoFacade facade = new DaoFacade(request.getServletContext());
        String name = request.getParameter(PARAMETER_NAME);
        int year = Integer.parseInt(request.getParameter(PARAMETER_YEAR));
        long artistId = Long.parseLong(request.getParameter(PARAMETER_ARTIST_ID));

        if (requestMethod.equalsIgnoreCase(CREATE_METHOD)){
            Album album = new Album(name,year, artistId);
            boolean wasCreated = facade.getAlbumDao().add(album);
            try (PrintWriter pw = response.getWriter()){
                if (wasCreated){
                    response.setStatus(HttpServletResponse.SC_OK);
                    pw.print("{\"response\":\"Created\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    pw.print("{\"error\":\"Failed to create album\"}");
                }
            }
        } else if (requestMethod.equalsIgnoreCase(UPDATE_METHOD)){
            long id = Long.parseLong(request.getParameter(PARAMETER_ID));
            Album album = new Album(id, name,year, artistId);
            boolean wasUpdated = facade.getAlbumDao().update(album);
            try (PrintWriter pw = response.getWriter()){
                if (wasUpdated){
                    response.setStatus(HttpServletResponse.SC_OK);
                    pw.print("{\"response\":\"Updated\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    pw.print("{\"error\":\"Failed to update album\"}");
                }
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            try (PrintWriter out = response.getWriter()) {
                out.print("{\"error\":\"Method not found\"}");
            }
        }

        facade.closeSqlConnection();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestMethod = request.getParameter(PARAMETER_METOD);
        response.setContentType("application/json;charset=UTF-8");
        DaoFacade facade = new DaoFacade(request.getServletContext());

        if (requestMethod.equalsIgnoreCase(DELETE_METHOD)){
            long id = Long.parseLong(request.getParameter(PARAMETER_ID));
            boolean wasDeleted = facade.getAlbumDao().delete(id);
            try (PrintWriter pw = response.getWriter()){
                if (wasDeleted){
                    response.setStatus(HttpServletResponse.SC_OK);
                    pw.print("{\"response\":\"Deleted\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    pw.print("{\"error\":\"Failed to delete artist\"}");
                }
            }
        } else if(requestMethod.equals("getAlbums")){
            long artistId = Long.parseLong(request.getParameter(PARAMETER_ARTIST_ID));
            List<Album> albums = facade.getAlbumDao().loadAllbyValue(artistId);
            String json = new Gson().toJson(albums);
            response.setContentType("application/json");
            response.getWriter().write(json);


        }

        facade.closeSqlConnection();
    }
}
