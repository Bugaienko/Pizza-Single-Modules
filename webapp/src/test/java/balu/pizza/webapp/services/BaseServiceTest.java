package balu.pizza.webapp.services;

import balu.pizza.webapp.models.Base;
import balu.pizza.webapp.util.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class BaseServiceTest {


    private final BaseService baseService;

    @Autowired
    BaseServiceTest(BaseService baseService) {
        this.baseService = baseService;
    }

    @Test
    void baseServiceTest() {
        List<Base> bases = baseService.findAll();
        String baseName = "Base1";
        Base base1 = new Base("Small", baseName, 4);
        base1 = baseService.create(base1);
        List<Base> basesAfterAddBase = baseService.findAll();
        List<Base> sortedBases = baseService.findAllSorted();

        assertEquals(bases.size() + 1, basesAfterAddBase.size());
        assertEquals(bases.size() + 1, sortedBases.size());
        assertTrue(sortedBases.contains(base1));

        Optional<Base> foundedByName = baseService.findByName(baseName);
        assertTrue(foundedByName.isPresent());
        assertEquals(baseName, foundedByName.get().getName());

        Base foundedById = baseService.findById(base1.getId());
        assertEquals(base1.getId(), foundedById.getId());
        assertThrows(NotFoundException.class, () -> {
            baseService.findById(110024111);
        });

        double newPrice = 5.0;
        base1.setPrice(newPrice);
        baseService.update(base1);
        Base savedBase = baseService.findById(base1.getId());
        assertEquals(newPrice, savedBase.getPrice());
    }
}