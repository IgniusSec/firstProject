package br.edu.ifmg.produto.services;

import br.edu.ifmg.produto.dtos.EmailDTO;
import br.edu.ifmg.produto.dtos.NewPasswordDTO;
import br.edu.ifmg.produto.dtos.RequestTokenDTO;
import br.edu.ifmg.produto.entities.PasswordRecover;
import br.edu.ifmg.produto.entities.User;
import br.edu.ifmg.produto.repository.PasswordRecoveryRepository;
import br.edu.ifmg.produto.repository.UserRepository;
import br.edu.ifmg.produto.services.exceptions.ResourceNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class AuthService {
    @Value("${email.password-recover.token.minutes}")
    private int tokenMinutes;

    @Value("${email.password-recover.uri}")
    private String uri;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordRecoveryRepository passwordRecoveryRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void createRecoverToken(RequestTokenDTO dto) {
        //busca user pelo email
        User user = userRepository.findByEmail(dto.getEmail());
        if (user == null) {
            throw new ResourceNotFound("Email not found");
        }

        //gera token
        String token = UUID.randomUUID().toString();

        //salva token no BD
        PasswordRecover passwordRecover = new PasswordRecover();
        passwordRecover.setToken(token);
        passwordRecover.setEmail(user.getEmail());
        passwordRecover.setExpiration(Instant.now().plusSeconds(tokenMinutes* 60L));
        passwordRecoveryRepository.save(passwordRecover);

        //envia email
        String body = "Acesse o link para definir uma nova senha (válido por"
                + tokenMinutes + ")" + "\n\n" + uri+token;

        emailService.sendMail(
                new EmailDTO(user.getEmail(), "Recuperação de senha", body)
        );
    }

    public void saveNewPassword(NewPasswordDTO dto) {
        List<PasswordRecover> list = passwordRecoveryRepository.searchValidToken(dto.getToken(), Instant.now());
        if (list.isEmpty()) {
            throw new ResourceNotFound("Token not found!");
        }

        User user = userRepository.findByEmail(list.getFirst().getEmail());

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));

        userRepository.save(user);
    }
}
