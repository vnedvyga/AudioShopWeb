package ua.org.oa.nedvygav.servlets;



import com.google.gson.Gson;
import ua.org.oa.nedvygav.dao.DaoFacade;
import ua.org.oa.nedvygav.data.Genre;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;


public class GenresServlet extends HttpServlet {
    private static final String PARAMETER_METOD = "method";
    private static final String PARAMETER_ID = "id";
    private static final String PARAMETER_NAME = "name";

    private static final String GET_ALL_METHOD = "get";
    private static final String CREATE_METHOD = "create";
    private static final String UPDATE_METHOD = "update";
    private static final String DELETE_METHOD = "delete";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestMethod = request.getParameter(PARAMETER_METOD);
        response.setContentType("application/json;charset=UTF-8");
        DaoFacade facade = new DaoFacade(request.getServletContext());

        if (requestMethod.equalsIgnoreCase(CREATE_METHOD)){
            String name = request.getParameter(PARAMETER_NAME);
            Genre genre = new Genre(name);
            boolean wasCreated = facade.getGenreDao().add(genre);
            try (PrintWriter pw = response.getWriter()){
                if (wasCreated){
                    response.setStatus(HttpServletResponse.SC_OK);
                    pw.print("{\"response\":\"Created\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    pw.print("{\"error\":\"Failed to create genre\"}");
                }
            }
        } else if (requestMethod.equalsIgnoreCase(UPDATE_METHOD)){
            long id = Long.parseLong(request.getParameter(PARAMETER_ID));
            String name = request.getParameter(PARAMETER_NAME);
            Genre genre = new Genre(id, name);
            boolean wasUpdated = facade.getGenreDao().update(genre);
            try (PrintWriter pw = response.getWriter()){
                if (wasUpdated){
                    response.setStatus(HttpServletResponse.SC_OK);
                    pw.print("{\"response\":\"Updated\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    pw.print("{\"error\":\"Failed to update genre\"}");
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
            List<Genre> genres = facade.getGenreDao().loadAll();
            try (PrintWriter out = response.getWriter()) {
                Gson gson = new Gson();
                gson.toJson(genres, out);
            }
            response.setStatus(HttpServletResponse.SC_OK);
        } else if (requestMethod.equalsIgnoreCase(DELETE_METHOD)){
            long id = Long.parseLong(request.getParameter(PARAMETER_ID));
            boolean wasDeleted = facade.getGenreDao().delete(id);
            try (PrintWriter pw = response.getWriter()){
                if (wasDeleted){
                    response.setStatus(HttpServletResponse.SC_OK);
                    pw.print("{\"response\":\"Deleted\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    pw.print("{\"error\":\"Failed to delete genre\"}");
                }
            }
        }

        facade.closeSqlConnection();
    }
}
