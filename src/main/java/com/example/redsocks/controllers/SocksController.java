package com.example.redsocks.controllers;

import com.example.redsocks.model.Socks;
import com.example.redsocks.services.SocksService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/socks")
public class SocksController {
    private final SocksService socksService;

    public SocksController(SocksService socksService) {
        this.socksService = socksService;
    }

    @PostMapping("/add")
    public ResponseEntity<Socks> addSocks(@RequestBody Socks socks,
                                          @RequestParam int stockSize) {
        socksService.addSocks(socks, stockSize);
        return ResponseEntity.ok(socks);
    }

    @GetMapping
    public String greetings() {
        return "asdadadadas";
    }
}
