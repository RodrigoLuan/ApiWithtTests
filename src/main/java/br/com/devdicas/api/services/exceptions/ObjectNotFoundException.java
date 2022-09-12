package br.com.devdicas.api.services.exceptions;

public class ObjectNotFoundException extends RuntimeException{

        public ObjectNotFoundException(String msg) {
            super(msg);
        }
}
