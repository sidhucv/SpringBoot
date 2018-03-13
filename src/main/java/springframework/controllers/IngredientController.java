package springframework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springframework.commands.IngredientCommand;
import springframework.commands.RecipeCommand;
import springframework.commands.UnitOfMeasureCommand;
import springframework.domain.Ingredients;
import springframework.domain.Recipe;
import springframework.domain.UnitOfMeasure;
import springframework.service.IngredientService;
import springframework.service.RecipeService;
import springframework.service.UnitOfMeasureService;



@Controller
public class IngredientController {

    RecipeService recipeService;
    IngredientService ingredientService;
    UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService,IngredientService ingredientService,UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService=unitOfMeasureService;
    }

    @GetMapping("/recipe/{id}/ingredient/new")
    public String newRecipe(Model model,@PathVariable String id){
        RecipeCommand recipeCommand = recipeService.findById(Long.valueOf(id));
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(Long.valueOf(id));
        ingredientCommand.setUom(new UnitOfMeasureCommand());
        model.addAttribute("uomList",unitOfMeasureService.getUom());
        model.addAttribute("ingredient",ingredientCommand);

        return "recipe/ingredient/ingredientform";
    }

    @GetMapping("/recipe/{id}/ingredients")
    public String listIngredients(Model model, @PathVariable String id){
           Recipe recipe =  recipeService.getRecpieByID(Long.valueOf(id));
           model.addAttribute("recipe",recipe);
        return "recipe/ingredient/list";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{id}/show")
    public String showRecipeIngredient(@PathVariable String recipeId,
                                       @PathVariable String id, Model model){
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));
        return "recipe/ingredient/show";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{id}/update")
    public String updateIngredient(Model model,@PathVariable String recipeId, @PathVariable String id){
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));
        model.addAttribute("uomList",unitOfMeasureService.getUom());
        return "recipe/ingredient/ingredientform";
    }

    @PostMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand ingredientCommand){
        IngredientCommand ingredientCommand1 =ingredientService.saveIngredientCOmmand(ingredientCommand);
        return "redirect:/recipe/"+ingredientCommand1.getRecipeId()+"/ingredient/"+ingredientCommand1.getId()+"/show";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{id}/delete")
    public String deleteIngredient(@PathVariable String recipeId, @PathVariable String id){
            ingredientService.deleteIngredient(Long.valueOf(recipeId),Long.valueOf(id));
            return "redirect:/";
    }
}
