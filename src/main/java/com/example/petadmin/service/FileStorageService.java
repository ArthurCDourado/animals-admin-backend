package com.example.petadmin.service;

import com.example.petadmin.config.FileStoragePropertiesConfiguration;
import com.example.petadmin.exception.NotFoundException;
import com.example.petadmin.repository.AnimalRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;
    private final AnimalRepository animalRepository;

    public FileStorageService(FileStoragePropertiesConfiguration fileStorageProperties, AnimalRepository animalRepository) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        this.animalRepository = animalRepository;
    }

    public String uploadFile(MultipartFile file, long animalId) {

        var animal = animalRepository.findById(animalId).orElseThrow(() -> new NotFoundException("Animal not found"));

        String fileName = "animal_" + animalId + "." + getFileExtension(file.getOriginalFilename());

        try {
            Path targetLocation = fileStorageLocation.resolve(fileName);
            file.transferTo(targetLocation);

            animal.setImageUrl(targetLocation.toString());
            animalRepository.save(animal);

            return ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/files/download/")
                    .path(animalId + "")
                    .toUriString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    public static String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return ""; // No extension
        }

        int lastDotIndex = fileName.lastIndexOf('.');

        // Check if there's no dot or it's the last character
        if (lastDotIndex == -1 || lastDotIndex == fileName.length() - 1) {
            return ""; // No extension
        }

        return fileName.substring(lastDotIndex + 1);
    }

    public Resource downloadFile(long animalId) throws IOException {

        var animal = animalRepository.findById(animalId).orElseThrow(() -> new NotFoundException("Animal not found"));

        Path filePath = Paths.get(animal.getImageUrl());

        Resource resource = new UrlResource(filePath.toUri());

        return resource;
    }
}
