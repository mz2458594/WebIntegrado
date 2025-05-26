package com.example.domain.ecommerce.models.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Email {
    private String mailFrom;
    private String mailTo;
    private String subject;
    private String userName;
    private String jwt;
}