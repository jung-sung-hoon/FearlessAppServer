package com.fans.bravegirls.batch;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import com.fans.bravegirls.batch.exception.http.BadRequestException;

import javax.xml.bind.JAXBException;
import java.text.ParseException;

@SpringBootTest
class BraveGirlsBatchApplicationTests {


    @Value("${omnitel.coupon.url}")
    private String url;


    @Test
    void contextLoads() throws ParseException, BadRequestException, JAXBException {
    }


}
