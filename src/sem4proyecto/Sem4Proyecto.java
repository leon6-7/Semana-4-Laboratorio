/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sem4proyecto;
import sem4proyecto.gui.VentanaPrincipal;
import sem4proyecto.gui.sonido.GestorSonido;
import sem4proyecto.modelo.AdministradorPalabras;
 
import javax.swing.SwingUtilities;
 
public class Main {
 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdministradorPalabras administrador = new AdministradorPalabras();
            GestorSonido sonido = new GestorSonido();
 
            VentanaPrincipal ventana = new VentanaPrincipal(administrador, sonido);
            ventana.setVisible(true);
        });
    }
}
 

