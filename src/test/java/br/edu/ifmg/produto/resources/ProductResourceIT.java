package br.edu.ifmg.produto.resources;

import br.edu.ifmg.produto.dtos.ProductDTO;
import br.edu.ifmg.produto.util.Factory;
import br.edu.ifmg.produto.util.TokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductResourceIT {
    // OBJ que irá fazer as requisições
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TokenUtil tokenUtil;
    private String username;
    private String password;
    private String token;

    private Long existingId;
    private Long nonExistingId;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 2000L;
        username = "maria@gmail.com";
        password = "123456";
        token = tokenUtil.obtainAccessToken(mockMvc, username, password);
    }

    @Test
    void findAllShouldReturnSortedPageWhenSortByName() throws Exception {
        ResultActions result = mockMvc.perform(get("/product?page=0&size=10&sort=name,asc").accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content[0].name").value("Macbook Pro"));
        result.andExpect(jsonPath("$.content[1].name").value("PC Gamer"));
    }

    @Test
    public void updateShouldReturnDtoWhenIdExists() throws Exception {
        ProductDTO dto = Factory.createProductDTO();
        String dtoJson = objectMapper.writeValueAsString(dto);
        String nameExpected = dto.getName();
        String descriptionExpected = dto.getDescription();

        ResultActions result = mockMvc.perform(put("/product/{id}", existingId)
                .header("Authorization", "Bearer " + token)
                .content(dtoJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").value(existingId));
        result.andExpect(jsonPath("$.name").value(nameExpected));
        result.andExpect(jsonPath("$.description").value(descriptionExpected));
    }

    @Test
    public void updateShouldReturnNotFoundWhenIdDoesNotExists() throws Exception {
        ProductDTO dto = Factory.createProductDTO();
        String dtoJson = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(put("/product/{id}", nonExistingId)
                .header("Authorization", "Bearer " + token)
                .content(dtoJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void insertShouldReturnNewObjectWhenDataCorrect() throws Exception {
        ProductDTO dto = Factory.createProductDTO();
        String dtoJson = objectMapper.writeValueAsString(dto);
        String nameExpected = dto.getName();
        long idExpected = 26;

        ResultActions result = mockMvc.perform(post("/product")
                .header("Authorization", "Bearer " + token)
                .content(dtoJson)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").value(idExpected));
        result.andExpect(jsonPath("$.name").value(nameExpected));
    }

    @Test
    public void deleteShouldReturnNoContentWhenIdExists() throws Exception {
        ResultActions result = mockMvc.perform(delete("/product/{id}", existingId)
                .header("Authorization", "Bearer " + token));

        result.andExpect(status().isNoContent());
    }

    @Test
    public void deleteShouldReturnNotFoundWhenIdDoesNotExists() throws Exception {
        ResultActions result = mockMvc.perform(delete("/product/{id}", nonExistingId)
                .header("Authorization", "Bearer " + token));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void findByIdShouldReturnProductWhenIdExists() throws Exception {
        ResultActions result = mockMvc.perform(get("/product/{id}", existingId)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        // get result as string json
        String resultJson = result.andReturn().getResponse().getContentAsString();

        // recreate a dto using the response get before
        ProductDTO dto = objectMapper.readValue(resultJson, ProductDTO.class);

        Assertions.assertEquals(existingId, dto.getId());
        Assertions.assertEquals("The Lord of the Rings", dto.getName());
    }
}
