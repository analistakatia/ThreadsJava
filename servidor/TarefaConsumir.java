package br.com.alura.servidor;

import java.util.concurrent.BlockingQueue;

public class TarefaConsumir implements Runnable {

	private BlockingQueue<String> filaComandos;
	
	public TarefaConsumir(BlockingQueue filaComandos) {
		this.filaComandos = filaComandos;
	}
	
	@Override
	public void run() {
		
		try {
			String comando = null;
			
			while((comando = filaComandos.take()) != null) {
				System.out.println("Consumindo comando C3");
				Thread.sleep(5000);
			}
		}catch (InterruptedException e) {
			throw new RuntimeException(e);		
		}
	}
}
