package springframework.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import springframework.commands.RecipeCommand;
import springframework.domain.Recipe;
import springframework.exception.NotFoundException;
import springframework.exception.NumberException;
import springframework.service.RecipeService;

@Controller
public class RecipeController{

    RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @RequestMapping("/recipe/{id}/show")
    public String getRecipe(@PathVariable String id, Model model){
        Recipe recipe=null;
        try {
             recipe= recipeService.getRecpieByID(Long.valueOf(id));
        }catch (Exception e){
                throw new NumberException("error exception while parsing number");
        }

        model.addAttribute("recipe",recipe);
        return "recipe/show";
    }

    @RequestMapping("/recipe/new")
    public String newRecipe(Model model){
        model.addAttribute("recipe", new RecipeCommand());
        return "recipe/recipeform";
    }

    @RequestMapping("recipe/{id}/update")
    public String updateByID(@PathVariable String id, Model model){
       RecipeCommand recipeCommand= recipeService.findById(Long.valueOf(id));
       model.addAttribute("recipe",recipeCommand);
    return "recipe/recipeform";
    }

    @PostMapping("recipe")
    public String saveRecipe(@ModelAttribute RecipeCommand recipeCommand){
        RecipeCommand savedCommand = recipeService.setRecipe(recipeCommand);
        return "redirect:/recipe/"+savedCommand.getId()+"/show/";
    }

   @RequestMapping("/recipe/{id}/delete")
    public String deleteById(@PathVariable String id,Model model){
        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }
}
