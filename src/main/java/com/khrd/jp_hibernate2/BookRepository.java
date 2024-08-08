package com.khrd.jp_hibernate2;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
@Transactional
public class BookRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public BookRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Book getAllBookByID(UUID id) {
        return entityManager.find(Book.class,id);
    }


    public Book InsertBook(BookRequest bookRequest) {
        Book b = new Book();
        b.setTitle(bookRequest.getTitle());
        b.setDescription(bookRequest.getDescription());
        b.setAuthor(bookRequest.getAuthor());
        b.setPublicationYear(bookRequest.getPublicationYear());
        entityManager.persist(b);
        return b;
    }

    public List<Book> getAllBook() {
        String jpqlQuery = "SELECT e FROM Book e";
        Query query = entityManager.createQuery(jpqlQuery, Book.class);

        return query.getResultList();
    }

    public List<Book> getAllBookTitle(String title) {
        String jpqlQuery = "SELECT b FROM Book b WHERE b.title ILIKE :titlePattern";
        TypedQuery<Book> query = entityManager.createQuery(jpqlQuery, Book.class);
        query.setParameter("titlePattern", "%" + title + "%");
        return query.getResultList();
    }


    public Book removeBook(UUID id) {
        Book book = entityManager.find(Book.class, id);
        if (book != null) {
            entityManager.remove(book);
            System.out.println("Book with ID " + id + " removed successfully.");
        } else {
            System.out.println("Book with ID " + id + " not found.");
        }
        return book;
    }

    public Book updateBook(UUID id, BookRequest bookRequest) {
        Book book = entityManager.find(Book.class, id);
        if (book != null){
            entityManager.detach(book);
            System.out.println("Detach: "+book);

            book.setTitle(bookRequest.getTitle());
            book.setDescription(bookRequest.getDescription());
            book.setAuthor(bookRequest.getAuthor());
            book.setPublicationYear(bookRequest.getPublicationYear());
            entityManager.merge(book);
            return book;
        }else {
            System.out.println("Update data not success...!");
            return null;
        }
    }
}
