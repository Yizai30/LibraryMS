package com.yizai.libraryms.service;

import com.yizai.libraryms.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author yizai
 * @since 2022/10/29 12:17
 */
@SpringBootTest
public class InsertBooksTest {

    @Resource
    BookService bookService;

    private final ExecutorService executorService = new ThreadPoolExecutor(7, 1000,
            10000, TimeUnit.MINUTES, new ArrayBlockingQueue<>(10000));

    /**
     * 批量插入书籍
     */
    @Test
    public void doInsertBooks() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final int INSERT_NUM = 1000000;
        List<Book> bookList = new ArrayList<>();
        for (int i = 0; i < INSERT_NUM; i++) {
            Book book = new Book();
            book.setBookName("testBook" + i);
            book.setBookDescription("testBookDescription" + i);
            bookList.add(book);
        }
        bookService.saveBatch(bookList, INSERT_NUM);
        stopWatch.stop();
        // 49 秒 100 万条
        System.out.println(stopWatch.getTotalTimeMillis());
    }

    /**
     * 并发批量插入书籍
     * h2 数据库无法并发插入数据
     */
    @Test
    public void doConcurrencyInsertBooks() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final int INSERT_NUM = 10000;
        // divide into 20 groups
        int batchSize = 500;
        int j = 0;
        List<CompletableFuture<Void>> futureList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            List<Book> bookList = new ArrayList<>();
            while (true) {
                j++;
                Book book = new Book();
                book.setBookName("testBook" + j);
                book.setBookDescription("testBookDescription" + j);
                bookList.add(book);
                if (j % batchSize == 0) {
                    break;
                }
            }
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                System.out.println("ThreadName: " + Thread.currentThread().getName());
                bookService.saveBatch(bookList, batchSize);
            }, executorService);
            futureList.add(future);
        }
        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[]{})).join();
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());
    }
}
