package pt.c02classes.s01knowledge.s02app.app;

import java.util.Scanner;

import pt.c02classes.s01knowledge.s01base.impl.BaseConhecimento;
import pt.c02classes.s01knowledge.s01base.impl.Statistics;
import pt.c02classes.s01knowledge.s01base.inter.IBaseConhecimento;
import pt.c02classes.s01knowledge.s01base.inter.IEnquirer;
import pt.c02classes.s01knowledge.s01base.inter.IResponder;
import pt.c02classes.s01knowledge.s01base.inter.IStatistics;
import pt.c02classes.s01knowledge.s02app.actors.EnquirerAnimals;
import pt.c02classes.s01knowledge.s02app.actors.EnquirerMaze;
import pt.c02classes.s01knowledge.s02app.actors.ResponderAnimals;
import pt.c02classes.s01knowledge.s02app.actors.ResponderMaze;

public class OrchestratorInteractive
{
	public static void main(String[] args)
	{
		IEnquirer enq;
		IResponder resp;
		IStatistics stat;
		
		Scanner entrada = new Scanner(System.in);
		
		String modoDeJogo;
		System.out.println("Escolha o modo de jogo: Animals ou Maze");
		modoDeJogo = entrada.nextLine();
		
		IBaseConhecimento base = new BaseConhecimento();
		stat = new Statistics();
		
		if (modoDeJogo.equalsIgnoreCase("animals")) {
			System.out.println("Escolha um animal");
			base.setScenario("animals");
			String animalEscolhido = entrada.nextLine();
			System.out.println("Enquirer com " + animalEscolhido + "...");
			resp = new ResponderAnimals(stat, animalEscolhido);
			enq = new EnquirerAnimals();
        	
		} else {
			System.out.println("Escolha um labirinto");
			base.setScenario("maze");
			String mazeEscolhido = entrada.nextLine();
			System.out.println("Enquirer com " + mazeEscolhido + "...");
			resp = new ResponderMaze(stat, mazeEscolhido);
			enq = new EnquirerMaze();
		}
		enq.connect(resp);
		enq.discover();
		System.out.println("----------------------------------------------------------------------------------------\n");
		
		entrada.close();
	}
	
}
