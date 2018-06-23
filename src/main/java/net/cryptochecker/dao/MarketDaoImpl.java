package net.cryptochecker.dao;


import net.cryptochecker.config.MyDataSource;
import net.cryptochecker.model.Market;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class MarketDaoImpl implements MarketDao {

    private static final Logger LOGGER = Logger.getLogger(MarketDaoImpl.class);

    private final MyDataSource dataSource;

    private final Connection connection;

    @Autowired
    public MarketDaoImpl(MyDataSource source) throws SQLException, ClassNotFoundException {
        this.dataSource = source;
        connection = dataSource.getConnection();
    }

    @Override
    public void saveAndUpdateAllMarkets(List<Market> list) throws ClassNotFoundException, SQLException {
        Statement statement = getStatement();
        long start = System.currentTimeMillis();
        LOGGER.info("Start updating all markets...");
        list.forEach(market -> {
            String queryInsertUpdate = createInsertUpdateQuery(market);
            try {
                statement.execute(queryInsertUpdate);
                updateLastValueTable(market);
            } catch (SQLException e) {
                e.printStackTrace();
                LOGGER.error(e.getMessage());
            }
        });
        deleteOldLastInLastValueTable();
        long millis = System.currentTimeMillis() - start;
        LOGGER.info("Insert/update all markets: - " + millis + " millis");
    }

    public boolean checkMarketName(String name){
        boolean flag = false;
        try {
            ResultSet set = getStatement().executeQuery(String.format("select * from markets where market_name=\'%s\'", name ));
            set.next();
            if (set.getString("market_name").equals(name)){
                flag = true;
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return flag;
    }

    @Override
    public Market findMarketByName(String name) throws SQLException {
        Statement statement = getStatement();
        LOGGER.info("Search market with name: " + name);
        ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM markets WHERE market_name = \'%s\'", name));
        resultSet.next();
        LOGGER.info("Find market with name");
        LOGGER.info("Start convert result set to Market....");
        Market market = new Market();
        market.setMarketName(resultSet.getString("market_name"));
        market.setHigh(resultSet.getDouble("high"));
        market.setLow(resultSet.getDouble("low"));
        market.setVolume(resultSet.getDouble("volume"));
        market.setLast(resultSet.getDouble("last"));
        market.setBaseVolume(resultSet.getDouble("base_volume"));
        market.setTimestamp(resultSet.getString("time_stamp"));
        market.setBid(resultSet.getDouble("bid"));
        market.setAsk(resultSet.getDouble("ask"));
        market.setOpenBuyOrders(resultSet.getInt("open_buy_orders"));
        market.setOpenSellOrders(resultSet.getInt("open_sell_orders"));
        market.setPrevDay(resultSet.getDouble("prev_day"));
        market.setCreated(resultSet.getString("created"));
        market.setDisplayMarketName(resultSet.getString("display_market_name"));
        LOGGER.info("Convert to Market successful");
        return market;
    }

    private void updateLastValueTable(Market market) throws SQLException {
        Statement statement = getStatement();
        statement.execute(String.format("Insert into last_values (market_name, last, time_stamp) " +
                        "values (\'%s\', %.10f, \'%s\')",
                market.getMarketName(), market.getLast(), market.getTimestamp()));
    }

    private void deleteOldLastInLastValueTable() throws SQLException {
        Statement statement = getStatement();
        statement.execute("Delete from last_values where time_stamp <= now() - INTERVAL 1 HOUR and id > 0");

    }

    public double getLastChangesNyMarketName(String marketName) throws SQLException {
        //todo select double with min/max aggregation func
        ResultSet set1 = getStatement().executeQuery(String.format("select last from last_values where market_name = \'%1$s\' and time_stamp = (select min(time_stamp) from last_values where market_name=\'%1$s\') order by id limit 1", marketName));
        ResultSet set2 = getStatement().executeQuery(String.format("select last from last_values where market_name = \'%1$s\' and time_stamp = (select max(time_stamp) from last_values where market_name=\'%1$s\') order by id limit 1", marketName));
        set1.next();
        set2.next();
        double oldestLastVal = set1.getDouble("last");
        double latestLAstVal = set2.getDouble("last");
        return latestLAstVal - oldestLastVal;
    }

    private Statement getStatement() throws SQLException {
        return connection.createStatement();
    }

    private String createInsertUpdateQuery(Market market) {
        return String.format("Insert into markets (market_name, high, low, volume, last, base_volume, time_stamp," +
                        " bid, ask, open_buy_orders, open_sell_orders, prev_day, created, display_market_name)" +
                        "values (\'%s\', %.10f, %.10f, %.10f, %.10f, %.10f, \'%s\', %.10f, %.10f, %d, %d, %.10f, \'%s\', \'%s\')" +
                        " on duplicate KEY UPDATE " +
                        "high = %2$.10f," +
                        "low = %3$.10f," +
                        "volume = %4$.10f," +
                        "last = %5$.10f," +
                        "base_volume = %6$.10f," +
                        "time_stamp = \'%7$s\'," +
                        "bid = %8$.10f," +
                        "ask = %9$.10f," +
                        "open_buy_orders = %10$d," +
                        "open_sell_orders = %11$d," +
                        "prev_day = %12$.10f," +
                        "created = \'%13$s\'," +
                        "display_market_name = \'%14$s\'",
                market.getMarketName(),
                market.getHigh(),
                market.getLow(),
                market.getVolume(),
                market.getLast(),
                market.getBaseVolume(),
                market.getTimestamp(),
                market.getBid(),
                market.getAsk(),
                market.getOpenBuyOrders(),
                market.getOpenSellOrders(),
                market.getPrevDay(),
                market.getCreated(),
                market.getDisplayMarketName());
    }
}
