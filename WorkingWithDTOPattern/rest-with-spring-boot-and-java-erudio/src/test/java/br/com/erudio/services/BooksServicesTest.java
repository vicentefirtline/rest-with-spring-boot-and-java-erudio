package br.com.erudio.services;

import br.com.erudio.data.dto.BooksDTO;
import br.com.erudio.data.dto.PersonDTO;
import br.com.erudio.exception.RequiredObjectsNullException;
import br.com.erudio.model.Books;
import br.com.erudio.model.Person;
import br.com.erudio.repository.BooksRepository;
import br.com.erudio.repository.PersonRepository;
import br.com.erudio.unitetests.mapper.mocks.MockBooks;
import br.com.erudio.unitetests.mapper.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.print.Book;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BooksServicesTest {

    MockBooks input;

    @InjectMocks
    private BooksServices service; //declarei a classe original so que agora é simulado.


    @Mock //na classe original seria Autowired
    BooksRepository repository;

    @BeforeEach
    void setUp() {
        input = new MockBooks();
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void findById() {
        Books books = input.mockEntity(1);
        books.setId(1L); //valor inteiro tipo long
        when(repository.findById(1L)).thenReturn(Optional.of(books));
        var result = service.findById(1L);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                && link.getHref().endsWith("/api/books/v1/1")
                && link.getType().equals("GET")));

        //verificando um dado de um link depois do self do content negotiation.
        // verificando o verbo GET do http.

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/books/v1")
                        && link.getType().equals("GET")));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/books/v1")
                        && link.getType().equals("POST")));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("PUT")));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/books/v1/1")
                        && link.getType().equals("DELETE")));


        assertEquals("Author Test1" ,result.getAuthor());
        assertEquals("Title Test1",result.getTitle());
        assertEquals(new BigDecimal("25.0"),result.getPrice());
        assertNotNull(result.getLaunchDate());
    }




    @Test
    void create() {
        Books books = input.mockEntity(1);
        Books persisted = books;
        persisted.setId(1L); //valor inteiro tipo long

        BooksDTO dto = input.mockDTO(1);

        when(repository.save(any(Books.class))).thenReturn(persisted);
        var result = service.create(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/books/v1/1")
                        && link.getType().equals("GET")));

        //verificando um dado de um link depois do self do content negotiation.
        // verificando o verbo GET do http.

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/books/v1")
                        && link.getType().equals("GET")));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/books/v1")
                        && link.getType().equals("POST")));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/books/v1")
                        && link.getType().equals("PUT")));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/books/v1/1")
                        && link.getType().equals("DELETE")));


        assertEquals("Author Test1" ,result.getAuthor());
        assertEquals("Title Test1",result.getTitle());
        assertEquals(new BigDecimal("25.0"),result.getPrice());
        assertNotNull(result.getLaunchDate());
    }


    @Test
    void testCreateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectsNullException.class,
                ()->{
            service.create(null);
        });
        String expectedMessage = "NAO E PERMITIDO PERSISTIR UMA ENTIDADE COM VALOR NULO, IS NOT ALLOWED TO PERSIST A NULL OBJECT!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    void update() {
        Books books = input.mockEntity(1);
        Books persisted = books;
        persisted.setId(1L); //valor inteiro tipo long

        BooksDTO dto = input.mockDTO(1);

        when(repository.findById(1L)).thenReturn(Optional.of(books));
        when(repository.save(books)).thenReturn(persisted);
        var result = service.update(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());
        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/books/v1/1")
                        && link.getType().equals("GET")));

        //verificando um dado de um link depois do self do content negotiation.
        // verificando o verbo GET do http.

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/books/v1")
                        && link.getType().equals("GET")));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/books/v1")
                        && link.getType().equals("POST")));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/books/v1")
                        && link.getType().equals("PUT")));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/books/v1/1")
                        && link.getType().equals("DELETE")));


        assertEquals("Author Test1" ,result.getAuthor());
        assertEquals("Title Test1",result.getTitle());
        assertEquals(new BigDecimal("25.0"),result.getPrice());
        assertNotNull(result.getLaunchDate());

    }

    @Test
    void testUpdadeWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectsNullException.class,
                ()->{
                    service.update(null);
                });
        String expectedMessage = "NAO E PERMITIDO PERSISTIR UMA ENTIDADE COM VALOR NULO, IS NOT ALLOWED TO PERSIST A NULL OBJECT!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void delete() {
        Books books = input.mockEntity(1);
        books.setId(1L); //valor inteiro tipo long
        when(repository.findById(1L)).thenReturn(Optional.of(books));
         service.delete(1L);
         verify(repository,times(1)).findById(anyLong()) ;
        verify(repository,times(1)).delete(any(Books.class)) ;
        verifyNoMoreInteractions(repository);
    }

    @Test
    void findAll() {
        List<Books> list = input.mockEntityList();
        when(repository.findAll()).thenReturn(list);
        List<BooksDTO> books = service.findAll();

        assertNotNull(books);
        assertEquals(14,books.size());

        var booksOne = books.get(1);

        assertNotNull(booksOne);
        assertNotNull(booksOne.getId());
        assertNotNull(booksOne.getLinks());
        assertNotNull(booksOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/books/v1/1")
                        && link.getType().equals("GET")));

        //verificando um dado de um link depois do self do content negotiation.
        // verificando o verbo GET do http.

        assertNotNull(booksOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/books/v1")
                        && link.getType().equals("GET")));

        assertNotNull(booksOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/books/v1")
                        && link.getType().equals("POST")));

        assertNotNull(booksOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/books/v1")
                        && link.getType().equals("PUT")));

        assertNotNull(booksOne.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/books/v1/1")
                        && link.getType().equals("DELETE")));



        assertEquals("Author Test1" ,booksOne.getAuthor());
        assertEquals("Title Test1",booksOne.getTitle());
        assertEquals(new BigDecimal("25.0"),booksOne.getPrice());
        assertNotNull(booksOne.getLaunchDate());

       var booksFour = books.get(4);

        assertNotNull(booksFour);
        assertNotNull(booksFour.getId());
        assertNotNull(booksFour.getLinks());
        assertNotNull(booksFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/books/v1/4")
                        && link.getType().equals("GET")));

        //verificando um dado de um link depois do self do content negotiation.
        // verificando o verbo GET do http.

        assertNotNull(booksFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/books/v1")
                        && link.getType().equals("GET")));

        assertNotNull(booksFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/books/v1")
                        && link.getType().equals("POST")));

        assertNotNull(booksFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/books/v1")
                        && link.getType().equals("PUT")));

        assertNotNull(booksFour.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/books/v1/4")
                        && link.getType().equals("DELETE")));


        assertEquals("Author Test4" ,booksFour.getAuthor());
        assertEquals("Title Test4",booksFour.getTitle());
        assertEquals(new BigDecimal("100.0"),booksFour.getPrice());
        assertNotNull(booksFour.getLaunchDate());

        var booksSeven = books.get(7);

        assertNotNull(booksSeven);
        assertNotNull(booksSeven.getId());
        assertNotNull(booksSeven.getLinks());
        assertNotNull(booksSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/books/v1/7")
                        && link.getType().equals("GET")));

        //verificando um dado de um link depois do self do content negotiation.
        // verificando o verbo GET do http.

        assertNotNull(booksSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/books/v1")
                        && link.getType().equals("GET")));

        assertNotNull(booksSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/books/v1")
                        && link.getType().equals("POST")));

        assertNotNull(booksSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/books/v1")
                        && link.getType().equals("PUT")));

        assertNotNull(booksSeven.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/books/v1/7")
                        && link.getType().equals("DELETE")));


        assertEquals("Author Test7" ,booksSeven.getAuthor());
        assertEquals("Title Test7",booksSeven.getTitle());
        assertEquals(new BigDecimal("175.0"),booksSeven.getPrice());
        assertNotNull(booksSeven.getLaunchDate());



    }

}