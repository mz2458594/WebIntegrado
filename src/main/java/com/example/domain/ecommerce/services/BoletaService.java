package com.example.domain.ecommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.ecommerce.repositories.BoletaDAO;

@Service
public class BoletaService {
    @Autowired
    private BoletaDAO boletaDAO;


}
