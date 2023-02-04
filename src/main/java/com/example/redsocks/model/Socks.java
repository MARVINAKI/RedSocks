package com.example.redsocks.model;

import com.example.redsocks.model.category.ColorOfSocks;
import com.example.redsocks.model.category.ShoeSize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Socks implements Serializable {

    private ColorOfSocks color;
    private ShoeSize shoeSize;
    private int cottonComposition;
}