package com.fans.bravegirls;

import com.fans.bravegirls.common.exception.http.BadRequestException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

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
