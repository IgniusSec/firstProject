package br.edu.ifmg.produto.entities;
<<<<<<< HEAD
import br.edu.ifmg.produto.dtos.CategoryDTO;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "product")
=======
import jakarta.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

>>>>>>> 12e728f844bae804c7a188d8df551a1d1a5ccb18
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private double price;
<<<<<<< HEAD

=======
>>>>>>> 12e728f844bae804c7a188d8df551a1d1a5ccb18
    private String imageUrl;
    private Instant createAt;
    private Instant updateAt;

    @ManyToMany
    @JoinTable(
        name = "product_category", joinColumns = @JoinColumn(name="product_id"), 
        inverseJoinColumns = @JoinColumn(name="category_id")
    )
<<<<<<< HEAD
    private Set<CategoryDTO> categories = new HashSet<CategoryDTO>();
=======
    private Set<Category> categories = new HashSet<>();
>>>>>>> 12e728f844bae804c7a188d8df551a1d1a5ccb18

    public Product() {}

    public Product(Long id, String name, String description, double price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
    }
    
    public Product(Product entity) {
        this.id = entity.getId();
<<<<<<< HEAD
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.price = entity.getPrice();
        this.imageUrl = entity.getImageUrl();
        this.createAt = entity.getCreateAt();
        this.updateAt = entity.getUpdateAt();
    }

    public Product(Product product, Set<CategoryDTO> categories) {
        this(product);
        this.categories = categories;
    }

    public Set<CategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryDTO> categories) {
        this.categories = categories;
=======
    }

    public Product(Product product, Set<Category> category) {
        this(product);
>>>>>>> 12e728f844bae804c7a188d8df551a1d1a5ccb18
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

<<<<<<< HEAD
    @PrePersist
    public void prePersist() {
        this.createAt = Instant.now();
    }

    public Instant getCreateAt() {
        return createAt;
    }
    
    @PreUpdate
    public void PreUpdate() {
        this.updateAt = Instant.now();
    }
=======
    public Instant getCreateAt() {
        return createAt;
    }
>>>>>>> 12e728f844bae804c7a188d8df551a1d1a5ccb18

    public Instant getUpdateAt() {
        return updateAt;
    }

<<<<<<< HEAD
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Product product)) return false;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
=======
>>>>>>> 12e728f844bae804c7a188d8df551a1d1a5ccb18
}
