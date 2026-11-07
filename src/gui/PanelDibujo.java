
package gui;

import Modelo.EtapaAhorcado;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;

public class PanelDibujo extends JPanel{
    private EtapaAhorcado etapaActual;
    private EtapaAhorcado etapaObjetivo;
    private float progresoAnimacion;
    private Timer timer;
    private Runnable alTerminarAnimacion;
    
    public PanelDibujo(){
        this.etapaActual = EtapaAhorcado.VACIO;
        this.etapaObjetivo = EtapaAhorcado.VACIO;
        this.progresoAnimacion = 1.0f;
        setBackground(Color.WHITE);
        
        timer = new Timer(30, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                progresoAnimacion += 0.08f;
                if (progresoAnimacion >= 1.0f){
                    progresoAnimacion = 1.0f;
                    etapaActual = etapaObjetivo;
                    timer.stop();
                    if (alTerminarAnimacion != null){
                        alTerminarAnimacion.run();
                    }
                }
                repaint();
            }
        });
    }
    
    public void actualizarEtapa(EtapaAhorcado nuevaEtapa, Runnable alFinalizar){
        this.etapaObjetivo = nuevaEtapa;
        this.alTerminarAnimacion = alFinalizar;

         if (nuevaEtapa == EtapaAhorcado.VACIO) {
            this.etapaActual = EtapaAhorcado.VACIO;
            this.progresoAnimacion = 1.0f;
            repaint();
            if (alFinalizar != null) {
                alFinalizar.run();
            }
        }else{
            this.progresoAnimacion = 0.0f;
            timer.start();        
        }
    } 
    
    public boolean estaAnimando(){
        return timer.isRunning();
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(4));
        g2.setColor(Color.BLACK);

        g2.drawLine(20, 230, 120, 230); // base
        g2.drawLine(50, 230, 50, 20);   // poste vertical
        g2.drawLine(50, 20, 150, 20);   // viga superior
        g2.drawLine(150, 20, 150, 50);  // cuerda

        EtapaAhorcado aDibujar = timer.isRunning() ? etapaObjetivo : etapaActual;
        int maxOrden = aDibujar.ordinal();

        for (int i = 1; i <= maxOrden; i++) {
            float p = (i == maxOrden && timer.isRunning()) ? progresoAnimacion : 1.0f;
            dibujarParte(g2, i, p);
        }
    }
    
    private void dibujarParte(Graphics2D g2, int parte, float p){
        switch (parte) {
            case 1: // cabeza
                int diametro = (int) (30 * p);
                g2.drawOval(135, 50, diametro, diametro);
                break;
            case 2: // torso
                int altoTronco = (int) (50 * p);
                g2.drawLine(150, 80, 150, 80 + altoTronco);
                break;
            case 3: // brazo izquierdo
                int xIzq = 150 - (int) (25 * p);
                int yIzq = 100 + (int) (20 * p);
                g2.drawLine(150, 100, xIzq, yIzq);
                break;
            case 4: // brazo derecho
                int xDer = 150 + (int) (25 * p);
                int yDer = 100 + (int) (20 * p);
                g2.drawLine(150, 100, xDer, yDer);
                break;
            case 5: // pierna izquierda
                int pxIzq = 150 - (int) (25 * p);
                int pyIzq = 130 + (int) (30 * p);
                g2.drawLine(150, 130, pxIzq, pyIzq);
                break;
            case 6: // pierna derecha
                int pxDer = 150 + (int) (25 * p);
                int pyDer = 130 + (int) (30 * p);
                g2.drawLine(150, 130, pxDer, pyDer);
                break;
        }
    }
}
