package com.LMS.LibraryManagementSystem.service;

import com.LMS.LibraryManagementSystem.DTO.UsersDTO;
import com.LMS.LibraryManagementSystem.DTO.ValidityResponse;
import com.LMS.LibraryManagementSystem.model.Users;
import com.LMS.LibraryManagementSystem.repository.UserRepository;
import com.LMS.LibraryManagementSystem.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordUtils passwordUtils;

    public Users createUser(UsersDTO usersDTO){
        String hashedPassword = PasswordUtils.encodePassword(usersDTO.getPassword());

        Users user = Users.builder()
                .name(usersDTO.getName())
                .userType(usersDTO.getUserType())
                .emailId(usersDTO.getEmailId())
                .password(hashedPassword)
                .build();

        Optional<Users> saved = Optional.of(userRepository.save(user));
        return saved.orElseGet(Users::new);
    }

    public ValidityResponse validateUser(String userName,String password){
        Optional<Users> userOptional = userRepository.findByName(userName);
        ValidityResponse validityResponse = new ValidityResponse();
        if (userOptional.isPresent()){
            validityResponse.setValid(PasswordUtils.matches(password,userOptional.get().getPassword()));
            validityResponse.setUsers(userOptional.get());
        }else{
            return validityResponse;
        }
        return validityResponse;
    }
}
