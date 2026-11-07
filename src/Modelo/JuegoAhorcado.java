
package Modelo;

import excepciones.*;
import java.util.List;
public interface JuegoAhorcado {
    void establecerPalabraSecreta(String palabra) throws PalabraInvalidaException;

    ResultadoLetra ingresarLetra(String entrada)
            throws LetraInvalidaException, LetraRepetidaException, PartidaTerminadaException;

    void reiniciar() throws PalabraInvalidaException;

    String getPalabraMostrada();
    int getIntentosRestantes();
    List<Character> getLetrasAcertadas();
    List<Character> getLetrasErradas();
    EtapaAhorcado getEtapa();
    EstadoPartida getEstado();
    Modalidad getModalidad();
    String getPalabraSecreta();
}

