package br.edu.ifmg.produto.util;

import br.edu.ifmg.produto.dtos.ProductDTO;
import br.edu.ifmg.produto.entities.Category;
import br.edu.ifmg.produto.entities.Product;

public class Factory {
    public static Product createProduct() {
        Product p = new Product();
        p.setName("IFome XXX");
        p.setPrice(4500.23);
        p.setImageUrl("https://img.com/imagem.jpg");
        p.getCategories().add(new Category(2L, "Eletrônicos"));
        return p;
    }

    public static ProductDTO createProductDTO() {
        Product p = createProduct();
        return new ProductDTO(p);
    }
}
