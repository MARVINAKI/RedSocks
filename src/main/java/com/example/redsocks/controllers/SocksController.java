package com.example.redsocks.controllers;

import com.example.redsocks.model.Socks;
import com.example.redsocks.model.category.ColorOfSocks;
import com.example.redsocks.model.category.ShoeSize;
import com.example.redsocks.services.SocksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Tag(name = "Носки")
@RequestMapping("/api/socks")
public class SocksController {
    private final SocksService socksService;

    public SocksController(SocksService socksService) {
        this.socksService = socksService;
    }

    @PostMapping("/add")
    @Operation(summary = "Приход товара", description = "Регистрация товара на складе")
    public ResponseEntity<Socks> addSocks(@RequestBody Socks socks,
                                          @RequestParam int quantity) {
        if (quantity < 0 || socks.getCottonComposition() < 0 || socks.getCottonComposition() > 100) {
            return ResponseEntity.badRequest().header("Error", "Incorrect values").build();
        }
        socksService.addSocks(socks, quantity);
        return ResponseEntity.ok(socks);
    }

    @PutMapping("/release")
    @Operation(summary = "Отпуск товара", description = "Отпуск товара со склада")
    public ResponseEntity<Void> releaseOfSocks(Socks socks, int quantity) {
        boolean status = socksService.releaseOfSocks(socks, quantity);
        return status ? ResponseEntity.ok().build() : ResponseEntity.badRequest().header("Error", "Incorrect values").build();
    }

    @GetMapping("/selection")
    @Operation(summary = "Выборка", description = "Отчет о количестве конкретных носков на складе")
    public ResponseEntity<Integer> getReplyAboutSocks(ColorOfSocks color, ShoeSize shoeSize, int cottonMin, int cottonMax) {
        int selection = socksService.getInfoOfSocks(color, shoeSize, cottonMin, cottonMax);
        return ResponseEntity.ok(selection);
    }

    @GetMapping
    @Operation(summary = "Итог", description = "Запасы носков на складе")

    public ResponseEntity<Map<Socks, Integer>> getAll() {

        return ResponseEntity.ok(socksService.getInfoOfAllSocks());
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Расход товара", description = "Списание испорченного товара")
    public ResponseEntity<Void> deleteSocks(Socks socks, int quantity) {
        boolean status = socksService.deleteSocks(socks, quantity);
        return status ? ResponseEntity.ok().build() : ResponseEntity.badRequest().header("Error", "Incorrect values").build();
    }
}
