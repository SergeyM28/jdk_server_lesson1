package server.server;

import server.client.Client;
import server.client.ClientGUI;

import javax.swing.*;
import java.util.List;

public class Server{

    List<Client> clienList;

    boolean work;

    public List<Client> getClientList() {
        return clienList;
    }

    public boolean connectUser(Client client){
        if (!work){
            return false;
        }
        clienList.add(client);
        return true;
    }

    public void disconnectUser(Client client){
        clienList.remove(client);
        if (client != null){
            client.disconnect();
        }
    }
}
