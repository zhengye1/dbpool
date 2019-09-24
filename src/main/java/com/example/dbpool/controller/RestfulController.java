package com.example.dbpool.controller;

import com.example.dbpool.entity.ResultEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class RestfulController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        LOGGER.info("INFO");
        LOGGER.trace("TRACE");
        LOGGER.error("ERROR");
        LOGGER.debug("DEBUG");
    }

    @GetMapping(value = "/test")
    public ResponseEntity<ResultEntity> test() throws InterruptedException {
        //LOGGER.info("Logger test");
        dbtest();
        ResultEntity result = ResultEntity.builder().result("SUCCESS").build();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //@Transactional
    public void dbtest() throws InterruptedException {
        LocalDateTime now = LocalDateTime.now();
        LOGGER.info("DB test on {} on schedule 1", now);
        String transactionId = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + String
            .format("%07d", ThreadLocalRandom.current().nextInt(0, 10000000));
        jdbcTemplate.update("insert into timeouttest(transactionid, request) values (?,?)", transactionId,
            UUID.randomUUID().toString());

        int random = ThreadLocalRandom.current().nextInt(10, 61);
        LOGGER.info("Sleep for {} seconds", random);
        TimeUnit.SECONDS.sleep(random);
        LOGGER.info("Begin to update response");
        int rows = jdbcTemplate
            .update("update timeouttest set Response = ? where transactionid = ?", "SUCCESS", transactionId);
        LOGGER.info("Database updated and {} row(s) got effected on schedule 1", rows);
    }

    @Scheduled(fixedRate = 20000)
    public void dbtest2() throws InterruptedException {
        LocalDateTime now = LocalDateTime.now();
        LOGGER.info("DB test on {} on schedule 2", now);
        String transactionId = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + String
            .format("%07d", ThreadLocalRandom.current().nextInt(0, 10000000));
        jdbcTemplate.update("insert into timeouttest(transactionid, request) values (?,?)", transactionId,
            UUID.randomUUID().toString());

        Thread.sleep(1000);
        int rows = jdbcTemplate
            .update("update timeouttest set Response = ? where transactionid = ?", "SUCCESS", transactionId);
        LOGGER.info("Database updated and {} row(s) got effected on schedule 2", rows);
    }
}
