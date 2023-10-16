package server.server;

import java.io.FileReader;
import java.io.FileWriter;

public class Logger implements Repository{
    private String log;
    public static final String LOG_PATH = "src/server/log.txt";

    @Override
    public void saveInLog() {
        try (FileWriter writer = new FileWriter(LOG_PATH, true)){
            writer.write(this.log);
            writer.write("\n");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void readLog() {
        StringBuilder stringBuilder = new StringBuilder();
        try (FileReader reader = new FileReader(LOG_PATH);){
            int c;
            while ((c = reader.read()) != -1){
                stringBuilder.append((char) c);
            }
            stringBuilder.delete(stringBuilder.length()-1, stringBuilder.length());
            this.log = stringBuilder.toString();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void appendLog(String text) {
        this.log += text + "\n";
    }

    public String getLog() {
        return log;
    }
}
