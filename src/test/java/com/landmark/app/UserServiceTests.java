package com.landmark.app;

import com.landmark.app.utils.helper.StaticHelper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTests {

    @Test
    public void getCertNumTest() {
        System.out.println("certNum = " + StaticHelper.getCertNum());
    }

}
