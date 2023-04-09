package com.scm.module.validator;

import com.scm.module.DTO.UserDTO;
import com.scm.module.DTO.UserLoginDTO;
import com.scm.module.Exception.UserAlreadyExistsException;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    // Validation logic for user login
    public void validateUserLogin(UserLoginDTO userLoginDTO) {
        if (userLoginDTO == null) {
            throw new IllegalArgumentException("User login data must not be null");
        }

        if (userLoginDTO.getEmail() == null || userLoginDTO.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email must not be empty");
        }

        if (userLoginDTO.getPassword() == null || userLoginDTO.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password must not be empty");
        }
    }

    // Validation logic for user signup
    public void validateUserSignup(UserDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("User data must not be null");
        }

        if (userDTO.getName() == null || userDTO.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name must not be empty");
        }

        if (userDTO.getEmail() == null || userDTO.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email must not be empty");
        }

        if (!isValidEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("Email is not valid");
        }

        if (userDTO.getPassword() == null || userDTO.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password must not be empty");
        }

        // Add more validation logic for other fields as necessary, e.g., checking for valid phone numbers, etc.
    }

    // Validation logic for user delete
    public void validateUserDelete(Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
    }

    // Validation logic for user update
    public void validateUserUpdate(Long userId, UserDTO userDTO) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        validateUserSignup(userDTO);
    }

    private boolean isValidEmail(String email) {
        // Add your email validation logic here, e.g., using regex or an email validation library
        // For example, using Apache Commons Validator:
        // return EmailValidator.getInstance().isValid(email);

        // Simple regex example:
        String emailRegex = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)*(\\.[a-zA-Z]{2,})$";
        return email.matches(emailRegex);
    }
}
