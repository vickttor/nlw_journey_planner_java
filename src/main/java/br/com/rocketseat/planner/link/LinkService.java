package br.com.rocketseat.planner.link;

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

    public LinkResponse registerLink(LinkRequestPayload payload, Trip trip) {
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
