package ua.org.oa.nedvygav.dao;



import ua.org.oa.nedvygav.data.Audio;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AudioDao implements AbstractDao<Audio>{
    private static final String TABLE_NAME = "audio";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DURATION = "duration";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_ARTIST_ID = "artist_id";
    private static final String COLUMN_ALBUM_ID = "album_id";
    private Connection connection;
    private Statement statement;

    AudioDao(Connection connection, Statement statement){
        this.connection=connection;
        this.statement=statement;
    }
    @Override
    public List<Audio> loadAll() {
        List<Audio> audios = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME + ";");
            while (resultSet.next()){
                long id = resultSet.getLong(COLUMN_ID);
                String name = resultSet.getString(COLUMN_NAME);
                int duration = resultSet.getInt(COLUMN_DURATION);
                int price = resultSet.getInt(COLUMN_PRICE);
                long artistId = resultSet.getLong(COLUMN_ARTIST_ID);
                long albumId = resultSet.getLong(COLUMN_ALBUM_ID);
                audios.add(new Audio(id, name, duration, price, artistId, albumId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return audios;
    }
    @Override
    public List<Audio> loadAllbyValue(long album_id) {
        List<Audio> audios = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+COLUMN_ALBUM_ID
                    +"="+album_id + ";");
            while (resultSet.next()){
                long id = resultSet.getLong(COLUMN_ID);
                String name = resultSet.getString(COLUMN_NAME);
                int duration = resultSet.getInt(COLUMN_DURATION);
                int price = resultSet.getInt(COLUMN_PRICE);
                long artistId = resultSet.getLong(COLUMN_ARTIST_ID);
                long albumId = resultSet.getLong(COLUMN_ALBUM_ID);
                audios.add(new Audio(id, name, duration, price, artistId, albumId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return audios;
    }

    @Override
    public Audio findById(long objectId) {
        Audio audio = null;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME + " WHERE " +
                    COLUMN_ID + "=" + objectId + ";");
            while (resultSet.next()){
                long id = resultSet.getLong(COLUMN_ID);
                String name = resultSet.getString(COLUMN_NAME);
                int duration = resultSet.getInt(COLUMN_DURATION);
                int price = resultSet.getInt(COLUMN_PRICE);
                long artistId = resultSet.getLong(COLUMN_ARTIST_ID);
                long albumId = resultSet.getLong(COLUMN_ALBUM_ID);
                audio = new Audio(id, name, duration, price, artistId, albumId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return audio;
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
    public boolean update(Audio changed) {
        try {
            statement.executeUpdate("UPDATE " + TABLE_NAME + " SET " +
                    COLUMN_NAME + "='" + changed.getName() + "', " + COLUMN_PRICE + "='" + changed.getPrice() + "', " +
                    COLUMN_DURATION + "='" + changed.getDuration() + "', " + COLUMN_ARTIST_ID + "='" + changed.getArtistId()
                    + "', " + COLUMN_ALBUM_ID + "='" + changed.getAlbumId() + "' WHERE " + COLUMN_ID + "=" + changed.getId() + ";");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean add(Audio item) {
        try {
            statement.executeUpdate("INSERT INTO " + TABLE_NAME + " (" + COLUMN_NAME + "," + COLUMN_DURATION +
                    "," + COLUMN_PRICE + "," + COLUMN_ARTIST_ID + "," + COLUMN_ALBUM_ID + ") VALUES('" +
                    item.getName() + "','" + item.getDuration() + "','" + item.getPrice()+"','"+
            item.getArtistId()+"','"+item.getAlbumId()+"');");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<Audio> collection) {
        if (collection!=null){
            for (Audio audio : collection){
                add(audio);
            }
            return true;
        } else return false;

    }
}
