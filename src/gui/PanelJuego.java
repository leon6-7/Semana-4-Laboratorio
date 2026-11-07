
package gui;

import guiSonido.EfectoSonido;
import guiSonido.GestorSonido;
import Modelo.EstadoPartida;
import Modelo.JuegoAhorcado;
import Modelo.ResultadoLetra;
import excepciones.AhorcadoException;
import excepciones.LetraRepetidaException;
import excepciones.PalabraInvalidaException;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class PanelJuego extends JPanel{
    private Principal ventana;
    private JuegoAhorcado juego;
    private GestorSonido gestorSonido;

    private PanelDibujo panelDibujo;
    private JLabel lblPalabra;
    private JLabel lblIntentos;
    private JLabel lblMensajes;
    private JLabel lblAcertadas;
    private JLabel lblErradas;
    private JTextField txtEntrada;
    private JButton btnProbar;
    private JButton btnReintentar;
    private JButton btnAudio;

    private JPanel panelTeclado;
    private Map<Character, JButton> botonesTeclado;
    
    public PanelJuego(Principal ventana, GestorSonido gestorSonido) {
        this.ventana = ventana;
        this.gestorSonido = gestorSonido;
        this.botonesTeclado = new HashMap<>();

        setLayout(new BorderLayout(10, 10));

        JPanel panelTop = new JPanel(new GridLayout(3, 1));
        lblIntentos = new JLabel("Intentos restantes: 6/6", SwingConstants.CENTER);
        lblIntentos.setFont(new Font("SansSerif", Font.BOLD, 14));

        lblPalabra = new JLabel("_ _ _ _", SwingConstants.CENTER);
        lblPalabra.setFont(new Font("Monospaced", Font.BOLD, 28));

        lblMensajes = new JLabel("Ingrese una letra para comenzar", SwingConstants.CENTER);
        lblMensajes.setFont(new Font("SansSerif", Font.ITALIC, 12));
        lblMensajes.setForeground(Color.BLUE);

        panelTop.add(lblIntentos);
        panelTop.add(lblPalabra);
        panelTop.add(lblMensajes);
        add(panelTop, BorderLayout.NORTH);
                
        panelDibujo = new PanelDibujo();
        add(panelDibujo, BorderLayout.CENTER);

        JPanel panelDerecho = new JPanel(new BorderLayout());
        
        JPanel panelListas = new JPanel(new GridLayout(2, 1));
        lblAcertadas = new JLabel("Acertadas: []");
        lblAcertadas.setForeground(new Color(0, 128, 0));
        lblErradas = new JLabel("Erradas: []");
        lblErradas.setForeground(Color.RED);
        panelListas.add(lblAcertadas);
        panelListas.add(lblErradas);
        panelDerecho.add(panelListas, BorderLayout.NORTH);

        construirTeclado();
        panelDerecho.add(panelTeclado, BorderLayout.CENTER);
        add(panelDerecho, BorderLayout.EAST);

        JPanel panelBottom = new JPanel();
        txtEntrada = new JTextField(3);
        btnProbar = new JButton("Probar");
        btnReintentar = new JButton("Jugar de nuevo");
        btnAudio = new JButton("Audio: ON");
        JButton btnVolver = new JButton("Volver al Menu");

        panelBottom.add(new JLabel("Letra:"));
        panelBottom.add(txtEntrada);
        panelBottom.add(btnProbar);
        panelBottom.add(btnReintentar);
        panelBottom.add(btnAudio);
        panelBottom.add(btnVolver);
        add(panelBottom, BorderLayout.SOUTH);
        
        ActionListener accionProbar = e -> procesarEntrada(txtEntrada.getText().trim());
        btnProbar.addActionListener(accionProbar);
        txtEntrada.addActionListener(accionProbar);

        btnReintentar.addActionListener(e -> reiniciarJuego());
        btnVolver.addActionListener(e -> ventana.mostrarPantalla("MENU"));

        btnAudio.addActionListener(e -> {
            boolean actual = gestorSonido.isSilenciado();
            gestorSonido.setSilenciado(!actual);
            btnAudio.setText("Audio: " + (!actual ? "OFF" : "ON"));
        });
    }
    private void construirTeclado(){
        panelTeclado = new JPanel(new GridLayout(5, 6, 2, 2));
        char[] letras = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ".toCharArray();

        for (char c : letras) {
            JButton btn = new JButton(String.valueOf(c));
            btn.setFont(new Font("SansSerif", Font.PLAIN, 10));
            btn.setOpaque(true);
            btn.addActionListener(e -> procesarEntrada(String.valueOf(c)));
            botonesTeclado.put(c, btn);
            panelTeclado.add(btn);
        }
    }
    public void cargarJuego(JuegoAhorcado nuevoJuego){
        this.juego = nuevoJuego;
        reiniciarControlesGUI();
        actualizarGUI();
    }
    
    private void procesarEntrada(String entrada){
        if (juego == null || panelDibujo.estaAnimando()) {
            return;
        }

        try {
            txtEntrada.setText("");
            lblMensajes.setForeground(Color.BLUE);

            ResultadoLetra res = juego.ingresarLetra(entrada);

            setControlesHabilitados(false);

            if (res == ResultadoLetra.ACIERTO) {
                gestorSonido.reproducir(EfectoSonido.ACIERTO);
                lblMensajes.setText("¡Bien! La letra esta en la palabra.");
                actualizarGUI();
                verificarEstadoFinal();
            } else if (res == ResultadoLetra.ERROR) {
                lblMensajes.setText("La letra no pertenece a la palabra.");
                panelDibujo.actualizarEtapa(juego.getEtapa(), () -> {
                    gestorSonido.reproducir(EfectoSonido.ERROR);
                    actualizarGUI();
                    verificarEstadoFinal();
                });
            }

        } catch (LetraRepetidaException ex) {
            gestorSonido.reproducir(EfectoSonido.ALERTA);
            lblMensajes.setForeground(Color.ORANGE.darker());
            lblMensajes.setText("La letra '" + ex.getLetra() + "' ya fue ingresada.");
            setControlesHabilitados(true);
        } catch (AhorcadoException ex) {
            gestorSonido.reproducir(EfectoSonido.ALERTA);
            lblMensajes.setForeground(Color.RED);
            lblMensajes.setText(ex.getMessage());
            setControlesHabilitados(true);
        }
    }
    
    private void verificarEstadoFinal() {
        if (juego.getEstado() == EstadoPartida.GANADA) {
            gestorSonido.reproducir(EfectoSonido.VICTORIA);
            lblMensajes.setForeground(new Color(0, 128, 0));
            lblMensajes.setText("¡Ganaste! Completaste la palabra correctamente.");
            setControlesHabilitados(false);
        } else if (juego.getEstado() == EstadoPartida.PERDIDA) {
            gestorSonido.reproducir(EfectoSonido.DERROTA);
            lblMensajes.setForeground(Color.RED);
            lblMensajes.setText("Perdiste. Se agotaron los intentos. Palabra: " + juego.getPalabraSecreta());
            setControlesHabilitados(false);
        } else {
            setControlesHabilitados(true);
        }
    }
    
    private void reiniciarJuego() {
        try {
            juego.reiniciar();
            reiniciarControlesGUI();
            actualizarGUI();
        } catch (PalabraInvalidaException e) {
            lblMensajes.setText("Error al reiniciar la partida: " + e.getMessage());
        }
    }
    
    private void reiniciarControlesGUI() {
        panelDibujo.actualizarEtapa(juego.getEtapa(), null);
        lblMensajes.setForeground(Color.BLUE);
        lblMensajes.setText("Nueva partida iniciada.");

        for (Map.Entry<Character, JButton> entry : botonesTeclado.entrySet()) {
            entry.getValue().setEnabled(true);
            entry.getValue().setBackground(null);
        }
        setControlesHabilitados(true);
    }
    private void actualizarGUI() {
        StringBuilder pb = new StringBuilder();
        for (char c : juego.getPalabraMostrada().toCharArray()) {
            pb.append(c).append(" ");
        }
        lblPalabra.setText(pb.toString());

        lblIntentos.setText("Intentos restantes: " + juego.getIntentosRestantes() + "/6");
        lblAcertadas.setText("Acertadas: " + juego.getLetrasAcertadas().toString());
        lblErradas.setText("Erradas: " + juego.getLetrasErradas().toString());

        for (char c : juego.getLetrasAcertadas()) {
            if (botonesTeclado.containsKey(c)) {
                botonesTeclado.get(c).setEnabled(false);
                botonesTeclado.get(c).setBackground(Color.GREEN);
            }
        }
        for (char c : juego.getLetrasErradas()) {
            if (botonesTeclado.containsKey(c)) {
                botonesTeclado.get(c).setEnabled(false);
                botonesTeclado.get(c).setBackground(Color.LIGHT_GRAY);
            }
        }
    }

    private void setControlesHabilitados(boolean habilitar) {
        txtEntrada.setEnabled(habilitar);
        btnProbar.setEnabled(habilitar);
        if (habilitar) {
            txtEntrada.requestFocusInWindow();
        }
    }
}
