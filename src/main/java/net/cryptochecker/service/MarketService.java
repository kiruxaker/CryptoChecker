package net.cryptochecker.service;

import net.cryptochecker.exception.MarketNotFoundException;
import net.cryptochecker.model.Market;

import java.io.IOException;
import java.sql.SQLException;

public interface MarketService {


    void updateMarkets() throws IOException, SQLException, ClassNotFoundException;

    Market getMarketInfoByName(String marketName) throws MarketNotFoundException, SQLException;

    double getLastChangesLastHourByMarketName(String marketName) throws MarketNotFoundException, SQLException;

}
