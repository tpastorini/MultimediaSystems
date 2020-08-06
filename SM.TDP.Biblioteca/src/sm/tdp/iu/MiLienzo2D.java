/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.tdp.iu;

import sm.tdp.graficos.ColorFigura;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import sm.tdp.graficos.ColorDegradadoFigura;
import sm.tdp.graficos.ColorRellenoFigura;
import sm.tdp.graficos.Elipse2D;
import sm.tdp.graficos.Linea2D;
import sm.tdp.graficos.MiFigura2D;
import sm.tdp.graficos.MiFiguraRellena2D;
import sm.tdp.graficos.PropiedadAlisado;
import sm.tdp.graficos.PropiedadTransparencia;
import sm.tdp.graficos.PropiedadTrazo;
import sm.tdp.graficos.Rectangulo2D;
import sm.tdp.graficos.RoundRect2D;

/**
 * Esta clase representa la zona de dibujo.
 * @author Tatiana Daniela Pastorini
 * @version 1.0
 */
public class MiLienzo2D extends javax.swing.JPanel {

    private Point inicio, fin;
    private ArrayList<MiFigura2D> vectorFiguras;
    // Para definir mi área clip y no pintar fuera
    private Shape clip;
    private MiFigura2D s;
    
    // Para controlar los valores de creación del lienzo por defecto / usuario
    private int ancho_lienzo;
    private int alto_lienzo;
    
    private Color color_pincel;
    private Color color_relleno_liso;
    private Color color_frente;
    private Color color_fondo;
    private Formas figura_seleccionada;
    private int tam_grosor;
    private float grado_transparencia;
    private String tipo_trazo;
    
    private boolean edicion;
    private boolean esta_relleno;
    private boolean esta_relleno_liso;
    private boolean esta_relleno_degradado;
    private boolean degradado_vertical;
    private boolean degradado_horizontal;
    private boolean transparencia;
    private boolean alisado;
    private boolean figura_creada;
    
    /**
     * Creates new form MiLienzo2D
     */
    public MiLienzo2D() {
        initComponents();
        this.vectorFiguras = new ArrayList();
        this.tipo_trazo = "Trazo contínuo";
        this.grado_transparencia = 1.0f;
        this.figura_seleccionada = null;
        this.color_pincel = Color.RED;
        this.tam_grosor = 1;
    }
            
    @Override
    public void paint(Graphics g){
        super.paint(g);
        
        Graphics2D g2d = (Graphics2D) g;
        
        // Para definir el marco
        if(this.clip != null){
            g2d.clip(this.clip);
        }
        
        for(MiFigura2D s : vectorFiguras){
            s.pintarShape(g);
        }         
        
        if(this.edicion)
            s.pintar_recuadro_edicion(g2d);
        
        this.repaint();
    }
    
    public void set_nuevas_coordenadas_figura(double p1_x, double p1_y){
        Point2D p1 = new Point2D.Double(p1_x, p1_y);
                
        double dx = fin.getX() - (this.s.getPunto_ini()).getX();
        double dy = fin.getY() - (this.s.getPunto_ini()).getY();
        
        Point2D nuevoPunto = new Point2D.Double(p1_x + (this.fin.getX()-this.inicio.getX()),
                p1_y + (this.fin.getY()-this.inicio.getY()));
        
        this.s.setLocalizacionShape(p1, nuevoPunto);
        
        this.s.setPunto_ini(p1);
        this.s.setPunto_fin(nuevoPunto);
        
        this.repaint();
    }
    
    public Point2D coords_p1_figura(){
        return this.s.getPunto_ini();
    }
    
    public Point2D coords_p2_figura(){
        return this.s.getPunto_fin();
    }
    
    // Devuelve la figura que se ha seleccionado en la lista de figuras
    public void set_figura_a_editar(int pos){
        this.s = this.vectorFiguras.get(pos);
    }
    
    public ArrayList<MiFigura2D> getFigurasDibujadas(){
        return this.vectorFiguras;
    }
    
    public void set_area_visible(Shape clip){
        this.clip = clip;
    }
    
    public Shape get_area_visible(){
        return this.clip;
    }

    public float getGrado_transparencia() {
        return grado_transparencia;
    }

    public void setGrado_transparencia(float grado_transparencia) {
        this.grado_transparencia = grado_transparencia;
    }
        
    public void set_tam_ancho(int ancho){
        this.ancho_lienzo = ancho;
    }
    
    public void set_tam_alto(int alto){
        this.alto_lienzo = alto;
    }
    
    public Color getColor_pincel() {
        return color_pincel;
    }

    public void setColor_pincel(Color color_pincel) {
        if(this.getEdicion()){
            s.getColor_figura().setColor_trazo(color_pincel);
        }else 
            this.color_pincel = color_pincel;
            
        this.repaint();
    }
    
    public Formas getFigura_seleccionada() {
        return figura_seleccionada;
    }

    public void setFigura_seleccionada(Formas figura_seleccionada) {
        this.figura_seleccionada = figura_seleccionada;
    }

    public Point getPunto_ini() {
        return inicio;
    }

    public void setPunto_ini(Point punto_ini) {
        this.inicio = punto_ini;
    }

    public Point getPunto_fin() {
        return fin;
    }

    public void setPunto_fin(Point punto_fin) {
        this.fin = punto_fin;
    }
    
    public boolean getEsta_relleno() {
        if(this.getEdicion()){
            if(s instanceof MiFiguraRellena2D){
                return ((MiFiguraRellena2D)s).isSi_relleno();
            }
            else 
                return false;
            
        }else 
            return this.esta_relleno;
    }

    public void setEsta_relleno(boolean esta_relleno) {
        this.esta_relleno = esta_relleno;
    }

    public boolean getEsta_relleno_liso() {
        
        if(this.getEdicion()){
            if(s instanceof MiFiguraRellena2D){
                return ((MiFiguraRellena2D)s).isSi_degradado();
            }
            else 
                return false;
            
        }else 
            return this.esta_relleno;
    }

    public void setEsta_relleno_liso(boolean esta_relleno_liso) {
        this.esta_relleno_liso = esta_relleno_liso;
    }
    
    public boolean getEsta_relleno_degradado() {
        return esta_relleno_degradado;
    }

    public void setEsta_relleno_degradado(boolean esta_relleno_degradado) {
        this.esta_relleno_degradado = esta_relleno_degradado;
    }
    
    public boolean get_si_degradado_vertical() {
        return degradado_vertical;
    }

    public void set_si_degradado_vertical(boolean si_vertical) {
        this.degradado_vertical = si_vertical;
    }
    
    public boolean get_si_degradado_horizontal() {
        return degradado_horizontal;
    }

    public void set_si_degradado_horizontal(boolean si_horizontal) {
        this.degradado_horizontal = si_horizontal;
    }
    
    public void setEdicion(boolean edicion) {
        this.edicion = edicion;
    }
    
    public boolean getEdicion(){
        return this.edicion;
    }
    
    public Boolean es_transparente(){
        return this.transparencia;
    }
    
    public void setTransparencia(boolean transparencia){
        this.transparencia = transparencia;
    }
    
    public Boolean getAlisado(){
        return this.alisado;
    }
    
    public void setAlisado(boolean alisado){
        this.alisado = alisado;
    }
    
    public void setTamGrosor(int g){
        if(this.getEdicion()){
           s.getProp_trazo().setTam_grosor(g);
        }else  
            this.tam_grosor = g;
    }
    
    public int getTamGrosor(){
        return this.tam_grosor;
    }

    public String getTipo_trazo() {
        return tipo_trazo;
    }

    public void setTipo_trazo(String tipo_trazo) {
        this.tipo_trazo = tipo_trazo;
    }
    
    public void setColorRellenoLisoFigura(Color c_relleno_liso){
        this.color_relleno_liso = c_relleno_liso;
    }
    
    public Color getColorRellenoLisoFigura(){
        return this.color_relleno_liso;
    }
    
    public void setColorRellenoFrenteFigura(Color c_relleno_frente){
        this.color_frente = c_relleno_frente;
    }
    
    public Color getColorRellenoFrenteFigura(){
        return this.color_frente;
    }
    
    public void setColorRellenoFondoFigura(Color c_relleno_fondo){
        this.color_fondo = c_relleno_fondo;
    }
    
    public Color getColorRellenoFondoFigura(){
        return this.color_fondo;
    }
        
    private void createShape(Point punto_inicial){
        
        // Establece el alisado por defecto si está activado
        PropiedadAlisado prop_alisado = new PropiedadAlisado(this.alisado);
        PropiedadTransparencia prop_transparencia = new PropiedadTransparencia(this.grado_transparencia);
        PropiedadTrazo prop_trazo = new PropiedadTrazo(this.tam_grosor, this.tipo_trazo);
        ColorFigura color_trazo;
                        
        switch(this.figura_seleccionada){
            case RECTANGULO:
                color_trazo = preparar_relleno_figura();
                this.s = new Rectangulo2D(punto_inicial, punto_inicial, 
                        this.alisado, this.transparencia, this.esta_relleno_liso,
                        this.esta_relleno_degradado, prop_alisado, 
                        prop_transparencia, prop_trazo, color_trazo);
                this.vectorFiguras.add(s);
                
                break;
            case LINEA:
                color_trazo = new ColorFigura(this.color_pincel);
                this.s = new Linea2D(punto_inicial, punto_inicial, 
                        this.alisado, this.transparencia, prop_alisado, 
                        prop_transparencia, prop_trazo, color_trazo);
                this.vectorFiguras.add(s);
                
                break;
            case OVALO:
                color_trazo = preparar_relleno_figura();
                this.s = new Elipse2D(punto_inicial, punto_inicial, 
                        this.alisado, this.transparencia, this.esta_relleno_liso,
                        this.esta_relleno_degradado, prop_alisado, 
                        prop_transparencia, prop_trazo, color_trazo);
                this.vectorFiguras.add(s);
                
                break;
            case ROUNDRECT:
                color_trazo = preparar_relleno_figura();
                this.s = new RoundRect2D(punto_inicial, punto_inicial, 
                        this.alisado, this.transparencia, this.esta_relleno_liso,
                        this.esta_relleno_degradado, prop_alisado, 
                        prop_transparencia, prop_trazo, color_trazo);
                this.vectorFiguras.add(s);
                
                break;
        }
        
    }
    
    public ColorFigura preparar_relleno_figura(){
        ColorFigura c = null;
        
        if(esta_relleno_liso){
            c = new ColorRellenoFigura(color_relleno_liso, color_pincel);
        }
        else if(esta_relleno_degradado){
            c = new ColorDegradadoFigura(this.inicio, this.inicio, color_pincel, 
                    color_frente, color_fondo, this.degradado_vertical, this.degradado_vertical);
        }
        else
            c = new ColorFigura(color_pincel);
        
        return c;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    
    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        this.inicio = evt.getPoint();
        if(this.figura_seleccionada != null && !this.edicion)
            createShape(this.inicio);
    }//GEN-LAST:event_formMousePressed

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        this.formMouseDragged(evt);
    }//GEN-LAST:event_formMouseReleased

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        this.fin = evt.getPoint();
        
        if(this.figura_seleccionada != null && !this.edicion){
            switch (this.figura_seleccionada){                       
                case RECTANGULO:
                    ((Rectangulo2D) s).setLocalizacionShape(this.inicio, this.fin);
                    break;
                case LINEA:
                    ((Linea2D) s).setLocalizacionShape(this.inicio, this.fin);
                    break;
                case OVALO:
                    ((Elipse2D) s).setLocalizacionShape(this.inicio, this.fin);
                    break;
                case ROUNDRECT:
                    ((RoundRect2D) s).setLocalizacionShape(this.inicio, this.fin);
                    break;
            }
            this.repaint();
        }
    }//GEN-LAST:event_formMouseDragged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
