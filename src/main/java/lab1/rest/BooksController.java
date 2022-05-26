package lab1.rest;

import lab1.jpa.entities.Author;
import lab1.jpa.entities.Book;
import lab1.jpa.entities.Library;
import lab1.jpa.persistence.AuthorsDAO;
import lab1.jpa.persistence.BooksDAO;
import lab1.jpa.persistence.LibrariesDAO;
import lab1.rest.contracts.AuthorDTO;
import lab1.rest.contracts.BookDTO;
import lab1.rest.contracts.LibraryDTO;
import lab1.services.BookGenerator;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@ApplicationScoped
@Path("/books")
public class BooksController {
    @Inject
    @Setter
    @Getter
    private BooksDAO booksDAO;

    @Inject
    @Setter
    @Getter
    private AuthorsDAO authorsDAO;

    @Inject
    @Setter
    @Getter
    private LibrariesDAO librariesDAO;

    @Inject
    private BookGenerator bookGenerator;

    @Context
    private UriInfo uriInfo;

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") final int id) {
        Book book = booksDAO.findById(id);
        if (book == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setName(book.getName());
        bookDTO.setIsbnCode(book.getIsbnCode());

        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(book.getAuthor().getId());
        authorDTO.setName(book.getAuthor().getName());

        authorDTO.setLastName(book.getAuthor().getLastName());
        authorDTO.setFullAuthorName(book.getAuthor().getFullAuthorName());
        bookDTO.setAuthor(authorDTO);

        LibraryDTO libraryDTO = new LibraryDTO();
        libraryDTO.setId(book.getLibrary().getId());
        libraryDTO.setName(book.getLibrary().getName());

        bookDTO.setLibrary(libraryDTO);
        return Response.ok(bookDTO).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response create(@NotNull BookDTO bookDTO) {
        Author c = authorsDAO.findById(bookDTO.getAuthor().getId());
        System.out.println(bookDTO.getAuthor().getId());
        if(c == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Book must have a valid authorId").build();
        }
        Library n = librariesDAO.findById(bookDTO.getLibrary().getId());
        if(n == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Book must have a valid LibraryId").build();
        }
        n.getAuthorsWithBooks().add(c);
        Book book = new Book();
        book.setName(bookDTO.getName());
        book.setIsbnCode(bookGenerator.generateId());

        book.setAuthor(c);
        book.setLibrary(n);
        booksDAO.persist(book);
        return Response.created(uriInfo.getRequestUri().resolve(String.valueOf(book.getId()))).build();
    }

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response update(
            @PathParam("id") final int bookId,
            @NotNull BookDTO bookDTO) {
        try {
            Book existingBook = booksDAO.findById(bookId);
            if (existingBook == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Author c = authorsDAO.findById(bookDTO.getAuthor().getId());
            if(c == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Book must have a valid authorId").build();
            }
            Library n = librariesDAO.findById(bookDTO.getLibrary().getId());
            if(n == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Book must have a valid libraryId").build();
            }
            n.getAuthorsWithBooks().add(c);
            existingBook.setName(bookDTO.getName());
            existingBook.setAuthor(c);
            existingBook.setLibrary(n);

            booksDAO.update(existingBook);
            return Response.ok().build();
        } catch (OptimisticLockException ole) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }
}
