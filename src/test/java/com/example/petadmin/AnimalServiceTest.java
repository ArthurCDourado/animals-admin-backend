package com.example.petadmin;

import com.example.petadmin.dto.AnimalCreateDto;
import com.example.petadmin.enums.AnimalStatus;
import com.example.petadmin.service.AnimalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AnimalServiceTest {

    @Autowired
    private AnimalService service;

    private AnimalCreateDto getAnimalCreateDto() {
        AnimalCreateDto animalToCreate = new AnimalCreateDto();
        animalToCreate.setName("Test Name");
        animalToCreate.setDescription("Test Description");
        animalToCreate.setImageUrl("http://test.com/image.jpg");
        animalToCreate.setCategory("Test Category");
        animalToCreate.setBirthDate(OffsetDateTime.now(ZoneOffset.UTC));

        return animalToCreate;
    }

    @Test
    public void itShouldCreateAnimal() {
        var animal = getAnimalCreateDto();
        var persistedAnimal = service.createAnimal(animal);

        assertEquals(animal.getName(), persistedAnimal.getName());
    }

    @Test
    public void itShouldGetAllAnimals() {
        service.createAnimal(getAnimalCreateDto());
        service.createAnimal(getAnimalCreateDto());

        assertEquals(2, service.getAllAnimals().size());
    }

    @Test
    public void itShouldChangeAnimalStatus() {
        var animal = service.createAnimal(getAnimalCreateDto());

        assertEquals(AnimalStatus.ADOPTED, service.changeStatus(animal.getId(), AnimalStatus.ADOPTED).getStatus());
    }

}
