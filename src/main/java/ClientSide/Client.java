/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClientSide;

import ServerSide.Chattable;
import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author DELL
 */
public class Client  extends UnicastRemoteObject implements ChatClient,Runnable{
private String name;
private ClientRMIGUI ChatGUI;
private Chattable Chat;
private String hostName = "localhost";
	private String serviceName = "GroupChatService";
	private String clientServiceName;
	protected boolean connectionProblem = false;

    public Client() throws RemoteException {
        super();
    }

    public Client(String name, ClientRMIGUI ChatGUI) throws RemoteException{
        this.name = name;
        this.ChatGUI = ChatGUI;
        this.clientServiceName = "ClientListenService_" + name;
    }

    public String getName() {
        return name;
    }

    public Chattable getChat() {
        return Chat;
    }

  

  
    
   @Override
    public void run(){
try{
Registry registry = LocateRegistry.getRegistry(1099);
 Chat = (Chattable)registry.lookup("Chat");
 

                                       String[] details = {name, hostName, clientServiceName};	

		try {
			Naming.rebind("rmi://" + hostName + "/" + clientServiceName, this);
				
		} 
		catch (ConnectException  e) {
			
			connectionProblem = true;
			e.printStackTrace();
		}
		catch(Exception me){
			connectionProblem = true;
			me.printStackTrace();
		}
			        Chat.join(details);

		System.out.println("Client Listen RMI Server is running...\n");

  
}catch(Exception e){
    System.out.println("run"+e);
}


}

    @Override
    public void messageFromServer(String message) throws RemoteException {
       
        System.out.println( message );
		ChatGUI.textArea.append( message );
		//make the gui display the last appended text, ie scroll to bottom
		ChatGUI.textArea.setCaretPosition(ChatGUI.textArea.getDocument().getLength());
    }
@Override
	public void updateUserList(String[] currentUsers) throws RemoteException {

		if(currentUsers.length < 2){
			ChatGUI.privateMsgButton.setEnabled(false);
		}
		ChatGUI.userPanel.remove(ChatGUI.clientPanel);
		ChatGUI.setClientPanel(currentUsers);
		ChatGUI.clientPanel.repaint();
		ChatGUI.clientPanel.revalidate();
	}


}
