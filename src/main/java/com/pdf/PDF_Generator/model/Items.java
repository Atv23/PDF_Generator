package com.pdf.PDF_Generator.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Items {
    private String name;
    private String quantity;
    private double rate;
    private double amount;
}
