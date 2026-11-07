
package sem4proyecto.Modelo;


public enum EtapaAhorcado {
    VACIO, CABEZA, TRONCO, BRAZO_IZQ, BRAZO_DER, PIERNA_IZQ, PIERNA_DER;

    
    public static EtapaAhorcado dePorErrores(int errores) {
        EtapaAhorcado[] valores = values();
        int i = Math.max(0, Math.min(errores, valores.length - 1));
        return valores[i];
    }
}