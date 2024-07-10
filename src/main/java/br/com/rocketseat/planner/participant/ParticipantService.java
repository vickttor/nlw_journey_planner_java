package br.com.rocketseat.planner.participant;

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

       System.out.println(participants.get(0).getId());
    }

    public void triggerConfirmationEmailToParticipants(UUID tripId) {

    }
}
