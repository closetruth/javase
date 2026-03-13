package com.closetruth.ui.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Card {
    private String size;
    private String color;

    @Override
    public String toString() {
        return size + color;
    }
}
