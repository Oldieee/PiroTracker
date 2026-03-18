package com.v1.piRo.Aexposition;
import com.v1.piRo.Bapplication.PortfolioHistoryQueryService;
import com.v1.piRo.Ddomain.PortfolioHistory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/portfolio/history")
@CrossOrigin(origins = "http://localhost:4200")
public class PortfolioHistoryController {

    private final PortfolioHistoryQueryService queryService;

    public PortfolioHistoryController(PortfolioHistoryQueryService queryService) {
        this.queryService = queryService;
    }

    @GetMapping
    public ResponseEntity<List<PortfolioHistory>> getHistory() {
        List<PortfolioHistory> history = queryService.getFullHistory();
        return ResponseEntity.ok(history);
    }
}