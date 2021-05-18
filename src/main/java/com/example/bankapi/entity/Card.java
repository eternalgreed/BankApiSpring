package com.example.bankapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Card {
    private int id;
    private String number;
    @JsonIgnore
    private int accountId;
    @JsonIgnore
    private boolean active;

}
