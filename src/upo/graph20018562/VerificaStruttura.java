package upo.graph20018562;

import java.util.Scanner;

import upo.graph.base.Graph;

public class VerificaStruttura {
	static Scanner tastiera;

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		
		tastiera = new Scanner(System.in);
		Class<?> graph;
		
		System.out.println("Digita la tua matricola");
		String matricola = tastiera.nextLine();
		String nomeGrafo="";
		switch(matricola.charAt(matricola.length()-1)) {
		case '0' :
		case '1' :
			nomeGrafo = "AdjListDir";
			break;
		case '2' :
		case '3' :
			nomeGrafo = "AdjListUndir";
			break;
		case '4' :
		case '5' :
		case '6' :
			nomeGrafo = "AdjMatrixDir";
			break;
		case '7' :
		case '8' :
		case '9' :
			nomeGrafo = "AdjMatrixUndir";
			break;
		default : 
			System.out.println("ERRORE");
			throw new RuntimeException();
		}
		try {
			graph = Class.forName("upo.graph"+matricola+"."+nomeGrafo);
			//SE LA PROSSIMA RIGA E' SEGNATA COME ERRORE, NON AVETE IMPORTATO IL VOSTRO JAR IN QUESTO PROGETTO!!!
			Graph g = (Graph) graph.newInstance();
			Graph g2 = (Graph) graph.newInstance();
			if(!g.equals(g2)) {
				System.out.println("Sei sicuro di aver implementato correttamente la equals di "+nomeGrafo+"?");
				System.exit(0);
			}
			
			nomeGrafo = nomeGrafo+"Weight";
			graph = Class.forName("upo.graph"+matricola+"."+nomeGrafo);
			g = (Graph) graph.newInstance();
			g2 = (Graph) graph.newInstance();
			if(!g.equals(g2)) {
				System.out.println("Sei sicuro di aver implementato correttamente la equals di "+nomeGrafo+"?");
				System.exit(0);
			}
			
			System.out.println("Il test � terminato con successo: l'organizzazione delle tue classi � ok per la consegna.");
			
		} catch (ClassNotFoundException e) {
			System.out.println("La classe "+nomeGrafo+" non esiste nella corretta locazione. Controlla di aver importato il tuo package, che l'organizzazione dei package sia quella richiesta e che la classe si chiami nel modo corretto.");
		} catch (InstantiationException e) {
			System.out.println("Ops, sembra che tu non abbia un costruttore senza parametri per il tuo grafo "+nomeGrafo+". Per favore implementalo.");
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}