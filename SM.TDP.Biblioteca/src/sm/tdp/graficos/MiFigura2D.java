/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.tdp.graficos;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Esta clase contiene los atributos que definen un objeto de tipo MiFigura2D
 * @author Tatiana Daniela Pastorini
 * @version 1.0
 */
public abstract class MiFigura2D {
    
    protected Point2D punto_ini;
    protected Point2D punto_fin;
    protected Shape s;
    
    protected PropiedadAlisado prop_alisado;
    protected PropiedadTrazo prop_trazo;
    protected PropiedadTransparencia prop_transparencia;
    protected ColorFigura color_figura;
    
    protected boolean si_alisado;
    protected boolean si_transparente;
    
    protected int tipo_figura;
    
    protected static int FIGURA_LINEA = 0;
    protected static int FIGURA_RECTANGULO = 1;
    protected static int FIGURA_ELIPSE = 2;
    protected static int FIGURA_ROUND_RECT = 3;
    
    /**
     * Metodo constructor por defecto
     */
    public MiFigura2D(){};
    
    /**
     * Metodo para devolver un entero que representa el tipo de figura
     * @return tipo_figura es el tipo de figura
     */
    public int getTipoFigura(){
        return this.tipo_figura;
    }

    /**
     * Metodo para devolver un ColorFigura
     * @return color_figura es el objeto ColorFigura
     */
    public ColorFigura getColor_figura() {
        return color_figura;
    }

    /**
     * Establece el ColorFigura para el trazo
     * @param color_figura color de trazo
     */
    public void setColor_figura(ColorFigura color_figura) {
        this.color_figura = color_figura;
    }
    
    /**
     * Metodo para devolver un Shape
     * @return s es el objeto Shape
     */
    public Shape getFigura(){
        return s;
    }

    /**
     * Metodo para devolver un si la figura tiene alisado o no
     * @return si_alisado 
     */
    public boolean is_alisado() {
        return si_alisado;
    }

    /**
     * Establece si esta con alisado o no
     * @param si_alisado 
     */
    public void set_si_alisado(boolean si_alisado) {
        this.si_alisado = si_alisado;
    }

    /**
     * Metodo para devolver un si la figura tiene transparencia o no
     * @return si_transparente
     */
    public boolean is_transparente() {
        return si_transparente;
    }

    /**
     * Establece si tiene transparencia
     * @param si_transparente
     */
    public void set_si_transparente(boolean si_transparente) {
        this.si_transparente = si_transparente;
    }
    
    /**
     * Metodo para devolver la PropiedadAlisado
     * @return prop_alisado objeto de tipo PropiedadAlisado 
     */
    public PropiedadAlisado getProp_alisado() {
        return prop_alisado;
    }

    /**
     * Establece la propiedad PropiedadAlisado
     * @param prop_alisado
     */
    public void setProp_alisado(PropiedadAlisado prop_alisado) {
        this.prop_alisado = prop_alisado;
    }

    /**
     * Metodo para devolver la PropiedadTrazo
     * @return prop_trazo objeto de tipo PropiedadTrazo
     */
    public PropiedadTrazo getProp_trazo() {
        return prop_trazo;
    }

    /**
     * Establece la propiedad PropiedadTrazo
     * @param prop_trazo
     */
    public void setProp_trazo(PropiedadTrazo prop_trazo) {
        this.prop_trazo = prop_trazo;
    }

    /**
     * Metodo para devolver la PropiePropiedadTransparenciadadAlisado
     * @return prop_alisado objeto de tipo PropiedadAlisado 
     */
    public PropiedadTransparencia getProp_transparencia() {
        return prop_transparencia;
    }

    /**
     * Establece la propiedad PropiedadTransparencia
     * @param prop_transparencia 
     */
    public void setProp_transparencia(PropiedadTransparencia prop_transparencia) {
        this.prop_transparencia = prop_transparencia;
    }

    /**
     * Metodo para devolver el punto inicial de la figura
     * @return punto_ini de la figura
     */
    public Point2D getPunto_ini() {
        return punto_ini;
    }

    /**
     * Establece el punto inicial de la figura
     * @param punto_ini
     */
    public void setPunto_ini(Point2D punto_ini) {
        this.punto_ini = punto_ini;
    }

    /**
     * Metodo para devolver el punto final de la figura
     * @return punto_fin de la figura
     */
    public Point2D getPunto_fin() {
        return punto_fin;
    }

    /**
     * Establece el punto final de la figura
     * @param punto_fin
     */
    public void setPunto_fin(Point2D punto_fin) {
        this.punto_fin = punto_fin;
    }

    /**
     * Metodo para devolver una figura de tipo Shape
     * @return s un objeto de tipo Shape
     */
    public Shape getS() {
        return s;
    }

    /**
     * Establece el objeto de tipo shapr
     * @param s
     */
    public void setS(Shape s) {
        this.s = s;
    }

    /**
     * Metodo para devolver el tipo de la figura
     * @return tipo_figura tipo de la figura
     */
    public int getTipo_figura() {
        return tipo_figura;
    }

    /**
     * Establece el tipo de la figura 
     * @param tipo_figura
     */
    public void setTipo_figura(int tipo_figura) {
        this.tipo_figura = tipo_figura;
    }
    
    /**
     * Dibujado de forma
     * @param g objeto Graphics
     */
    public abstract void pintarShape(Graphics g);
    
    /**
     * Establece la localización de la figura 
     * @param ini punto nuevo inicial
     * @param fin punto nuevo final
     */
    public abstract void setLocalizacionShape(Point2D ini, Point2D fin);
            
    public String toString(){
        return null;
    }
    
    /**
     * Dibujado del recuadro de edición de una figura 
     * @param g2d objeto Graphics2D
     */
    public void pintar_recuadro_edicion(Graphics2D g2d){
        Rectangle2D recuadro;
        recuadro = new Rectangle2D.Double(this.punto_ini.getX()-5, this.punto_ini.getY()-5,
                this.punto_fin.getX(), this.punto_fin.getY());
        g2d.setStroke(new BasicStroke(2));
        g2d.setPaint(Color.RED);
        
        g2d.draw(recuadro);
    }
}