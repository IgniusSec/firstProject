package br.edu.ifmg.produto.repository;

import br.edu.ifmg.produto.entities.PasswordRecover;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;

public interface PasswordRecoveryRepository extends JpaRepository<PasswordRecover, Long> {
    @Query("SELECT obj FROM PasswordRecover obj"
            + " WHERE (obj.token = :token)" +
            " AND (obj.expiration > :now)"
    )
    public List<PasswordRecover> searchValidToken(String token, Instant now);
}
