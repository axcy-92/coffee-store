package com.bse.backend.assignment.coffeestore.drink.internal;

import com.bse.backend.assignment.coffeestore.drink.api.DrinkService;
import com.bse.backend.assignment.coffeestore.drink.api.exception.DrinkNotFoundException;
import com.bse.backend.assignment.coffeestore.drink.api.model.Drink;
import com.bse.backend.assignment.coffeestore.drink.api.model.InputDrink;
import com.bse.backend.assignment.coffeestore.drink.internal.persistence.DrinkEntity;
import com.bse.backend.assignment.coffeestore.drink.internal.persistence.DrinkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional
public class DrinkServiceImpl implements DrinkService {

    public static final String DRINK_NOT_FOUND = "Drink not found";

    private final DrinkRepository repository;
    private final DrinkMapper mapper;

    @Override
    public List<Drink> getAllDrinks() {
        List<DrinkEntity> entities = repository.findAll();
        log.debug("Found {} drink entities", entities.size());
        return mapper.toDtoList(entities);
    }

    @Override
    public Drink getDrinkById(Long id) throws DrinkNotFoundException {
        return repository.findById(id)
                .map(entity -> {
                    log.debug("Found {} drink by id {}", entity, id);
                    return mapper.toDto(entity);
                }).orElseThrow(() -> new DrinkNotFoundException(DRINK_NOT_FOUND));
    }

    @Override
    public Drink createDrink(InputDrink drink) {
        DrinkEntity entity = mapper.toEntity(drink);

        DrinkEntity savedEntity = repository.save(entity);
        log.debug("New Drink has been successfully created: {}", savedEntity);

        return mapper.toDto(savedEntity);
    }

    @Override
    public Drink updateDrink(Long id, InputDrink drink) throws DrinkNotFoundException {
        DrinkEntity entity = repository.findById(id)
                .orElseThrow(() -> new DrinkNotFoundException(DRINK_NOT_FOUND));
        log.debug("Update drink {} with values {}", entity, drink);

        if (drink == null) return mapper.toDto(entity);

        DrinkEntity updatedEntity = mapper.toEntity(drink);
        updatedEntity.setId(id);

        updatedEntity = repository.save(updatedEntity);
        log.debug("Drink has been successfully updated: {}", updatedEntity);

        return mapper.toDto(updatedEntity);
    }

    @Override
    public void deleteDrink(Long id) {
        repository.deleteById(id);
        log.debug("Drink with id {} has been deleted", id);
    }

}
