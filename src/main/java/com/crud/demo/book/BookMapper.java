package com.crud.demo.book;

public class BookMapper {

    public BookDTO toDTO(BookModel bookModel) {
        BookDTO dto = new BookDTO();
        dto.setId(bookModel.getId());
        dto.setNome(bookModel.getNome());
        dto.setCategoria(bookModel.getCategoria());

        return dto;
    }

    public BookModel toEntity(BookDTO dto) {
        BookModel entity = new BookModel();
        entity.setId(dto.getId());
        entity.setNome(dto.getNome());
        entity.setCategoria(dto.getCategoria());
        return entity;
    }
}
