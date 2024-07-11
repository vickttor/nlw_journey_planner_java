package br.com.rocketseat.planner.participant;

import br.com.rocketseat.planner.exceptions.InvalidEmailException;
import br.com.rocketseat.planner.exceptions.MaxLengthExceededException;
import br.com.rocketseat.planner.participant.dtos.ParticipantCreateResponse;
import br.com.rocketseat.planner.participant.dtos.ParticipantData;
import br.com.rocketseat.planner.trip.Trip;
import br.com.rocketseat.planner.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ParticipantService {

    @Autowired
    private ParticipantRepository participantRepository;

    public void registerParticipantsToTrip(List<String> participantToInvite, Trip trip) throws
        MaxLengthExceededException,
        InvalidEmailException
    {
       List<Participant> participants = participantToInvite.stream()
           .map((email) -> {
               if(email.length() > 255) throw new MaxLengthExceededException(email, 255);
               if(!Validator.isValidEmail(email)) throw new InvalidEmailException("The email " + email + " is not valid");
               return new Participant(email, trip);
           }).toList();

       this.participantRepository.saveAll(participants);
    }

    public ParticipantCreateResponse registerParticipantToTrip(String email, Trip trip) throws
        MaxLengthExceededException,
        InvalidEmailException
    {
        if(email.length() > 255) throw new MaxLengthExceededException(email, 255);
        if(!Validator.isValidEmail(email)) throw new InvalidEmailException("The email " + email + " is not valid");

        Participant participant = new Participant(email, trip);

        this.participantRepository.save(participant);

        return new ParticipantCreateResponse(participant.getId());
    }

    public void triggerConfirmationEmailToParticipants(UUID tripId) {

    }

    public void triggerConfirmationEmailToParticipant(String email) {

    }

    public List<ParticipantData> getAllParticipantsFromTrip(UUID tripId){
        return this.participantRepository.findByTripId(tripId).stream().map((participant)->
                new ParticipantData(
                        participant.getId(),
                        participant.getName(),
                        participant.getEmail(),
                        participant.getIsConfirmed()
                        )).toList();
    }
}
