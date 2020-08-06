/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.tdp.graficos;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

/**
 * Esta clase contiene los atributos que definen un objeto de tipo Rectangulo2D
 * @author Tatiana Daniela Pastorini
 * @version 1.0
 */
public class Rectangulo2D extends MiFiguraRellena2D{
    
    /**
     * Metodo constructor parametrizado
     * @param ini punto inicial de la figura
     * @param fin punto final de la figura
     * @param liso si va a estar rellena o no
     * @param transparente si tiene transparencia o no
     * @param si_relleno_liso define si la figura va a tener relleno liso o no
     * @param si_degradado define si la figura va a tener relleno degradado o no
     * @param pa propiedad de alisado
     * @param pt propiedad de transparencia
     * @param ptrazo propiedad de trazo
     * @param ct color de trazo
     */
    public Rectangulo2D(Point2D ini, Point2D fin, boolean liso, boolean transparente,
            boolean si_relleno_liso, boolean si_degradado, PropiedadAlisado pa, 
            PropiedadTransparencia pt, PropiedadTrazo ptrazo, ColorFigura ct){
        this.punto_ini = ini;
        this.punto_fin = fin;
        this.s = new Rectangle.Double(ini.getX(), ini.getY(), 0, 0);
        this.tipo_figura = FIGURA_RECTANGULO;
        this.prop_alisado = pa;
        this.prop_transparencia = pt;
        this.prop_trazo = ptrazo;
        this.si_alisado = liso;
        this.si_transparente = transparente;
        this.si_relleno_liso = si_relleno_liso;
        this.si_degradado = si_degradado;
        this.color_figura = ct;
    }
    
    /**
     * Dibujado de forma de tipo Rectangulo2D
     * @param g objeto Graphics
     */
    @Override
    public void pintarShape(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
                
        g2d.setRenderingHints(this.prop_alisado.getRender_alisado());
        g2d.setComposite(this.prop_transparencia.getAlfa_transparencia());
       
        if(this.si_relleno_liso || this.si_degradado){
            
            if(this.si_degradado){
               g2d.setPaint( ((ColorDegradadoFigura)this.color_figura).getDegradado() );
            }
            else
                g2d.setPaint( ((ColorRellenoFigura)this.color_figura).getColor_liso() );
                
            g2d.fill(s);
        }
        
        g2d.setStroke(this.prop_trazo.getTipo_trazo());
        g2d.setPaint( ((ColorFigura)this.color_figura).getColor_trazo());
        g2d.draw(this.s);
    }
    
    /**
     * Establece la localización de la figura Rectangulo2D
     * @param ini punto nuevo inicial
     * @param fin punto nuevo final
     */
    @Override
    public void setLocalizacionShape(Point2D ini, Point2D fin) {
        this.punto_ini = ini;
        this.punto_fin = fin;
        
        if(this.si_degradado)
            ((ColorDegradadoFigura)this.color_figura).setPuntoFin(fin);
                
        ((Rectangle.Double)s).setFrameFromDiagonal(ini, fin);
    }

    /**
     * Sobrecarga del método toString para el listado de figuras
     */
    @Override
    public String toString(){
        return "Rectángulo 2D";
    }
}
