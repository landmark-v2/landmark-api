package com.landmark.app;

import com.landmark.app.service.RefreshService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartUp implements ApplicationListener<ApplicationReadyEvent> {

    private RefreshService refreshService;

    @Autowired
    public ApplicationStartUp(RefreshService refreshService) {
        this.refreshService = refreshService;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        refreshService.refreshAll();
    }

}
