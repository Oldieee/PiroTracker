package com.v1.piRo.Cinfrastructure.mappers;

import com.v1.piRo.Cinfrastructure.repository.dbo.AssetDbo;
import com.v1.piRo.Ddomain.VO.Asset;
import org.mapstruct.Mapper;

// We tell MapStruct to USE the MoneyMapper for any Money fields it finds
@Mapper(componentModel = "spring", uses = {MoneyMapper.class})
public interface AssetMapper {

    Asset toDomain(AssetDbo dbo);

    AssetDbo toDbo(Asset domain);
}