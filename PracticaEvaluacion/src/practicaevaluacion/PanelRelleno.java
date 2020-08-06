/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicaevaluacion;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import sm.tdp.iu.MiLienzo2D;

/**
 * Esta clase contiene los atributos que definen un objeto de tipo PanelRelleno
 * @author Tatiana Daniela Pastorini
 * @version 1.0
 */
public class PanelRelleno extends javax.swing.JDialog {
    
    private Color c_relleno_liso;
    private Color c_relleno_frente;
    private Color c_relleno_fondo;
    private boolean figura_sin_relleno;
    private boolean figura_relleno_liso;
    private boolean figura_relleno_degradado;
    private boolean si_vertical;
    private boolean si_horizontal;
    
    private MiLienzo2D lienzo;
    
    /**
     * Creates new form PanelRelleno
     * @param parent
     * @param modal
     * @param lienzo
     */
    public PanelRelleno(java.awt.Frame parent, boolean modal, MiLienzo2D lienzo) {
        initComponents();
        this.setTitle("Propiedades de relleno");
        
        this.sin_relleno.setSelected(true);
        this.color_relleno_liso.setEnabled(false);
        this.color_relleno_frente.setEnabled(false);
        this.color_relleno_fondo.setEnabled(false);
        this.orientacion_vertical.setEnabled(false);
        this.orientacion_horizontal.setEnabled(false);
        
        if(lienzo.getEdicion()){
            // si la figura no está rellena, entonces pongo el botón a "sin relleno"
            if(!lienzo.getEsta_relleno()){
                this.sin_relleno.setSelected(true);
                this.color_relleno_liso.setEnabled(false);
                this.color_relleno_frente.setEnabled(false);
                this.color_relleno_fondo.setEnabled(false);
                this.orientacion_vertical.setEnabled(false);
                this.orientacion_horizontal.setEnabled(false);
            }
            else if(lienzo.getEsta_relleno_liso()){
                this.sin_relleno.setSelected(false);
                this.relleno_liso.setSelected(true);
                this.color_relleno_liso.setEnabled(true);
                this.color_relleno_frente.setEnabled(false);
                this.color_relleno_fondo.setEnabled(false);
                this.orientacion_vertical.setEnabled(false);
                this.orientacion_horizontal.setEnabled(false);
            }
            else{
                this.sin_relleno.setSelected(false);
                this.relleno_liso.setSelected(false);
                this.orientacion_horizontal.setSelected(true);
                this.relleno_degradado.setSelected(true);
                this.color_relleno_liso.setEnabled(false);
                this.color_relleno_frente.setEnabled(true);
                this.color_relleno_fondo.setEnabled(true);
                this.orientacion_vertical.setEnabled(true);
                this.orientacion_horizontal.setEnabled(true);
                
                if(lienzo.get_si_degradado_horizontal())
                    this.orientacion_horizontal.setSelected(true);
                else
                    this.orientacion_vertical.setSelected(true);
            }
        }
        
        this.lienzo = lienzo;
        
        this.color_relleno_liso.setBackground(Color.CYAN);
        this.color_relleno_frente.setBackground(Color.MAGENTA);
        this.color_relleno_fondo.setBackground(Color.YELLOW);
        
        this.c_relleno_liso = Color.CYAN;
        this.c_relleno_frente = Color.MAGENTA;
        this.c_relleno_fondo = Color.YELLOW;
    }
     /**
     * Metodo para obtener el color del relleno liso.
     * @return c_relleno_liso Color del relleno liso
     * 
     */
    public Color getC_relleno_liso() {
        return c_relleno_liso;
    }
/**
     * Establece el color de relleno liso
     * @param c_relleno_liso Color de relleno liso
     */
    public void setC_relleno_liso(Color c_relleno_liso) {
        this.c_relleno_liso = c_relleno_liso;
    }
     /**
     * Metodo para obtener el color de frente del relleno para el degradado.
     * @return c_relleno_frente Color de frente del relleno
     * 
     */

    public Color getC_relleno_frente() {
        return c_relleno_frente;
    }
    /**
     * Establece el color frente de relleno de degradado
     * @param c_relleno_frente color de frente relleno liso
     */

    public void setC_relleno_frente(Color c_relleno_frente) {
        this.c_relleno_frente = c_relleno_frente;
    }
 /**
     * Metodo para obtener el color de fondo del relleno para el degradado.
     * @return c_relleno_fondo Color de fondo del relleno
     * 
     */

    public Color getC_relleno_fondo() {
        return c_relleno_fondo;
    }
        /**
     * Establece el color fondo de relleno de degradado
     * @param c_relleno_fondo color de fondo relleno liso
     */


    public void setC_relleno_fondo(Color c_relleno_fondo) {
        this.c_relleno_fondo = c_relleno_fondo;
    }
 /**
     * Metodo para ver si un objeto tiene o no relleno.
     * @return figura_sin_relleno Tiene relleno o no
     * 
     */
    public boolean isFigura_sin_relleno() {
        return figura_sin_relleno;
    }

        /**
     * Establece a true o false el relleno de la figura
     * @param figura_sin_relleno variable booleana 
     */


    public void setFigura_sin_relleno(boolean figura_sin_relleno) {
        this.figura_sin_relleno = figura_sin_relleno;
    }
 /**
     * Metodo para ver si un objeto tiene o no relleno liso.
     * @return figura_sin_relleno_liso Tiene relleno liso o no
     * 
     */
    public boolean isFigura_relleno_liso() {
        return figura_relleno_liso;
    }
      /**
     * Establece a true o false el relleno liso de la figura
     * @param figura_relleno_liso variable booleana 
     */

    public void setFigura_relleno_liso(boolean figura_relleno_liso) {
        this.figura_relleno_liso = figura_relleno_liso;
    }
 /**
     * Metodo para ver si un objeto tiene o no relleno con degradado.
     * @return figura_sin_relleno_degradado Tiene relleno con degradado o no
     * 
     */
    public boolean isFigura_relleno_degradado() {
        return figura_relleno_degradado;
    }
    /**
     * Establece a true o false el relleno degradado de la figura
     * @param figura_relleno_degradado variable booleana 
     */

    public void setFigura_relleno_degradado(boolean figura_relleno_degradado) {
        this.figura_relleno_degradado = figura_relleno_degradado;
    }
 /**
     * Metodo para ver si un objeto tiene o no relleno con degradado vertical.
     * @return si_vertical Tiene relleno con degradado vertical o no
     * 
     */
    public boolean isSi_vertical() {
        return si_vertical;
    }

/**
     * Establece a true o false el relleno degradado si es vertical de la figura
     * @param si_vertical variable booleana 
     */
    public void setSi_vertical(boolean si_vertical) {
        this.si_vertical = si_vertical;
    }
/**
     * Metodo para ver si un objeto tiene o no relleno con degradado horizontal.
     * @return si_horizontal Tiene relleno con degradado horizontal o no
     * 
     */
    public boolean isSi_horizontal() {
        return si_horizontal;
    }
/**
     * Establece a true o false el relleno degradado si es horizontal de la figura
     * @param si_horizontal variable booleana 
     */
    public void setSi_horizontal(boolean si_horizontal) {
        this.si_horizontal = si_horizontal;
    }
/**
     * Metodo que devuelve un componente.
     * @return color_relleno_fondo Componente
     * 
     */
    public JButton getColor_relleno_fondo() {
        return color_relleno_fondo;
    }
  /**
     * Establece el componente
     * @param color_relleno_fondo Componente
     */
    public void setColor_relleno_fondo(JButton color_relleno_fondo) {
        this.color_relleno_fondo = color_relleno_fondo;
    }
    /**
     * Metodo que devuelve un componente.
     * @return color_relleno_frente Componente
     * 
     */

    public JButton getColor_relleno_frente() {
        return color_relleno_frente;
    }
/**
     * Establece el componente
     * @param color_relleno_frente Componente
     */
    public void setColor_relleno_frente(JButton color_relleno_frente) {
        this.color_relleno_frente = color_relleno_frente;
    }
    /**
     * Metodo que devuelve un componente.
     * @return color_relleno_liso Componente
     * 
     */

    public JButton getColor_relleno_liso() {
        return color_relleno_liso;
    }
/**
     * Establece el componente
     * @param color_relleno_liso Componente
     */
    public void setColor_relleno_liso(JButton color_relleno_liso) {
        this.color_relleno_liso = color_relleno_liso;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grupo_relleno = new javax.swing.ButtonGroup();
        grupo_orientacion = new javax.swing.ButtonGroup();
        panel_superior = new javax.swing.JPanel();
        panel_tipo_relleno = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        sin_relleno = new javax.swing.JRadioButton();
        relleno_liso = new javax.swing.JRadioButton();
        relleno_degradado = new javax.swing.JRadioButton();
        panel_medio = new javax.swing.JPanel();
        panel_relleno_liso = new javax.swing.JPanel();
        color_relleno_liso = new javax.swing.JButton();
        panel_inferior = new javax.swing.JPanel();
        panel_relleno_degradado = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        color_relleno_frente = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        color_relleno_fondo = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        orientacion_horizontal = new javax.swing.JRadioButton();
        orientacion_vertical = new javax.swing.JRadioButton();
        jPanel6 = new javax.swing.JPanel();
        aceptar_relleno = new javax.swing.JButton();
        cancelar_relleno = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(350, 550));
        setMinimumSize(new java.awt.Dimension(350, 550));
        setPreferredSize(new java.awt.Dimension(350, 550));
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.PAGE_AXIS));

        panel_superior.setLayout(new java.awt.BorderLayout());

        panel_tipo_relleno.setBorder(javax.swing.BorderFactory.createTitledBorder("Tipo de relleno"));
        panel_tipo_relleno.setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.BorderLayout());

        grupo_relleno.add(sin_relleno);
        sin_relleno.setText("Sin relleno");
        sin_relleno.setToolTipText("Sin relleno de figura");
        sin_relleno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sin_rellenoActionPerformed(evt);
            }
        });
        jPanel1.add(sin_relleno, java.awt.BorderLayout.LINE_START);

        grupo_relleno.add(relleno_liso);
        relleno_liso.setText("Relleno liso");
        relleno_liso.setToolTipText("Figura rellena");
        relleno_liso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                relleno_lisoActionPerformed(evt);
            }
        });
        jPanel1.add(relleno_liso, java.awt.BorderLayout.CENTER);

        grupo_relleno.add(relleno_degradado);
        relleno_degradado.setText("Relleno degradado");
        relleno_degradado.setToolTipText("Figura con relleno degradado");
        relleno_degradado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                relleno_degradadoActionPerformed(evt);
            }
        });
        jPanel1.add(relleno_degradado, java.awt.BorderLayout.LINE_END);

        panel_tipo_relleno.add(jPanel1, java.awt.BorderLayout.CENTER);

        panel_superior.add(panel_tipo_relleno, java.awt.BorderLayout.CENTER);

        getContentPane().add(panel_superior);

        panel_medio.setLayout(new java.awt.BorderLayout());

        panel_relleno_liso.setBorder(javax.swing.BorderFactory.createTitledBorder("Color de relleno liso"));
        panel_relleno_liso.setMaximumSize(new java.awt.Dimension(42, 30));
        panel_relleno_liso.setMinimumSize(new java.awt.Dimension(42, 30));
        panel_relleno_liso.setPreferredSize(new java.awt.Dimension(42, 30));
        panel_relleno_liso.setLayout(new javax.swing.BoxLayout(panel_relleno_liso, javax.swing.BoxLayout.LINE_AXIS));

        color_relleno_liso.setBackground(new java.awt.Color(0, 0, 0));
        color_relleno_liso.setToolTipText("Color de relleno liso");
        color_relleno_liso.setMaximumSize(new java.awt.Dimension(30, 30));
        color_relleno_liso.setMinimumSize(new java.awt.Dimension(30, 30));
        color_relleno_liso.setPreferredSize(new java.awt.Dimension(30, 30));
        color_relleno_liso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                color_relleno_lisoActionPerformed(evt);
            }
        });
        panel_relleno_liso.add(color_relleno_liso);

        panel_medio.add(panel_relleno_liso, java.awt.BorderLayout.CENTER);

        getContentPane().add(panel_medio);

        panel_inferior.setLayout(new java.awt.BorderLayout());

        panel_relleno_degradado.setBorder(javax.swing.BorderFactory.createTitledBorder("Colores degradado"));
        panel_relleno_degradado.setLayout(new java.awt.BorderLayout());

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.LINE_AXIS));

        jLabel1.setText("Color frente");
        jLabel1.setMaximumSize(new java.awt.Dimension(76, 16));
        jLabel1.setMinimumSize(new java.awt.Dimension(76, 16));
        jLabel1.setPreferredSize(new java.awt.Dimension(76, 16));
        jPanel4.add(jLabel1);

        color_relleno_frente.setBackground(new java.awt.Color(255, 153, 0));
        color_relleno_frente.setToolTipText("Color de frente del degradado");
        color_relleno_frente.setMaximumSize(new java.awt.Dimension(33, 33));
        color_relleno_frente.setMinimumSize(new java.awt.Dimension(33, 33));
        color_relleno_frente.setPreferredSize(new java.awt.Dimension(33, 33));
        color_relleno_frente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                color_relleno_frenteActionPerformed(evt);
            }
        });
        jPanel4.add(color_relleno_frente);

        jPanel5.setLayout(new javax.swing.BoxLayout(jPanel5, javax.swing.BoxLayout.LINE_AXIS));

        jLabel2.setText("Color fondo");
        jLabel2.setMaximumSize(new java.awt.Dimension(76, 16));
        jLabel2.setMinimumSize(new java.awt.Dimension(76, 16));
        jLabel2.setPreferredSize(new java.awt.Dimension(76, 16));
        jPanel5.add(jLabel2);

        color_relleno_fondo.setBackground(new java.awt.Color(51, 0, 255));
        color_relleno_fondo.setToolTipText("Color de fondo del degradado");
        color_relleno_fondo.setMaximumSize(new java.awt.Dimension(33, 33));
        color_relleno_fondo.setMinimumSize(new java.awt.Dimension(33, 33));
        color_relleno_fondo.setPreferredSize(new java.awt.Dimension(33, 33));
        color_relleno_fondo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                color_relleno_fondoActionPerformed(evt);
            }
        });
        jPanel5.add(color_relleno_fondo);

        jPanel4.add(jPanel5);

        jPanel3.add(jPanel4, java.awt.BorderLayout.PAGE_START);

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Orientación del degradado"));
        jPanel9.setLayout(new javax.swing.BoxLayout(jPanel9, javax.swing.BoxLayout.LINE_AXIS));

        grupo_orientacion.add(orientacion_horizontal);
        orientacion_horizontal.setText("Horizontal");
        orientacion_horizontal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orientacion_horizontalActionPerformed(evt);
            }
        });
        jPanel9.add(orientacion_horizontal);

        grupo_orientacion.add(orientacion_vertical);
        orientacion_vertical.setText("Vertical");
        orientacion_vertical.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orientacion_verticalActionPerformed(evt);
            }
        });
        jPanel9.add(orientacion_vertical);

        jPanel3.add(jPanel9, java.awt.BorderLayout.CENTER);

        panel_relleno_degradado.add(jPanel3, java.awt.BorderLayout.CENTER);

        panel_inferior.add(panel_relleno_degradado, java.awt.BorderLayout.CENTER);

        getContentPane().add(panel_inferior);

        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.LINE_AXIS));

        aceptar_relleno.setText("Aceptar");
        aceptar_relleno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aceptar_rellenoActionPerformed(evt);
            }
        });
        jPanel6.add(aceptar_relleno);

        cancelar_relleno.setText("Cancelar");
        cancelar_relleno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelar_rellenoActionPerformed(evt);
            }
        });
        jPanel6.add(cancelar_relleno);

        getContentPane().add(jPanel6);
    }// </editor-fold>//GEN-END:initComponents

    private void sin_rellenoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sin_rellenoActionPerformed
        this.color_relleno_liso.setEnabled(false);
        this.color_relleno_frente.setEnabled(false);
        this.color_relleno_fondo.setEnabled(false);
        this.sin_relleno.setSelected(true);
        this.orientacion_vertical.setEnabled(false);
        this.orientacion_horizontal.setEnabled(false);
 
        this.figura_sin_relleno = true;
        this.figura_relleno_liso = false;
        this.figura_relleno_degradado = false;
    }//GEN-LAST:event_sin_rellenoActionPerformed

    private void relleno_lisoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_relleno_lisoActionPerformed
        this.color_relleno_liso.setEnabled(true);
        this.color_relleno_frente.setEnabled(false);
        this.color_relleno_fondo.setEnabled(false);
        this.orientacion_vertical.setEnabled(false);
        this.orientacion_horizontal.setEnabled(false);
        this.relleno_liso.setSelected(true);
        
        this.figura_sin_relleno = false;
        this.figura_relleno_liso = true;
        this.figura_relleno_degradado = false;
    }//GEN-LAST:event_relleno_lisoActionPerformed

    private void relleno_degradadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_relleno_degradadoActionPerformed
        this.color_relleno_liso.setEnabled(false);
        this.color_relleno_frente.setEnabled(true);
        this.color_relleno_fondo.setEnabled(true);
        this.orientacion_vertical.setEnabled(true);
        this.orientacion_horizontal.setEnabled(true);
        this.relleno_degradado.setSelected(true);
        
        this.figura_sin_relleno = false;
        this.figura_relleno_liso = false;
        this.figura_relleno_degradado = true;
    }//GEN-LAST:event_relleno_degradadoActionPerformed

    private void color_relleno_lisoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_color_relleno_lisoActionPerformed
        Color color_seleccionado = JColorChooser.showDialog(null, "Escoge un color de relleno liso", null);
        this.color_relleno_liso.setBackground(color_seleccionado);
        this.c_relleno_liso = color_seleccionado;
    }//GEN-LAST:event_color_relleno_lisoActionPerformed

    private void color_relleno_frenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_color_relleno_frenteActionPerformed
        Color color_seleccionado_frente = JColorChooser.showDialog(null, "Escoge un color de frente", null);
        this.color_relleno_frente.setBackground(color_seleccionado_frente);
        this.c_relleno_frente = color_seleccionado_frente;
    }//GEN-LAST:event_color_relleno_frenteActionPerformed

    private void color_relleno_fondoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_color_relleno_fondoActionPerformed
        Color color_seleccionado_fondo = JColorChooser.showDialog(null, "Escoge un color de fondo", null);
        this.color_relleno_fondo.setBackground(color_seleccionado_fondo);
        this.c_relleno_fondo = color_seleccionado_fondo;
    }//GEN-LAST:event_color_relleno_fondoActionPerformed

    private void orientacion_verticalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orientacion_verticalActionPerformed
        lienzo.set_si_degradado_vertical(true);
        lienzo.set_si_degradado_horizontal(false);
    }//GEN-LAST:event_orientacion_verticalActionPerformed

    private void orientacion_horizontalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orientacion_horizontalActionPerformed
        lienzo.set_si_degradado_vertical(false);
        lienzo.set_si_degradado_horizontal(true);
    }//GEN-LAST:event_orientacion_horizontalActionPerformed

    private void aceptar_rellenoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aceptar_rellenoActionPerformed
        
        lienzo.setEsta_relleno(!this.figura_sin_relleno);
        lienzo.setEsta_relleno_liso(this.figura_relleno_liso);
        lienzo.setEsta_relleno_degradado(this.figura_relleno_degradado);
                
        if(this.figura_relleno_liso){
            lienzo.setColorRellenoLisoFigura(this.c_relleno_liso);
        }
        else if(this.figura_relleno_degradado){
            lienzo.setColorRellenoFrenteFigura(this.c_relleno_frente);
            lienzo.setColorRellenoFondoFigura(this.c_relleno_fondo);
        }
        this.dispose();
    }//GEN-LAST:event_aceptar_rellenoActionPerformed

    private void cancelar_rellenoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelar_rellenoActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelar_rellenoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton aceptar_relleno;
    private javax.swing.JButton cancelar_relleno;
    private javax.swing.JButton color_relleno_fondo;
    private javax.swing.JButton color_relleno_frente;
    private javax.swing.JButton color_relleno_liso;
    private javax.swing.ButtonGroup grupo_orientacion;
    private javax.swing.ButtonGroup grupo_relleno;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton orientacion_horizontal;
    private javax.swing.JRadioButton orientacion_vertical;
    private javax.swing.JPanel panel_inferior;
    private javax.swing.JPanel panel_medio;
    private javax.swing.JPanel panel_relleno_degradado;
    private javax.swing.JPanel panel_relleno_liso;
    private javax.swing.JPanel panel_superior;
    private javax.swing.JPanel panel_tipo_relleno;
    private javax.swing.JRadioButton relleno_degradado;
    private javax.swing.JRadioButton relleno_liso;
    private javax.swing.JRadioButton sin_relleno;
    // End of variables declaration//GEN-END:variables
}
