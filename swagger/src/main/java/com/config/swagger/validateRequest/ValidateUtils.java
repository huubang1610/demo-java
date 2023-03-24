package com.config.swagger.validateRequest;

import com.config.swagger.errors.ServiceException;
import lombok.experimental.UtilityClass;
import org.springframework.validation.Errors;

import javax.validation.constraints.NotNull;
import java.util.function.Supplier;

@UtilityClass
public class ValidateUtils {
    public static void rejectIfEmpty(@NotNull Errors errors, String key, @NotNull Supplier<String> supplier) {
        if (errors.hasFieldErrors(key)) {
            return;
        }
        String error = getError(supplier);
        if (error == null || error.isEmpty()) {
            return;
        }
        errors.rejectValue(key, error);
    }
    
    private static String getError(final Supplier<String> supplier) {
        try {
            return supplier.get();
        } catch (Exception e) {
            if (e instanceof ServiceException){
                return e.getMessage();
            }
            throw e;
        }
    }
}