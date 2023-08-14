package com.example.loginSystem.rest.service.exceptions;

import org.springframework.http.HttpStatus;

public class ObjectNotFoundException extends BaseBusinessException{

    public ObjectNotFoundException(String msg){
        super(msg);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.NOT_FOUND;
    }

}
