package com.v1.piRo.Aexposition;

import com.v1.piRo.Bapplication.PortfolioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/watchlist")
@CrossOrigin(origins = "http://localhost:4200")
public class WatchlistController {

    private final PortfolioService portfolioService;

    public WatchlistController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }


    private Long getCurrentUserId() {
        return 1L;
    }

    @GetMapping
    public ResponseEntity<List<StockQuote>> getMyWatchlist() {
        System.out.println("📡 API Watchlist: Se cer datele pentru userul " + getCurrentUserId());
        return ResponseEntity.ok(portfolioService.getFullWatchlist(getCurrentUserId()));
    }

    @PostMapping("/{ticker}")
    public ResponseEntity<Void> add(@PathVariable String ticker) {
        System.out.println("📡 API Watchlist: Adăugăm " + ticker);
        portfolioService.addToWatchlist(getCurrentUserId(), ticker);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{ticker}")
    public ResponseEntity<Void> remove(@PathVariable String ticker) {
        System.out.println("📡 API Watchlist: Ștergem " + ticker);
        portfolioService.removeFromWatchlist(getCurrentUserId(), ticker);
        return ResponseEntity.ok().build();
    }
}