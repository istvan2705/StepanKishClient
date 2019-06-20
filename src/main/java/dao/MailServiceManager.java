package dao;

import model.Email;
import utills.DataProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MailServiceManager {
    private static final String JSON_FILE_PATH = "src\\main\\resources\\email.json";

    public static List<Email> getAllEmails() {
        return DataProvider.readEmailsFromJsonFile(JSON_FILE_PATH);
    }

    public static List<Email> getEmailsBySenderEmailAddress(String emailAddress) {
        List<Email> emailsList = DataProvider.readEmailsFromJsonFile(JSON_FILE_PATH);
        List<Email> filteredEmailList = new ArrayList<Email>();

        for (Email email : emailsList) {
            if (email.getEmailAddress().equals(emailAddress)) {
                filteredEmailList.add(email);
            }
        }
        return filteredEmailList;
    }

    public static List<Email> getEmailsByEmailSubject(String subject) {
        List<Email> emailsList = DataProvider.readEmailsFromJsonFile(JSON_FILE_PATH);
        List<Email> filteredEmailList = new ArrayList<Email>();

        for (Email email : emailsList) {
            if (email.getSubject().equals(subject)) {
                filteredEmailList.add(email);
            }
        }
        return filteredEmailList;
    }

    public static boolean addEmailToMailBox(Email email) {
        List<Email> emailsList = DataProvider.readEmailsFromJsonFile(JSON_FILE_PATH);
        boolean isAdded;
        if (!emailsList.contains(email) && isAllFieldsFilled(email)) {
            emailsList.add(email);
            DataProvider.writeEmailsToJsonFile(emailsList, new File(JSON_FILE_PATH));
            isAdded = true;
        } else {
            isAdded = false;
        }
        return isAdded;
    }

    private static boolean isAllFieldsFilled(Email email) {
        return !email.getBody().isEmpty() && !email.getEmailAddress().isEmpty() && !email.getSubject().isEmpty();
    }


    public static boolean removeEmailFromMailBox(String subject) {
        List<Email> emailsList = DataProvider.readEmailsFromJsonFile(JSON_FILE_PATH);
        boolean isRemoved = false;
        if (!Objects.isNull(getEmailsByEmailSubject(subject))) {
            for (int i = 0; i < emailsList.size(); i++) {
                if (emailsList.get(i).getSubject().equals(subject)) {
                    emailsList.remove(emailsList.get(i));
                    isRemoved = true;
                }
                DataProvider.writeEmailsToJsonFile(emailsList, new File(JSON_FILE_PATH));
            }
        }
        return isRemoved;
    }


}
