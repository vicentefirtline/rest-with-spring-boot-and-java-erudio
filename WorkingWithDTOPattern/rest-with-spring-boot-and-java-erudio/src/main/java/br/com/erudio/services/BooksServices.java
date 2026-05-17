package br.com.erudio.services;

import br.com.erudio.controllers.BooksController;
import br.com.erudio.controllers.PersonController;
import br.com.erudio.data.dto.BooksDTO;
import br.com.erudio.data.dto.PersonDTO;
import br.com.erudio.exception.RequiredObjectsNullException;
import br.com.erudio.exception.ResourceNotFoundException;
import br.com.erudio.model.Books;
import br.com.erudio.model.Person;
import br.com.erudio.repository.BooksRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.List;

import static br.com.erudio.mapper.ObjectMapper.parseListObjects;
import static br.com.erudio.mapper.ObjectMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BooksServices {
    private Logger logger = LoggerFactory.getLogger(BooksServices.class.getName());

    @Autowired
    BooksRepository repository;
    public List<BooksDTO> findAll() {

        logger.info("Finding all People!");

        var books = parseListObjects(repository.findAll(), BooksDTO.class);
        books.forEach(this::addHateoasLinks);

        return books;
    }

    public BooksDTO findById(Long id) {
        logger.info("Finding one Person!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        var dto = parseObject(entity, BooksDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public BooksDTO create(BooksDTO books) {

        if (books == null) throw new RequiredObjectsNullException(); //testei para mockito

        logger.info("Creating one Person!");
        var entity = parseObject(books, Books.class);

        var dto = parseObject(repository.save(entity), BooksDTO.class);
        addHateoasLinks (dto);
        return dto;
    }

    public BooksDTO update(BooksDTO books) {

        if (books == null) throw new RequiredObjectsNullException();

        logger.info("Updating one Person!");
        Books entity = repository.findById(books.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setAuthor(books.getAuthor());
        entity.setLaunchDate(books.getLaunchDate());
        entity.setTitle(books.getTitle());
        entity.setPrice(books.getPrice());

        var dto = parseObject(repository.save(entity), BooksDTO.class);
        addHateoasLinks (dto);
        return dto;
    }

    public void delete(Long id) {

        logger.info("Deleting one Person!");

        Books entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        repository.delete(entity);
    }

    private  void addHateoasLinks(BooksDTO dto) {
        dto.add(linkTo(methodOn(BooksController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(BooksController.class).findById(dto.getId())).withRel("delete").withType("DELETE"));
        dto.add(linkTo(methodOn(BooksController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(BooksController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(BooksController.class).update(dto)).withRel("update").withType("PUT"));
    }
}