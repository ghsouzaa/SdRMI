import javax.swing.*;
import javax.swing.border.*;
 
import java.awt.*;
import java.awt.event.*;
import java.rmi.Naming;
import java.util.*;
 
public class ChatUI{
  private ChatClient client;
  private ChatServerInt server;
  public void doConnect(){
	    if (btnConectar.getText().equals("Conectar")){
	    	if (txtNome.getText().length()<2){JOptionPane.showMessageDialog(frame, "Insira um nome."); return;}
	    	if (txtIp.getText().length()<2){JOptionPane.showMessageDialog(frame, "Insira IP do servidor."); return;}	    	
	    	try{
				client=new ChatClient(txtNome.getText());
	    		client.setGUI(this);
				server=(ChatServerInt)Naming.lookup("rmi://"+txtIp.getText()+"/myabc");
				server.login(client);
				updateUsers(server.getConnected());
			    btnConectar.setText("Desconectar");			    
	    	}catch(Exception e){e.printStackTrace();JOptionPane.showMessageDialog(frame, "Erro ao tentar conectar....");}		  
	      }else{
	    	  	updateUsers(null);
	    	  	btnConectar.setText("Conectar");
	    	  	//Better to implement Logout ....
		}
	  }  
  
  public void sendText(){
    if (btnConectar.getText().equals("Conectar")){
    	JOptionPane.showMessageDialog(frame, "É preciso se conectar antes de enviar uma mensagem."); return;	
    }
      String st=txtAreaMensagem.getText();
      st="["+txtNome.getText()+"] "+st;
      txtAreaMensagem.setText("");
      //Remove if you are going to implement for remote invocation
      try{
    	  	server.publish(st);
  	  	}catch(Exception e){e.printStackTrace();}
  }
 
  public void writeMsg(String st){  txtMensagens.setText(txtMensagens.getText()+"\n"+st);  }
 
  public void updateUsers(Vector v){
      DefaultListModel listModel = new DefaultListModel();
      if(v!=null) for (int i=0;i<v.size();i++){
    	  try{  String tmp=((ChatClientInt)v.get(i)).getName();
    	  		listModel.addElement(tmp);
    	  }catch(Exception e){e.printStackTrace();}
      }
      listaUsuarios.setModel(listModel);
  }
  
  public static void main(String [] args){
	ChatUI c = new ChatUI();
  }  
  
  public ChatUI(){
	    frame = new JFrame("Chat");
	    JPanel main = new JPanel();
	    JPanel top = new JPanel();
	    JPanel cn = new JPanel();
	    JPanel bottom = new JPanel();
	    txtIp = new JTextField();
	    txtAreaMensagem = new JTextField();
	    txtNome = new JTextField();
	    txtMensagens = new JTextArea();
	    btnConectar = new JButton("Conectar");
	    JButton btnEnviar = new JButton("Enviar");
	    listaUsuarios = new JList(); 
	    
	    main.setLayout(new BorderLayout(5,5));         
	    top.setLayout(new GridLayout(1,0,5,5));   
	    cn.setLayout(new BorderLayout(5,5));
	    bottom.setLayout(new BorderLayout(5,5));
	    top.add(new JLabel("Nome: "));top.add(txtNome);    
	    top.add(new JLabel("IP Servidor: "));top.add(txtIp);
	    top.add(btnConectar);
	    cn.add(new JScrollPane(txtMensagens), BorderLayout.CENTER);        
	    cn.add(listaUsuarios, BorderLayout.EAST);    
	    bottom.add(txtAreaMensagem, BorderLayout.CENTER);    
	    bottom.add(btnEnviar, BorderLayout.EAST);
	    main.add(top, BorderLayout.NORTH);
	    main.add(cn, BorderLayout.CENTER);
	    main.add(bottom, BorderLayout.SOUTH);
	    main.setBorder(new EmptyBorder(10, 10, 10, 10) );

	    btnConectar.addActionListener(new ActionListener()
	    {
	      public void actionPerformed(ActionEvent e)
	      { 
	    	  doConnect();   
		  }  
	    });
	    
	    btnEnviar.addActionListener(new ActionListener()
	    {
	      public void actionPerformed(ActionEvent e)
	      { 
	    	  sendText();   
    	  }  
	    });
	    
	    txtAreaMensagem.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent e)
	      { 
	    	  sendText();   
    	  }  
	    });
	    
	    frame.setContentPane(main);
	    frame.setSize(600,600);
	    frame.setVisible(true);  
	  }
	  JTextArea txtMensagens;
	  JTextField txtAreaMensagem, txtIp, txtNome;
	  JButton btnConectar;
	  JList listaUsuarios;
	  JFrame frame;
}