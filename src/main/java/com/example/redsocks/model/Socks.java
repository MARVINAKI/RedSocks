package com.example.redsocks.model;

import com.example.redsocks.model.category.ColorOfSocks;
import com.example.redsocks.model.category.ShoeSize;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import org.json.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сущность объекта")
public class Socks
        implements Serializable {
    @Schema(description = "Цвет носков", example = "RED")
    private ColorOfSocks color;
    @Schema(description = "Размер обуви", example = "RU42")
    private ShoeSize shoeSize;
    @PositiveOrZero
    @Schema(description = "Содержания хлопка в изделии", example = "50")
    private int cottonComposition;

    public Socks(String key) {
        JSONObject jsonObject = new JSONObject(key);
        String colorToString = (String) jsonObject.get("color");
        String shoeSizeToString = (String) jsonObject.get("shoeSize");
        String cottonCompositionToString = (String) jsonObject.get("cottonComposition");
        this.color = ColorOfSocks.valueOf(colorToString);
        this.shoeSize = ShoeSize.valueOf(shoeSizeToString);
        this.cottonComposition = Integer.parseInt(cottonCompositionToString);
    }

    @Override
    @JsonValue
    public String toString() {
        return "{\"color\":\"" + color + "\", \"shoeSize\":\"" + shoeSize + "\", \"cottonComposition\":\"" + cottonComposition + "\"}";
    }
}