package net.cryptochecker.parser;

import net.cryptochecker.model.Market;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BittrexParserTest {


    private BittrexParser parser;

    @Before
    public void setUp(){
        parser = new BittrexParser();
    }

    @Test
    public void bittrexParserTest() throws IOException {
        List<Market> list = parser.readJsonFromUrl(
                new File("/home/ivpion/dev/ideaProject/bittrex-markets-aggregator-tz/src/test/resources/testBittrex.json")
                        .toURI()
                        .toURL());
        Assert.assertNotNull(list);
        Assert.assertThat(list.size(), Matchers.is(14));
    }
}
