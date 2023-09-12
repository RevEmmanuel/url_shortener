package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.data.models.UserEntity;
import org.example.exceptions.UrlShortenerException;
import org.example.exceptions.UserNotAuthorizedException;
import org.example.security.AppUtils;
import org.example.data.dtos.requests.CreateLinkRequest;
import org.example.data.dtos.requests.UpdateLinkRequest;
import org.example.data.dtos.responses.LinkDTO;
import org.example.data.models.Link;
import org.example.data.repositories.LinkRepository;
import org.example.exceptions.LinkNotFoundException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LinkServiceImpl implements LinkService {

    private final LinkRepository linkRepository;


    private final UserService userService;

    @Override
    public LinkDTO createLink(CreateLinkRequest createLinkRequest) {
        List<LinkDTO> userLinks = getAllLinks();
        for (LinkDTO link : userLinks) {
            if (link.getTitle().equals(createLinkRequest.getTitle())) throw new UrlShortenerException("Title has been used previously.");
        }
        Link newLink = Link.builder()
                .linkName(AppUtils.generateLinkName())
                .url(createLinkRequest.getUrl())
                .title(createLinkRequest.getTitle())
                .owner(UserEntity.builder().id(userService.getCurrentUser().getId()).build())
                .build();
        Link savedLink = linkRepository.save(newLink);
        return AppUtils.mapLinkEntityToLinkDTO(savedLink);
    }

    @Override
    public List<LinkDTO> getAllLinks() {
        return linkRepository.findAllByOwner(userService.getCurrentUser()).stream().map(AppUtils::mapLinkEntityToLinkDTO).toList();
    }

    @Override
    public LinkDTO getLink(Long id) {
        Link link = linkRepository.findById(id).orElseThrow(LinkNotFoundException::new);
        if (!link.getOwner().getId().equals(userService.getCurrentUser().getId())) throw new UserNotAuthorizedException();
        return AppUtils.mapLinkEntityToLinkDTO(link);
    }

    @Override
    public LinkDTO updateLink(UpdateLinkRequest updateLinkRequest) {
        UserEntity user = userService.getCurrentUser();
        Link savedLink = linkRepository.findByOwnerAndId(user, updateLinkRequest.getId()).orElseThrow(LinkNotFoundException::new);

        savedLink.setUrl(updateLinkRequest.getUrl());
        savedLink.setTitle(updateLinkRequest.getTitle());
        savedLink.setDateLastModified(LocalDateTime.now());

        Link updatedLink = linkRepository.save(savedLink);
        return AppUtils.mapLinkEntityToLinkDTO(updatedLink);
    }

    @Override
    public String deleteLink(Long id) {
        Link foundLink = linkRepository.findById(id).orElseThrow(LinkNotFoundException::new);
        if (!foundLink.getOwner().getId().equals(userService.getCurrentUser().getId())) throw new UserNotAuthorizedException();
        linkRepository.delete(foundLink);
        return "DELETED SUCCESSFULLY";
    }

    @Override
    public String getOriginalUrl(String linkName) {
        Link link = linkRepository.findByLinkName(linkName).orElseThrow(LinkNotFoundException::new);
        return link.getUrl();
    }

    @Override
    public LinkDTO findLinkByTitle(String title) {
        return AppUtils.mapLinkEntityToLinkDTO(linkRepository.findByTitleAndOwner(title, userService.getCurrentUser()).orElseThrow(LinkNotFoundException::new));
    }

}
