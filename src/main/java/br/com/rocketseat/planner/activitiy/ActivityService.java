package br.com.rocketseat.planner.activitiy;

import br.com.rocketseat.planner.activitiy.dtos.ActivityData;
import br.com.rocketseat.planner.activitiy.dtos.ActivityRequestPayload;
import br.com.rocketseat.planner.activitiy.dtos.ActivityResponse;
import br.com.rocketseat.planner.trip.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    public ActivityResponse registerActivity(ActivityRequestPayload payload, Trip trip) {
        Activity newActivity = new Activity(payload.title(), payload.occurs_at(), trip);

        activityRepository.save(newActivity);

        return new ActivityResponse(newActivity.getId());
    }

    public List<ActivityData> getAllActivitiesFromTripId(UUID tripId) {
        return this.activityRepository.findByTripId(tripId).stream().map((activity)->
                new ActivityData(
                        activity.getId(),
                        activity.getTitle(),
                        activity.getOccursAt()
                )).toList();
    }
}
