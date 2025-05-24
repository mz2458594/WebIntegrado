package com.example.domain.ecommerce.security;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// @Component
// public class JwtFilter extends OncePerRequestFilter {

//     @Autowired
//     private JwtUtil jwtUtil;

//     public JwtFilter(JwtUtil jwtUtil) {
//         this.jwtUtil = jwtUtil;
//     }

//     @Override
//     protected void doFilterInternal(HttpServletRequest request,
//             HttpServletResponse response,
//             FilterChain filterChain)
//             throws ServletException, IOException {
//         final String authHeader = request.getHeader("Authorization");

//         if (authHeader != null && authHeader.startsWith("Bearer")) {
//             String token = authHeader.substring(7);
//             String username = jwtUtil.extractUsername(token);

//             if (username != null && jwtUtil.validateToken(token, username)) {
//                 UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(token,
//                         null, new ArrayList<>());

//                 SecurityContextHolder.getContext().setAuthentication(authentication);
//             }
//         }

//         filterChain.doFilter(request, response);
//     }

// }
