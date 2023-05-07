package balu.pizza.webapp.services;

import balu.pizza.webapp.models.Base;
import balu.pizza.webapp.repositiries.BaseRepository;
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
 * Service layer of the project for the Base entity
 *
 * @author Sergii Bugaienko
 */

@Service
@Transactional(readOnly = true)
public class BaseService {

    private final BaseRepository baseRepository;

    private final Logger logger = LoggerFactory.getLogger(BaseService.class);

    /**
     * Implementing the repository layer to interact with the database
     * @param baseRepository Base Repository
     */
    @Autowired
    public BaseService(BaseRepository baseRepository) {
        this.baseRepository = baseRepository;
    }

    /**
     * Base search method by name
     * @param name
     * @return An object that may contain a Base or be empty
     */
    public Optional<Base> findByName(String name) {
        return baseRepository.findByName(name);
    }

    /**
     * Create new Base
     * @param base
     * @return new Base from DB
     */
    @Transactional
    public Base create(Base base) {
        Base newBase = new Base(base.getSize(), base.getName(), base.getPrice());
        //DONE save tp BD
        logger.info("Add new base {}", base.getName());
        return baseRepository.save(base);
    }

    /**
     *
     * @return List of all Bases from DB
     */
    public List<Base> findAll(){
        return baseRepository.findAll();
    }

    /**
     *
     * @return Sorted by name list of all Bases from DB
     */
    public List<Base> findAllSorted() {
        return baseRepository.findAll(Sort.by("size").and(Sort.by("name")));
    }

    /**
     * Find base by ID
     * @param baseId
     * @return Base or throw NotFoundException
     */
    public Base findById(int baseId) {
        return baseRepository.findById(baseId).orElseThrow(NotFoundException::new);
    }


    /**
     * Update Base in DB
     * @param base
     * @return updated Base
     */
    @Transactional
    public Base update(Base base) {
        logger.info("Update base {}", base.getId() + "/" + base.getName());
        return baseRepository.save(base);
    }
}
