package study.jwt.more.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import study.jwt.more.domain.UserEntity;
import study.jwt.more.dto.JoinForm;
import study.jwt.more.repository.UserRepository;

@Service
public class JoinService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public JoinService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void joinProcess(JoinForm form) {
        String username = form.getUsername();
        String password = form.getPassword();

        Boolean exist = userRepository.existsByUsername(username);
        if (exist) {
            return;
        }
        UserEntity data = new UserEntity(username, passwordEncoder.encode(password), "ROLE_ADMIN");
        userRepository.save(data);
    }

}
