package com.jdnl.demo.service;

import com.jdnl.demo.DemoApplication;
import javafx.application.Application;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest

public class TestTestService {
    @Autowired
    TestService testService;

    @Test
    void testAdd(){
        Assertions.assertThat(testService.add(1,1)).isEqualTo(2);
    }
}
