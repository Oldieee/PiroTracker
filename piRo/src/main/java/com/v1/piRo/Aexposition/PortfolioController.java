package com.v1.piRo.Aexposition;

import com.v1.piRo.Bapplication.PortfolioService;
import com.v1.piRo.Bapplication.TransactionResponse;
import com.v1.piRo.Ddomain.Portfolio;
import com.v1.piRo.Ddomain.Transaction;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
@RequestMapping("/api/portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    public Long getCurrentUserId(Authentication authentication) {
        System.out.println("⚠️ Securitate bypassată.");
        return 1L;
    }

    @GetMapping
    public ResponseEntity<PortfolioResponse> getMyPortfolio() {
        Long userId=1L;
       Portfolio portfolio=portfolioService.getPortfolio(userId);
       List<Transaction>transactions=portfolioService.getTransactionsByPortfolioId(portfolio.getId());
       List<TransactionResponse>txResponses=transactions.stream()
               .map(tx->new TransactionResponse(
                       tx.getId(),
                       tx.getAsset().getTicker(),
                       tx.getType().name(),
                       tx.getPrice(),
                       tx.getQuantity(),
                       tx.getTimestamp()
               ))
               .collect(Collectors.toList());

       PortfolioResponse response=new PortfolioResponse(
               portfolio.getId(),
               portfolio.getName(),
               txResponses);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/transactions")
    public ResponseEntity<Void> addTransaction(@RequestBody @Valid AddTransactionRequest request, Authentication authentication) {
        Long currentUserId = getCurrentUserId(authentication);
        portfolioService.addTransactionToPortfolio(currentUserId, request.ticker(), request.quantity(), request.priceAmount(), request.currency());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/transactions/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long transactionId, Authentication authentication) { // <-- Trebuie să fie identic aici
        Long currentUserId = 1L;
        portfolioService.removeTransaction(currentUserId, transactionId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/total-value")
    public ResponseEntity<BigDecimal> getTotalValue(Authentication authentication) {
        Long currentUserId = getCurrentUserId(authentication);
        BigDecimal totalValue = portfolioService.calculateTotalValue(currentUserId);
        return ResponseEntity.ok(totalValue);
    }

    @GetMapping("/summary/{userId}")
    public ResponseEntity<PortfolioSummaryResponse> getPortfolioSummary(@PathVariable Long userId) {

        PortfolioSummaryResponse summary = portfolioService.getPortfolioSummary(userId);
        return ResponseEntity.ok(summary);
    }
}


record AddTransactionRequest(
        @NotBlank String ticker,
        @NotNull @Positive BigDecimal quantity,
        @NotNull @Positive BigDecimal priceAmount,
        @NotBlank String currency
) {}


record PortfolioResponse(
        Long id,
        String name,
        List<TransactionResponse> transactions
) {}

