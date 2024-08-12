package com.jmt.board.exceptions;

import com.jmt.global.exceptions.script.AlertException;
import org.springframework.http.HttpStatus;

public class BoardNotFoundException extends AlertException {
    public BoardNotFoundException() {
        super("NotFound.Baord", HttpStatus.NOT_FOUND);
        setErrorCode(true);
    }
}
