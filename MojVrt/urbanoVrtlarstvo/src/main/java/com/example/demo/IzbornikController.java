package com.example.demo;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class IzbornikController {
    public void showPovrtnjakScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("povrtnjak-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 950, 550);
        HelloApplication.getMainStage().setTitle("Povrće");
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();
    }
    public void showPromjeneScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("promjene-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 950, 550);
        HelloApplication.getMainStage().setTitle("Promjene");
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();
    }
    public void showUnosCvijecaScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("unosCvijeca_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 950, 550);
        HelloApplication.getMainStage().setTitle("Cvijeće");
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();
    }
}
