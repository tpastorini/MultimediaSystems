/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.tdp.graficos;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 * Esta clase contiene los atributos que definen un objeto de tipo Linea2D
 * @author Tatiana Daniela Pastorini
 * @version 1.0
 */
public class Linea2D extends MiFigura2D{

    /**
     * Metodo constructor parametrizado
     * @param ini punto inicial de la figura
     * @param fin punto final de la figura
     * @param liso si va a estar rellena o no
     * @param transparente si tiene transparencia o no
     * @param pa propiedad de alisado
     * @param pt propiedad de transparencia
     * @param ptrazo propiedad de trazo
     * @param color_trazo color de trazo
     */
    public Linea2D(Point2D ini, Point2D fin, boolean liso, boolean transparente, 
            PropiedadAlisado pa, PropiedadTransparencia pt, PropiedadTrazo ptrazo, 
            ColorFigura color_trazo){
        this.punto_ini = ini;
        this.punto_fin = fin;
        this.s = new Line2D.Double(ini, fin);
        this.tipo_figura = FIGURA_LINEA;
        this.prop_alisado = pa;
        this.prop_transparencia = pt;
        this.prop_trazo = ptrazo;
        this.si_alisado = liso;
        this.si_transparente = transparente;
        this.color_figura = color_trazo;
    }

    /**
     * Dibujado de forma de tipo Linea2D
     * @param g objeto Graphics
     */
    @Override
    public void pintarShape(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        
        g2d.setRenderingHints(this.prop_alisado.getRender_alisado());
        g2d.setComposite(this.prop_transparencia.getAlfa_transparencia());
        g2d.setStroke(this.prop_trazo.getTipo_trazo());
        g2d.setPaint(this.color_figura.getColor_trazo());
        g2d.draw(this.s);
    }

    /**
     * Establece la localización de la figura Linea2D
     * @param ini punto nuevo inicial
     * @param fin punto nuevo final
     */
    @Override
    public void setLocalizacionShape(Point2D ini, Point2D fin) {
        this.punto_ini = ini;
        this.punto_fin = fin;
        
        ((Line2D)this.s).setLine(this.punto_ini, this.punto_fin);
    }
    
    /**
     * Sobrecarga del método toString para el listado de figuras
     */
    @Override
    public String toString(){
        return "Linea 2D";
    }
}
