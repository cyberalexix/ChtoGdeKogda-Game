package com.game.dto;

import com.game.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserDTOValidator implements Validator {
    private UserService userService;

    @Autowired
    public UserDTOValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass == UserDTO.class;
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserDTO userDTO = (UserDTO) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "empty.username");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "empty.password");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "matching_password", "empty.matching.password");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name_uk", "empty.name.uk");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name_en", "empty.name.en");

        if(!errors.hasFieldErrors("username")) {
            if(userService.findByUsername(userDTO.getUsername()).isPresent()){
                errors.rejectValue("username", "error.user.exists");
            }
        }

        if(!errors.hasFieldErrors("matching_password")) {
            if(!userDTO.getPassword().equals(userDTO.getMatching_password())){
                errors.rejectValue("matching_password", "error.password.matching");
            }
        }

        if(!errors.hasFieldErrors("name_uk")) {
            for (char a : userDTO.getName_uk().toCharArray()) {
                if (Character.UnicodeBlock.of(a) != Character.UnicodeBlock.CYRILLIC) {
                    errors.rejectValue("name_uk", "error.name.uk.alphabet");
                    break;
                }
            }
        }

        if(!errors.hasFieldErrors("name_en")) {
            for (char a : userDTO.getName_en().toCharArray()) {
                if (Character.UnicodeBlock.of(a) != Character.UnicodeBlock.BASIC_LATIN) {
                    errors.rejectValue("name_en", "error.name.en.alphabet");
                    break;
                }
            }
        }
    }
}
