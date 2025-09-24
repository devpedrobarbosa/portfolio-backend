package dev.pedrao.portfolio_api.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AgeService {

    private final LocalDateTime birthDate = LocalDateTime.of(2004, 10, 20, 0, 0);

    public int calculateAge() {
        final LocalDateTime now = LocalDateTime.now();
        int age = now.getYear() - birthDate.getYear();
        if(now.getMonthValue() < birthDate.getMonthValue() || now.getDayOfMonth() < birthDate.getDayOfMonth())
            age--;
        return age;
    }
}