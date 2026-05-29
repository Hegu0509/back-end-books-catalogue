package com.unir.books.catalogue.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import com.unir.books.catalogue.controller.model.GetBookResponseDto;
import com.unir.books.catalogue.controller.model.WriteBookRequestDto;
import com.unir.books.catalogue.exception.BookNotFoundException;
import com.unir.books.catalogue.repository.BookJpaRepository;
import com.unir.books.catalogue.repository.model.Book;
import com.unir.books.catalogue.utils.BookMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class ModifyBooksService {

    private final BookJpaRepository bookJpaRepository;
    private final BookMapper bookMapper;
    private final ObjectMapper objectMapper;

    @Transactional
    public GetBookResponseDto modifyBook(Long bookId, WriteBookRequestDto bookDto) {
        Book modifiedBook = bookMapper.asBook(bookId, bookDto);
        Book updatedBook = bookJpaRepository.save(modifiedBook);
        return bookMapper.asGetBookResponseDto(updatedBook);
    }

    @Transactional
    public GetBookResponseDto modifyBook(Long bookId, String jsonPart) {
        Book currentBook = bookJpaRepository
                .findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book with ID " + bookId + " not found."));
        try {
            JsonNode patch = objectMapper.readTree(jsonPart);
            WriteBookRequestDto currentBookDto = bookMapper.asWriteBookRequestDto(currentBook);
            JsonNode actualBook = objectMapper.valueToTree(currentBookDto);
            JsonMergePatch mergePatch = JsonMergePatch.fromJson(patch);
            JsonNode patchedBookNode = mergePatch.apply(actualBook);
            WriteBookRequestDto patchedBook = objectMapper.treeToValue(patchedBookNode, WriteBookRequestDto.class);
            Book savedBook = bookJpaRepository.save(bookMapper.asBook(bookId, patchedBook));
            return bookMapper.asGetBookResponseDto(savedBook);
        } catch (JsonProcessingException | JsonPatchException e) {
            log.error("Error processing JSON patch for book ID {}: {}", bookId, e.getMessage(), e);
            throw new RuntimeException("Error processing JSON patch", e);
        }
    }
}
