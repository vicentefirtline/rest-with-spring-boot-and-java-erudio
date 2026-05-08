package com.vicentefirtline.rest_with_spring_boot_and_java_erudio.services;

import com.vicentefirtline.rest_with_spring_boot_and_java_erudio.data.dto.v1.PersonDTO;
import com.vicentefirtline.rest_with_spring_boot_and_java_erudio.data.dto.v2.PersonDTOV2;
import com.vicentefirtline.rest_with_spring_boot_and_java_erudio.exception.ResourceNotFoundException;
import com.vicentefirtline.rest_with_spring_boot_and_java_erudio.mapper.ObjectMapper;

import static com.vicentefirtline.rest_with_spring_boot_and_java_erudio.mapper.ObjectMapper.parseObject;

import com.vicentefirtline.rest_with_spring_boot_and_java_erudio.mapper.custom.PersonMapper;
import com.vicentefirtline.rest_with_spring_boot_and_java_erudio.model.Person;
import com.vicentefirtline.rest_with_spring_boot_and_java_erudio.repository.PersonRepository;
import jakarta.annotation.Nonnull;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service

public class PersonServices {

    private final AtomicLong counter = new AtomicLong();
    private org.slf4j.Logger logger = LoggerFactory.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;

    @Autowired
    PersonMapper converter;



    public List<PersonDTO> findAll(){
        logger.info("Finding all People!, pesquisar todas as pessoas");
         return  ObjectMapper.parseListObject(repository.findAll(),PersonDTO.class); //listando para DTO
    }



    public PersonDTO findById(Long id){
        logger.info("Finding one Person!");
        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No Records found for this ID," +
                " nao foram encontrados registros para o ID"));
        return parseObject(entity,PersonDTO.class); //usando a biblioteca static fica mais facil a listagem
    }

    public PersonDTO create(PersonDTO person){
        logger.info("Create one Person!");
        var entity = parseObject(person,Person.class);

        return parseObject(repository.save(entity),PersonDTO.class);
    }

    public PersonDTOV2 createV2(PersonDTOV2 person){
        logger.info("Create one Person!");
        var entity = converter.convertDTOtoEntity(person);

        return converter.convertEntityToDTO(repository.save(entity));
    }

    public PersonDTO update(@Nonnull @org.jetbrains.annotations.UnknownNullability PersonDTO person){
        logger.info("Updating one Person!");
        Person entity = repository.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundException("No Records found for this ID," +
                " nao foram encontrados registros para o ID"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return  parseObject(repository.save(entity),PersonDTO.class);
    }
    public void delete(Long id){
        logger.info("Deleting one Person!");

        Person entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No Records found for this ID," +
                " nao foram encontrados registros para o ID"));
        repository.delete(entity);
    }

    /* private Person mockPerson(int i) { //mocks
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Firstname"+i);
        person.setLastName("lastname"+i);
        person.setAddress("Some Address in Brasil");
        person.setGender("Male");
        return person;
    }*/
}
