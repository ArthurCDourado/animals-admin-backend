package com.example.petadmin.controller;

import com.example.petadmin.service.FileStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "file-storage-controller", description = "Métodos de gerenciamento de arquivos")
@Controller
@RequestMapping("/files")
public class FileStorageController {

    private final FileStorageService fileStorageService;

    public FileStorageController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @Operation(summary = "Upload do arquivo por ID", description = "Faz o download de um arquivo pelo id do registro")
    @PostMapping("/upload/{animalId}")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable long animalId) {
        var fileDownloadUri = fileStorageService.uploadFile(file, animalId);

        return ResponseEntity.ok("File uploaded successfully. Download link: " + fileDownloadUri);
    }

    @Operation(summary = "Download do arquivo por id", description = "Faz o download de um arquivo pelo id do registro")
    @GetMapping("/download/{animalId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable long animalId,
                                                 HttpServletRequest request) throws IOException {

        var resource = fileStorageService.downloadFile(animalId);

        String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
