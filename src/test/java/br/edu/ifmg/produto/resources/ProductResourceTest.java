package br.edu.ifmg.produto.resources;

import br.edu.ifmg.produto.dtos.ProductDTO;
import br.edu.ifmg.produto.services.ProductService;
import br.edu.ifmg.produto.util.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(ProductResource.class)
class ProductResourceTest {

    @Autowired
    //responsável pelas requisições a qual quero testar
    private MockMvc mockMvc;

    // camada a se mokar
    @MockitoBean
    private ProductService productService;

    private ProductDTO productDTO;
    private PageImpl<ProductDTO> page;
    private Long existingId;
    private Long nonExistingId;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 2000L;
        productDTO = Factory.createProductDTO();
        productDTO.setId(existingId);
        page = new PageImpl<>(List.of(productDTO));
    }

    @Test
    void findAllShouldReturnAllPage() throws Exception {
        //mocando metodo
        when(productService.findAll(any())).thenReturn(page);

        //testando requisicao
        ResultActions result = mockMvc.perform(get("/product").accept("application/json"));

        //Analisa o resultado
        result.andExpect(status().isOk());

    }

    @Test
    void findByIdShouldReturnProductWhenIdExists() throws Exception {
        //mocando metodo
        when(productService.findById(existingId)).thenReturn(productDTO);

        //testando requisicao
        ResultActions result = mockMvc.perform(get("/product/{id}", existingId).accept("application/json"));

        //Analisa o resultado
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").value(existingId));
        result.andExpect(jsonPath("$.name").value(productDTO.getName()));
        result.andExpect(jsonPath("$.description").value(productDTO.getDescription()));

    }
}