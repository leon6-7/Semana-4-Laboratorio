package sem4proyecto.Modelo;

import excepciones.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AdministradorPalabras {

    private static final long serialVersionUID = 1L;

    private final List<String> palabras = new ArrayList<>();
    private transient Random random = new Random();

    public AdministradorPalabras() {
        String[] iniciales = {
            "ARBOL", "CASA", "COMPUTADORA", "PROGRAMA", "JAVA",
            "TECLADO", "MONTAÑA", "PINGÜINO", "ELEFANTE", "VENTANA",
            "GUITARRA", "CAMPEON"
        };
        for (String p : iniciales) {
            palabras.add(UtilTexto.normalizar(p));
        }
    }
    public void agregar(String Palabra) throws PalabraInvalidaException,PalabraDuplicadaException{
        String normalizada = UtilTexto.normalizar(Palabra == null ? "" : Palabra.trim());
        if(!UtilTexto.esPalabraValida(normalizada)){
            throw new PalabraInvalidaException("La palabra debe tener entre 3 y 15 letras, sin espacios ni números.");
        }
        if (existe(normalizada, 0)) {
            throw new PalabraDuplicadaException(
                "La palabra «" + normalizada + "» ya existe en la lista.");
        }
        palabras.add(normalizada);
    }
    private boolean existe(String palabra, int indice) {
        if (indice >= palabras.size()) {
            return false;
        }
        if (palabras.get(indice).equals(palabra)) {
            return true;
        }
        return existe(palabra, indice + 1);
    }
    public int cantidad() {
        return palabras.size();
    }
    public List<String> getPalabras() {
        return Collections.unmodifiableList(new ArrayList<>(palabras));
    }
}
