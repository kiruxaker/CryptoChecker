package net.cryptochecker;

import net.cryptochecker.deamon.SyncMarketUpdDeamon;
import net.cryptochecker.service.MarketService;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Run {

    private static final Logger LOGGER = Logger.getLogger(Run.class.getName());

    public static void main(String[] args) {
        LOGGER.info("Start server..");
        MarketService service = (MarketService) SpringApplication.run(Run.class, args).getBean("marketServiceImpl");
        SyncMarketUpdDeamon.start(service);
    }
}
