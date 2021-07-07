package by.artur.authentication_app.service.impl;

import by.artur.authentication_app.model.Role;
import by.artur.authentication_app.model.User;
import by.artur.authentication_app.repository.RoleRepository;
import by.artur.authentication_app.repository.UserRepository;
import by.artur.authentication_app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository repository, RoleRepository roleRepository,BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.repository = repository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

    }

    @Override
    public User register(User user) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);

        User registerUser = repository.save(user);

        log.info("IN register - user: {} successfully registered", registerUser);
        return registerUser;
    }

    @Override
    public List<User> getAll() {
        List<User> result = repository.findAll();

        log.info("IN getAll {} found", result.size());
        return result;
    }

    @Override
    public User findByUsername(String username) {
        User user = repository.findByUsername(username);

        if(user == null){
            log.warn("IN findByUsername - user found by username {}", user.getUsername());
        }

        log.info("IN findByUsername - user: {} found by username {}", user, user.getUsername());
        return user;
    }

    @Override
    public User findById(Long id) {
        Optional<User> user = repository.findById(id);

        if(!user.isPresent()){
            log.warn("IN findById - user: {} no found by id {}", user, user.get().getId());
            return null;
        }

        log.info("IN findById - user: {} found by id {}", user, user.get().getId());
        return user.get();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
        log.info("IN delete - user with id: {} successfully deleted", id);
    }
}
