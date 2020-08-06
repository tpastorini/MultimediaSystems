/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.tdp.graficos;

import java.awt.Color;

/**
/**
 * Esta clase contiene los atributos que definen un objeto de tipo ColorFigura
 * @author Tatiana Daniela Pastorini
 * @version 1.0
 */
public class ColorFigura{
    
    protected Color color_trazo;
    
    /**
     * Metodo constructor parametrizado
     * @param ct color de trazo
     */
    public ColorFigura(Color ct){
        this.color_trazo = ct;
    }

    /**
     * Metodo para devolver un color de trazo
     * @return color de trazo
     */
    public Color getColor_trazo() {
        return color_trazo;
    }

    /**
     * Establece el color de trazo de una figura
     * @param color_trazo color de trazo de la figura
     */
    public void setColor_trazo(Color color_trazo) {
        this.color_trazo = color_trazo;
    }
}
