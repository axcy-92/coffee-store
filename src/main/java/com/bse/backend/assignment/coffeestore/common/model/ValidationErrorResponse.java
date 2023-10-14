package com.bse.backend.assignment.coffeestore.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ValidationErrorResponse {

    private String reason;
    private List<Violation> errors = new ArrayList<>();

}
