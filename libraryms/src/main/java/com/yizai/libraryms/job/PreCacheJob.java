package com.yizai.libraryms.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yizai.libraryms.model.Book;
import com.yizai.libraryms.model.dto.BookQuery;
import com.yizai.libraryms.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 缓存预热任务
 *
 * @author Mena
 */
@Component
@Slf4j
public class PreCacheJob {

    @Resource
    private BookService bookService;

    @Resource
    private RedisTemplate<String, Page<Book>> redisTemplate;

    @Resource
    private RedissonClient redissonClient;

    // 每天执行，预热推荐书籍
    @Scheduled(cron = "0 40 14 * * *")
    public void doCacheRecommendUser() {
        RLock lock = redissonClient.getLock("yizai:precachejob:docache:lock");
        try {
            // 只有一个线程能够获取到值
            if (lock.tryLock(0, 30000L, TimeUnit.MILLISECONDS)) {
                System.out.println("CurrentThreadId: " + Thread.currentThread().getId());
                BookQuery redisKey = new BookQuery();
                Page<Book> bookPage = bookService.listBooksByPage(new Page<>(redisKey.getPageNum(), redisKey.getPageSize()), redisKey);
                ValueOperations<String, Page<Book>> valueOperations = redisTemplate.opsForValue();
                // 写缓存
                try {
                    valueOperations.set(redisKey.toString(), bookPage, 300000, TimeUnit.MILLISECONDS);
                } catch (Exception e) {
                    log.error("redis set key error", e);
                }
            }
        } catch (InterruptedException e) {
            log.error("doCacheRecommendBook error ", e);
        } finally {
            // 只能释放自己的锁
            if (lock.isHeldByCurrentThread()) {
                System.out.println("CurrentThreadId: " + Thread.currentThread().getId());
                lock.unlock();
            }
        }
    }
}