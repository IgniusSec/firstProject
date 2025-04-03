package br.edu.ifmg.produto.services.exceptions;

//Runtime exception não obriga try-catch
public class ResourceNotFound extends RuntimeException {
    public ResourceNotFound() {
        super();
    }

    public ResourceNotFound(String message) {
        super(message);
    }
}
