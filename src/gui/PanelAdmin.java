
package gui;

import guiSonido.EfectoSonido;
import guiSonido.GestorSonido;
import Modelo.AdministradorPalabras;
import excepciones.AhorcadoException;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class PanelAdmin extends JPanel{
    private Principal ventana;
    private AdministradorPalabras admin;
    private GestorSonido gestorSonido;

    private JList<String> listaGUI;
    private DefaultListModel<String> modelLista;
    private JTextField txtNuevaPalabra;
    private JLabel lblEstado;
    
    public PanelAdmin(Principal ventana, AdministradorPalabras admin, GestorSonido gestorSonido){
        this.ventana = ventana;
        this.admin = admin;
        this.gestorSonido = gestorSonido;

        setLayout(new BorderLayout(10, 10));

        JLabel lblTitulo = new JLabel("ADMINISTRACION DE PALABRAS", JLabel.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(lblTitulo, BorderLayout.NORTH);

        modelLista = new DefaultListModel<>();
        listaGUI = new JList<>(modelLista);
        add(new JScrollPane(listaGUI), BorderLayout.CENTER);

        JPanel panelBottom = new JPanel(new BorderLayout());
        
        JPanel panelInput = new JPanel(new FlowLayout());
        txtNuevaPalabra = new JTextField(12);
        JButton btnAgregar = new JButton("Agregar");
        JButton btnVolver = new JButton("Volver al Menu");

        panelInput.add(new JLabel("Palabra:"));
        panelInput.add(txtNuevaPalabra);
        panelInput.add(btnAgregar);
        panelInput.add(btnVolver);

        lblEstado = new JLabel(" ", JLabel.CENTER);
        lblEstado.setForeground(Color.RED);

        panelBottom.add(panelInput, BorderLayout.NORTH);
        panelBottom.add(lblEstado, BorderLayout.SOUTH);
        add(panelBottom, BorderLayout.SOUTH);

        btnAgregar.addActionListener(e -> agregarPalabra());
        btnVolver.addActionListener(e -> ventana.mostrarPantalla("MENU"));

        refrescarLista();
    }
    
    public void refrescarLista(){
        modelLista.clear();
        for (String p : admin.getPalabras()){
            modelLista.addElement(p);
        }
    }

    private void agregarPalabra(){
        String input = txtNuevaPalabra.getText().trim();
        try {
            admin.agregar(input);
            gestorSonido.reproducir(EfectoSonido.ACIERTO);
            lblEstado.setForeground(new Color(0, 128, 0));
            lblEstado.setText("Palabra '" + input.toUpperCase() + "' agregada con exito.");
            txtNuevaPalabra.setText("");
            refrescarLista();
        }catch(AhorcadoException e){
            gestorSonido.reproducir(EfectoSonido.ALERTA);
            lblEstado.setForeground(Color.RED);
            lblEstado.setText(e.getMessage());
        }
    }
}
