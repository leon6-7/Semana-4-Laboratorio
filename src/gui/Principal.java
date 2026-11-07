
package gui;

import guiSonido.GestorSonido;
import Modelo.AdministradorPalabras;
import Modelo.JuegoAhorcado;
import Modelo.JuegoAlAzar;
import Modelo.JuegoPalabraFija;
import excepciones.PalabraInvalidaException;

import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Principal extends JFrame{
    private CardLayout cardLayout;
    private JPanel panelContenedor;
    
    private AdministradorPalabras adminPalabras;
    private GestorSonido gestorSonido;
    private JuegoAhorcado juegoActual;
    
    private PanelMenu panelMenu;
    private PanelJuego panelJuego;
    private PanelAdmin panelAdmin;
    
    public Principal(){
        setTitle("Juego del Ahorcado");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        adminPalabras = new AdministradorPalabras();
        gestorSonido = new GestorSonido();
        
        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);
        
        panelMenu = new PanelMenu(this);
        panelJuego = new PanelJuego(this, gestorSonido);
        panelAdmin = new PanelAdmin(this, adminPalabras, gestorSonido);

        panelContenedor.add(panelMenu, "MENU");
        panelContenedor.add(panelJuego, "JUEGO");
        panelContenedor.add(panelAdmin, "ADMIN");

        add(panelContenedor);
        mostrarPantalla("MENU");
    }
    
    public void mostrarPantalla(String nombre){
        if (nombre.equals("ADMIN")){
            panelAdmin.refrescarLista();
        }
        cardLayout.show(panelContenedor, nombre);
    }
    
    public void iniciarJuegoPalabraFija(String palabra){
        try{
            juegoActual = new JuegoPalabraFija(palabra);
            panelJuego.cargarJuego(juegoActual);
            mostrarPantalla("JUEGO");
        }catch (PalabraInvalidaException e){
            JOptionPane.showMessageDialog(this, e.getMessage(), "Palabra Invalida", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void iniciarJuegoAlAzar() {
        try {
            juegoActual = new JuegoAlAzar(adminPalabras);
            panelJuego.cargarJuego(juegoActual);
            mostrarPantalla("JUEGO");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al iniciar modalidad al azar: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Principal().setVisible(true);
        });
    }
}
