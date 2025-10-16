package com.blog.modules.user.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.modules.user.domain.exception.EmailAlreadyExistsException;
import com.blog.modules.user.domain.exception.UserNotFoundException;
import com.blog.modules.user.domain.exception.UsernameAlreadyExistsException;
import com.blog.modules.user.domain.model.User;
import com.blog.modules.user.domain.port.in.UserService;
import com.blog.modules.user.infrastructure.adapter.in.web.dto.UpdateUserCommand;
import com.blog.modules.user.infrastructure.adapter.out.persistence.UserRepositoryImpl;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepositoryImpl userRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public UserServiceImpl(UserRepositoryImpl userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id.toString()));
        return user;
    }

    // TODO : implement userExist
    @Override
    public Boolean userExist(UUID id) {
        return userRepository.findById(id).isPresent();
    }

@Override
public String getUserReadme(UUID userId) {
    return """
             # Welcome to My Profile!

             Hello! 👋 I'm [Your Name], a passionate [Your Profession].

              ## About Me
             I specialize in [mention your skills, experience, or areas of interest]. I love working on [types of projects], and I'm always open to learning new technologies.
            - 🔭 Currently working on: [Current Project]
            - 🌱 Currently learning: [Skills you're currently learning]
            - 💬 Ask me about: [Topics you're open to discuss]
            - 📫 How to reach me: [Your Contact Information]

            ## Skills
            - 🖥️ [Skill 1]
            - 🔧 [Skill 2]
            - 🎨 [Skill 3]

            ## Projects
            Check out some of my awesome work below:

            - [Project 1 Name](#) – Brief description of the project.
            - [Project 2 Name](#) – Brief description of the project.

        Thanks for visiting my profile! Feel free to connect with me.
            
             """;
}


    @Override
    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        return user;
    }

    @Override
    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
        return user;
    }

    @Override
    public User updateUser(UUID id, UpdateUserCommand cmd) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id.toString()));

        if (cmd.getName() != null) {
            user.changeName(cmd.getName());
        }

        if (cmd.getUsername() != null && !user.getUsername().equals(cmd.getUsername())) {
            if (userRepository.findByUsername(cmd.getUsername()).isPresent()) {
                throw new UsernameAlreadyExistsException(cmd.getUsername());
            }
            user.changeUsername(cmd.getUsername());
        }

        if (cmd.getEmail() != null && !user.getEmail().equals(cmd.getEmail())) {
            if (userRepository.findByEmail(cmd.getEmail()).isPresent()) {
                throw new EmailAlreadyExistsException(cmd.getEmail());
            }
            user.changeEmail(cmd.getEmail());
        }

        if (cmd.getPassword() != null) {
            user.changePassword(encoder.encode(cmd.getPassword()));
        }

        userRepository.save(user);

        return user;
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id.toString()));
        userRepository.deleteById(id);
    }

}
