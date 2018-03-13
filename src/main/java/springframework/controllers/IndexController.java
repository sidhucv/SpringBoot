package springframework.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import springframework.domain.Category;
import springframework.domain.Recipe;
import springframework.domain.UnitOfMeasure;
import springframework.repositories.CategoryRepository;
import springframework.repositories.UnitofMeasureRepository;
import springframework.service.RecipeService;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Controller

public class IndexController {

    RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"","/","index"})
    public String getIndexPage(Model model){

        log.debug("inside controller");
        model.addAttribute("recipes",recipeService.getRecipies());
        return "index";
    }
}


