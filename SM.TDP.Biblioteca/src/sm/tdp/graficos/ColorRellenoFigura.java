/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.tdp.graficos;

import java.awt.Color;

/**
 * Esta clase contiene los atributos que definen un objeto de tipo ColorRellenoFigura
 * @author Tatiana Daniela Pastorini
 * @version 1.0
 */
public class ColorRellenoFigura extends ColorFigura{
    
    private Color color_liso;

    /**
     * Metodo constructor parametrizado
     * @param color_liso color de relleno liso
     * @param color_trazo color de trazo
     */
    public ColorRellenoFigura(Color color_liso, Color color_trazo) {
        super(color_trazo);
        this.color_liso = color_liso;
    }

    /**
     * Metodo para devolver el color de relleno liso
     * @return color de relleno liso
     */
    public Color getColor_liso() {
        return this.color_liso;
    }

    /**
     * Establece el color de relleno liso de una figura
     * @param color_liso color de relleno liso de la figura
     */
    public void setColor_liso(Color color_liso) {
        this.color_liso = color_liso;
    }

  
}
