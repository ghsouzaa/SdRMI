import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
 
public class ChatServer extends UnicastRemoteObject implements ChatServerInt{
	
	private Vector usuarios = new Vector();	
	public ChatServer() throws RemoteException{}
		
	public boolean login(ChatClientInt client) throws RemoteException{	
		System.out.println(client.getName() + " conectou....");	
		client.tell("Conectado com sucesso.");
		publish(client.getName()+ " entrou na sala.");
		usuarios.add(client);
		return true;		
	}
	
	public void publish(String mensagem) throws RemoteException{
	    System.out.println(mensagem);
	    
		for(int i = 0; i < usuarios.size(); i++){
		    try{
		    	ChatClientInt tmp = (ChatClientInt)usuarios.get(i);
		    	tmp.tell(mensagem);
		    }catch(Exception e){
		    	
		    }
		}
	}
 
	public Vector getConnected() throws RemoteException{
		return usuarios;
	}
}