
package Modelo;


public enum Modalidad {
    PALABRA_FIJA("Palabra fija (2 jugadores)"),
    AL_AZAR("Palabra al azar");
 
    private final String textoBoton;
 
    Modalidad(String textoBoton) {
        this.textoBoton = textoBoton;
    }
 
    public String getTextoBoton() {
        return textoBoton;
    }
}
 
