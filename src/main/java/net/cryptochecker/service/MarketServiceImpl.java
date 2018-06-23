package net.cryptochecker.service;

import net.cryptochecker.dao.MarketDao;
import net.cryptochecker.exception.MarketNotFoundException;
import net.cryptochecker.model.Market;
import net.cryptochecker.parser.BittrexParser;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;


@Service
public class MarketServiceImpl implements MarketService{

    private final static Logger LOGGER = Logger.getLogger(MarketServiceImpl.class);
    @Value("${fetch.url}")
    private  String URL;
    private final MarketDao dao;
    private final BittrexParser parser;

    @Autowired
    public MarketServiceImpl(MarketDao dao, BittrexParser parser) {
        this.parser = parser;
        this.dao = dao;
    }


    @Override
    public void updateMarkets() throws IOException, SQLException, ClassNotFoundException {
        LOGGER.info("Start updates markets");
        dao.saveAndUpdateAllMarkets(parser.readJsonFromUrl(new URL(URL)));
    }

    @Override
    public Market getMarketInfoByName(String marketName) throws MarketNotFoundException, SQLException {
        LOGGER.info("Searching market with name: -" + marketName);
        if (!dao.checkMarketName(marketName)){
            throw new MarketNotFoundException();
        }
        return dao.findMarketByName(marketName);

    }

    @Override
    public double getLastChangesLastHourByMarketName(String marketName) throws MarketNotFoundException, SQLException {
        LOGGER.info("Start search last changes for market: " + marketName);
        if (!dao.checkMarketName(marketName)){
            throw new MarketNotFoundException();
        }
        return dao.getLastChangesNyMarketName(marketName);
    }
}
