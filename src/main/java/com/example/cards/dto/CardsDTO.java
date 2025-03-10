package com.example.cards.dto;

import lombok.Data;

@Data
public class CardsDTO {
    private String mobileNumber;
    private String cardNumber;
    private String cardType;
    private int totalLimit;
    private int amountUsed;
    private int availableAmount;
}
