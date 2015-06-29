package ua.org.oa.nedvygav.dao;

import ua.org.oa.nedvygav.data.Audio;
import ua.org.oa.nedvygav.data.Genre;
import ua.org.oa.nedvygav.data.Order;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class OrderDao implements AbstractDao<Order>{
    private static final String TABLE_ORDER = "orders";
    private static final String TABLE_ORDER_ITEMS = "order_items";
    private static final String COLUMN_ORDER_ID = "id";
    private static final String COLUMN_ARTIST_ID = "audio_id";
    private static final String COLUMN_DATE_TIME = "date_time";
    private static final String COLUMN_TOTAL_PRICE = "total_price";
    private static final String COLUMN_ORDER_ITEMS_ID = "order_id";
    private Connection connection;
    private Statement statement;

    OrderDao(Connection connection, Statement statement){
        this.connection=connection;
        this.statement=statement;
    }
    @Override
    public List<Order> loadAll() {
        List<Order> orders = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_ORDER + ";");
            while (resultSet.next()){
                long id = resultSet.getLong(COLUMN_ORDER_ID);
                String dateTimeString = resultSet.getString(COLUMN_DATE_TIME);
                LocalDateTime dateTime = LocalDateTime.parse(dateTimeString);
                int totalPrice = resultSet.getInt(COLUMN_TOTAL_PRICE);
                orders.add(new Order(id, dateTime, loadOrderItems(id), totalPrice));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    private List<Audio> loadOrderItems(long orderId){
        List<Audio> items = new ArrayList<>();
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM "
                    + TABLE_ORDER_ITEMS + " WHERE order_id=" + orderId + ";");
            while (resultSet.next()){
                int audioId = resultSet.getInt("audio_id");
                Audio item = new AudioDao(connection, connection.createStatement()).findById(audioId);
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public Order findById(long objectId) {
        Order order = null;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_ORDER + " WHERE " +
                    COLUMN_ORDER_ID + "=" + objectId + ";");
            while (resultSet.next()){
                long id = resultSet.getLong(COLUMN_ORDER_ID);
                String dateTimeString = resultSet.getString(COLUMN_DATE_TIME);
                LocalDateTime dateTime = LocalDateTime.parse(dateTimeString);
                int totalPrice = Integer.parseInt(COLUMN_TOTAL_PRICE);
                order = new Order(id, dateTime, loadOrderItems(id), totalPrice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    @Override
    public boolean delete(long objectId) {
        try {
            statement.executeUpdate("DELETE FROM "+TABLE_ORDER_ITEMS+" WHERE "+
                    COLUMN_ORDER_ITEMS_ID+"="+objectId+";");
            statement.executeUpdate("DELETE FROM "+TABLE_ORDER+" WHERE "+
                    COLUMN_ORDER_ID+"="+objectId+";");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Order changed) {

        return false;
    }

    @Override
    public boolean add(Order item) {
        try {
            long orderId = -1;
            statement.executeUpdate("INSERT INTO "+TABLE_ORDER+" "+" ("+COLUMN_DATE_TIME+", "+COLUMN_TOTAL_PRICE+") "+
                    " VALUES('"+item.getDateTime()+"','"+item.getTotalPrice()+"');");
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_ORDER + " WHERE " +
                    COLUMN_DATE_TIME + "='" + item.getDateTime() + "';");
            while (resultSet.next()){
                orderId = resultSet.getLong(COLUMN_ORDER_ID);
            }
            if (orderId>0){
                for (Audio audio : item.getItems()){
                    statement.executeUpdate("INSERT INTO "+TABLE_ORDER_ITEMS+" "+" ("+COLUMN_ORDER_ITEMS_ID+
                            ","+COLUMN_ARTIST_ID+") "+
                            " VALUES('"+orderId+"','"+audio.getId()+"');");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<Order> collection) {
        if (collection!=null){
            for (Order orders : collection){
                add(orders);
            }
            return true;
        } else return false;

    }
}
