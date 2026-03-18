package com.v1.piRo.Cinfrastructure.dbo;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreatePortfolioRequest {
private UUID userId;
private String name;
}
