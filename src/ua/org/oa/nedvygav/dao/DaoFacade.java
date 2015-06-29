package ua.org.oa.nedvygav.dao;
import ua.org.oa.nedvygav.data.*;

import javax.servlet.ServletContext;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DaoFacade {
    private static final String URL = "jdbc:mysql://localhost/audioshop_db";
    private static final String LOGIN = "root";
    private static final String PASS = "toor";

    private Connection connection;
    private Statement statement;

    public DaoFacade(ServletContext context){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL, LOGIN, PASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (connection == null){
            System.exit(1);
        }
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public AbstractDao<Genre> getGenreDao(){
        return new GenreDao(connection,statement);
    }
    public AbstractDao<Artist> getArtistDao(){
        return new ArtistDao(connection,statement);
    }
    public AbstractDao<Album> getAlbumDao(){
        return new AlbumDao(connection,statement);
    }
    public AbstractDao<Audio> getAudioDao(){
        return new AudioDao(connection,statement);
    }
    public AbstractDao<Order> getOrderDao(){
        return new OrderDao(connection,statement);
    }

    public void closeSqlConnection(){
        if (statement!=null){
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection!=null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
