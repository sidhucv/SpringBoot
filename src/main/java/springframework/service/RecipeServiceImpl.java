package springframework.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springframework.commands.RecipeCommand;
import springframework.converters.RecipeCommandToRecipe;
import springframework.converters.RecipeToRecipeCommand;
import springframework.domain.Recipe;
import springframework.exception.NotFoundException;
import springframework.repositories.RecipeRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService{

    RecipeRepository recipeRepository;

    RecipeCommandToRecipe recipeCommandToRecipe;

    RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe,RecipeToRecipeCommand recipeToRecipeCommand ) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    public Set<Recipe> getRecipies(){
        log.debug("inside service");

        Set<Recipe> recipeSet = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
        return recipeSet;
    }

    @Override
    public Recipe getRecpieByID(Long Id) {
        Optional<Recipe> recipe = recipeRepository.findById(Id);
        if(!recipe.isPresent()){
            throw new NotFoundException(Id+":is not present please reverify");
        }
        return recipe.get();
    }

    @Override
    @Transactional
    public RecipeCommand setRecipe(RecipeCommand recipeCommand) {
        Recipe recipe = recipeCommandToRecipe.convert(recipeCommand);
        Recipe save = recipeRepository.save(recipe);
        RecipeCommand recipeCommand1 = recipeToRecipeCommand.convert(save);
        return recipeCommand1;
    }

    @Override
    @Transactional
    public RecipeCommand findById(Long id) {
        return recipeToRecipeCommand.convert(getRecpieByID(id));
    }

    public void deleteById(Long id){
        recipeRepository.deleteById(id);
    }
}
