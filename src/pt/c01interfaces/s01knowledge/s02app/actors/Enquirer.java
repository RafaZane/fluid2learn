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
		int i=0, k=0;
        IBaseConhecimento bc = new BaseConhecimento();
        /* Contem o nome de todos os animais */
        String[] animais;
        /* Lista do tipo string com todas as perguntas feitas */
        ArrayList<String> perguntasFeitas = new ArrayList<String>();
        /* Lista do tipo string com as respostas para essas perguntas */
        ArrayList<String> respostasDadas = new ArrayList<String>();
        
    	boolean animalEsperado = false;
    	
    	/* Pega o nome de tods os animais */
        animais = bc.listaNomes();
        
        /* Emquanto não encontrar o animal esperado */
        while(!animalEsperado) {
        	
        	/* Pega as informacoes de um animal e salva em obj */
        	obj = bc.recuperaObjeto(animais[i]);
        
        	IDeclaracao decl = obj.primeira();
		
        	animalEsperado = true;
        	
        	/* Enquanto nao encontrar o animal esperado e ainda houver declaracoes */
        	while (decl != null && animalEsperado) {
        		k=0;
        		String pergunta = decl.getPropriedade();
        		/* Se ja foi feita alguma pergunta */
        		if(perguntasFeitas.size() != 0)
        			/* Percorre as perguntas feitas e para quando for igual a pergunta atual ou quando percorrer todas as perguntas ja feitas */
        			for(k=0; k < perguntasFeitas.size() && !(perguntasFeitas.get(k).equalsIgnoreCase(pergunta)); k++);
        		
        		/* Resposta esperada para o animal em questao */
    			String respostaEsperada = decl.getValor();
    			
    			/* Resposta dada para a pergunta atual */
    			String resposta;
    			
    			/* Se foram percorridas todas as perguntas */
        		if(k+1 > perguntasFeitas.size()) {
        			/* Adiciona a pergunta atual as perguntas feitas */
        			perguntasFeitas.add(pergunta);
        			
        			/* Recebe a perguntar do responder */
    				resposta = responder.ask(pergunta);
    				
        			/* Salva a resposta atual a lista de respostas */
        			respostasDadas.add(resposta);
        			
        		} else {
        			/* Se encontrou a pergunta nas perguntas feitas, faz a resposta atual ser igual a resposta dada para essa pergunta anteriormente */
        			resposta = respostasDadas.get(k);
        		}
        		
        		if (resposta.equalsIgnoreCase(respostaEsperada))
        			decl = obj.proxima();
        		else
        			animalEsperado = false;
        	}
        	
        		i++;
        }
		
		boolean acertei = responder.finalAnswer(animais[i-1]);
		
		if (acertei)
			System.out.println("Oba! Acertei!");
		else
			System.out.println("fuem! fuem! fuem!");

	}

}
