package springframework.service;

import springframework.commands.IngredientCommand;

public interface IngredientService {

    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

    public IngredientCommand saveIngredientCOmmand(IngredientCommand ingredientCommand);

    public IngredientCommand deleteIngredient(Long recipeId,Long id);
}
