package springframework.service;

import org.springframework.transaction.annotation.Transactional;
import springframework.commands.IngredientCommand;
import springframework.commands.UnitOfMeasureCommand;
import springframework.converters.IngredientCommandToIngredient;
import springframework.converters.IngredientToIngredientCommand;
import springframework.converters.UnitOfMeasureCommandToUnitOfMeasure;
import springframework.domain.Ingredients;
import springframework.domain.Recipe;
import springframework.domain.UnitOfMeasure;
import springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by jt on 6/28/17.
 */
@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final RecipeRepository recipeRepository;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand, RecipeRepository recipeRepository,
                                 IngredientCommandToIngredient ingredientCommandToIngredient,UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.recipeRepository = recipeRepository;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.unitOfMeasureCommandToUnitOfMeasure = unitOfMeasureCommandToUnitOfMeasure;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()){
            //todo impl error handling
            log.error("recipe id not found. Id: " + recipeId);
        }

        Recipe recipe = recipeOptional.get();

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient-> ingredient.getId().equals(ingredientId))
                .map( ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();

        if(!ingredientCommandOptional.isPresent()){
            //todo impl error handling
            log.error("Ingredient id not found: " + ingredientId);
        }

        return ingredientCommandOptional.get();
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCOmmand(IngredientCommand ingredientCommand) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());
        if(!recipeOptional.isPresent()){
            log.error("error triggered");
            return new IngredientCommand();
        }else {
            Recipe recipe = recipeOptional.get();
            Optional<Ingredients> ingredientsOptional = recipe.getIngredients().stream().
                    filter(ingredients -> ingredients.getId().equals(ingredientCommand.getId())).findFirst();
            if(ingredientsOptional.isPresent()){
                Ingredients ingredients = ingredientsOptional.get();
                ingredients.setDescription(ingredientCommand.getDescription());
                ingredients.setAmount(ingredientCommand.getAmount());
                ingredients.setUom(unitOfMeasureCommandToUnitOfMeasure.convert(ingredientCommand.getUom()));
            }else{
                Ingredients ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
                ingredient.setRecipe(recipe);
                recipe.addIngredients(ingredient);
            }
            Recipe savedRecipe = recipeRepository.save(recipe);
            Set<Ingredients> ingredients = savedRecipe.getIngredients();

            Optional<Ingredients> savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            //check by description
            if(!savedIngredientOptional.isPresent()){
                //not totally safe... But best guess
                savedIngredientOptional = savedRecipe.getIngredients().stream()
                        .filter(recipeIngredients -> recipeIngredients.getDescription().equals(ingredientCommand.getDescription()))
                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(ingredientCommand.getAmount()))
                        .filter(recipeIngredients -> recipeIngredients.getUom().getId().equals(ingredientCommand.getUom().getId()))
                        .findFirst();
            }

            Ingredients ingredients1 =savedIngredientOptional.get();
            //to do check for fail
            return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
        }

    }

    @Override
    @Transactional
    public IngredientCommand deleteIngredient(Long recipeId, Long id) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if(!recipeOptional.isPresent()){
            log.error("error triggered");
        }else {
            Recipe recipe = recipeOptional.get();

            Set<Ingredients> test = StreamSupport.stream(recipe.getIngredients().spliterator(),false).filter(ingredients -> !(ingredients.getId().equals(id))).
                    collect(Collectors.toSet());
            recipe.setIngredients(test);
            Recipe recipe1 =recipeRepository.save(recipe);
        }

        return null;
    }


}