package ua.org.oa.nedvygav.dao;


import java.util.Collection;
import java.util.List;

public interface AbstractDao <T> {
    List<T> loadAll();
    default List<T> loadAllbyValue(long value) {
        return null;
    }
    T findById(long id);
    boolean delete (long id);
    boolean update (T changed);
    boolean add (T item);
    boolean addAll (Collection<T> collection);
}
