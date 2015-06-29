package ua.org.oa.nedvygav.dao;



import ua.org.oa.nedvygav.data.Genre;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GenreDao implements AbstractDao<Genre>{
    private static final String TABLE_NAME = "genre";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private Connection connection;
    private Statement statement;

    GenreDao(Connection connection, Statement statement){
        this.connection=connection;
        this.statement=statement;
    }
    @Override
    public List<Genre> loadAll() {
        List<Genre> genres = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME + ";");
            while (resultSet.next()){
                int id = resultSet.getInt(COLUMN_ID);
                String name = resultSet.getString(COLUMN_NAME);
                genres.add(new Genre(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genres;
    }

    @Override
    public Genre findById(long objectId) {
        Genre genre = null;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME + " WHERE " +
            COLUMN_ID + "=" + objectId + ";");
            while (resultSet.next()){
                int id = resultSet.getInt(COLUMN_ID);
                String name = resultSet.getString(COLUMN_NAME);
                genre = new Genre(id, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genre;
    }

    @Override
    public boolean delete(long objectId) {
        try {
            statement.executeUpdate("DELETE FROM "+TABLE_NAME+" WHERE "+
            COLUMN_ID+"="+objectId+";");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Genre changed) {
        try {
            statement.executeUpdate("UPDATE "+TABLE_NAME+" SET "+
                    COLUMN_NAME+"='"+changed.getName()+"' WHERE "+COLUMN_ID+"="+changed.getId()+";");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean add(Genre item) {
        try {
            statement.executeUpdate("INSERT INTO "+TABLE_NAME+" "+" ("+COLUMN_NAME+") "+" VALUES('"+item.getName()+"');");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<Genre> collection) {
        if (collection!=null){
            for (Genre genre : collection){
                add(genre);
            }
            return true;
        } else return false;

    }
}
