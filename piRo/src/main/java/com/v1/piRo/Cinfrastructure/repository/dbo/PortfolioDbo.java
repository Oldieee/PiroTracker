package com.v1.piRo.Cinfrastructure.repository.dbo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@Repository
@Entity
@Table(name="portfolios")
public class PortfolioDbo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true)
    private Long userId;
    @OneToMany(
            mappedBy = "portfolio",
            cascade=CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<AssetDbo>assets=new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Set<AssetDbo> getAssets() {
        return assets;
    }

    public void setAssets(Set<AssetDbo> assets) {
        this.assets = assets;
    }
}
