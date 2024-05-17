/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ServerSide;

import ClientSide.Client;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author DELL
 */
public interface Chattable extends Remote {
    public void join(String[] details) throws RemoteException;
   public void updateChat(String userName, String chatMessage)throws RemoteException;
   public void leaveChat(String userName)throws RemoteException;
   public void sendPM(int[] privateGroup, String privateMessage)throws RemoteException;

}
