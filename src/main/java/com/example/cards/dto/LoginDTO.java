package com.example.cards.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class LoginDTO {
    private String cardNumber;
    private String mobileNumber;
}
