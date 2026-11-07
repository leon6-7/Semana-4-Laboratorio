
package sem4proyecto.Modelo;


public enum Modalidad {
    PALABRA_FIJA("Palabra fija (2 jugadores)"),
    AL_AZAR("Palabra al azar");

    private final String texto;

    Modalidad(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }
}
