package com.examen.facturation.domaine.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Command implements Serializable {

    @Id
    @Column(nullable = false)
    private Integer clientId;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String article;

    @Column(nullable = false)
    private Long Quantity;

    @Column(nullable = false)
    private BigDecimal price;
}