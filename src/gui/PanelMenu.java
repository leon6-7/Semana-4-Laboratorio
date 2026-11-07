
package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class PanelMenu extends JPanel{
    private Principal ventana;
    
    public PanelMenu(Principal ventana){
        this.ventana=ventana;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        
        JLabel lblTitulo = new JLabel("JUEGO DEL AHORCADO", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        gbc.gridy = 0;
        add(lblTitulo, gbc);

        JButton btnAlAzar = new JButton("Modalidad Al Azar");
        gbc.gridy = 1;
        add(btnAlAzar, gbc);

        JButton btnPalabraFija = new JButton("Modalidad Palabra Fija");
        gbc.gridy = 2;
        add(btnPalabraFija, gbc);

        JButton btnAdmin = new JButton("Administrar Palabras");
        gbc.gridy = 3;
        add(btnAdmin, gbc);

        JButton btnSalir = new JButton("Salir");
        gbc.gridy = 4;
        add(btnSalir, gbc);
        
        btnAlAzar.addActionListener(e -> ventana.iniciarJuegoAlAzar());

        btnPalabraFija.addActionListener(e -> {
            JPasswordField pf = new JPasswordField();
            int option = JOptionPane.showConfirmDialog(
                    this, 
                    pf, 
                    "Ingrese la palabra secreta (oculta):", 
                    JOptionPane.OK_CANCEL_OPTION, 
                    JOptionPane.PLAIN_MESSAGE
            );

            if (option == JOptionPane.OK_OPTION) {
                String palabra = new String(pf.getPassword());
                ventana.iniciarJuegoPalabraFija(palabra);
            }
        });

        btnAdmin.addActionListener(e -> ventana.mostrarPantalla("ADMIN"));
        btnSalir.addActionListener(e -> System.exit(0));
    }
}
