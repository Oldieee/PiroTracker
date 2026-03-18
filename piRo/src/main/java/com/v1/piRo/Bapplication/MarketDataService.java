package com.v1.piRo.Bapplication;

import com.v1.piRo.Aexposition.StockNews;
import com.v1.piRo.Aexposition.StockQuote;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;

@Service
public class MarketDataService {

    private final RestTemplate restTemplate = new RestTemplate();

    private HttpEntity<String> getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36");
        headers.set("Accept-Language", "en-US,en;q=0.9");
        headers.set("Connection", "keep-alive");
        return new HttpEntity<>(headers);
    }


    @Cacheable(value = "quotes", key = "#ticker")
    public StockQuote getQuote(String ticker) {
        System.out.println(" Căutăm prețul pe Yahoo pentru: " + ticker);
        String url = "https://query2.finance.yahoo.com/v8/finance/chart/" + ticker + "?interval=1d";
        try {

            Thread.sleep(500);
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, getHeaders(), Map.class);
            Map body = response.getBody();
            Map chart = (Map) body.get("chart");
            List results = (List) chart.get("result");

            if (results == null || results.isEmpty()) {
                throw new RuntimeException("Nu s-au găsit date.");
            }

            Map resultData = (Map) results.get(0);
            Map meta = (Map) resultData.get("meta");

            BigDecimal price = new BigDecimal(meta.get("regularMarketPrice").toString());
            String currency=(String)meta.get("currency");
            if(ticker.toUpperCase().endsWith(".RO")){
                currency="RON";
            }else if(currency==null){
                currency="USD";
            }

            BigDecimal prevClose = new BigDecimal(meta.getOrDefault("chartPreviousClose", price).toString());
            BigDecimal change = BigDecimal.ZERO;
            if (prevClose.compareTo(BigDecimal.ZERO) != 0) {
                change = price.subtract(prevClose)
                        .divide(prevClose, 4, java.math.RoundingMode.HALF_UP)
                        .multiply(new BigDecimal("100"));
            }

            System.out.println("✅ Date descărcate: " + ticker + " | " + price + " " + currency);


            return new StockQuote(ticker.toUpperCase(), price, currency, change);

        } catch (Exception e) {
            System.err.println(" Yahoo a respins cererea pentru " + ticker + ": " + e.getMessage());

            throw new RuntimeException("Eroare Yahoo API pentru " + ticker);
        }
    }
@Cacheable(value="exchangeRate",key = "'EUR_RON'")
    public BigDecimal getLiveEurRonRate(){
        try{
            System.out.println("Cautam cursul EUR/RON..");
            return getQuote("EURRON=X").currentPrice();
        }catch (Exception e){
            System.err.println("️ Eroare curs EUR/RON. Folosim fallback (4.97)");
            return new BigDecimal("5.07");
        }
}
    @Cacheable(value = "exchangeRate", key = "'EUR_USD'")
    public BigDecimal getLiveEurUsdRate() {
        try {
            System.out.println("💶 Căutăm cursul EUR/USD...");
            return getQuote("EURUSD=X").currentPrice();
        } catch (Exception e) {
            System.err.println("⚠️ Eroare curs EUR/USD. Folosim fallback (1.08)");
            return new BigDecimal("1.08");
        }
    }
    public List<StockNews> getNews(String ticker) {
        return Collections.emptyList();
    }
}