package ua.org.oa.nedvygav.dao;


import ua.org.oa.nedvygav.data.Album;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AlbumDao implements AbstractDao<Album>{
    private static final String TABLE_NAME = "album";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_ARTIST_ID = "artist_id";
    private Connection connection;
    private Statement statement;

    AlbumDao(Connection connection, Statement statement){
        this.connection=connection;
        this.statement=statement;
    }
    @Override
    public List<Album> loadAll() {
        List<Album> albums = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME + ";");
            while (resultSet.next()){
                long id = resultSet.getLong(COLUMN_ID);
                String name = resultSet.getString(COLUMN_NAME);
                int year = resultSet.getInt(COLUMN_YEAR);
                long artistId = resultSet.getLong(COLUMN_ARTIST_ID);
                albums.add(new Album(id, name, year, artistId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return albums;
    }
    @Override
    public List<Album> loadAllbyValue(long artist_id) {
        List<Album> albums = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+COLUMN_ARTIST_ID
                    +"="+artist_id + ";");
            while (resultSet.next()){
                long id = resultSet.getLong(COLUMN_ID);
                String name = resultSet.getString(COLUMN_NAME);
                int year = resultSet.getInt(COLUMN_YEAR);
                long artistId = resultSet.getLong(COLUMN_ARTIST_ID);
                albums.add(new Album(id, name, year, artistId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return albums;
    }

    @Override
    public Album findById(long objectId) {
        Album album = null;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME + " WHERE " +
                    COLUMN_ID + "=" + objectId + ";");
            while (resultSet.next()){
                long id = resultSet.getLong(COLUMN_ID);
                String name = resultSet.getString(COLUMN_NAME);
                int year = resultSet.getInt(COLUMN_YEAR);
                long artistId = resultSet.getLong(COLUMN_ARTIST_ID);
                album = new Album(id, name, year, artistId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return album;
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
    public boolean update(Album changed) {
        try {
            statement.executeUpdate("UPDATE " + TABLE_NAME + " SET " +
                    COLUMN_NAME + "='" + changed.getName() + "', " + COLUMN_YEAR + "='" + changed.getYear() +
                    "', "+ COLUMN_ARTIST_ID + "='" + changed.getArtistId() +
                    "' WHERE " + COLUMN_ID + "=" + changed.getId() + ";");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean add(Album item) {
        try {
            statement.executeUpdate("INSERT INTO " + TABLE_NAME + " (" + COLUMN_NAME + "," + COLUMN_YEAR +
                    "," + COLUMN_ARTIST_ID + ") VALUES('" +
                    item.getName()+"','"+item.getYear()+"','"+item.getArtistId()+"');");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<Album> collection) {
        if (collection!=null){
            for (Album album : collection){
                add(album);
            }
            return true;
        } else return false;

    }
}
