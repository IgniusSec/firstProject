package br.edu.ifmg.produto.resources;


import br.edu.ifmg.produto.dtos.ProductDTO;
import br.edu.ifmg.produto.dtos.UserDTO;
import br.edu.ifmg.produto.dtos.UserInsertDTO;
import br.edu.ifmg.produto.entities.User;
import br.edu.ifmg.produto.services.ProductService;
import br.edu.ifmg.produto.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/user")
@Tag(name = "User", description = "Controller/Resource for users")
public class UserResource {
    @Autowired
    private UserService userService;

    @GetMapping(produces = "application/json")
    @Operation(
            description = "Get all users",
            summary = "Return all users",
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200"),
            }
    )
    public ResponseEntity<Page<UserDTO>> findAll(Pageable pageable) {
        Page<UserDTO> list = userService.findAll(pageable);
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @Operation(
            description = "Get one user",
            summary = "Return one user",
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200"),
            }
    )
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        UserDTO list = userService.findById(id);
        return ResponseEntity.ok(list);
    }
    @PostMapping(produces = "application/json")
    @Operation(
            description = "Insert a new user",
            summary = "Create a new user",
            responses = {
                    @ApiResponse(description = "created", responseCode = "201"),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Forbidden", responseCode = "403"),
            }
    )
    public ResponseEntity<UserDTO> insert(@Valid @RequestBody UserInsertDTO dto) {
        UserDTO user = userService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(user);
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    @Operation(
            description = "Update a user",
            summary = "Update a user",
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200"),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Forbidden", responseCode = "403"),
                    @ApiResponse(description = "Not Found", responseCode = "404"),
            }
    )
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @Valid @RequestBody UserDTO dto) {
        dto = userService.update(id,dto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    @Operation(
            description = "Delete a user",
            summary = "Delete a user",
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200"),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Forbidden", responseCode = "403"),
                    @ApiResponse(description = "Not Found", responseCode = "404"),
            }
    )
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
