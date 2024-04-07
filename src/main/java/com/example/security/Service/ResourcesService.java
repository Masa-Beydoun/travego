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


