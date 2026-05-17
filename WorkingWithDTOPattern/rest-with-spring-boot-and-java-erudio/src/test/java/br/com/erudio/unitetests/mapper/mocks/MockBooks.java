package br.com.erudio.unitetests.mapper.mocks;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.erudio.data.dto.BooksDTO;
import br.com.erudio.model.Books;

public class MockBooks {

    public Books mockEntity() {
        return mockEntity(0);
    }

    public BooksDTO mockDTO() {
        return mockDTO(0);
    }

    public List<Books> mockEntityList() {
        List<Books> books = new ArrayList<Books>();
        for (int i = 0; i < 14; i++) {
            books.add(mockEntity(i));
        }
        return books;
    }

    public List<BooksDTO> mockDTOList() {
        List<BooksDTO> books = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            books.add(mockDTO(i));
        }
        return books;
    }

    public Books mockEntity(Integer number) {
        Books book = new Books();
        book.setId(number.longValue());
        book.setAuthor("Author Test" + number);
        book.setLaunchDate(new Date(System.currentTimeMillis() - (number * 24 * 60 * 60 * 1000L)));
        book.setPrice(new BigDecimal("10.0").multiply(new BigDecimal(number)));
        book.setTitle("Title Test" + number);
        return book;
    }

    public BooksDTO mockDTO(Integer number) {
        BooksDTO book = new BooksDTO();
        book.setId(number.longValue());
        book.setAuthor("Author Test" + number);
        book.setLaunchDate(new Date(System.currentTimeMillis() - (number * 24 * 60 * 60 * 1000L)));
        book.setPrice(new BigDecimal("10.0").multiply(new BigDecimal(number)));
        book.setTitle("Title Test" + number);
        return book;
    }
}