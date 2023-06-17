package org.example.security;

import org.example.data.dtos.responses.LinkDTO;
import org.example.data.models.Link;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class AppUtils {
    private static final int LENGTH_OF_LINK_NAME = 12;
    private static List<String> linkNamesAlreadyTaken = new ArrayList<>();
    public static final String BEARER = "Bearer ";
    public static final String ISSUER= "Administrator";

    public static String generateLinkName() {
        SecureRandom secureRandom = new SecureRandom();
        String charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder linkName = new StringBuilder();

        for (int i = 0; i < LENGTH_OF_LINK_NAME; i++) {
            int randomIndex = secureRandom.nextInt(charset.length());
            char character = charset.charAt(randomIndex);
            linkName.append(character);
        }
        if (linkNamesAlreadyTaken.contains(linkName.toString())) return AppUtils.generateLinkName();
        linkNamesAlreadyTaken.add(linkName.toString());
        return linkName.toString();
    }

    public static LinkDTO mapLinkEntityToLinkDTO(Link link){
        return LinkDTO.builder()
                .linkName(link.getLinkName())
                .title(link.getTitle())
                .url(link.getUrl())
                .id(link.getId())
                .dateCreated(link.getDateCreated())
                .dateLastModified(link.getDateLastModified())
                .build();
    }

    public static void setLinkNames(List<String> links) {
        linkNamesAlreadyTaken = links;
    }
}
