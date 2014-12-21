package com.xeiam.xchange.btc38;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.xeiam.xchange.btc38.dto.marketdata.Btc38TickerReturn;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.TimeZone;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by Yingzhe on 12/20/2014.
 */
public class Btc38AdapterTest {
    @Test
    public void testTickerAdapter() throws IOException {
        // Read in the JSON from the example resources
        InputStream is = Btc38AdapterTest.class.getResourceAsStream("/example-ticker-data.json");

        // Use Jackson to parse it
        ObjectMapper mapper = new ObjectMapper();
        TypeFactory t = TypeFactory.defaultInstance();
        Map<String, Btc38TickerReturn> btc38AllTickers = mapper.readValue(is, t.constructMapType(Map.class, String.class, Btc38TickerReturn.class));
        Btc38TickerReturn tickerReturn = btc38AllTickers.get("doge");

        Ticker ticker = Btc38Adapters.adaptTicker(tickerReturn.getTicker(),CurrencyPair.DOGE_BTC);

        assertThat(ticker.getHigh()).isEqualTo(new BigDecimal("6.0e-7"));
        assertThat(ticker.getLow()).isEqualTo(new BigDecimal("5.1e-7"));
        assertThat(ticker.getLast()).isEqualTo(new BigDecimal("5.5e-7"));
        assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("9201658.258436"));
        assertThat(ticker.getAsk()).isEqualTo(new BigDecimal("5.7e-7"));
        assertThat(ticker.getBid()).isEqualTo(new BigDecimal("5.5e-7"));
    }
}
