package Modelo;

import java.text.Normalizer;

public class UtilTexto {

    private static final char MARCADOR_ENIE = '\uE000';
    public static final int MIN_LETRAS = 3;
    public static final int MAX_LETRAS = 15;

    private UtilTexto() {
    }

    public static String normalizar(String texto) {
        if (texto == null) {
            return "";
        }
        String s = Normalizer.normalize(texto, Normalizer.Form.NFC);
        s = s.replace('Ñ', MARCADOR_ENIE).replace('ñ', MARCADOR_ENIE);
        s = Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{M}+", "");
        s = s.toUpperCase();
        return s.replace(MARCADOR_ENIE, 'Ñ');
    }

    public static boolean esLetra(char c) {
        return (c >= 'A' && c <= 'Z') || c == 'Ñ';
    }

    public static boolean esPalabraValida(String normalizada) {
        if (normalizada == null
                || normalizada.length() < MIN_LETRAS
                || normalizada.length() > MAX_LETRAS) {
            return false;
        }
        for (int i = 0; i < normalizada.length(); i++) {
            if (!esLetra(normalizada.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
