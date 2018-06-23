package net.cryptochecker.dao;

import net.cryptochecker.model.Market;
import net.cryptochecker.parser.BittrexParser;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MarketDaoImplTest {
    @Autowired
    MarketDaoImpl dao;

    @Ignore
    @Test
    public void test() throws IOException, SQLException, ClassNotFoundException {
        dao.saveAndUpdateAllMarkets(new BittrexParser().readJsonFromUrl(new URL("https://bittrex.com/api/v1.1/public/getmarketsummaries")));
        Market market = dao.findMarketByName("BTC-2GIVE");
        System.out.println(market);
        Assert.assertTrue(market.getAsk() != 0);
    }

    @Ignore
    @Test
    public void test2() throws SQLException {
        dao.checkMarketName("BTC-2GIVE");
        dao.getLastChangesNyMarketName("BTC-2GIVE");

    }
}
