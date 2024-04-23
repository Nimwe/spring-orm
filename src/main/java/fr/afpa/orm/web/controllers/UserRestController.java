package fr.afpa.orm.web.controllers;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.afpa.orm.dto.UserDto;
import fr.afpa.orm.entities.User;
import fr.afpa.orm.repositories.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
    
    private final UserRepository userRepository;
    
    public UserRestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto toUserDto(User user) {
        return new UserDto(user.getId(),
                            user.getFirstName(),
                            user.getLastName(),
                            user.getEmail(),
                            user.getBirthdate(),
                            user.getAccounts());
    }

    @GetMapping
    public Iterable<UserDto> getAll() {
        Iterable<User> users = userRepository.findAll();
        Stream<User> usersStream = StreamSupport.stream(users.spliterator(), false);
        
        return usersStream.map(this::toUserDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable UUID id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent())
            return new ResponseEntity<>(toUserDto(user.get()), HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
