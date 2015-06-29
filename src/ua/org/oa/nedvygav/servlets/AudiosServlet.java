package ua.org.oa.nedvygav.servlets;

import com.google.gson.Gson;
import ua.org.oa.nedvygav.dao.DaoFacade;
import ua.org.oa.nedvygav.data.Album;
import ua.org.oa.nedvygav.data.Audio;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


public class AudiosServlet extends HttpServlet {
    private static final String PARAMETER_METOD = "method";
    private static final String PARAMETER_ID = "id";
    private static final String PARAMETER_NAME = "name";
    private static final String PARAMETER_DURATION = "duration";
    private static final String PARAMETER_PRICE = "price";
    private static final String PARAMETER_ARTIST_ID = "artist_id";
    private static final String PARAMETER_ALBUM_ID = "album_id";

    private static final String CREATE_METHOD = "create";
    private static final String UPDATE_METHOD = "update";
    private static final String DELETE_METHOD = "delete";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestMethod = request.getParameter(PARAMETER_METOD);
        response.setContentType("application/json;charset=UTF-8");
        DaoFacade facade = new DaoFacade(request.getServletContext());
        String name = request.getParameter(PARAMETER_NAME);
        int duration = Integer.parseInt(request.getParameter(PARAMETER_DURATION));
        int price = Integer.parseInt(request.getParameter(PARAMETER_PRICE));
        long artistId = Long.parseLong(request.getParameter(PARAMETER_ARTIST_ID));
        long albumId = Long.parseLong(request.getParameter(PARAMETER_ALBUM_ID));

        if (requestMethod.equalsIgnoreCase(CREATE_METHOD)){
            Audio audio = new Audio(name,duration,price,artistId,albumId);
            boolean wasCreated = facade.getAudioDao().add(audio);
            try (PrintWriter pw = response.getWriter()){
                if (wasCreated){
                    response.setStatus(HttpServletResponse.SC_OK);
                    pw.print("{\"response\":\"Created\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    pw.print("{\"error\":\"Failed to create audio\"}");
                }
            }
        } else if (requestMethod.equalsIgnoreCase(UPDATE_METHOD)){
            long id = Long.parseLong(request.getParameter(PARAMETER_ID));
            Audio audio = new Audio(id,name,duration,price,artistId,albumId);
            boolean wasUpdated = facade.getAudioDao().update(audio);
            try (PrintWriter pw = response.getWriter()){
                if (wasUpdated){
                    response.setStatus(HttpServletResponse.SC_OK);
                    pw.print("{\"response\":\"Updated\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    pw.print("{\"error\":\"Failed to update audio\"}");
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
            boolean wasDeleted = facade.getAudioDao().delete(id);
            try (PrintWriter pw = response.getWriter()){
                if (wasDeleted){
                    response.setStatus(HttpServletResponse.SC_OK);
                    pw.print("{\"response\":\"Deleted\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    pw.print("{\"error\":\"Failed to delete audio\"}");
                }
            }
        }

        facade.closeSqlConnection();
    }
}
