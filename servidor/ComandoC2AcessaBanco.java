package br.com.alura.servidor;

import java.io.PrintStream;
import java.util.Random;
import java.util.concurrent.Callable;

public class ComandoC2AcessaBanco implements Callable<String>{
	private PrintStream saida;

	public ComandoC2AcessaBanco(PrintStream saida) {
		this.saida = saida;
	}

	@Override
	public String call() throws Exception{
		System.out.println("Servidor recebeu comando C2 - BD");
		saida.println("Processando comando C2 - BD");
		Thread.sleep(15000);
		
		int numero = new Random().nextInt(100) + 1;//Gerando um número para retornar
		
		System.out.println("Servidor ComandoC2ChamaWSfinalizou o comando C2 - BD");
		
		return Integer.toString(numero);
	}
}
