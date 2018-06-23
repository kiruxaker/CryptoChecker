package net.cryptochecker.controller;


import net.cryptochecker.exception.MarketNotFoundException;
import net.cryptochecker.model.Market;
import net.cryptochecker.service.MarketService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;

@RestController
public class MarketController {

    private static final Logger LOGGER = Logger.getLogger(MarketController.class);
    @Value("${fetchperiod.milis}")
    private int sleepTime;
    private final MarketService service;


    @Autowired
    public MarketController(MarketService service) {
        this.service = service;
    }


    @RequestMapping(path = "/get-market-info", method = RequestMethod.GET)
    public ResponseEntity<Object> getMarketInfo(@RequestParam("name") String marketName) {
        LOGGER.info("Start getting info with market name " + marketName);
        Market market;
        try {
            market = service.getMarketInfoByName(marketName);
        } catch (MarketNotFoundException e) {
            LOGGER.error("there is no market with this name " + marketName);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        LOGGER.info("Response is successful");
        return new ResponseEntity<>(market, HttpStatus.OK);
    }

    @RequestMapping(path = "/get-last-changes", method = RequestMethod.GET)
    public ResponseEntity<Object> getLastLowChanges(@RequestParam("name") String marketName){
        LOGGER.info("Start getting last low changes with market name: " + marketName);
        Double lastChanges;
        try {
            lastChanges = service.getLastChangesLastHourByMarketName(marketName);
        } catch (MarketNotFoundException e) {
            LOGGER.error("there is no market with this name: " + marketName);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        LOGGER.info("Response is successful");
        return new ResponseEntity<>(BigDecimal.valueOf(lastChanges).setScale(9, RoundingMode.HALF_UP).doubleValue(), HttpStatus.OK);
    }

}
