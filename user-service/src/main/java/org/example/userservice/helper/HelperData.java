package org.example.userservice.helper;

import lombok.RequiredArgsConstructor;
import org.example.commonsservice.exception.InputFieldException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
@RequiredArgsConstructor
public class HelperData {

    public void processInputErrors(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        }
    }
}