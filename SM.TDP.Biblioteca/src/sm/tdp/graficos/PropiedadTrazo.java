/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.tdp.graficos;

import java.awt.BasicStroke;
import java.awt.Stroke;

/**
 *
 * @author Tatiana
 */
public class PropiedadTrazo extends Propiedad{
    
    private int tam_grosor;
    private Stroke tipo_trazo;
    
    /**
     * Metodo constructor parametrizado
     * @param tamanio_grosor es el tamaño del grosor de trazo
     * @param tipo_trazo es el tipo de trazo
     */
    public PropiedadTrazo(int tamanio_grosor, String tipo_trazo){
        this.tipo_propiedad = PROPIEDAD_TRAZO;
        this.tam_grosor = tamanio_grosor;
        
        if(tipo_trazo.equals("Trazo contínuo"))
            setTipo_trazo(set_trazo_continuo(tam_grosor));
        else if(tipo_trazo.equals("Trazo punteado"))
            setTipo_trazo(set_trazo_punteado(tam_grosor));
        else if(tipo_trazo.equals("Trazo punto y raya"))
            setTipo_trazo(set_trazo_punto_raya(tam_grosor));
    }
    
    /**
     * Establece el tipo de trazo continup
     * @param tam_grosor es el tamaño de grosor
     */
    private Stroke set_trazo_continuo(int tam_grosor){
        return new BasicStroke(1.0f * tam_grosor);
    }
    
    /**
     * Establece el tipo de trazo punto y raya
     * @param tam_grosor es el tamaño de grosor
     */
    private Stroke set_trazo_punto_raya(int tam_grosor){
        float patron_punto_raya[] = {50.0f, 15.0f, 10.0f, 15.0f};

        return new BasicStroke(1.0f * tam_grosor, BasicStroke.CAP_SQUARE,
                                        BasicStroke.JOIN_ROUND, 1.0f,
                                        patron_punto_raya, 0.0f);
    }
    
    /**
     * Establece el tipo de trazo punteado
     * @param tam_grosor es el tamaño de grosor
     */
    private Stroke set_trazo_punteado(int tam_grosor){
        float patron_punteado[] = {2.0f, 10.0f};

        return new BasicStroke(1.0f * tam_grosor, BasicStroke.CAP_SQUARE,
                                        BasicStroke.JOIN_MITER, 1.0f,
                                        patron_punteado, 0.0f);
    }
    
    public int getTam_grosor() {
        return tam_grosor;
    }
    
    /**
     * Establece el tamaño de grosor de trazo
     * @param tam es el tamaño del grosor de trazo
     */
    public void setTam_grosor(int tam){
        this.tam_grosor = tam;
    }

    /**
     * Establece el tipo de trazo según un grosor
     * @param tam_grosor es el tamaño del grosor
     */
    public void crear_tipo_trazo(int tam_grosor) {
        
        if(tipo_trazo.equals("Trazo contínuo"))
            setTipo_trazo(set_trazo_continuo(tam_grosor));
        else if(tipo_trazo.equals("Trazo punteado"))
            setTipo_trazo(set_trazo_punteado(tam_grosor));
        else if(tipo_trazo.equals("Trazo punto y raya"))
            setTipo_trazo(set_trazo_punto_raya(tam_grosor));
        
    }

    public Stroke getTipo_trazo() {
        return tipo_trazo;
    }

    /**
     * Establece el tipo de trazo
     * @param tipo_trazo  es el tipo de trazo
     */
    public void setTipo_trazo(Stroke tipo_trazo) {
        this.tipo_trazo = tipo_trazo;
    }
}
