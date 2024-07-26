package com.example.petadmin.service;

import com.example.petadmin.dto.AnimalCreateDto;
import com.example.petadmin.entity.Animal;
import com.example.petadmin.enums.AnimalStatus;
import com.example.petadmin.exception.NotFoundException;
import com.example.petadmin.repository.AnimalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalService {

    private final AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    public Animal createAnimal(AnimalCreateDto animalDto) {
        Animal animal = new Animal();

        animal.setName(animalDto.getName());
        animal.setDescription(animalDto.getDescription());
        animal.setImageUrl(animalDto.getImageUrl());
        animal.setCategory(animalDto.getCategory());
        animal.setBirthdate(animalDto.getBirthDate());

        return animalRepository.save(animal);
    }

    public List<Animal> getAllAnimals() {
        return animalRepository.findAll();
    }

    public Animal changeStatus(Long animalId, AnimalStatus status) {
        var animal = animalRepository.findById(animalId).orElseThrow(() -> new NotFoundException("Animal not found"));

        animal.setStatus(status);

        return animalRepository.save(animal);
    }
}
