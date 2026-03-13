package com.closetruth.ui.project;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Movie {
    private String name;
    private String actor;
    private double score;
    private double price;
}
