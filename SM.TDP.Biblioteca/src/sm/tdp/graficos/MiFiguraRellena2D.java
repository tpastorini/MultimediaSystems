/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.tdp.graficos;

import java.awt.Graphics;
import java.awt.geom.Point2D;

/**
 * Esta clase contiene los atributos que definen un objeto de tipo MiFiguraRellena2D
 * @author Tatiana Daniela Pastorini
 * @version 1.0
 */
public class MiFiguraRellena2D extends MiFigura2D{
    
    protected boolean si_relleno;
    protected boolean si_relleno_liso;
    protected boolean si_degradado;

    public boolean isSi_relleno() {
        return si_relleno_liso;
    }

    public void setSi_relleno(boolean si_relleno) {
        this.si_relleno = si_relleno;
    }

    public boolean isSi_relleno_liso() {
        return si_relleno_liso;
    }

    public void setSi_relleno_liso(boolean si_relleno_liso) {
        this.si_relleno_liso = si_relleno_liso;
    }

    public boolean isSi_degradado() {
        return si_degradado;
    }

    public void setSi_degradado(boolean si_degradado) {
        this.si_degradado = si_degradado;
    }
    
    @Override
    public void pintarShape(Graphics g) {
        
    }

    @Override
    public void setLocalizacionShape(Point2D ini, Point2D fin) {

    }
    
}
