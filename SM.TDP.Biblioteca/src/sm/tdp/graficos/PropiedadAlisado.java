/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.tdp.graficos;

import java.awt.RenderingHints;

/**
 * Esta clase contiene los atributos que definen un objeto de tipo PropiedadAlisado
 * @author Tatiana Daniela Pastorini
 * @version 1.0
 */
public class PropiedadAlisado extends Propiedad{
    
    private RenderingHints render_alisado;
    
    /**
     * Metodo constructor parametrizado
     * @param alisar construye un objeto alisado según el valor de alisar
     */
    public PropiedadAlisado(boolean alisar){
        this.tipo_propiedad = PROPIEDAD_ALISADO;
        
        if(alisar == true){
            // Alisado de formas y texto 
            this.render_alisado = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                                                     RenderingHints.VALUE_ANTIALIAS_ON);
            // Alisado de color
            this.render_alisado.put(RenderingHints.KEY_COLOR_RENDERING,
                    RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        }
        else{
            this.render_alisado = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                                                     RenderingHints.VALUE_ANTIALIAS_OFF);
        }
    }

    /**
     * Metodo para devolver un tipo de alisado
     * @return render_alisado es el tipo de alisado
     */
    public RenderingHints getRender_alisado() {
        return render_alisado;
    }

    /**
     * Establece el tipo de alisado de la figura
     * @param render_alisado tipo de alisado
     */
    public void setRender_alisado(RenderingHints render_alisado) {
        this.render_alisado = render_alisado;
    }
}
