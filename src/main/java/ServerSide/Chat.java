/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ServerSide;

import ClientSide.ChatClient;
import ClientSide.Client;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

  
/**
 *
 * @author DELL
 */
public class Chat extends UnicastRemoteObject implements Chattable {
ChatClient cl;
private Vector<Chatter> L;

    public Chat() throws RemoteException{
        super();
        L = new Vector<Chatter>(10, 1);
        
    }
    public static void main(String[] args) {
        try{
        Registry registry = LocateRegistry.createRegistry(1099);
        Chattable Chat = new Chat();
        registry.rebind("Chat", Chat);
    
     
        
        }catch(Exception e){
            System.out.println("main"+e);
        }
        
        
        
        
    }
  
    @Override
    public void join(String[] details) throws RemoteException {
   try{
			ChatClient nextClient = ( ChatClient )Naming.lookup("rmi://" + details[1] + "/" + details[2]);
			
			L.addElement(new Chatter(details[0], nextClient));
			
			
			
			sendToAll("[Server] : [ "+  details[0] + " has joined the chat!]\n");
			
			updateUserList();		
		}
		catch(Exception e){
			e.printStackTrace();
		}

    }

   
    @Override
	public void leaveChat(String userName) throws RemoteException{
		
		for(Chatter c : L){
			if(c.getName().equals(userName)){
				System.out.println(  userName + " left the chat ");
				
				L.remove(c);
				break;
			}
		}		
		if(!L.isEmpty()){
			updateUserList();
		}			
	}
    public void sendToAll(String newMessage){	
		for(Chatter c : L){
			try {
				c.getClient().messageFromServer(newMessage);
			} 
			catch (RemoteException e) {
				e.printStackTrace();
			}
		}	
	}
    
    private void updateUserList() {
		String[] currentUsers = getUserList();	
		for(Chatter c : L){
			try {
				c.getClient().updateUserList(currentUsers);
			} 
			catch (RemoteException e) {
				e.printStackTrace();
			}
		}	
	}
    private String[] getUserList(){
		// generate an array of current users
		String[] allUsers = new String[L.size()];
		for(int i = 0; i< allUsers.length; i++){
			allUsers[i] = L.elementAt(i).getName();
		}
		return allUsers;
	}

    @Override
   public void updateChat(String name, String nextPost) throws RemoteException {
		String message =  name + " : " + nextPost + "\n";
		sendToAll(message);
	}

  	@Override
	public void sendPM(int[] privateGroup, String privateMessage) throws RemoteException{
		Chatter pc;
		for(int i : privateGroup){
			pc= L.elementAt(i);
			pc.getClient().messageFromServer(privateMessage);
		}
	}	
}
