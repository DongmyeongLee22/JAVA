package me.sun.springdi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookServiceTest {

    @Autowired
    BookService bookService;

    @Test
    void di() {
        // 어떻게 빈으로 등록하였을 때 얘들이 주입받아 질까?
        assertNotNull(bookService);
        assertNotNull(bookService.bookRepository);
    }

}