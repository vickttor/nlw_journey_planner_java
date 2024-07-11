package br.com.rocketseat.planner.activitiy;

import br.com.rocketseat.planner.activitiy.dtos.ActivityData;
import br.com.rocketseat.planner.activitiy.dtos.ActivityRequestPayload;
import br.com.rocketseat.planner.activitiy.dtos.ActivityResponse;
import br.com.rocketseat.planner.exceptions.InvalidDateRangeException;
import br.com.rocketseat.planner.exceptions.MaxLengthExceededException;
import br.com.rocketseat.planner.exceptions.MissingPropertyException;
import br.com.rocketseat.planner.trip.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    public ActivityResponse registerActivity(ActivityRequestPayload payload, Trip trip) throws
            MaxLengthExceededException,
            MissingPropertyException,
            InvalidDateRangeException,
            DateTimeParseException
    {

        if(payload.title() == null || payload.title().isEmpty()) throw new MissingPropertyException("title");
        if(payload.occurs_at() == null || payload.occurs_at().isEmpty()) throw new MissingPropertyException("occurs_at");
        if(payload.title().length() > 255) throw new MaxLengthExceededException("title", 255);

        LocalDateTime parsedOccursAt = LocalDateTime.parse(payload.occurs_at(), DateTimeFormatter.ISO_DATE_TIME);

        if(parsedOccursAt.isBefore(trip.getStartsAt()))
            throw new InvalidDateRangeException("occurs_at is before the trip start date");
        if(parsedOccursAt.isAfter(trip.getEndsAt()))
            throw new InvalidDateRangeException("occurs_at is after the trip end date");

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
