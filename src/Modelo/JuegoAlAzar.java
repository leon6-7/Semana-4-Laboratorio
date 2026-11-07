
package Modelo;
import excepciones.*;

public class JuegoAlAzar extends JuegoBase {
    private static final long serialVersionUID = 1L;

    
    private final AdministradorPalabras administrador;

    public JuegoAlAzar(AdministradorPalabras administrador) throws PalabraInvalidaException {
        this.administrador = administrador;
        reiniciar();   
    }

    @Override
    protected String elegirPalabra() {
        return administrador.obtenerAleatoria();
    }

    @Override
    public Modalidad getModalidad() {
        return Modalidad.AL_AZAR;
    }
}
