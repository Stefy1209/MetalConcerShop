package concert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ConcertServiceInterface {
    Optional<Concert> addConcert(String name, String location, String artist, LocalDateTime date);
    List<Concert> getConcerts();
}
