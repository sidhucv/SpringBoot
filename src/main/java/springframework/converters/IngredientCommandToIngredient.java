package springframework.converters;


import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import springframework.commands.IngredientCommand;
import springframework.domain.Ingredients;

/**
 * Created by jt on 6/21/17.
 */
@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredients> {

    private final UnitOfMeasureCommandToUnitOfMeasure uomConverter;

    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Nullable
    @Override
    public Ingredients convert(IngredientCommand source) {
        if (source == null) {
            return null;
        }

        final Ingredients ingredient = new Ingredients();
        ingredient.setId(source.getId());
        ingredient.setAmount(source.getAmount());
        ingredient.setDescription(source.getDescription());
        ingredient.setUom(uomConverter.convert(source.getUom()));
        return ingredient;
    }
}
