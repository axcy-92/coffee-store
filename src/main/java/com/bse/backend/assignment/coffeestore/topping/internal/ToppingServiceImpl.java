package com.bse.backend.assignment.coffeestore.topping.internal;

import com.bse.backend.assignment.coffeestore.topping.api.ToppingService;
import com.bse.backend.assignment.coffeestore.topping.api.exception.ToppingNotFoundException;
import com.bse.backend.assignment.coffeestore.topping.api.model.InputTopping;
import com.bse.backend.assignment.coffeestore.topping.api.model.Topping;
import com.bse.backend.assignment.coffeestore.topping.internal.persistence.ToppingEntity;
import com.bse.backend.assignment.coffeestore.topping.internal.persistence.ToppingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional
public class ToppingServiceImpl implements ToppingService {

    public static final String TOPPING_NOT_FOUND = "Topping not found";

    private final ToppingRepository repository;
    private final ToppingMapper mapper;

    @Override
    public List<Topping> getAllToppings() {
        List<ToppingEntity> entities = repository.findAll();
        log.debug("Found {} topping entities", entities.size());
        return mapper.toDtoList(entities);
    }

    @Override
    public Topping getToppingById(Long id) throws ToppingNotFoundException {
        return repository.findById(id)
                .map(entity -> {
                    log.debug("Found {} topping by id {}", entity, id);
                    return mapper.toDto(entity);
                }).orElseThrow(() -> new ToppingNotFoundException(TOPPING_NOT_FOUND));
    }

    @Override
    public List<Topping> getAllToppingsById(List<Long> ids) {
        List<ToppingEntity> entities = repository.findAllById(ids);

        return mapper.toDtoList(entities);
    }

    @Override
    public Topping createTopping(InputTopping topping) {
        ToppingEntity entity = mapper.toEntity(topping);

        ToppingEntity savedEntity = repository.save(entity);
        log.debug("New Topping has been successfully created: {}", savedEntity);

        return mapper.toDto(savedEntity);
    }

    @Override
    public Topping updateTopping(Long id, InputTopping topping) throws ToppingNotFoundException {
        ToppingEntity entity = repository.findById(id)
                .orElseThrow(() -> new ToppingNotFoundException(TOPPING_NOT_FOUND));
        log.debug("Update topping {} with values {}", entity, topping);

        if (topping == null) return mapper.toDto(entity);

        ToppingEntity updatedEntity = mapper.toEntity(topping);
        updatedEntity.setId(id);

        updatedEntity = repository.save(updatedEntity);
        log.debug("Topping has been successfully updated: {}", updatedEntity);

        return mapper.toDto(updatedEntity);
    }

    @Override
    public void deleteTopping(Long id) {
        repository.deleteById(id);
        log.debug("Topping with id {} has been deleted", id);
    }

}
