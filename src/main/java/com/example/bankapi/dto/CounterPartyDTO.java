package com.example.bankapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CounterPartyDTO {
    private int partyID;
    private int counterPartyId;
}
