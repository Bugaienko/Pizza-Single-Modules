package balu.pizza.webapp.services;

import balu.pizza.webapp.models.TypeIngredient;
import balu.pizza.webapp.repositiries.TypesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author Sergii Bugaienko
 */

@Service
@Transactional(readOnly = true)
public class TypeService {
    private final Logger logger = LoggerFactory.getLogger(TypeService.class);
    private final TypesRepository typesRepository;

    @Autowired
    public TypeService(TypesRepository typesRepository) {
        this.typesRepository = typesRepository;
    }

    public List<TypeIngredient> findAllSorted(){
        return typesRepository.findAll(Sort.by("id").ascending());
    }

    @Transactional
    public TypeIngredient create(TypeIngredient type) {
        TypeIngredient newType = new TypeIngredient(type.getName());
//        System.out.println(newType);
        logger.info("Create new TypeIngredient {}", newType.getName());
        return typesRepository.save(newType);
    }

    public Optional<TypeIngredient> findByName(String name) {
        return typesRepository.findByName(name);
    }

    public TypeIngredient findById(int id) {
        return typesRepository.findById(id).get();
    }

    @Transactional
    public TypeIngredient update(TypeIngredient type) {
        TypeIngredient typeIngredient = typesRepository.findById(type.getId()).get();
        typeIngredient.setName(type.getName());
        logger.info("Update TypeIngredient id={}", type.getId());
        return typesRepository.save(typeIngredient);
    }
}
