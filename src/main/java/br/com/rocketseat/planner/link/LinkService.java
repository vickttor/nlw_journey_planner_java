package br.com.rocketseat.planner.link;

import br.com.rocketseat.planner.exceptions.MaxLengthExceededException;
import br.com.rocketseat.planner.exceptions.MissingPropertyException;
import br.com.rocketseat.planner.link.dtos.LinkData;
import br.com.rocketseat.planner.link.dtos.LinkRequestPayload;
import br.com.rocketseat.planner.link.dtos.LinkResponse;
import br.com.rocketseat.planner.trip.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LinkService {

    @Autowired
    private LinkRepository linkRepository;

    public LinkResponse registerLink(LinkRequestPayload payload, Trip trip) throws
        MaxLengthExceededException,
        MissingPropertyException
    {
        if(payload.title() == null || payload.title().isEmpty()) throw new MissingPropertyException("title");
        if(payload.url() == null || payload.url().isEmpty()) throw new MissingPropertyException("url");

        if(payload.title().length() > 255) throw new MaxLengthExceededException("title", 255);
        if(payload.url().length() > 500) throw new MaxLengthExceededException("url", 500);

        Link newLink = new Link(payload.title(), payload.url(), trip);

        linkRepository.save(newLink);

        return new LinkResponse(newLink.getId());
    }

    public List<LinkData> getAllLinksFromTripId(UUID tripId) {
        return this.linkRepository.findByTripId(tripId).stream().map((activity)->
                new LinkData(
                        activity.getId(),
                        activity.getTitle(),
                        activity.getUrl()
                )).toList();
    }
}
