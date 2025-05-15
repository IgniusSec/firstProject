package br.edu.ifmg.produto.repository;

import br.edu.ifmg.produto.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // se precisar tá ai
    User findByEmail(String email);
    User findByEmailAndPassword(String email, String password);
}
