package com.moreno.ecommerceMoreno.controller;

import com.moreno.ecommerceMoreno.dto.UploadResponseDTO;
import com.moreno.ecommerceMoreno.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/v1/uploads")
public class FileUploadController {

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping
    public ResponseEntity<UploadResponseDTO> uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/v1/uploads/")
                .path(fileName)
                .toUriString();

        return ResponseEntity.ok(new UploadResponseDTO(fileName, fileUrl));
    }

    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        Path filePath = Paths.get("./uploads").toAbsolutePath().normalize().resolve(filename);
        try {
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                String contentType = "application/octet-stream";

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}