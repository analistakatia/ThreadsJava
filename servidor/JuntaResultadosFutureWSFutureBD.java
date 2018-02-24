package br.com.alura.servidor;

import java.io.PrintStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class JuntaResultadosFutureWSFutureBD implements Callable<Void> {
	
	private Future<String> futureWS;
	private Future<String> futureBD;
	private PrintStream saidaCliente;

	public JuntaResultadosFutureWSFutureBD(Future<String> futureWS, Future<String> futureBD, PrintStream saidaCliente) {
		this.futureWS = futureWS;
		this.futureBD = futureBD;
		this.saidaCliente = saidaCliente;
	}

	//Gambiarra pra não retornar nada no Callable 
	//Nesse caso, faria mais sentido usar um Runnable
	@Override
	public Void call() {
		
		System.out.println("Aguardando resultados do future WS e BD");
		
		try {
			String numeroMagico = this.futureWS.get(20, TimeUnit.SECONDS);
			String numeroMagico2 = this.futureBD.get(20, TimeUnit.SECONDS);
			
			this.saidaCliente.println("Resultado do comando C2: "+ numeroMagico + ", "+numeroMagico2);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			System.out.println("TimeOut: Cancelando execução do comando C2");
			this.saidaCliente.println("TimeOut na execução do comando C2");
			this.futureWS.cancel(true);
			this.futureBD.cancel(true);
		}
		
		System.out.println("Finalizou JuntaResultadosFutureWSFutureBD");
		
		return null;
	}
}
