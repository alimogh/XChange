package com.xeiam.xchange.examples.bitcoinium;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitcoinium.BitcoiniumExchange;
import com.xeiam.xchange.bitcoinium.dto.marketdata.BitcoiniumOrderbook;
import com.xeiam.xchange.bitcoinium.dto.marketdata.BitcoiniumTicker;
import com.xeiam.xchange.bitcoinium.service.polling.BitcoiniumMarketDataServiceRaw;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * Demonstrate requesting Market Data from CampBX
 */
public class BitcoiniumMarketDataDemo {

  public static void main(String[] args) throws Exception {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(BitcoiniumExchange.class.getName());
    exchangeSpecification.setApiKey("42djci5kmbtyzrvglfdw3e2dgmh5mr37");
    exchangeSpecification.setPlainTextUri("http://173.10.241.154:9090");
    System.out.println(exchangeSpecification.toString());
    Exchange bitcoiniumExchange = ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);

    generic(bitcoiniumExchange);
    raw(bitcoiniumExchange);
  }

  private static void generic(Exchange exchange) throws IOException {

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService bitcoiniumGenericMarketDataService = exchange.getPollingMarketDataService();

    // Get the latest ticker data showing BTC to USD
    Ticker ticker = bitcoiniumGenericMarketDataService.getTicker(new CurrencyPair("BTC", "BITSTAMP_USD"));

    System.out.println("Last: " + ticker.getLast());
    System.out.println("Bid: " + ticker.getBid());
    System.out.println("Ask: " + ticker.getAsk());
    System.out.println("Volume: " + ticker.getVolume());

    // Get the latest order book data for BTC/USD
    OrderBook orderBook = bitcoiniumGenericMarketDataService.getOrderBook(new CurrencyPair("BTC", "BITSTAMP_USD"), "TEN_PERCENT");

    System.out.println("Order book: " + orderBook);
  }

  private static void raw(Exchange exchange) throws IOException {

    // Interested in the public polling market data feed (no authentication)
    BitcoiniumMarketDataServiceRaw bitcoiniumSpecificMarketDataService = (BitcoiniumMarketDataServiceRaw) exchange.getPollingMarketDataService();

    // Get the latest ticker data showing BTC to USD
    BitcoiniumTicker bitcoiniumTicker = bitcoiniumSpecificMarketDataService.getBitcoiniumTicker(Currencies.BTC, "BITSTAMP_USD");

    System.out.println("Last: " + bitcoiniumTicker.getLast());
    System.out.println("Bid: " + bitcoiniumTicker.getBid());
    System.out.println("Ask: " + bitcoiniumTicker.getAsk());
    System.out.println("Volume: " + bitcoiniumTicker.getVolume());

    // Get the latest order book data for BTC/USD - MtGox
    BitcoiniumOrderbook bitcoiniumOrderbook = bitcoiniumSpecificMarketDataService.getBitcoiniumOrderbook(Currencies.BTC, "BITSTAMP_USD", "TEN_PERCENT");

    System.out.println("Order book: " + bitcoiniumOrderbook);
  }
}
