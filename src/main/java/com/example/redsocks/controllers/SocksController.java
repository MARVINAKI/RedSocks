package com.example.redsocks.controllers;

import com.example.redsocks.model.Socks;
import com.example.redsocks.model.category.ColorOfSocks;
import com.example.redsocks.model.category.ShoeSize;
import com.example.redsocks.services.SocksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.Map;

@RestController
@Tag(name = "Socks CRUD operations")
@RequestMapping("/api/socks")
public class SocksController {
    private final SocksService socksService;

    public SocksController(SocksService socksService) {
        this.socksService = socksService;
    }

    @PostMapping("/add")
    @Operation(summary = "Приход товара", description = "Регистрация товара на складе или изменение количества")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = """
            { \n
            "color" : "RED", \n
            "shoeSize" : "RU39", \n
            "cottonComposition" : 50 \n
            }""",
            required = true)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Удалось добавить приход"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Параметры запроса отсутствуют или имеют некорректный формат"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Произошла ошибка, не зависящая от вызывающей стороны"
            )
    })
    public ResponseEntity<Socks> addSocks(@RequestBody Socks socks,
                                          @Min(1) @RequestParam(name = "Количество пар", defaultValue = "1") int quantity) {
        if (socks.getCottonComposition() < 0 || socks.getCottonComposition() > 100) {
            return ResponseEntity.badRequest().header("Error", "Incorrect values").build();
        }
        socksService.addSocks(socks, quantity);
        return ResponseEntity.ok(socks);
    }

    @PutMapping("/release")
    @Operation(summary = "Отпуск товара", description = "Отпуск товара со склада")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Удалось произвести отпуск носков со склада"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Товара нет на складе в нужном количестве или параметры запроса имеют некорректный формат"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Произошла ошибка, не зависящая от вызывающей стороны"
            )
    })
    public ResponseEntity<Void> releaseOfSocks(ColorOfSocks color, ShoeSize shoeSize, int cottonComposition, int quantity) {
        boolean status = socksService.releaseOfSocks(color, shoeSize, cottonComposition, quantity);
        return status ? ResponseEntity.ok().build() : ResponseEntity.badRequest().header("Error", "Incorrect values").build();
    }

    @GetMapping("/selection")
    @Operation(summary = "Выборка", description = "Отчет о количестве конкретных носков на складе")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Запрос выполнен, результат в теле ответа в виде строкового представления целого числа"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Параметры запроса отсутствуют или имеют некорректный формат"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Произошла ошибка, не зависящая от вызывающей стороны"
            )
    })
    public ResponseEntity<Integer> getReplyAboutSocks(ColorOfSocks color, ShoeSize shoeSize, int cottonMin, int cottonMax) {
        int selection = socksService.getInfoOfSocks(color, shoeSize, cottonMin, cottonMax);
        return ResponseEntity.ok(selection);
    }

    @GetMapping
    @Operation(summary = "Итог", description = "Запасы носков на складе")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Запрос выполнен успешно"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Произошла ошибка, не зависящая от вызывающей стороны"
            )
    })
    public ResponseEntity<Map<Socks, Integer>> getAll() {
        return ResponseEntity.ok(socksService.getInfoOfAllSocks());
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Расход товара", description = "Списание испорченного товара")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Запрос выполнен, товар списан со склада"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Параметры запроса отсутствуют или имеют некорректный формат"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Произошла ошибка, не зависящая от вызывающей стороны"
            )
    })
    public ResponseEntity<Void> deleteSocks(ColorOfSocks color, ShoeSize shoeSize, int cottonComposition, int quantity) {
        Socks socks = new Socks(color, shoeSize, cottonComposition);
        boolean status = socksService.deleteSocks(socks, quantity);
        return status ? ResponseEntity.ok().build() : ResponseEntity.badRequest().header("Error", "Incorrect values").build();
    }
}
