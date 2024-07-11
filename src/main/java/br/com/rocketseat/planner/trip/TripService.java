package br.com.rocketseat.planner.trip;

import br.com.rocketseat.planner.participant.ParticipantService;
import br.com.rocketseat.planner.trip.dtos.TripCreateResponse;
import br.com.rocketseat.planner.trip.dtos.TripData;
import br.com.rocketseat.planner.trip.dtos.TripRequestPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Service
public class TripService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private ParticipantService participantService;


    public TripCreateResponse createTrip(TripRequestPayload payload) {
        Trip newTrip = new Trip(payload);

        this.tripRepository.save(newTrip);

        this.participantService.registerParticipantsToTrip(payload.emails_to_invite(), newTrip);

        return new TripCreateResponse(newTrip.getId());
    }

    public Optional<TripData> getTripDetails(UUID tripId) {
        return this.tripRepository.findById(tripId).map((trip)-> new TripData(
                trip.getId(),
                trip.getDestination(),
                trip.getStartsAt(),
                trip.getEndsAt(),
                trip.getIsConfirmed(),
                trip.getOwnerName(),
                trip.getOwnerEmail()
        ));
    }

    public Trip updateTripDetails(Trip rawTrip, TripRequestPayload payload){

        rawTrip.setEndsAt(LocalDateTime.parse(payload.ends_at(), DateTimeFormatter.ISO_DATE_TIME));
        rawTrip.setEndsAt(LocalDateTime.parse(payload.starts_at(), DateTimeFormatter.ISO_DATE_TIME));
        rawTrip.setDestination(payload.destination());

        this.saveTrip(rawTrip);

        return rawTrip;
    }

    public Trip confirmTrip(Trip rawTrip){
        rawTrip.setIsConfirmed(true);

        this.saveTrip(rawTrip);

        this.participantService.triggerConfirmationEmailToParticipants(rawTrip.getId());

        return rawTrip;
    }

    public Optional<Trip> findTripById(UUID tripId) {
       return this.tripRepository.findById(tripId);
    }

    public void saveTrip(Trip trip) {
        this.tripRepository.save(trip);
    }
}
