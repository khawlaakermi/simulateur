/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ordonnanceur;

import java.util.Date;
import java.util.Scanner;
import java.util.Calendar;
/**
 *
 * @author malak
 */
public class Politiques {
    
    public Politiques(){ /* Consructeur */
    } 
    
    
     public Processus[] registerProcessus(int ptr, Processus[] listeProcessus ,String nomProcessus , int dureeProcessus , int prioriteProcessus, int tempArrivee){ /* Enregistrement de processus */
                
  
        Processus p1 = new Processus (nomProcessus, tempArrivee ,dureeProcessus, prioriteProcessus);       
        listeProcessus[ptr] = p1;
        ptr++;
       
        return listeProcessus;
                     
    }
    
     public void AfficheListeProcessus(int ptr, Processus[] listeProcessus){
         
        System.out.println("--------------------");
        for (int i = 0; i < ptr; i++){
                     
             listeProcessus[i].afficheProcessus();
             System.out.println("--------------------");
         }
         System.out.println("");
            
    }
    public float []FIFO(int ptr, Processus[] listeProcessus)
    {
                //Trier selon temp d'arrivée
                float res[]=new float[2];
                Processus aux =new Processus();
                int Tempsfinal[] = new int[ptr];     // Temps final ou se termine le prcs
		int TempsRotation[] = new int[ptr];     // Temps de rotation
		int TempsAttente[] = new int[ptr];     // Temps d'attente
		
		float avgTempsAttente=0,avgTempsRotation=0;
		for(int i = 0 ; i <ptr; i++)
		{
			for(int  j=0;  j < ptr-(i+1) ; j++)
			{
				if( listeProcessus[j].getTempArriveeProcessus() > listeProcessus[j+1].getTempArriveeProcessus() )
				{
					aux = listeProcessus[j];
					listeProcessus[j] = listeProcessus[j+1];
					listeProcessus[j+1]= aux;

				}
			}
		}
                
                for(int  i = 0 ; i < ptr; i++)
		{
			if( i == 0)
			{	
				Tempsfinal[i] = listeProcessus[i].getTempArriveeProcessus()  + listeProcessus[i].getDureeProcessus();
			}
			else
			{
				if( listeProcessus[i].getTempArriveeProcessus()  > Tempsfinal[i-1])
				{
					Tempsfinal[i] = listeProcessus[i].getTempArriveeProcessus() + listeProcessus[i].getDureeProcessus();
				}
				else
					Tempsfinal[i] = Tempsfinal[i-1] + listeProcessus[i].getDureeProcessus();
			}
			TempsRotation[i] = Tempsfinal[i] - listeProcessus[i].getTempArriveeProcessus()  ;          // turnaround time= completion time- arrival time
			TempsAttente[i]= TempsRotation[i]  - listeProcessus[i].getDureeProcessus() ;          // waiting time= turnaround time- burst time
			avgTempsAttente +=TempsAttente[i] ;               // total waiting time
			avgTempsRotation += TempsRotation[i]  ;               // total turnaround time
		}
		
		System.out.println("\npid  \t arrival  brust  complete turn waiting");
		for(int  i = 0 ; i< ptr;  i++)
		{
			System.out.println(listeProcessus[i].getNomProcessus() + "  \t " + listeProcessus[i].getTempArriveeProcessus()  + "\t" + listeProcessus[i].getDureeProcessus() + "\t" + Tempsfinal[i] + "\t" + TempsRotation[i]  + "\t"  + TempsAttente[i] ) ;
		}
		
		System.out.println("\naverage waiting time: "+ (avgTempsAttente/ptr));     // printing average waiting time.
		System.out.println("average turnaround time:"+(avgTempsRotation/ptr));    // printing average turnaround time.
    res[0]=(avgTempsAttente/ptr);
    res[1]=(avgTempsRotation/ptr);
    return res;
    
    }
    
     public float [] SJF(int ptr, Processus[] listeProcessus)
    {
                //Trier selon temp d'arrivée
                float res[]=new float[2];
                Processus aux =new Processus();
                int Tempsfinal[] = new int[ptr];     // Temps final ou se termine le prcs
		int TempsRotation[] = new int[ptr];     // Temps de rotation
		int TempsAttente[] = new int[ptr];     // Temps d'attente
		
                int Drapeau[] = new int[ptr];       //prcs a terminé ou nn
                
                int st=0, nb_prcs_termine=0;
                
		float avgTempsAttente=0,avgTempsRotation=0;
                
                
                boolean a = true;
		while(true)
		{
			int c = ptr, min = 999;
			if (nb_prcs_termine == ptr) //  nb total de prcs = completed process loop will be terminated
				break;
			
			for (int i=0; i<ptr; i++)
			{
				/*
				 * If i'th process arrival time <= system time and its flag=0 and burst<min 
				 * That process will be executed first 
				 */ 
				if ((listeProcessus[i].getTempArriveeProcessus() <= st) && (Drapeau[i] == 0) && (listeProcessus[i].getDureeProcessus()<min))
				{
					min = listeProcessus[i].getDureeProcessus();
					c = i;
				}
			}
			
			/* If c==n means c value can not updated because no process arrival time< system time so we increase the system time */
			if (c == ptr) 
				st++;
			else
			{
				Tempsfinal[c]=st+listeProcessus[c].getDureeProcessus();
				st+=listeProcessus[c].getDureeProcessus();
				TempsRotation[c]=Tempsfinal[c]-listeProcessus[c].getTempArriveeProcessus();
				TempsAttente[c]=TempsRotation[c]-listeProcessus[c].getDureeProcessus();
				Drapeau[c]=1;
				nb_prcs_termine++;
			}
		}
		
		System.out.println("\npid \t \t arrival  brust  complete turn waiting");
		for(int  i = 0 ; i< ptr;  i++)
		{       
                        avgTempsAttente+= TempsAttente[i];
			avgTempsRotation+= TempsRotation[i];
			System.out.println(listeProcessus[i].getNomProcessus() + "  \t " + listeProcessus[i].getTempArriveeProcessus()  + "\t" + listeProcessus[i].getDureeProcessus() + "\t" + Tempsfinal[i] + "\t" + TempsRotation[i]  + "\t"  + TempsAttente[i] ) ;
		}
		
		System.out.println("\naverage waiting time: "+ (avgTempsAttente/ptr));     // printing average waiting time.
		System.out.println("average turnaround time:"+(avgTempsRotation/ptr));    // printing average turnaround time.
                res[0]=(avgTempsAttente/ptr);
                res[1]=(avgTempsRotation/ptr);
                 return res;
                   
                
    }
      public String [] roundRobin(Processus[] listeProcessus,int ptr , int n) 
	{ 
		// result of average times 
		int res = 0; 
		int resc = 0; 

		// for sequence storage 
		String [] seq = new String[1000]; 

		// copy the burst array and arrival array 
		// for not effecting the actual array 
		int res_b[] = new int[ptr]; 
		int res_a[] = new int[ptr]; 

		for (int i = 0; i < res_b.length; i++) { 
			res_b[i] = listeProcessus[i].getDureeProcessus(); 
			res_a[i] = listeProcessus[i].getTempArriveeProcessus(); 
		} 

		// critical time of system 
		int t = 0; 

		// for store the waiting time 
		int w[] = new int[ptr]; 

		// for store the Completion time 
		int comp[] = new int[ptr]; 
                int k=0;
                seq[0]=""+0;
		while (true) { 
			boolean flag = true; 
			for (int i = 0; i < ptr; i++) { 

				// these condition for if 
				// arrival is not on zero 

				// check that if there come before qtime 
				if (res_a[i] <= t) { 
					if (res_a[i] <= n) { 
						if (res_b[i] > 0) { 
							flag = false; 
							if (res_b[i] > n) { 

								// make decrease the b time 
								t = t + n; 
								res_b[i] = res_b[i] - n; 
								res_a[i] = res_a[i] + n; 
                                                                k++;
								seq [k]= listeProcessus[i].getNomProcessus(); 
                                                              
							} 
							else { 

								// for last time 
								t = t + res_b[i]; 

								// store comp time 
								comp[i] = t - listeProcessus[i].getTempArriveeProcessus();

								// store wait time 
								w[i] = t - listeProcessus[i].getDureeProcessus() - listeProcessus[i].getTempArriveeProcessus(); 
								res_b[i] = 0; 

								// add sequence 
                                                                k++;
								seq [k]= listeProcessus[i].getNomProcessus(); 
                                                              
							} 
						} 
					} 
					else if (res_a[i] > n) { 

						// is any have less arrival time 
						// the coming process then execute them 
						for (int j = 0; j < ptr; j++) { 

							// compare 
							if (res_a[j] < res_a[i]) { 
								if (res_b[j] > 0) { 
									flag = false; 
									if (res_b[j] > n) { 
										t = t + n; 
										res_b[j] = res_b[j] - n; 
										res_a[j] = res_a[j] + n; 
                                                                                                                                                                
                                                                             k++;
                                                                             seq [k]= listeProcessus[j].getNomProcessus(); 
                                                                               
									} 
									else { 
										t = t + res_b[j]; 
										comp[j] = t - listeProcessus[j].getTempArriveeProcessus() ;
										w[j] = t - listeProcessus[j].getDureeProcessus() - listeProcessus[j].getTempArriveeProcessus(); 
										res_b[j] = 0;
                                                                                 k++;
										seq [k]= listeProcessus[j].getNomProcessus(); 
                                                                              
									} 
								} 
							} 
						} 

						// now the previous porcess according to 
						// ith is process 
						if (res_b[i] > 0) { 
							flag = false; 

							// Check for greaters 
							if (res_b[i] > n) { 
								t = t + n; 
								res_b[i] = res_b[i] - n; 
								res_a[i] = res_a[i] + n; 
                                                                k++;
								seq [k]= listeProcessus[i].getNomProcessus(); 
                                                               
							} 
							else { 
								t = t + res_b[i]; 
								comp[i] = t - listeProcessus[i].getTempArriveeProcessus() ;
								w[i] = t - listeProcessus[i].getDureeProcessus() - listeProcessus[i].getTempArriveeProcessus(); 
								res_b[i] = 0; 
                                                                k++;
								seq [k]= listeProcessus[i].getNomProcessus(); 
                                                               
							} 
						} 
					} 
				} 

				// if no process is come on thse critical 
				else if (res_a[i] > t) { 
					t++; 
					i--; 
				} 
			} 
			// for exit the while loop 
			if (flag) { 
				break; 
			} 
		} 
       
       seq[0]=""+(k);
       
		System.out.println("name ctime wtime"); 
		for (int i = 0; i < ptr; i++) { 
			System.out.println(" " + listeProcessus[i].getNomProcessus() + " " + comp[i] 
							+ " " + w[i]); 

			res = res + w[i]; 
			resc = resc + comp[i]; 
		} 

		System.out.println("Average waiting time is "
						+ (float)res /ptr); 
		System.out.println("Average compilation time is "
						+ (float)res /ptr);
                
                for (int i = 1; i < k+1; i++)
                {
		System.out.println("Sequence is like that " + seq[i]); }
                seq[k+1]=""+(float)res /ptr;
                seq[k+2]=""+(float)res /ptr;
                return seq;
	} 
}

