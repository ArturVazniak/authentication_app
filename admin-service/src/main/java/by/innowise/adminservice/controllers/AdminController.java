package by.innowise.adminservice.controllers;

import by.innowise.adminservice.model.TourOperator;
import by.innowise.adminservice.payload.request.JwtRequest;
import by.innowise.adminservice.service.TourOperatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class AdminController {

    private final TourOperatorService tourOperatorService;

    public AdminController(TourOperatorService tourOperatorService) {
        this.tourOperatorService = tourOperatorService;
    }

    @GetMapping("/tour")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllTourOperator(){

    return ResponseEntity.ok("Hello");

    }
}
