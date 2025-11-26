package com.v1.piRo.Cinfrastructure.mappers;

import com.v1.piRo.Cinfrastructure.repository.dbo.MoneyDbo;
import com.v1.piRo.Ddomain.VO.Money;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring") // Tells Spring to manage it as a bean
public interface MoneyMapper {

    Money toDomain(MoneyDbo dbo);

    MoneyDbo toDbo(Money domain);
}