package com.portfolio.exception;

public class FileStorageException extends RuntimeException {
    public FileStorageException(String m){ super(m); }
    public FileStorageException(String m, Throwable c){ super(m,c); }
}

