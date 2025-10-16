package ru.together.documents.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import ru.together.documents.entity.LibUser;
import ru.together.documents.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LibUser register(String username, String email, String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            return null;
        }

        if (userRepository.findByUsername(username).isPresent() || userRepository.findByEmail(email).isPresent()) {
            return null;
        }

        LibUser libUser = new LibUser();
        libUser.setUsername(username);
        libUser.setEmail(email);
        libUser.setPassword(passwordEncoder.encode(password));
        userRepository.save(libUser);

        return libUser;
    }

    public boolean login(String username, String password){
        if(userRepository.findByUsername(username).isPresent()){
            LibUser libUser = userRepository.findByUsername(username).get();
            return passwordEncoder.matches(password, libUser.getPassword());
        }
        return false;
    }
    public Optional<LibUser> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<LibUser> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<LibUser> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public LibUser save(LibUser user) {
        return userRepository.save(user);
    }


    public LibUser updateUser(Long id, String newEmail, String newPassword) {
        LibUser user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (newEmail != null && !newEmail.isBlank()) {
            user.setEmail(newEmail);
        }

        if (newPassword != null && !newPassword.isBlank()) {
            user.setPassword(passwordEncoder.encode(newPassword));
        }

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found");
        }
        userRepository.deleteById(id);
    }
}
