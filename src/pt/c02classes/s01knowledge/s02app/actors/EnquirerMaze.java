package pt.c02classes.s01knowledge.s02app.actors;

import java.util.ArrayList;

import pt.c02classes.s01knowledge.s01base.inter.IEnquirer;
import pt.c02classes.s01knowledge.s01base.inter.IResponder;

public class EnquirerMaze implements IEnquirer {

	public enum Cordenadas {
		norte, sul, leste, oeste;
	}
	
	
	public Cordenadas direcao = Cordenadas.norte;

	IResponder responder;
	
	public void connect(IResponder responder) {
		this.responder = responder;
	}
	
	public boolean discover() {
		ArrayList<Pontos> pontosPercorridos = new ArrayList<Pontos>();
		boolean saidaEncontrada = false;
		boolean caminhoInvalido = false;
		
		Pontos novoPonto = new Pontos();
		novoPonto.x = 0;
		novoPonto.y = 0;
		
		pontosPercorridos.add(novoPonto);
		while (!saidaEncontrada) {
			
			while(!caminhoInvalido) {
				VerificaSaida(saidaEncontrada);
				
				if(!saidaEncontrada) {
					String resposta = responder.ask(direcao.toString());
					
					if(PontoNaoPercorrido(pontosPercorridos) && responder.move(direcao.toString())) {
						AdicionaPontos(pontosPercorridos);
						direcao = Cordenadas.norte;
					} else {
						if(direcao == Cordenadas.sul) {
							caminhoInvalido = true;
							RetiraPontos(pontosPercorridos);
						} else {
							TrocaDirecao();
						}
					}
				} else {
					caminhoInvalido = true;
				}
				System.out.print(pontosPercorridos.get(pontosPercorridos.size()-1).x);
				System.out.print(pontosPercorridos.get(pontosPercorridos.size()-1).y);
				System.out.println();
			}
			caminhoInvalido = false;
		}

		return true;
	}
	
	void VerificaSaida(boolean saidaEncontrada) {
		String resposta = responder.ask("aqui");
		if(resposta.equalsIgnoreCase("saida")) {
			saidaEncontrada = true;
		}
	}
	
	void TrocaDirecao() {
		switch (direcao) {
			case norte:
				direcao = Cordenadas.leste;
				break;
			case leste:
				direcao = Cordenadas.oeste;
				break;
			case oeste:
				direcao = Cordenadas.sul;
				break;
			case sul:
				direcao = Cordenadas.norte;
				break;
		}
	}
	
	boolean PontoNaoPercorrido(ArrayList<Pontos>pontosPercorridos) {
		int k = pontosPercorridos.size() - 1;
		
		if(pontosPercorridos.size() == 0)
			return true;
		
		switch (direcao) {
			case norte:
				for(Pontos pontoTeste: pontosPercorridos) {
					if((pontoTeste.x == pontosPercorridos.get(k).x) && pontoTeste.y == pontosPercorridos.get(k).y + 1)
						return false;
				}
				break;
				
			case sul:
				for(Pontos pontoTeste: pontosPercorridos) {
					if((pontoTeste.x == pontosPercorridos.get(k).x) && pontoTeste.y == pontosPercorridos.get(k).y - 1)
						return false;
				}
				break;
			
			case leste:
				for(Pontos pontoTeste: pontosPercorridos) {
					if((pontoTeste.x == pontosPercorridos.get(k).x + 1) && pontoTeste.y == pontosPercorridos.get(k).y)
						return false;
				}
				break;
				
			case oeste:
				for(Pontos pontoTeste: pontosPercorridos) {
					if((pontoTeste.x == pontosPercorridos.get(k).x - 1) && pontoTeste.y == pontosPercorridos.get(k).y)
						return false;
				}
				break;
		}
		return true;
	}
	
	void AdicionaPontos(ArrayList<Pontos> pontosPercorridos) {
		Pontos novoPonto = new Pontos();
		int k = pontosPercorridos.size() - 1;

		switch (direcao) {
			case norte:
				novoPonto.x = pontosPercorridos.get(k).x;
				novoPonto.y = pontosPercorridos.get(k).y + 1;
				pontosPercorridos.add(novoPonto);
				break;
				
			case leste:
				novoPonto.x = pontosPercorridos.get(k).x + 1;
				novoPonto.y = pontosPercorridos.get(k).y;
				pontosPercorridos.add(novoPonto);
				break;
				
			case oeste:
				novoPonto.x = pontosPercorridos.get(k).x - 1;
				novoPonto.y = pontosPercorridos.get(k).y;
				pontosPercorridos.add(novoPonto);
				break;
				
			case sul:
				novoPonto.x = pontosPercorridos.get(k).x;
				novoPonto.y = pontosPercorridos.get(k).y - 1;
				pontosPercorridos.add(novoPonto);
				break;
		}
	}
	
	void RetiraPontos(ArrayList<Pontos> pontosPercorridos) {
		int k = pontosPercorridos.size() - 1;
		if(pontosPercorridos.get(k).x == pontosPercorridos.get(k-1).x) {
			if(pontosPercorridos.get(k).y == pontosPercorridos.get(k-1).y + 1) {
				responder.move("sul");
				direcao = Cordenadas.leste;
			} else {
				responder.move("norte");
				direcao = Cordenadas.norte;
			}
		} else if (pontosPercorridos.get(k).x == pontosPercorridos.get(k-1).x + 1) {
			responder.move("oeste");
			direcao = Cordenadas.oeste;
		} else {
			responder.move("leste");
			direcao = Cordenadas.sul;
		}
		pontosPercorridos.remove(k);
	}
}
