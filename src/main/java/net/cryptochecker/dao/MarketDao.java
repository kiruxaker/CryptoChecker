package net.cryptochecker.dao;

import net.cryptochecker.model.Market;

import java.sql.SQLException;
import java.util.List;

public interface MarketDao {
    void saveAndUpdateAllMarkets(List<Market> list) throws ClassNotFoundException, SQLException;

    Market findMarketByName(String name) throws SQLException;

    double getLastChangesNyMarketName(String marketName) throws SQLException;

    boolean checkMarketName(String name);
}
