package balu.pizza.webapp.controllers;

import balu.pizza.webapp.services.StackService;
import balu.pizza.webapp.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**Controller for displaying the stack of tools and technologies used in project development
 *
 * @author Sergii Bugaienko
 */

@Controller
@RequestMapping("/stack")
public class StackController {


    private final UserUtil userUtil;
    private final StackService stackService;

    /**
     *
     * @param userUtil Set users utils
     * @param stackService Stack service
     */
    @Autowired
    public StackController(UserUtil userUtil, StackService stackService) {
        this.userUtil = userUtil;
        this.stackService = stackService;
    }

    /**
     * Stack page generation
     * <p style="text-align:left;">
     * <img src="doc-files/stack.png" style="max-width: 50%;" alt="admin panel">
     * </p>
     * @param model
     * @return Rendering page
     */
    @GetMapping()
    public String indexPage(Model model){
        model.addAttribute("user", userUtil.getActiveUser());
        model.addAttribute("stackItems", stackService.findAllSorted("priority"));
        return "stack/index";
    }


}
