package com.crud.demo.book;

import com.sun.net.httpserver.HttpsServer;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
     private BookRepository bookRepository;


    public List<BookModel> findAll(){
        return  bookRepository.findAll();
    }

    public BookModel criarLivro(BookModel bookModel){
        if (bookModel.getNome().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book name mustn't empty");
        }

        if (bookModel.getCategoria().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book category mustn't empty");
        }

        return bookRepository.save(bookModel);
    }

    public void deletarLivro(Long id){
        Optional<BookModel> exists = this.bookRepository.findById(id);
        if (exists.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        } else {
            bookRepository.deleteById(id);
        }
    }

    public BookModel update(Long id, BookModel bookMode){
       Optional<BookModel> newbook =  bookRepository.findById(id);
       if (newbook.isEmpty()) {
           return null;
       }

       BookModel book = new BookModel();
       book.setId(newbook.get().getId());
       book.setCategoria(bookMode.getCategoria());
       book.setNome(bookMode.getNome());
       return bookRepository.save(book);

    }

}
