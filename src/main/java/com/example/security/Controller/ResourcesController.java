package com.example.security.Controller;

import ai.djl.Model;
import com.example.security.Request.LectureRequest;
import com.example.security.Request.ResourcesRequest;
import com.example.security.Service.ResourcesService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController
@RequestMapping("/Resources")
@RequiredArgsConstructor
public class ResourcesController {

  private final   ResourcesService resourcesService;
//@PreAuthorize("hasAnyRole('AUTHOR','SIMPLE_STUDENT')")
  @GetMapping(value = "/stream", produces = "video/mp4")
  //, @RequestHeader("Range") String range
  public Mono<Resource> getVideo(@RequestBody LectureRequest request) {

    return resourcesService.stream(request);
  }

  }

