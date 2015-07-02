package ua.org.oa.nedvygav.servlets;

import com.google.gson.Gson;
import ua.org.oa.nedvygav.dao.DaoFacade;
import ua.org.oa.nedvygav.data.Artist;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


public class ArtistsServlet extends HttpServlet {
    private static final String PARAMETER_METOD = "method";
    private static final String PARAMETER_ID = "id";
    private static final String PARAMETER_NAME = "name";
    private static final String PARAMETER_COUNTRY = "country";
    private static final String PARAMETER_GENRE_ID = "genre_id";

    private static final String GET_ALL_METHOD = "get";
    private static final String CREATE_METHOD = "create";
    private static final String UPDATE_METHOD = "update";
    private static final String DELETE_METHOD = "delete";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestMethod = request.getParameter(PARAMETER_METOD);
        response.setContentType("application/json;charset=UTF-8");
        DaoFacade facade = new DaoFacade(request.getServletContext());
        String name = request.getParameter(PARAMETER_NAME);
        String country = request.getParameter(PARAMETER_COUNTRY);
        long genreId = Long.parseLong(request.getParameter(PARAMETER_GENRE_ID));

        if (requestMethod.equalsIgnoreCase(CREATE_METHOD)){
            Artist artist = new Artist(name,country, genreId);
            boolean wasCreated = facade.getArtistDao().add(artist);
            try (PrintWriter pw = response.getWriter()){
                if (wasCreated){
                    response.setStatus(HttpServletResponse.SC_OK);
                    pw.print("{\"response\":\"Created\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    pw.print("{\"error\":\"Failed to create artist\"}");
                }
            }
        } else if (requestMethod.equalsIgnoreCase(UPDATE_METHOD)){
            long id = Long.parseLong(request.getParameter(PARAMETER_ID));
            Artist artist = new Artist(id, name,country, genreId);
            boolean wasUpdated = facade.getArtistDao().update(artist);
            try (PrintWriter pw = response.getWriter()){
                if (wasUpdated){
                    response.setStatus(HttpServletResponse.SC_OK);
                    pw.print("{\"response\":\"Updated\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    pw.print("{\"error\":\"Failed to update artist\"}");
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

        if (GET_ALL_METHOD.equalsIgnoreCase(requestMethod)) {
            List<Artist> artists;
            if (request.getParameter(PARAMETER_GENRE_ID)!=null){
                long genreId = Long.parseLong(request.getParameter(PARAMETER_GENRE_ID));
                artists = facade.getArtistDao().loadAllbyValue(genreId);
            } else {
                artists = facade.getArtistDao().loadAll();
            }
            try (PrintWriter out = response.getWriter()) {
                Gson gson = new Gson();
                gson.toJson(artists, out);
            }
            response.setStatus(HttpServletResponse.SC_OK);
        } else if (requestMethod.equalsIgnoreCase(DELETE_METHOD)){
            long id = Long.parseLong(request.getParameter(PARAMETER_ID));
            boolean wasDeleted = facade.getArtistDao().delete(id);
            try (PrintWriter pw = response.getWriter()){
                if (wasDeleted){
                    response.setStatus(HttpServletResponse.SC_OK);
                    pw.print("{\"response\":\"Deleted\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    pw.print("{\"error\":\"Failed to delete artist\"}");
                }
            }
        }

        facade.closeSqlConnection();
    }
}

