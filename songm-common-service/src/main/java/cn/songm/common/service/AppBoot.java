package cn.songm.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppBoot {

    private static final Logger LOG = LoggerFactory.getLogger(AppBoot.class);

    private static volatile boolean running = true;

    public static boolean getRruning() {
        return running;
    }

    public static void start(String configLocation, String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                configLocation);

        try {
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    context.stop();
                    context.close();
                    LOG.info("Application service stopped!");
                    synchronized (AppBoot.class) {
                        running = false;
                        AppBoot.class.notify();
                    }
                }
            });

            context.start();
            LOG.info("Application service started!");
        } catch (RuntimeException e) {
            LOG.error(e.getMessage(), e);
            System.exit(1);
        }

        synchronized (AppBoot.class) {
            while (running) {
                try {
                    AppBoot.class.wait();
                } catch (InterruptedException e) {
                    LOG.error(e.getMessage(), e);
                }
            }
        }
    }
}
