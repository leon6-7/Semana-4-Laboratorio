
package sem4proyecto;

import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Principal extends JFrame{
    private CardLayout cardLayout;
    private JPanel panelContenedor;
    
    private PanelMenu panelMenu;
    private PanelJuego panelJuego;
    private PanelAdmin panelAdmin;
    
    public Principal(){
        setTitle("Juego del Ahorcado");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);
        
        

        add(panelContenedor);
    }
}
