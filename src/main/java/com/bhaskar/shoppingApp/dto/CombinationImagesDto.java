package com.bhaskar.shoppingApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CombinationImagesDto {
    String combinationId;
    List<byte[]> images;
}
