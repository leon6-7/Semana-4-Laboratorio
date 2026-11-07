/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
 
public enum Modalidad {
    PALABRA_FIJA("Palabra fija (2 jugadores)"),
    AL_AZAR("Palabra al azar");
 
    private final String textoBoton;
 
    Modalidad(String textoBoton) {
        this.textoBoton = textoBoton;
    }
 
    public String getTextoBoton() {
        return textoBoton;
    }
}
 