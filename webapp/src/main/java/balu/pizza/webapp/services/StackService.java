package balu.pizza.webapp.services;

import balu.pizza.webapp.models.StackItem;
import balu.pizza.webapp.repositiries.StacksRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Sergii Bugaienko
 */

@Service
@Transactional(readOnly = true)
public class StackService {
    private final Logger logger = LoggerFactory.getLogger(StackService.class);
    private  final StacksRepository stacksRepository;

    public StackService(StacksRepository stacksRepository) {
        this.stacksRepository = stacksRepository;
    }

    public StackItem create(StackItem stackItem){
        return stacksRepository.save(stackItem);
    }


    public List<StackItem> findAllSorted(String sortBy){
        return stacksRepository.findAll(Sort.by(sortBy).ascending());
    }

    public List<StackItem> findAll(){
        return stacksRepository.findAll();
    }




}
