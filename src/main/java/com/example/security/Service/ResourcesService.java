package com.example.security.Service;

import com.backblaze.b2.client.B2StorageClient;
import com.backblaze.b2.client.contentSources.B2ByteArrayContentSource;
import com.backblaze.b2.client.exceptions.B2Exception;
import com.backblaze.b2.client.structures.B2UploadFileRequest;
import com.example.security.Models.Resources;
import com.example.security.Repository.ResourcesRepository;
import com.example.security.Request.LectureRequest;
import com.example.security.Request.ResourcesRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;


import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ResourcesService {
    private final ResourcesRepository resourcesRepository;
    private final static String uploads_path = "C:\\Users\\karee\\Desktop\\SelfishNet v2";

   //SAVE ON LOCAL MACHINE
    public ResponseEntity<?> Add_Resource(
              MultipartFile video
            , ResourcesRequest request) throws IOException {



        Path path3 = Paths.get(filePath + java.io.File.separator + video.getOriginalFilename());
/*
        var Video_Resource = new Video();


        if (video != null && !video.isEmpty() && video.getSize() > 0) {
            Video_Resource = Video.builder()
                    .name(request.getName())
                    .size(request.getSize())
                    //LECTURE ID??
                    .lecture(request.getLecture())
                    .length(request.getLength())
                    .build();
            resourcesRepository.save(Video_Resource);
            Files.copy(video.getInputStream(), path3);
        }

*/
        return ResponseEntity.ok().body("Resources added succesfully");

    }

    public void Delete_Resource(LectureRequest request) {

        List<Resources> resources = resourcesRepository.getResourcesByIdIn(request.getResources_Id());
        if (!resources.isEmpty()) {
            resourcesRepository.deleteAll(resources);
            Path file = Paths.get(filePath + java.io.File.separator + request.getName());
            try {


                Files.delete(file);
            } catch (IOException e) {
                throw new IllegalStateException("FILE NOT FOUND TO DELETE");
            }
        } else
            throw new IllegalStateException("FILE NOT FOUND TO DELETE X222222222");
    }

    //LOCAL MACHINE UPLOAD
    public final static String filePath = "C:\\Users\\karee\\Desktop\\SelfishNet v2";


    private final B2StorageClient b2StorageClient;

    public ResponseEntity<?> Cloud_Upload(MultipartFile file) throws IOException {


        try {


            byte[] fileBytes = file.getBytes();
            String fileName = file.getOriginalFilename();
            String fileContnet = file.getContentType();

            //    B2ByteArrayContentSource contentSource = new B2ByteArrayContentSource(fileBytes, fileContnet);
            b2StorageClient.uploadSmallFile(
                    (B2UploadFileRequest) B2ByteArrayContentSource.build(fileBytes)

            );
            return ResponseEntity.ok().body("UPLOADED");
        } catch (B2Exception e) {
            return ResponseEntity.badRequest().body("DIDNT UPLOADED");
        }

    }

   // @Async
    public ResponseEntity<?> upload_vid(
            MultipartFile video
            , ResourcesRequest request) throws IOException {
        Resources resources = resourcesRepository.save(Resources.builder()
                .name(video.getOriginalFilename())
                .lecture(request.getLecture())
                .size(video.getSize())
                .video(video.getBytes())
                .build());

        if (resources != null) {
            return ResponseEntity.badRequest().body("file uploaded successfully : " + video.getOriginalFilename());
        }
        return ResponseEntity.ok().body("UPLAODED DONE");
    }


    public Mono<Resource> stream(LectureRequest request ) {
        return Mono.fromSupplier(() -> {
            // Retrieve the Resources entity by title from your repository
            Resources lecture_Resource = resourcesRepository.getResourcesByLectureId(request.getLecture_Id());

            if (!lecture_Resource.equals(null)) {

                byte[] videoBytes = lecture_Resource.getVideo();

                // Create a ByteArrayResource to stream the video bytes
                ByteArrayResource byteArrayResource = new ByteArrayResource(videoBytes);

                return byteArrayResource;
            } else {
                // If the video is not found, return empty resource
                return new ByteArrayResource(new byte[0]);
            }
        });
    }

}


