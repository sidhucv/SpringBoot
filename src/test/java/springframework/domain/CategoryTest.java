/*
package springframework.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class CategoryTest {

    Category category;

    @Before
    public void setUp(){
     category= new Category();
    }

    @Test
    public void getId() {
        Long idVal = 4L;
        category.setId(idVal);
        assertEquals(idVal,category.getId());
    }

    @Test
    public void getDescription() {
        String descriptionVal = "test";
        category.setDescription(descriptionVal);
        assertEquals(descriptionVal,category.getDescription());
    }

    @Test
    public void getRecipes() {
        Recipe recipe=new Recipe();
        Set<Recipe> recipeSet  = new HashSet<Recipe>();
        recipeSet.add(recipe);
        category.setRecipes(recipeSet);
        assertNotNull(category.getRecipes());

    }
}*/
