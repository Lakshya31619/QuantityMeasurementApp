package com.app.arithmeticservice.dto;
import lombok.*;
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class QuantityDTO { private Double value; private String measurementType; private String unitName; }
