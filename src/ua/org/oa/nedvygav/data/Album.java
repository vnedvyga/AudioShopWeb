package ua.org.oa.nedvygav.data;




public class Album {
    private final long id;
    private final long artistId;
    private String name = null;
    private int year = 0;

    public Album(long id, String name, int year, long artistId) {
        this.id = id;
        this.artistId = artistId;
        this.name = name;
        this.year = year;
    }
    public Album(String name, int year, long artistId) {
        this (0, name, year, artistId);
    }

    public long getId() {
        return id;
    }

    public long getArtistId() {
        return artistId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
