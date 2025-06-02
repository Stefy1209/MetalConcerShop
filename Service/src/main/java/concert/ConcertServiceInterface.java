package concert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConcertServiceInterface {
    Optional<Concert> addConcert(String name, String location, String artist, LocalDateTime date);
    List<Concert> getConcerts();
    Optional<Concert> removeConcert(UUID id);
    Optional<Concert> updateConcert(Concert concert, String name, String location, String artist, LocalDateTime date);
}
