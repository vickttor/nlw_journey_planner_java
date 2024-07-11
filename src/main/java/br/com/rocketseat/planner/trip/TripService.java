package br.com.rocketseat.planner.trip;

import br.com.rocketseat.planner.exceptions.InvalidDateRangeException;
import br.com.rocketseat.planner.exceptions.InvalidEmailException;
import br.com.rocketseat.planner.exceptions.MaxLengthExceededException;
import br.com.rocketseat.planner.exceptions.MissingPropertyException;
import br.com.rocketseat.planner.participant.ParticipantService;
import br.com.rocketseat.planner.trip.dtos.TripCreateResponse;
import br.com.rocketseat.planner.trip.dtos.TripData;
import br.com.rocketseat.planner.trip.dtos.TripRequestPayload;
import br.com.rocketseat.planner.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.UUID;

@Service
public class TripService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private ParticipantService participantService;

    public TripCreateResponse createTrip(TripRequestPayload payload) throws
        MaxLengthExceededException,
        InvalidEmailException,
        InvalidDateRangeException,
        DateTimeParseException,
        MissingPropertyException
    {
        if(payload.owner_name() == null || payload.owner_name().isEmpty()) throw new MissingPropertyException("name");
        if(payload.owner_email() == null || payload.owner_email().isEmpty()) throw new MissingPropertyException("email");
        if(payload.destination() == null || payload.destination().isEmpty()) throw new MissingPropertyException("destination");
        if(!Validator.isValidEmail(payload.owner_email()))
            throw new InvalidEmailException("The email " + payload.owner_email() + " is not valid");

        if(payload.emails_to_invite() == null) throw new MissingPropertyException("emails_to_invite");
        if(payload.owner_name().length() > 255) throw new MaxLengthExceededException("name", 255);
        if(payload.owner_email().length() > 255) throw new MaxLengthExceededException("email", 255);
        if(payload.destination().length() > 255) throw new MaxLengthExceededException("destination", 255);

        LocalDateTime parsedStartsAt = LocalDateTime.parse(payload.starts_at(), DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime parsedEndsAt = LocalDateTime.parse(payload.ends_at(), DateTimeFormatter.ISO_DATE_TIME);

        if(!parsedStartsAt.isBefore(parsedEndsAt))
            throw new InvalidDateRangeException("Start Date is not before the end date");

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

    public Trip updateTripDetails(Trip rawTrip, TripRequestPayload payload) throws
        MaxLengthExceededException,
        InvalidEmailException,
        InvalidDateRangeException,
        DateTimeParseException,
        MissingPropertyException
    {

        if(payload.destination() == null || payload.destination().isEmpty()) throw new MissingPropertyException("destination");
        if(payload.destination().length() > 255) throw new MaxLengthExceededException("destination", 255);

        LocalDateTime parsedStartsAt = LocalDateTime.parse(payload.starts_at(), DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime parsedEndsAt = LocalDateTime.parse(payload.ends_at(), DateTimeFormatter.ISO_DATE_TIME);

        if(!parsedStartsAt.isBefore(parsedEndsAt))
            throw new InvalidDateRangeException("Start Date is not before the end date");

        rawTrip.setEndsAt(parsedStartsAt);
        rawTrip.setEndsAt(parsedEndsAt);
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
