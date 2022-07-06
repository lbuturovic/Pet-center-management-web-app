package ba.unsa.etf.pnwt.petcenter;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String error) {
        super(error);
    }
}
