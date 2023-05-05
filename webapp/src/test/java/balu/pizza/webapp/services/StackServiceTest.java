package balu.pizza.webapp.services;

import balu.pizza.webapp.models.StackItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StackServiceTest {

    private final StackService stackService;

    @Autowired
    StackServiceTest(StackService stackService) {
        this.stackService = stackService;
    }

    @Test
    void findAllSorted() {

    }

    @Test
    void stackServiceFindAll() {
        StackItem stackItem1 = new StackItem("Stack name1", "image1.jpg", 1);
        stackItem1 = stackService.create(stackItem1);
        List<StackItem> items = stackService.findAll();
        StackItem stackItem2 = new StackItem("Stack name2", "image2.jpg", 2);
        stackItem2 = stackService.create(stackItem2);
        List<StackItem> items2 = stackService.findAll();
        List<StackItem> sortedItems = stackService.findAllSorted("name");

        assertEquals(items.size() + 1, items2.size());
        assertEquals(items.size() + 1, sortedItems.size());
        assertTrue(sortedItems.contains(stackItem2));
        assertTrue(items2.contains(stackItem1));

    }
}