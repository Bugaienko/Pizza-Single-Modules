package balu.pizza.webapp.services;

import balu.pizza.webapp.models.TypeIngredient;
import balu.pizza.webapp.repositiries.TypesRepository;
import balu.pizza.webapp.util.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Type of ingredient Service
 * @author Sergii Bugaienko
 */

@Service
@Transactional(readOnly = true)
public class TypeService {
    private final Logger logger = LoggerFactory.getLogger(TypeService.class);
    private final TypesRepository typesRepository;

    /**
     * Implements repository
     * @param typesRepository Type of ingredient Repository
     */
    @Autowired
    public TypeService(TypesRepository typesRepository) {
        this.typesRepository = typesRepository;
    }

    /**
     * Searches all types
     * @return List of all types from DB
     */
    public List<TypeIngredient> findAll(){
        return typesRepository.findAll();
    }

    /**
     * Searches all types
     * @return Sorted by id list of all types from DB
     */
    public List<TypeIngredient> findAllSorted(){
        return typesRepository.findAll(Sort.by("id").ascending());
    }

    /**
     * Creating and saving new Type ingredient
     * @param type
     * @return saved Type
     */
    @Transactional
    public TypeIngredient create(TypeIngredient type) {
        TypeIngredient newType = new TypeIngredient(type.getName());
//        System.out.println(newType);
        logger.info("Create new TypeIngredient {}", newType.getName());
        return typesRepository.save(newType);
    }

    /**
     * Searches Type ingredient by name
     * @param name Type name
     * @return An object that may contain a TypeIngredient or be empty
     */
    public Optional<TypeIngredient> findByName(String name) {
        return typesRepository.findByName(name);
    }

    /**
     * Searches TypeIngredient by ID
     * @param id type id
     * @return if successful: TypeIngredient
     * @throws NotFoundException If the TypeIngredient was not found
     */
    public TypeIngredient findById(int id) {
        return typesRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    /**
     * Update name of TypeIngredient
     * @param type new TypeIngredient name
     * @return updated TypeIngredient
     */
    @Transactional
    public TypeIngredient updateName(TypeIngredient type) {
        TypeIngredient typeIngredient = findById(type.getId());
        typeIngredient.setName(type.getName());
        logger.info("Update TypeIngredient id={}", type.getId());
        return typesRepository.save(typeIngredient);
    }


}
