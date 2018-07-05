package dawid.spring.exceptions;

/**
 * Created by private on 01.07.17.
 */
public class DomainException extends RuntimeException {

    public DomainException(String message) {
        super(message);
    }
}
