package net.cryptochecker.deamon;

import net.cryptochecker.service.MarketService;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

public class SyncMarketUpdDeamon {

    private static final Logger LOGGER = Logger.getLogger(SyncMarketUpdDeamon.class);

    public static void start(MarketService service){
        LOGGER.info("Start demon...");
        Thread thread = new Thread(() -> {
            while (true){
                try {
                    service.updateMarkets();
                } catch (IOException | SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                    LOGGER.error(e.getMessage());
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    LOGGER.error(e.getMessage());
                }
            }
        });
        CompletableFuture.runAsync(thread);
    }
}
