package com.vicentefirtline.rest_with_spring_boot_and_java_erudio.mapper.custom;

import com.vicentefirtline.rest_with_spring_boot_and_java_erudio.data.dto.v2.PersonDTOV2;
import com.vicentefirtline.rest_with_spring_boot_and_java_erudio.model.Person;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PersonMapper {

            //entidade para DTO
    public PersonDTOV2 convertEntityToDTO(Person person){
        PersonDTOV2 dto = new PersonDTOV2();
        dto.setId(person.getId());
        dto.setFirstName(person.getFirstName());
        dto.setLastName(person.getLastName());
        dto.setBirthday(person.getBirthday());
        dto.setAddress(person.getAddress());
        dto.setGender(person.getGender());
        return dto;
    }
           //DTO para entidade
    public Person convertDTOtoEntity(PersonDTOV2 person){
        Person entity = new Person();
        entity.setId(person.getId());
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setBirthday(person.getBirthday());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
        return entity;
    }

}
