package br.com.rocketseat.planner.participant;

import br.com.rocketseat.planner.participant.dtos.ParticipantCreateResponse;
import br.com.rocketseat.planner.participant.dtos.ParticipantData;
import br.com.rocketseat.planner.trip.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ParticipantService {

    @Autowired
    private ParticipantRepository participantRepository;

    public void registerParticipantsToTrip(List<String> participantToInvite, Trip trip){
       List<Participant> participants = participantToInvite.stream().map((email) -> new Participant(email, trip)).toList();

       this.participantRepository.saveAll(participants);
    }

    public ParticipantCreateResponse registerParticipantToTrip(String email, Trip trip){
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
