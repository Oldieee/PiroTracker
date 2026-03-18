package com.v1.piRo.Ddomain;



import lombok.*;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
private Long id;
private  String email;
private String username;
private List<Portfolio> portfolios;



}
