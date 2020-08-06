/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.tdp.graficos;

import java.awt.AlphaComposite;
import java.awt.Composite;

/**
 * Esta clase contiene los atributos que definen un objeto de tipo PropiedadTransparencia
 * @author Tatiana Daniela Pastorini
 * @version 1.0
 */
public class PropiedadTransparencia extends Propiedad{
    
    private float grado;
    private Composite alfa_transparencia;
    
    /**
     * Metodo constructor parametrizado
     * @param grado_transp valor del grado de transparencia
     */
    public PropiedadTransparencia(float grado_transp){
        this.tipo_propiedad = PROPIEDAD_TRANSPARENCIA;
        this.grado = grado_transp;
                
        this.alfa_transparencia = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, grado);
    }

    /**
     * Metodo para devolver el valor del grado de transparencia
     * @return grado de transparencia
     */
    public float getGrado() {
        return grado;
    }

    /**
     * Establece el grado de transparencia
     * @param grado grado de transparencia
     */
    public void setGrado(float grado) {
        this.grado = grado;
    }

    /**
     * Metodo para devolver un objeto de tipo Composite
     * @return alfa_composite de tipo Composite
     */
    public Composite getAlfa_transparencia() {
        return alfa_transparencia;
    }

    /**
     * Establece el tipo de Composite
     * @param alfa_transparencia es el objeto Composite
     */
    public void setAlfa_transparencia(Composite alfa_transparencia) {
        this.alfa_transparencia = alfa_transparencia;
    }
}
