package com.example.petadmin.controller;

import com.example.petadmin.dto.AnimalCreateDto;
import com.example.petadmin.dto.AnimalDetailDto;
import com.example.petadmin.enums.AnimalStatus;
import com.example.petadmin.service.AnimalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "animals-controller", description = "Métodos de gerenciamento de animais")
@RestController
@RequestMapping("/animals")
public class AnimalController {

    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @Operation(summary = "Cadastra um novo animal", description = "Cadastra um novo animal")
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid AnimalCreateDto animalDto) {
        var animal = animalService.createAnimal(animalDto);

        return ResponseEntity.ok(new AnimalDetailDto(animal));
    }

    @Operation(summary = "Obtém todos animais", description = "Obtém uma lista de todos os animais cadastrados")
    @GetMapping
    public ResponseEntity<Object> getAllAnimals() {
        var animals = animalService.getAllAnimals();

        return ResponseEntity.ok(animals.stream().map(AnimalDetailDto::new).toList());
    }

    @Operation(summary = "Atualiza o status pelo ID", description = "Altera os status de um animal pelo ID")
    @PatchMapping("/change-status")
    public ResponseEntity<Object> changeStatus(@RequestParam Long animalId, @RequestParam AnimalStatus status) {
        var animal = animalService.changeStatus(animalId, status);

        return ResponseEntity.ok(new AnimalDetailDto(animal));
    }
}
