/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.tdp.graficos;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.geom.Point2D;

/**
 * Esta clase contiene los atributos que definen un objeto de tipo ColorDegradadoFigura
 * @author Tatiana Daniela Pastorini
 * @version 1.0
 */
public class ColorDegradadoFigura extends ColorFigura{
    
    private Color frente;
    private Color fondo;
    private boolean si_vertical;
    private boolean si_horizontal;
    private GradientPaint degradado;
    private Point2D inicio_degradado;
    private Point2D fin_degradado;
    private float[] dist = {0.5f, 1.0f};

    /**
     * Metodo constructor parametrizado
     * @param ini punto de inicio del degradado.
     * @param fin punto final del degradado.
     * @param ct color de trazo de dibujo
     * @param frente color de frente del degradado
     * @param fondo color de fondo de degradado
     * @param vertical orientación vertical del degradado
     * @param horizontal orientación horizontal del degradado
     */
    public ColorDegradadoFigura(Point2D ini, Point2D fin, Color ct, Color frente, Color fondo, boolean vertical, boolean horizontal){
        super(ct);
        this.inicio_degradado = ini;
        this.frente = frente;
        this.fondo = fondo;
        this.si_vertical = vertical;
        this.si_horizontal = horizontal;
       
        this.setPuntoFin(fin);
    }
    
    /**
     * Metodo para devolver un degradado
     * @return degradado es el degradado
     */
    public GradientPaint getDegradado() {
        return degradado;
    }
    
    /**
     * Establece el punto final del degradado
     * @param fin punto final del degradado
     */
    public void setPuntoFin(Point2D fin){
        this.fin_degradado = fin;
        
        if(this.si_vertical){
            this.degradado = crear_gradiente_vertical(frente, fondo);
        }
        else
            this.degradado= crear_gradiente_horizontal(frente, fondo);
    }
    /*
    * Para crear un gradiente vertical,
    * las coordenadas a usar seran del {0,0} al {0, alto}
    */
    
    /**
     * Metodo para devolver un gradiente vertical
     * @param frente color de frente del degradado
     * @param fondo color de fondo del degradado
     * @return devuelve el gradiente vertical
     */
    public GradientPaint crear_gradiente_vertical(Color frente, Color fondo){
        //System.out.println("puntos ===== " + this.inicio_degradado + ", " + this.fin_degradado + "");
        float x_ini = (float) (this.inicio_degradado.getX() + (this.fin_degradado.getX()-this.inicio_degradado.getX())/2);
        float y_ini = (float) (this.inicio_degradado.getY());
        
        float x_fin = x_ini;
        float y_fin = (float) this.fin_degradado.getY();
        
        return new GradientPaint(x_ini, y_ini, frente, x_fin, y_fin, fondo);
    }
    
    /*
    * Para crear un gradiente horizontal,
    * las coordenadas a usar seran del {0,0} al {ancho, 0}
    */
    
    /**
     * Metodo para devolver un gradiente horizontal
     * @param frente color de frente del degradado
     * @param fondo color de fondo del degradado
     * @return devuelve el gradiente horizontal
     */
    public GradientPaint crear_gradiente_horizontal(Color frente, Color fondo){
        float x_ini = (float) (this.inicio_degradado.getX());
        float y_ini = (float) (this.inicio_degradado.getY() + (this.fin_degradado.getY()-this.inicio_degradado.getY())/2);
        
        float x_fin =(float) this.fin_degradado.getX();
        float y_fin = y_ini;
        
        return new GradientPaint(x_ini, y_ini, frente, x_fin, y_fin, fondo);
    }
}
