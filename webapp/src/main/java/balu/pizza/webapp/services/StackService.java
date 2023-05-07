package balu.pizza.webapp.services;

import balu.pizza.webapp.models.StackItem;
import balu.pizza.webapp.repositiries.StacksRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Stack item Service
 * @author Sergii Bugaienko
 */

@Service
@Transactional(readOnly = true)
public class StackService {
    private final Logger logger = LoggerFactory.getLogger(StackService.class);
    private  final StacksRepository stacksRepository;

    @Autowired
    public StackService(StacksRepository stacksRepository) {
        this.stacksRepository = stacksRepository;
    }

    /**
     * Writing a new Stack entity to the database
     * @param stackItem
     * @return saved Stack item
     */
    public StackItem create(StackItem stackItem){
        return stacksRepository.save(stackItem);
    }


    /**
     * Searches all Stack items
     * @param sortBy sorting parameter
     * @return Sorted list of stack items
     */
    public List<StackItem> findAllSorted(String sortBy){
        return stacksRepository.findAll(Sort.by(sortBy).ascending());
    }

    /**
     * Searches all Stack items
     * @return List of stack items
     */
    public List<StackItem> findAll(){
        return stacksRepository.findAll();
    }




}
