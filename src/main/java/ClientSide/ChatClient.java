/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ClientSide;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author DELL
 */
public interface ChatClient  extends Remote{
    public void messageFromServer(String message) throws RemoteException;
    public void updateUserList(String[] currentUsers) throws RemoteException;
}
