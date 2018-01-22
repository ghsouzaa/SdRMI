import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
 
public class ChatClient  extends UnicastRemoteObject implements ChatClientInt{
	
	private String nome;
	private ChatUI ui;	
	public ChatClient (String nome) throws RemoteException {
		this.nome = nome;
	}
	
	public void tell(String mensagem) throws RemoteException{
		System.out.println(mensagem);
		ui.writeMsg(mensagem);
	}
	public String getName() throws RemoteException{
		return nome;
	}
	
	public void setGUI(ChatUI t){ 
		ui=t ; 
	} 	
}