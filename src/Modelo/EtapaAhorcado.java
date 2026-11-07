
package Modelo;


public enum EtapaAhorcado {
    VACIO,
    CABEZA,
    TRONCO,
    BRAZO_IZQ,
    BRAZO_DER,
    PIERNA_IZQ,
    PIERNA_DER;
 
    public static EtapaAhorcado dePorErrores(int errores) {
        EtapaAhorcado[] etapas = values();
        int indice = Math.max(0, Math.min(errores, etapas.length - 1));
        return etapas[indice];
    }
}