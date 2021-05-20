package com.example.bankapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private int id;
    private Double amount;
    private boolean approved;
    private int fromId;
    private int toId;
}

