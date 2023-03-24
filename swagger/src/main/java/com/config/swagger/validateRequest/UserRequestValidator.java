package com.config.swagger.validateRequest;

import com.config.swagger.dto.UserRequest;
import com.config.swagger.errors.ServiceException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.List;

@Component
@AllArgsConstructor
public class UserRequestValidator extends RequestValidator {
    @Override
    public List<Class<?>> getSupportClasses() {
        return List.of(UserRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserRequest request = (UserRequest) target;
        ValidateUtils.rejectIfEmpty(errors, "name", () -> validateName(request.getName()));
        ValidateUtils.rejectIfEmpty(errors, "email", () -> validateEmail(request.getEmail()));
        ValidateUtils.rejectIfEmpty(errors, "gender", () -> validateGender(request.getGender().name()));
    }

    private String validateName(String name) {
        if (name.isEmpty()) {
            throw ServiceException.badRequest("Name không được rỗng");
        }
        return null;
    }

    private String validateEmail(String email) {
        if (email.isEmpty()) {
            throw ServiceException.badRequest("Email không được rỗng");
        }
        return null;
    }

    private String validateGender(String gender) {
        if (gender.isEmpty()) {
            throw ServiceException.badRequest("Gender không được rỗng");
        }
        return null;
    }

}