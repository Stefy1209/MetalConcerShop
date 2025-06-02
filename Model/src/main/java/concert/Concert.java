package concert;

import core.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@jakarta.persistence.Entity
@Table(name = "concerts")
public class Concert extends Entity<UUID> {

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    @Column(name = "artist")
    private String artist;

    @Column(name = "date")
    private LocalDateTime date;

    public Concert() {
        super();
    }

    public Concert(UUID id, String name, String location, String artist, LocalDateTime date) {
        super(id);
        this.name = name;
        this.location = location;
        this.artist = artist;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Concert concert = (Concert) o;
        return Objects.equals(name, concert.name) && Objects.equals(location, concert.location) && Objects.equals(artist, concert.artist) && Objects.equals(date, concert.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, location, artist, date);
    }
}
