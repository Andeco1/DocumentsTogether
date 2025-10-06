package ru.together.documents.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import ru.together.documents.entity.User;
import ru.together.documents.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public boolean register(String username, String email, String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            return false;
        }

        if (userRepository.findByUsername(username).isPresent() || userRepository.findByEmail(email).isPresent()) {
            return false;
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        userRepository.save(user);

        return true;
    }

    public boolean login(String username, String password){
        if(userRepository.findByUsername(username).isPresent()){
            User user = userRepository.findByUsername(username).get();
            if(user.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }
}
