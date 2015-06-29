package ua.org.oa.nedvygav.data;




public class Audio {
    private final long id;
    private final long albumId;
    private final long artistId;
    private final int duration;
    private String name = null;
    private int price = 0;

    public Audio(long id, String name, int duration, int price, long artistId, long albumId) {
        this.id = id;
        this.albumId = albumId;
        this.artistId = artistId;
        this.duration = duration;
        this.name = name;
        this.price = price;
    }
    public Audio(String name, int duration, int price, long artistId, long albumId) {
        this(0, name, duration, price, artistId, albumId);
    }

    public long getId() {
        return id;
    }

    public long getAlbumId() {
        return albumId;
    }

    public long getArtistId() {
        return artistId;
    }

    public int getDuration() {
        return duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Audio audio = (Audio) o;

        return id == audio.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
