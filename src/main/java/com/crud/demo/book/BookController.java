package com.crud.demo.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/books")
public class BookController {

    @Autowired
    private BookService bookService;

    private BookMapper bookMapper = new BookMapper();


    @GetMapping
    private ResponseEntity<List<BookDTO>> listarLivros(){
       List<BookModel> list = bookService.findAll();
       List<BookDTO> listdto = new ArrayList<BookDTO>();
        for (int i = 0; i < list.size(); i++) {
            BookDTO bookDto = new BookDTO();
            bookDto.setId(list.get(i).getId());
            bookDto.setCategoria(list.get(i).getCategoria());
            bookDto.setNome(list.get(i).getNome());

            listdto.add(bookDto);
        }

        return ResponseEntity.ok().body(listdto);
    }

    @PostMapping
    private ResponseEntity criarLivro(@RequestBody BookDTO bookDto){
        BookModel bookModel = bookMapper.toEntity(bookDto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(bookModel.getId()).toUri();

        try {
            BookModel response = bookService.criarLivro(bookModel);
            return ResponseEntity.created(uri).body(response);
        } catch (ResponseStatusException error) {
            return ResponseEntity.status(error.getStatusCode()).body(error.getReason());
        }
    }

    @DeleteMapping("/{id}")
    private ResponseEntity deletarLivro(@PathVariable Long id){
        try {
            bookService.deletarLivro(id);
            return ResponseEntity.noContent().build();
        } catch (ResponseStatusException error) {
            return ResponseEntity.status(error.getStatusCode()).body(error.getReason());
        }
    }

    @PutMapping("/{id}")
    private ResponseEntity<BookModel> update(@PathVariable Long id, @RequestBody BookDTO bookDTO){
        BookModel bookModel = bookMapper.toEntity(bookDTO);
         BookModel response  = bookService.update(id, bookModel);
         if (response == null) {
             return ResponseEntity.notFound().build();
         }
        return ResponseEntity.ok().body(response);
    }

}
