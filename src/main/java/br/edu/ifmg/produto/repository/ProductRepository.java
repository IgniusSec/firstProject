package br.edu.ifmg.produto.repository;

import br.edu.ifmg.produto.entities.Product;
import br.edu.ifmg.produto.projections.ProductProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(nativeQuery = true,
            value = """
                    SELECT *
                    FROM (
                        SELECT DISTINCT p.id, p.name, p.imageUrl, p.price
                        FROM product p
                        INNER JOIN product_category pc ON pc.product_id = p.id
                        WHERE (pc.category_id in :categoriesID)
                        AND LOWER(p.name) like LOWER(CONCAT('%',:name,'%'))
                    ) as result
                    """,
            countQuery = """
                        SELECT COUNT(*)
                        FROM (
                            SELECT DISTINCT p.id, p.name, p.imageUrl, p.price
                            FROM product p
                            INNER JOIN product_category pc ON pc.product_id = p.id
                            WHERE (pc.category_id in :categoriesID)
                            AND LOWER(p.name) like LOWER(CONCAT('%',:name,'%'))
                        ) as result
                        """
    )
    public Page<ProductProjection> searchProductsWithCategories(List<Long> categoriesID, String name, Pageable pageable);

    @Query(nativeQuery = true,
            value = """
                    SELECT *
                    FROM (
                        SELECT DISTINCT p.id, p.name, p.imageUrl, p.price
                        FROM product p
                        INNER JOIN product_category pc ON pc.product_id = p.id
                        WHERE LOWER(p.name) like LOWER(CONCAT('%',:name,'%'))
                    ) as result
                    """,
            countQuery = """
                        SELECT COUNT(*)
                        FROM (
                            SELECT DISTINCT p.id, p.name, p.imageUrl, p.price
                            FROM product p
                            INNER JOIN product_category pc ON pc.product_id = p.id
                            WHERE LOWER(p.name) like LOWER(CONCAT('%',:name,'%'))
                        ) as result
                        """
    )
    public Page<ProductProjection> searchProductsWithoutCategories(String name, Pageable pageable);
}
