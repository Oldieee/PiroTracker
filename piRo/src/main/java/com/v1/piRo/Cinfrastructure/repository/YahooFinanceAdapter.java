package com.v1.piRo.Cinfrastructure.repository;


import com.fasterxml.jackson.databind.JsonNode;
import com.v1.piRo.Ddomain.MarketDataRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;

@Component
public class YahooFinanceAdapter implements MarketDataRepository {
    private  final RestClient restClient;
    public YahooFinanceAdapter(RestClient.Builder builder){
        this.restClient=builder.baseUrl("https://query1.finance.yahoo.com/v8/finance/chart")
                .defaultHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                .build();
    }
    @Override
    public BigDecimal getPrice(String ticker){
        try{
            JsonNode response=restClient.get()
                    .uri("/"+ticker)
                    .retrieve()
                    .body(JsonNode.class);
            double price=response.path("chart")
                    .path("result")
                    .get(0)
                    .path("meta")
                    .path("regularMarketPrice")
                    .asDouble();
            return BigDecimal.valueOf(price);

        }catch (Exception e ){
            System.err.println("Eroare la preluarea pretului pentru "+ticker+": "+e.getMessage());
            return  BigDecimal.ZERO;
        }
    }

}
