/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerSide;

/**
 *
 * @author kunz
 */
import ClientSide.ChatClient;

public class Chatter {
    	public String name;
	public ChatClient client;
	
	//constructor
	public Chatter(String name, ChatClient client){
		this.name = name;
		this.client = client;
	}

	
	//getters and setters
	public String getName(){
		return name;
	}
	public ChatClient getClient(){
		return client;
	}
}
