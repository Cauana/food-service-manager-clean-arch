package com.adjt.food_service_manager_clean_arch.core.exception;

public class ResourceNotFoundException  extends RuntimeException{
    public ResourceNotFoundException(String mensage){
        super(mensage);
    }
}
