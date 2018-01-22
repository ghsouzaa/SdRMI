import java.rmi.*;
import java.rmi.server.*;
 
public class StartServer {
	public static void main(String[] args) {
		try 
		{				
			int portaServidor = 1099;
			String ipServidor = "10.10.50.52";
			
			java.rmi.registry.LocateRegistry.createRegistry(portaServidor);
			ChatServerInt chatServer = new ChatServer();
			
			Naming.rebind("rmi://"+ ipServidor +"/myabc", chatServer);
			
			System.out.println("Servidor escutando no IP e Porta: " + ipServidor + ":" + portaServidor);
		}
		catch (Exception e)
		{
			System.out.println("Erro ao tentar iniciar servidor: " + e);
		}
	}
}