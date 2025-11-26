package com.v1.piRo.Aexposition;


import com.v1.piRo.Bapplication.PortfolioService;
import com.v1.piRo.Ddomain.Portfolio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
@RequestMapping("/api/portfolio")
public class PortfolioController {
    private  final PortfolioService portfolioService;
    public PortfolioController(PortfolioService portfolioService){
        this.portfolioService=portfolioService;
    }
    @GetMapping
    public ResponseEntity<Portfolio>getMyPortfolio(){
        Long currentUserId=1L;
        Portfolio portfolio=portfolioService.getPortfolio(currentUserId);
        return  ResponseEntity.ok(portfolio);
    }
    @PostMapping("/assets")
    public ResponseEntity<Void>addAssets(@RequestBody AddAssetRequest request){
        Long currentUserId=1L;
        portfolioService.addAssetToPortfolio(
                currentUserId,
                request.ticker(),
                request.quantity(),
                request.priceAmount(),
                request.currency()

        );
        return  ResponseEntity.ok().build();
    }
    @DeleteMapping("assets/{assetId}")
    public ResponseEntity<Void>deleteAsset(@PathVariable Long assetId){
        Long currentUserId=1L;
        portfolioService.removeAssetFromPortfolio(currentUserId,assetId);
        return ResponseEntity.ok().build();
    }
}

record  AddAssetRequest(
        String ticker,
        BigDecimal quantity,
        BigDecimal priceAmount,
        String currency

){}
