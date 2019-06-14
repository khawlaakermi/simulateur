/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ordonnanceur;

import java.io.*;
import java.util.Date;
/**
 *
 * @author malak
 */
public class Processus implements Serializable{
     
    private String nomProcessus;
    private int tempArrivee; 
    private int dureeProcessus;
    private int prioriteProcessus;
    
     
    public Processus(){ /* Constructeur par défaut */
         
        this.nomProcessus = null;
        this.tempArrivee = 0;
        this.dureeProcessus = 1;
        this.prioriteProcessus = 0;
        
        
    }
     
     
     
    public Processus(String nom, int tempArrivee,int duree, int priorite ){ /* Constructeur par initialisation */
         
        this.nomProcessus = nom;
        this.tempArrivee = tempArrivee;
        this.dureeProcessus = duree;
        this.prioriteProcessus = priorite;
             
    }
     
     
    public void afficheProcessus(){
         
       System.out.println("nom: " + nomProcessus );
       System.out.println("temp d'arrivée: " + tempArrivee);
       System.out.println("durée: " + dureeProcessus );
       System.out.println("priorite: " + prioriteProcessus );
         
         
    }
     
     
    public String getNomProcessus(){
         
        return this.nomProcessus;
         
    }
      public int getTempArriveeProcessus(){
         
        return this.tempArrivee;
       
    }
     
    public int getDureeProcessus(){
         
        return this.dureeProcessus;
         
    }
     
    public int getPrioriteProcessus(){
         
        return this.prioriteProcessus;
         
    }
    
    
 
}
