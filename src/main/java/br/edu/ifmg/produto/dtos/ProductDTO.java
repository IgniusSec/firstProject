package br.edu.ifmg.produto.dtos;

import br.edu.ifmg.produto.entities.Category;
import br.edu.ifmg.produto.entities.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.hateoas.RepresentationModel;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ProductDTO extends RepresentationModel<ProductDTO> {
    @Schema(description = "Database generated ID product")
    private Long id;
    @Schema(description = "product name")
    @Size(min = 3, max = 255, message = "Deve ter entre 3 e 255 chars")
    private String name;
    @Schema(description = "product description")
    private String description;
    @Schema(description = "product value")
    @Positive(message = "Preço deve ser >= 0")
    private double price;
    @Schema(description = "product image url")
    private String imageUrl;

    @Schema(description = "Linked table categories {one or more}")
    @NotEmpty(message = "Produto deve possuir ao menos uma categoria")
    private Set<CategoryDTO> categories = new HashSet<CategoryDTO>();

    public ProductDTO() {}

    public ProductDTO(Long id, String name, String description, double price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public ProductDTO(Product entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.price = entity.getPrice();
        this.imageUrl = entity.getImageUrl();
        entity.getCategories().forEach(c -> this.categories.add(new CategoryDTO(c)));
    }

    public ProductDTO(Product product, Set<Category> categories) {
        this(product);
        categories.forEach(c -> this.categories.add(new CategoryDTO(c)));
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ProductDTO that)) return false;
        return Objects.equals(id, that.id);
    }

    public Set<CategoryDTO> getCategories() {
        return categories;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
