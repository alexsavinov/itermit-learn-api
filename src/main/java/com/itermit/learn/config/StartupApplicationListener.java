package com.itermit.learn.config;

import com.itermit.learn.utils.InitialDataGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@RequiredArgsConstructor
public class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    @Value("${app.generate_init_data}")
    private String generateInitData;

    private final InitialDataGenerator initialDataGenerator;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (Boolean.parseBoolean(generateInitData)) {
            initialDataGenerator.init();
        }
    }
}