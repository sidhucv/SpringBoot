package springframework.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import springframework.domain.Recipe;
import springframework.repositories.RecipeRepository;

import java.io.IOException;
import java.util.Optional;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveImageFile(Long id, MultipartFile file) {


        Optional<Recipe> recipeOptional = recipeRepository.findById(id);
        if(!recipeOptional.isPresent()){
        }else{
            try {
                Byte[] bytesObject = new Byte[file.getBytes().length];
                int i =0;
                for(byte b:file.getBytes()){
                    bytesObject[i++] = b;
                }
                Recipe recipe = recipeOptional.get();
                recipe.setImage(bytesObject);
                recipeRepository.save(recipe);
            }catch(IOException e){
                log.error("errror occured",e);
            }
        }
    }
}
