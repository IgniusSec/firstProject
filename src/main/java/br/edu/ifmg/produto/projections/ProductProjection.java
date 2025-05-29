package br.edu.ifmg.produto.projections;

public interface ProductProjection {
    public Long getId();
    public String getName();
    public String getImageURL();
    public Double gerPrice();
}
