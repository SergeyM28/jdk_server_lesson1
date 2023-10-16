package server.server;

public interface Repository {
    void saveInLog();
    void readLog();
    void appendLog(String text);

}
