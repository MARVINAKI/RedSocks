package com.example.redsocks.model.category;

public enum ColorOfSocks {
    RED("Красный"),
    BLUE("Синий"),
    GREEN("Зеленый"),
    YELLOW("Желтый"),
    WHITE("Белый"),
    BLACK("Черный"),
    BROWN("Коричневый"),
    GRAY("Серый"),
    PINK("Розовый"),
    PURPLE("Фиолетовый"),
    ORANGE("Оранжевый"),
    MULTICOLOR("Многоцветный");

    String color;

    ColorOfSocks(String color) {
        this.color = color;
    }

    public final String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return color;
    }
}
