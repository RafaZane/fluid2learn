package pt.c01interfaces.s01knowledge.s02app.actors;

import java.util.ArrayList;

import pt.c01interfaces.s01knowledge.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01knowledge.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01knowledge.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01knowledge.s01base.inter.IEnquirer;
import pt.c01interfaces.s01knowledge.s01base.inter.IObjetoConhecimento;
import pt.c01interfaces.s01knowledge.s01base.inter.IResponder;

public class Enquirer implements IEnquirer
{
    IObjetoConhecimento obj;
	
	public Enquirer()
	{
	}
	
	
	@Override
	public void connect(IResponder responder)
	{
        IBaseConhecimento bc = new BaseConhecimento();
		int i = 0, j = 0;
		boolean flag = false, animalEsperado = false;
		
		/* Declarando vetor de strings animais e carregando o nome de todos animais */
		String[] animais;
		animais = bc.listaNomes();
		/* Inicializando a string que contera a resposta */
		String resposta = null;
		
		/* Declarando lista de strings para perguntas feitas e suas respectivas respostas */
		ArrayList<String> perguntasFeitas = new ArrayList<String>();
		ArrayList<String> respostasDadas = new ArrayList<String>();
		
		/* Pega as informacoes de um animal e salva em obj */
		obj = bc.recuperaObjeto(animais[i]);
		IDeclaracao decl = obj.primeira();
		
		/* Repeticao ate encontrar animal esperado */
        while (!animalEsperado && i < animais.length) {
			String pergunta = decl.getPropriedade();
			String respostaEsperada = decl.getValor();
			
			/* Verificando se a pergunta ja foi feita */
			for (j = 0, flag = false; j < perguntasFeitas.size() && !flag; j++)
				if (perguntasFeitas.get(j).equalsIgnoreCase(pergunta)) {
					flag = true;
					resposta = respostasDadas.get(j);
				}
						
			/* Pergunta-se, caso ainda nao tenha sido perguntada essa pergunta e adiciona-se estas perguntas na lista caso contrario */
			if (!flag) {
				resposta = responder.ask(pergunta);
				perguntasFeitas.add(pergunta);
				respostasDadas.add(resposta);
			}
			/* Verificando a resposta */
			if (resposta.equalsIgnoreCase(respostaEsperada)) {
				decl = obj.proxima();
				/* Caso nao haja mais perguntas e todas estao corretas, o animal foi encontrado */
				if (decl == null)
					animalEsperado = true;
			}
			
			/* Caso esteja errada a resposta vai pro proximo animal */
			else {
				i++;
				/* Pega as informacoes de um animal e salva em obj */
				obj = bc.recuperaObjeto(animais[i]);
				decl = obj.primeira();
			}
		}
		
		boolean acertei = responder.finalAnswer(animais[i]);
		
		if (acertei)
			System.out.println("Oba! Acertei!");
		else
			System.out.println("fuem! fuem! fuem!");
	}
}