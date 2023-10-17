// Интерфейс репозиторий
// Класс, который реализует интерфейс "Репозиторий"
// Сервер виндов остается
// Появляется еще одитн класс "Сервер" и появляется интерфейс на подобии "ClientView" для графического окна, чтобы к нему могли из класса Сервер обращаться
//

package server.server;

import server.client.Client;
import server.client.ClientGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class ServerWindow extends JFrame implements ServerView{
    public static final int WIDTH = 400;
    public static final int HEIGHT = 300;
    public static final String LOG_PATH = "src/server/log.txt";

    //List<Client> clientGUIList;

    JButton btnStart, btnStop;
    JTextArea logArea;
    boolean work;

    Server server;
    Logger logger;

    public ServerWindow(){
        this.server = new Server(logger, this);
        //clientGUIList = new ArrayList<>();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Chat server");
        setLocationRelativeTo(null);

        createPanel();

        setVisible(true);
    }

    public boolean connectUser(Client client){
        if (!work){
            return false;
        }
        server.connectUser(client);
        return true;
    }

    public String getHistory() {
        logger.readLog();
        return logger.getLog();
    }

    public void disconnectUser(Client client){
        server.disconnectUser(client);
        if (client != null){
            client.disconnect();
        }
    }

    public void sendMessage(String text){
        if (!work){
            return;
        }
        logger.appendLog(text);
        answerAll(text);
        logger.saveInLog();
    }

    private void answerAll(String text){
        for (Client client: server.getClientList()){
            client.serverAnswer(text);
        }
    }

    private void saveInLog(String text){
        logger.appendLog(text);
        logger.saveInLog();
    }

    private String readLog(){
        logger.readLog();
        return logger.getLog();
    }

    private void appendLogArea(String text){
        logArea.append(text + "\n");
    }

    private void createPanel() {
        logArea = new JTextArea();
        add(logArea);
        add(createButtons(), BorderLayout.SOUTH);
    }

    private Component createButtons() {
        JPanel panel = new JPanel(new GridLayout(1, 2));
        btnStart = new JButton("Start");
        btnStop = new JButton("Stop");

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (work){
                    appendLogArea("Сервер уже был запущен");
                } else {
                    work = true;
                    appendLogArea("Сервер запущен!");
                }
            }
        });

        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!work){
                    appendLogArea("Сервер уже был остановлен");
                } else {
                    work = false;
                    for (Client client: server.getClientList()){
                        client.disconnect();
                    }
                    appendLogArea("Сервер остановлен!");
                }
            }
        });

        panel.add(btnStart);
        panel.add(btnStop);
        return panel;
    }
}
