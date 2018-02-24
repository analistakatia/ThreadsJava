package br.com.alura.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorTarefas {
	
	private ServerSocket servidor;
	private ExecutorService threadPool;
	private volatile boolean estaRodando;//usaremos isso em diferentes threads, por isso o 'volatile'
	private BlockingQueue<String> filaComandos;
	
	public ServidorTarefas() throws IOException{
		System.out.println(" --- Iniciando servidor ---");
		this.servidor = new ServerSocket(12345);
		
		//Limitando o número de Threads (nesse caso 4)
		this.threadPool = Executors.newCachedThreadPool(new FabricaDeThreads());		
		//Executors.newCachedThreadPool(); -> Cresce e diminui dinamicamente o numero de Threads
		
		this.estaRodando = true;
		this.filaComandos = new ArrayBlockingQueue<>(2);
		
		iniciarConsumidores();
	}
	
	private void iniciarConsumidores() {
		for(int i = 0;i<2;i++) {
			TarefaConsumir tarefa = new TarefaConsumir(filaComandos);
			this.threadPool.execute(tarefa);
		}
	}
		
	public void rodar() throws IOException{
		while(this.estaRodando) {
			try {
				Socket socket = servidor.accept();
				System.out.println("Aceitando novo cliente na porta "+socket.getPort());
				DistribuirTarefas distribuirTarefas = new DistribuirTarefas(threadPool, filaComandos, socket, this);
				threadPool.execute(distribuirTarefas);//Vai reaproveitar threads
			} catch (SocketException e) {
				
			}
		}
	}

	public void parar() throws IOException{
		estaRodando = false;
		servidor.close();
		threadPool.shutdown();
	}
	
	public static void main(String[] args) throws IOException{
		ServidorTarefas servidor = new ServidorTarefas();
		servidor.rodar();
		servidor.parar();
	}
}