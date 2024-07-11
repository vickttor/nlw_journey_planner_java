package br.com.rocketseat.planner.trip;

import br.com.rocketseat.planner.activitiy.ActivityService;
import br.com.rocketseat.planner.activitiy.dtos.ActivityData;
import br.com.rocketseat.planner.activitiy.dtos.ActivityRequestPayload;
import br.com.rocketseat.planner.activitiy.dtos.ActivityResponse;
import br.com.rocketseat.planner.link.LinkService;
import br.com.rocketseat.planner.link.dtos.LinkData;
import br.com.rocketseat.planner.link.dtos.LinkRequestPayload;
import br.com.rocketseat.planner.link.dtos.LinkResponse;
import br.com.rocketseat.planner.participant.ParticipantService;
import br.com.rocketseat.planner.participant.dtos.ParticipantCreateResponse;
import br.com.rocketseat.planner.participant.dtos.ParticipantData;
import br.com.rocketseat.planner.participant.dtos.ParticipantRequestPayload;
import br.com.rocketseat.planner.trip.dtos.TripCreateResponse;
import br.com.rocketseat.planner.trip.dtos.TripData;
import br.com.rocketseat.planner.trip.dtos.TripRequestPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/trips")
public class TripController {

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private TripService tripService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private LinkService linkService;

    @PostMapping("")
    public ResponseEntity<TripCreateResponse> createTrip(@RequestBody TripRequestPayload payload) {
        TripCreateResponse createdTrip = this.tripService.createTrip(payload);

        return ResponseEntity.ok(createdTrip);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TripData> getTripDetails(@PathVariable UUID id){
        Optional<TripData> trip = this.tripService.getTripDetails(id);

        return trip.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trip> updateTripDetails(@PathVariable UUID id, @RequestBody TripRequestPayload payload){
        Optional<Trip> trip = this.tripService.findTripById(id);

        if(trip.isPresent()){
            Trip updatedTrip = this.tripService.updateTripDetails(trip.get(), payload);

            return ResponseEntity.ok(updatedTrip);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/confirm")
    public ResponseEntity<Trip> confirmTrip(@PathVariable UUID id){
        Optional<Trip> trip = this.tripService.findTripById(id);

        if(trip.isPresent()){
            Trip confirmedTrip = this.tripService.confirmTrip(trip.get());
            return ResponseEntity.ok(confirmedTrip);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/invite")
    public ResponseEntity<ParticipantCreateResponse> inviteParticipant(@PathVariable UUID id, @RequestBody ParticipantRequestPayload payload) {
        Optional<Trip> trip = this.tripService.findTripById(id);

        if(trip.isPresent()){
            Trip rawTrip = trip.get();

            ParticipantCreateResponse participantCreateResponse = this.participantService.registerParticipantToTrip(payload.email(), rawTrip);

            if(rawTrip.getIsConfirmed()) this.participantService.triggerConfirmationEmailToParticipant(payload.email());

            return ResponseEntity.ok(participantCreateResponse);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/participants")
    public ResponseEntity<List<ParticipantData>> getAllParticipants(@PathVariable UUID id) {
        List<ParticipantData> participantList = this.participantService.getAllParticipantsFromTrip(id);
        return ResponseEntity.ok(participantList);
    }

    @GetMapping("/{id}/activities")
    public ResponseEntity<List<ActivityData>> getAllActivities(@PathVariable UUID id) {
        List<ActivityData> activityDataList = this.activityService.getAllActivitiesFromTripId(id);
        return ResponseEntity.ok(activityDataList);
    }


    @PostMapping("/{id}/activities")
    public ResponseEntity<ActivityResponse> registerActivity(@PathVariable UUID id, @RequestBody ActivityRequestPayload payload) {
        Optional<Trip> trip = this.tripService.findTripById(id);

        if(trip.isPresent()){
            Trip rawTrip = trip.get();

            ActivityResponse activityResponse =  this.activityService.registerActivity(payload, rawTrip);

            return ResponseEntity.ok(activityResponse);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/links")
    public ResponseEntity<LinkResponse> registerLink(@PathVariable UUID id, @RequestBody LinkRequestPayload payload) {
        Optional<Trip> trip = this.tripService.findTripById(id);

        if(trip.isPresent()){
            Trip rawTrip = trip.get();

            LinkResponse linkResponse =  this.linkService.registerLink(payload, rawTrip);

            return ResponseEntity.ok(linkResponse);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/links")
    public ResponseEntity<List<LinkData>> getAllLinks(@PathVariable UUID id) {
        List<LinkData> linkDataList = this.linkService.getAllLinksFromTripId(id);
        return ResponseEntity.ok(linkDataList);
    }
}
