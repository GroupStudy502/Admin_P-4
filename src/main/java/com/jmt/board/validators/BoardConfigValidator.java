package com.jmt.board.validators;


import com.jmt.board.controllers.RequestBoardConfig;
import com.jmt.board.repository.BoardConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class BoardConfigValidator implements Validator {

    private final BoardConfigRepository repository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestBoardConfig.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RequestBoardConfig form = (RequestBoardConfig) target;
        String bid = form.getBid();
        String mod = form.getMode();

        if(mod.equals("add") && StringUtils.hasText(bid) &&  repository.findById(bid).isPresent()) {
            errors.rejectValue("bid", "Duplicate");
        }

    }
}
