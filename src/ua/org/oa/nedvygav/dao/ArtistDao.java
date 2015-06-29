package ua.org.oa.nedvygav.dao;


import ua.org.oa.nedvygav.data.Artist;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ArtistDao implements AbstractDao<Artist>{
    private static final String TABLE_NAME = "artist";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_COUNTRY = "country";
    private static final String COLUMN_GENRE_ID = "genre_id";
    private Connection connection;
    private Statement statement;

    ArtistDao(Connection connection, Statement statement){
        this.connection=connection;
        this.statement=statement;
    }
    @Override
    public List<Artist> loadAll() {
        List<Artist> artists = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME + ";");
            while (resultSet.next()){
                long id = resultSet.getInt(COLUMN_ID);
                String name = resultSet.getString(COLUMN_NAME);
                String country = resultSet.getString(COLUMN_COUNTRY);
                long genreId = resultSet.getLong(COLUMN_GENRE_ID);
                artists.add(new Artist(id, name, country, genreId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return artists;
    }

    @Override
    public List<Artist> loadAllbyValue(long genreId){
        List<Artist> artists = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+COLUMN_GENRE_ID
                    +"="+genreId + ";");
            while (resultSet.next()){
                long id = resultSet.getInt(COLUMN_ID);
                String name = resultSet.getString(COLUMN_NAME);
                String country = resultSet.getString(COLUMN_COUNTRY);
                artists.add(new Artist(id, name, country, genreId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return artists;
    }


    @Override
    public Artist findById(long objectId) {
        Artist artist = null;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME + " WHERE " +
                    COLUMN_ID + "=" + objectId + ";");
            while (resultSet.next()){
                long id = resultSet.getInt(COLUMN_ID);
                String name = resultSet.getString(COLUMN_NAME);
                String country = resultSet.getString(COLUMN_COUNTRY);
                long genreId = resultSet.getLong(COLUMN_GENRE_ID);
                artist = new Artist(id, name, country, genreId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return artist;
    }

    @Override
    public boolean delete(long objectId) {
        try {
            statement.executeUpdate("DELETE FROM " + TABLE_NAME + " WHERE " +
                    COLUMN_ID + "=" + objectId + ";");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Artist changed) {
        try {
            statement.executeUpdate("UPDATE " + TABLE_NAME + " SET " +
                    COLUMN_NAME + "='" + changed.getName() + "', " + COLUMN_COUNTRY + "='" + changed.getOriginCountry() +
                    "' WHERE " + COLUMN_ID + "=" + changed.getId() + ";");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean add(Artist item) {
        try {
            statement.executeUpdate("INSERT INTO " + TABLE_NAME + " ("+COLUMN_NAME+","+COLUMN_COUNTRY+
                    ","+COLUMN_GENRE_ID+") VALUES('" +
                    item.getName() + "','" + item.getOriginCountry() + "','"+item.getGenreId()+"');");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<Artist> collection) {
        if (collection!=null){
            for (Artist artist : collection){
                add(artist);
            }
            return true;
        } else return false;

    }
}
