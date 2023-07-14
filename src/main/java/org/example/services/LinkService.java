package org.example.services;

import org.example.data.dtos.requests.CreateLinkRequest;
import org.example.data.dtos.requests.UpdateLinkRequest;
import org.example.data.dtos.responses.LinkDTO;
import java.util.List;

public interface LinkService {
    LinkDTO createLink(CreateLinkRequest createLinkRequest);

    List<LinkDTO> getAllLinks();

    LinkDTO getLink(Long id);

    LinkDTO updateLink(UpdateLinkRequest updateLinkRequest);

    String deleteLink(Long id);

    String getOriginalUrl(String linkName);

    LinkDTO findLinkByTitle(String title);
}
