package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Klasa Login ekrana
 */
public class LoginEkranController {


    @FXML

    private TextField korisnickoImeTextField;

    @FXML

    private PasswordField lozinkaPasswordField;
    Encryptor encryptor=new Encryptor();

    //Mapa sadrzi korisnicko i Lozinku
    HashMap<String, String> loginInfo=new HashMap<>();



    @FXML
    void createAccount(ActionEvent event) throws IOException, NoSuchAlgorithmException {
        writeToFile();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setContentText("Uspješno ste registrirani!");

        alert.showAndWait();
    }

    /**
     * Zapisuje korisnicko ime i kriptiranu lozinku u txt file
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    private void writeToFile() throws IOException, NoSuchAlgorithmException {
        String username = korisnickoImeTextField.getText();
        String password = lozinkaPasswordField.getText();
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File("dat/LoginPodaci.txt"), true));
        writer.write(username + "," + encryptor.encryptString(password) + "\n");
        writer.close();
    }

    @FXML
    void loginHandler(ActionEvent event) throws IOException, NoSuchAlgorithmException {
        String username=korisnickoImeTextField.getText();
        String password=lozinkaPasswordField.getText();
        updateLoginUsernamesAndPasswords();
        String encryptedPassword = loginInfo.get(username);
        if(encryptor.encryptString(password).equals(encryptedPassword)){
            showPocetnaScreen();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setContentText("Krivi podaci");

            alert.showAndWait();


        }
    }

   private void updateLoginUsernamesAndPasswords() throws FileNotFoundException {
       Scanner scanner = new Scanner(new File("dat/LoginPodaci.txt"));
       loginInfo.clear();
       loginInfo = new HashMap<>();
       while (scanner.hasNext()) {
           String[] usernameAndPassword = scanner.nextLine().split(",");
           loginInfo.put(usernameAndPassword[0], usernameAndPassword[1]);
       }
   }


    public void showPocetnaScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("pocetna-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 950, 550);
        HelloApplication.getMainStage().setTitle("Pocetna");
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();
    }


}
