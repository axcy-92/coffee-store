package com.bse.backend.assignment.coffeestore.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class Violation {

    private String fieldName;
    private String errorMessage;

}
