package ru.together.documents.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import ru.together.documents.entity.LibUser;
import ru.together.documents.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean register(String username, String email, String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            return false;
        }

        if (userRepository.findByUsername(username).isPresent() || userRepository.findByEmail(email).isPresent()) {
            return false;
        }

        LibUser libUser = new LibUser();
        libUser.setUsername(username);
        libUser.setEmail(email);
        libUser.setPassword(passwordEncoder.encode(password));
        userRepository.save(libUser);

        return true;
    }

    public boolean login(String username, String password){
        if(userRepository.findByUsername(username).isPresent()){
            LibUser libUser = userRepository.findByUsername(username).get();
            return passwordEncoder.matches(password, libUser.getPassword());
        }
        return false;
    }
}
