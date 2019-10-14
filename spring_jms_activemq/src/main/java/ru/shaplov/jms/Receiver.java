package ru.shaplov.jms;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jms.annotation.JmsListener;

import java.util.concurrent.CountDownLatch;

/**
 * @author shaplov
 * @since 14.10.2019
 */
public class Receiver {

    private static final Logger LOGGER = LogManager.getLogger(Receiver.class);

    private CountDownLatch latch = new CountDownLatch(1);

    public CountDownLatch getLatch() {
        return latch;
    }

    @JmsListener(destination = "helloworld.q")
    public void receive(String message) {
        LOGGER.info("received message='{}'", message);
        latch.countDown();
    }
}
