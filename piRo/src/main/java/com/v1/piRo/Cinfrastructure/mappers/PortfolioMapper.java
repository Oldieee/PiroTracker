// 1. Fixed package name
package com.v1.piRo.Cinfrastructure.mappers;

import com.v1.piRo.Cinfrastructure.repository.dbo.PortfolioDbo;
import com.v1.piRo.Ddomain.Portfolio;
// 2. Fixed import (capital 'M')
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {AssetMapper.class}) // This will be red for now
public interface PortfolioMapper {

    Portfolio toDomain(PortfolioDbo dbo);

    // 3. Fixed parameter name
    PortfolioDbo toDbo(Portfolio portfolio);
}