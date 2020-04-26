package com.landmark.app;

import com.landmark.app.service.RefreshService;
import com.landmark.app.utils.constants.Constants;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RefreshServiceTests {

    private RefreshService refreshService;

    @Autowired
    public RefreshServiceTests(RefreshService refreshService) {
        this.refreshService = refreshService;
    }

    @Test
    @Ignore
    public void refreshTest() {
        System.out.println(Constants.sigunguCodeMap);
    }
}
