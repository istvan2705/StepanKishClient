package utills;

import com.google.gson.Gson;
import model.Email;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataProvider {

    public static List<Email> readEmailsFromJsonFile(String path) {
        List<Email> email = Collections.emptyList();
        try {
            byte[] mapData = Files.readAllBytes(Paths.get(path));
            ObjectMapper objectMapper = new ObjectMapper();
            email = objectMapper.readValue(mapData, objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, Email.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
      return email;
    }

    public static void writeEmailsToJsonFile(List<Email> emailsList, File file) {
        List<Email> emails = new ArrayList<Email>(emailsList);
        String json = new Gson().toJson(emails);
        try (PrintStream out = new PrintStream(new FileOutputStream(file))) {
            out.print(json);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}





