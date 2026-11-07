package guiSonido;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class GestorSonido {

    private static final float FRECUENCIA_MUESTREO = 44100f;
    private static final double VOLUMEN = 0.5;
    private static final int MUESTRAS_SUAVIZADO = 200;

    private final AudioFormat formato;
    private boolean silenciado;
    private volatile boolean disponible;

    public GestorSonido() {
        formato = new AudioFormat(FRECUENCIA_MUESTREO, 16, 1, true, false);
        silenciado = false;
        disponible = true;
    }

    public void reproducir(EfectoSonido efecto) {
        if (silenciado || !disponible) {
            return;
        }
        byte[] datos = generarSonido(efecto);
        Thread hilo = new Thread(() -> tocar(datos));
        hilo.setDaemon(true);
        hilo.start();
    }

    public void setSilenciado(boolean silenciado) {
        this.silenciado = silenciado;
    }

    public boolean isSilenciado() {
        return silenciado;
    }

    public boolean isDisponible() {
        return disponible;
    }

    private byte[] generarSonido(EfectoSonido efecto) {
        int totalMuestras = 0;
        for (int nota = 0; nota < efecto.getCantidadNotas(); nota++) {
            totalMuestras = totalMuestras + calcularMuestras(efecto.getDuracion(nota));
        }

        byte[] datos = new byte[totalMuestras * 2];
        int posicion = 0;

        for (int nota = 0; nota < efecto.getCantidadNotas(); nota++) {
            int frecuencia = efecto.getFrecuencia(nota);
            int muestras = calcularMuestras(efecto.getDuracion(nota));

            for (int i = 0; i < muestras; i++) {
                double valor = 0;
                if (frecuencia > 0) {
                    double angulo = 2 * Math.PI * frecuencia * i / FRECUENCIA_MUESTREO;
                    valor = Math.sin(angulo) * VOLUMEN;
                }

                if (i < MUESTRAS_SUAVIZADO) {
                    valor = valor * i / MUESTRAS_SUAVIZADO;
                }
                if (muestras - i < MUESTRAS_SUAVIZADO) {
                    valor = valor * (muestras - i) / MUESTRAS_SUAVIZADO;
                }

                short muestra = (short) (valor * Short.MAX_VALUE);
                datos[posicion] = (byte) (muestra & 0xFF);
                datos[posicion + 1] = (byte) ((muestra >> 8) & 0xFF);
                posicion = posicion + 2;
            }
        }
        return datos;
    }

    private int calcularMuestras(int milisegundos) {
        return (int) (FRECUENCIA_MUESTREO * milisegundos / 1000);
    }

    private void tocar(byte[] datos) {
        try {
            SourceDataLine linea = AudioSystem.getSourceDataLine(formato);
            linea.open(formato);
            linea.start();
            linea.write(datos, 0, datos.length);
            linea.drain();
            linea.close();
        } catch (LineUnavailableException | IllegalArgumentException e) {
            disponible = false;
        }
    }
}
