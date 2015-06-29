package ua.org.oa.nedvygav.servlets;

import ua.org.oa.nedvygav.dao.DaoFacade;
import ua.org.oa.nedvygav.data.Audio;
import ua.org.oa.nedvygav.data.Order;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class CartServlet extends HttpServlet {
    private static final String PARAMETER_METOD = "method";

    private static final String PARAMETER_ITEM_ID = "id";

    private static final String ADD_TO_CART_METHOD = "add";
    private static final String ADD_ALBUM_TO_CART_METHOD = "add_album";
    private static final String REMOVE_FROM_CART_METHOD = "remove";
    private static final String CLEAR_CART_METHOD = "clear";
    private static final String BUY_METHOD = "buy";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);

        if (session.getAttribute("cart")==null){
            session.setAttribute("cart", new ArrayList<Audio>());
        }

        List<Audio> cart = (List<Audio>) session.getAttribute("cart");
        DaoFacade facade = new DaoFacade(request.getServletContext());
        Audio audio;

        if (request.getParameter(PARAMETER_METOD).equals(ADD_TO_CART_METHOD)){
            long audioId = Long.parseLong(request.getParameter(PARAMETER_ITEM_ID));
            audio = facade.getAudioDao().findById(audioId);
            boolean wasAdded = cart.add(audio);
            try (PrintWriter pw = response.getWriter()){
                if (wasAdded){
                    response.setStatus(HttpServletResponse.SC_OK);
                    pw.print("{\"response\":\"Added to cart\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    pw.print("{\"error\":\"Failed to add audio to cart\"}");
                }
            }
        } else if (request.getParameter(PARAMETER_METOD).equals(ADD_ALBUM_TO_CART_METHOD)){
            long albumId = Long.parseLong(request.getParameter(PARAMETER_ITEM_ID));
            List<Audio> items = facade.getAudioDao().loadAllbyValue(albumId);
            for (Audio item : items){
                boolean wasAdded = cart.add(item);
                try (PrintWriter pw = response.getWriter()){
                    if (wasAdded){
                        response.setStatus(HttpServletResponse.SC_OK);
                        pw.print("{\"response\":\"Added to cart\"}");
                    } else {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        pw.print("{\"error\":\"Failed to add audio to cart\"}");
                    }
                }
            }
            if (items.size()==0){
                try (PrintWriter pw = response.getWriter()){
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    pw.print("{\"error\":\"There are no songs in this album\"}");
                }
            }
        } else if (request.getParameter(PARAMETER_METOD).equals(REMOVE_FROM_CART_METHOD)){
            long audioId = Long.parseLong(request.getParameter(PARAMETER_ITEM_ID));
            audio = facade.getAudioDao().findById(audioId);
            boolean wasRemoved = cart.remove(audio);
            try (PrintWriter pw = response.getWriter()){
                if (wasRemoved){
                    response.setStatus(HttpServletResponse.SC_OK);
                    pw.print("{\"response\":\"Removed from cart\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    pw.print("{\"error\":\"Failed to remove audio from cart\"}");
                }
            }
        } else if (request.getParameter(PARAMETER_METOD).equals(CLEAR_CART_METHOD)){
            session.invalidate();
            session=request.getSession(false);
            try (PrintWriter pw = response.getWriter()) {
                if (session==null) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    pw.print("{\"response\":\"Cart has been cleared\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    pw.print("{\"error\":\"Failed to clear this cart\"}");
                }
            }
        } else if (request.getParameter(PARAMETER_METOD).equals(BUY_METHOD)) {
            int totalPrice=0;
            for (Audio items : cart){
                totalPrice+=items.getPrice();
            }
            Order order = new Order(LocalDateTime.now(), cart, totalPrice);
            boolean wasBought = facade.getOrderDao().add(order);
            try (PrintWriter pw = response.getWriter()) {
                if (wasBought) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    pw.print("{\"response\":\"Items was bought\"}");
                    session.invalidate();
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    pw.print("{\"error\":\"Failed to buy items\"}");
                }
            }
        }

        facade.closeSqlConnection();
    }
}
