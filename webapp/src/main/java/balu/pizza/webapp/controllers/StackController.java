package balu.pizza.webapp.controllers;

import balu.pizza.webapp.services.StackService;
import balu.pizza.webapp.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Sergii Bugaienko
 */

@Controller
@RequestMapping("/stack")
public class StackController {


    private final UserUtil userUtil;
    private final StackService stackService;

    @Autowired
    public StackController(UserUtil userUtil, StackService stackService) {
        this.userUtil = userUtil;
        this.stackService = stackService;
    }

    @GetMapping()
    public String indexPage(Model model){
        model.addAttribute("user", userUtil.getActiveUser());
        model.addAttribute("stackItems", stackService.findAllSorted("priority"));
        return "stack/index";
    }


}
