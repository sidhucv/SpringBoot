package springframework.domain;


import javax.persistence.*;

@Entity
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Recipe recipe;

    @Lob
    private String reciepeNotes;

    public Notes() {
    }

    public Long getId() {
        return this.id;
    }

    public Recipe getRecipe() {
        return this.recipe;
    }

    public String getReciepeNotes() {
        return this.reciepeNotes;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public void setReciepeNotes(String reciepeNotes) {
        this.reciepeNotes = reciepeNotes;
    }
}
