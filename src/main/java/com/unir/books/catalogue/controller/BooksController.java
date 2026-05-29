package com.unir.books.catalogue.controller;

import com.unir.books.catalogue.controller.model.GetBookResponseDto;
import com.unir.books.catalogue.controller.model.GetBooksResponseDto;
import com.unir.books.catalogue.controller.model.WriteBookRequestDto;
import com.unir.books.catalogue.service.CreateBooksService;
import com.unir.books.catalogue.service.DeleteBooksService;
import com.unir.books.catalogue.service.GetBooksService;
import com.unir.books.catalogue.service.ModifyBooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class BooksController {

    private final GetBooksService getBooksService;
    private final CreateBooksService createBooksService;
    private final ModifyBooksService modifyBooksService;
    private final DeleteBooksService deleteBooksService;

    @GetMapping("books")
    public ResponseEntity<GetBooksResponseDto> getBooks() {
        return ResponseEntity.ok(getBooksService.getBooks());
    }

    @GetMapping("books/{bookId}")
    public ResponseEntity<GetBookResponseDto> getBook(@PathVariable Long bookId) {
        return ResponseEntity.ok(getBooksService.getBook(bookId));
    }

    @PostMapping("books")
    public ResponseEntity<GetBookResponseDto> createBook(@RequestBody WriteBookRequestDto request) {
        return ResponseEntity.ok(createBooksService.createBook(request));
    }

    @PutMapping("books/{bookId}")
    public ResponseEntity<GetBookResponseDto> updateBook(
            @PathVariable Long bookId,
            @RequestBody WriteBookRequestDto request) {
        return ResponseEntity.ok(modifyBooksService.modifyBook(bookId, request));
    }

    @PatchMapping("books/{bookId}")
    public ResponseEntity<GetBookResponseDto> updateBook(
            @PathVariable Long bookId,
            @RequestBody String jsonPart) {
        return ResponseEntity.ok(modifyBooksService.modifyBook(bookId, jsonPart));
    }

    @DeleteMapping("books/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
        deleteBooksService.deleteBook(bookId);
        return ResponseEntity.noContent().build();
    }

}
