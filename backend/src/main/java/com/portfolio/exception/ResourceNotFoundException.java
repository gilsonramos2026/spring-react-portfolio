package com.portfolio.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String r, Long id){ super(r+" não encontrado: id="+id); }
    public ResourceNotFoundException(String msg){ super(msg); }
}

