package ua.org.oa.nedvygav.data;



public class Artist {
    private final long id;
    private final long genreId;
    private String name = null;
    private String originCountry = null;

    public Artist(long id, String name, String originCountry, long genreId) {
        this.id = id;
        this.genreId = genreId;
        this.name = name;
        this.originCountry = originCountry;
    }
    public Artist(String name, String originCountry, long genreId) {
        this(0, name, originCountry, genreId);
    }

    public long getId() {
        return id;
    }

    public long getGenreId() {
        return genreId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }
}
