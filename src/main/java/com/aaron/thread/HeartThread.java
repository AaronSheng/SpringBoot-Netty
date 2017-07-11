package com.aaron.thread;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class HeartThread extends Thread {
    private static Logger logger = LogManager.getLogger(HeartThread.class);

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("HeartThread");
                Thread.sleep(60000);
            } catch (Exception exception) {
                logger.error("HeartThread", exception);
            }
        }
    }
}
