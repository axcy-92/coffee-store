package com.bse.backend.assignment.coffeestore.order.api.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Represents an input request for modifying an order. This class is serializable and used for JSON requests.
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class InputOrder implements Serializable {

    @Serial
    private static final long serialVersionUID = -7287209856203858828L;

    @Size(min = 1)
    private List<@Valid InputOrderItem> items;

}
