package Modelo;

import excepciones.*;

public class JuegoPalabraFija extends JuegoBase {

    private static final long serialVersionUID = 1L;

    private String palabraConfigurada;

    public JuegoPalabraFija(String palabra) throws PalabraInvalidaException {
        establecerPalabraSecreta(palabra);   // lanza si es inválida
        this.palabraConfigurada = palabra;
    }

    public void cambiarPalabra(String palabra) throws PalabraInvalidaException {
        if (!UtilTexto.esPalabraValida(UtilTexto.normalizar(palabra == null ? "" : palabra.trim()))) {
            throw new PalabraInvalidaException(
                    "La palabra debe tener entre 3 y 15 letras, sin espacios ni números.");
        }
        this.palabraConfigurada = palabra;
    }

    @Override
    protected String elegirPalabra() {
        return palabraConfigurada;
    }

    @Override
    public Modalidad getModalidad() {
        return Modalidad.PALABRA_FIJA;
    }
}

