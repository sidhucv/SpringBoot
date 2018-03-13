package springframework.service;

import springframework.commands.RecipeCommand;
import springframework.domain.Recipe;
import springframework.exception.NotFoundException;

import java.util.Set;

public interface RecipeService {

    public Set<Recipe> getRecipies();

    public Recipe getRecpieByID(Long ID) throws NotFoundException;

    public RecipeCommand setRecipe(RecipeCommand recipeCommand);

    public RecipeCommand findById(Long id);

    public void deleteById(Long id);

}
