package concert;

import repository.concert.ConcertRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ConcertService implements ConcertServiceInterface {
    private final ConcertRepository concertRepository;

    public ConcertService(ConcertRepository concertRepository) {
        this.concertRepository = concertRepository;
    }

    @Override
    public Optional<Concert> addConcert(String name, String location, String artist, LocalDateTime date) {
        Concert concert = new Concert(UUID.randomUUID(), name, location, artist, date);
        Optional<Concert> result = concertRepository.save(concert);
        if(result.isEmpty()) {
            return Optional.of(concert);
        }
        return result;
    }

    @Override
    public List<Concert> getConcerts() {
        return (List<Concert>) concertRepository.findAll();
    }
}
