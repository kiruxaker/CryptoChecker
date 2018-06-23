package net.cryptochecker.parser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.cryptochecker.model.Market;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;


import java.io.*;
import java.net.URL;
import java.util.*;

@Component
public class BittrexParser {


    private final ObjectMapper mapper = new ObjectMapper();
    private static final Logger LOGGER = Logger.getLogger(BittrexParser.class);

    public BittrexParser() {
    }

    public List<Market> readJsonFromUrl(URL url) throws IOException {
        LOGGER.info("Start parsing data from url: " + url.toString());
        String jsonNode = mapper.readTree(url).path("result").toString();
        LOGGER.info("Start convert JSON to List<Markets>");
        return mapper.readValue(jsonNode, new TypeReference<List<Market>>() {
        });
    }

}
