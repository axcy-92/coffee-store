package com.bse.backend.assignment.coffeestore.order.api.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class InputOrderItem implements Serializable {

    @Serial
    private static final long serialVersionUID = 1055388549667759790L;

    @NotNull
    @Positive
    private Long drinkId;

    @Builder.Default
    private List<@NotNull @Positive Long> toppingIds = new ArrayList<>();
}
