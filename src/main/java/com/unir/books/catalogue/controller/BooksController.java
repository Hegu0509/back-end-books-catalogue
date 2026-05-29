package com.unir.books.catalogue.controller;

import com.unir.books.catalogue.controller.model.GetBookResponseDto;
import com.unir.books.catalogue.controller.model.GetBooksResponseDto;
import com.unir.books.catalogue.service.GetBooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class BooksController {

    private final GetBooksService getBooksService;

    @GetMapping("books")
    public ResponseEntity<GetBooksResponseDto> getBooks() {
        return ResponseEntity.ok(getBooksService.getBooks());
    }

    @GetMapping("books/{bookId}")
    public ResponseEntity<GetBookResponseDto> getBook(@PathVariable Long bookId) {
        return ResponseEntity.ok(getBooksService.getBook(bookId));
    }

}
