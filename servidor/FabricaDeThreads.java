package br.com.alura.servidor;

import java.util.concurrent.ThreadFactory;

//Função para 'customizar' as Threads do ThreadPool
public class FabricaDeThreads implements ThreadFactory {

	private static int numero = 1;
	
	@Override
	public Thread newThread(Runnable r) {
		Thread thread = new Thread(r, "Thread Servidor Tarefas "+numero);
		numero++;
		
		//Tratando as exceções
		thread.setUncaughtExceptionHandler(new TratadorDeExcecao());
		
		return thread;
	}

}
