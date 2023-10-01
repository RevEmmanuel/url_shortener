package org.example.controllers;

import org.example.data.models.KhalidVideo;
import org.example.data.models.TifeVideo;
import org.example.data.repositories.KhalidVideoRepository;
import org.example.data.repositories.TifeVideoRepository;
import org.example.exceptions.UrlShortenerException;
import org.example.services.cloud.CloudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/video")
public class VideoUpload {

    @Autowired
    private CloudService cloudService;
    @Autowired
    private KhalidVideoRepository khalidVideoRepository;
    @Autowired
    private TifeVideoRepository tifeVideoRepository;


    @PostMapping(value = "/upload/{name}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadProfilePicture(@RequestParam(value = "file") MultipartFile file, @PathVariable String name) {
        try {
            String response = cloudService.upload(file);
            khalidVideoRepository.save(KhalidVideo.builder().name(name).videoUrl(response).build());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (UrlShortenerException exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @GetMapping(value = "/{name}")
    public ResponseEntity<String> getVideoUrl(@PathVariable String name) {
        try {
            KhalidVideo khalidVideo = khalidVideoRepository.findByName(name);
            return new ResponseEntity<>(khalidVideo.getVideoUrl(), HttpStatus.OK);
        }catch (UrlShortenerException exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PostMapping(value = "/upload-video/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadVideo(@RequestParam(value = "file") MultipartFile file, @PathVariable String userId) {
        try {
            String response = cloudService.upload(file);
            tifeVideoRepository.save(TifeVideo.builder().uniqueUserId(userId).videoUrl(response).build());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (UrlShortenerException exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @GetMapping(value = "/all/{userId}")
    public ResponseEntity<?> getVideosUrl(@PathVariable String userId) {
        try {
            List<TifeVideo> tifeVideo = tifeVideoRepository.findAllByUniqueUserId(userId);
            return new ResponseEntity<>(tifeVideo, HttpStatus.OK);
        }catch (UrlShortenerException exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }


}
