package com.v1.piRo.Aexposition;

import com.v1.piRo.Bapplication.MarketDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/market")
@CrossOrigin(origins = "http://localhost:4200")
public class MarketDataController {

    private final MarketDataService marketDataService;

    public MarketDataController(MarketDataService marketDataService) {
        this.marketDataService = marketDataService;
    }

    @GetMapping("/quote/{ticker}")
    public ResponseEntity<StockQuote> getQuote(@PathVariable String ticker) {
        System.out.println("📡 API Market apelat pentru ticker: " + ticker);
        return ResponseEntity.ok(marketDataService.getQuote(ticker.toUpperCase()));
    }

    @GetMapping("/news/{ticker}")
    public ResponseEntity<List<StockNews>> getNews(@PathVariable String ticker) {
        return ResponseEntity.ok(marketDataService.getNews(ticker.toUpperCase()));
    }
}