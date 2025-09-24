package dev.pedrao.portfolio_api.controller;

import dev.pedrao.portfolio_api.service.AgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/age")
public class AgeController {

    private final AgeService ageService;

    @Autowired
    public AgeController(AgeService ageService) {
        this.ageService = ageService;
    }

    @GetMapping
    public ResponseEntity<Integer> age() {
        return ResponseEntity.ok(ageService.calculateAge());
    }
}