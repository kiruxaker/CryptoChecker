package net.cryptochecker.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.cryptochecker.model.Market;
import net.cryptochecker.service.MarketService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@RunWith(SpringRunner.class)
public class MarketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private MarketController controller;

    @Mock
    private MarketService service;

    @Autowired
    private ObjectMapper objectMapper;

    private List<Market> markets;

    @Before
    public void setUp() throws IOException {
        markets = objectMapper.readValue(this.getClass()
                .getResourceAsStream("/marketList.json"),
                new TypeReference<List<Market>>(){});

    }

    @Test
    public void getMarketInfoTest() throws Exception {
        doNothing().when(service).updateMarkets();
        when(service.getMarketInfoByName("BTC-2GIVE")).thenReturn(markets.get(0));
        String json = objectMapper.writeValueAsString(markets.get(0));


        MvcResult result = mockMvc.perform(get("/get-market-info").param("name","BTC-2GIVE")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)).andDo(print()).andExpect(status().isOk()).andReturn();

        String body = result.getResponse().getContentAsString();
        assertNotNull(body);
        assertThat(body.contains("0.00000073"));

    }

    @Test
    public void getLastLowChanges() throws Exception {
        doNothing().when(service).updateMarkets();
        when(service.getLastChangesLastHourByMarketName("BTC-2GIVE")).thenReturn(0.00000071 - 0.00000069);

        ResponseEntity entity = controller.getLastLowChanges("BTC-2GIVE");
        assertTrue(entity.getStatusCode().equals(HttpStatus.OK));
        System.out.println(entity.getBody().toString());
        String exp = Double.toString(0.00000002);
        assertTrue(entity.getBody().toString().equals(exp));


    }




}
