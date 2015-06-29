package ua.org.oa.nedvygav.data;


public class Genre {
    private final long id;
    private String name = null;

    public Genre(long id, String name) {
        this.id = id;
        this.name = name;
    }
    public Genre(String name){
        this.id = 0;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
