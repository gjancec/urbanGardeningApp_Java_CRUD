package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Klasa za pokretanje aplikacije
 */
public class HelloApplication extends Application {


    private static Stage mainStage;
    @Override
    public void start(Stage stage) throws IOException {
        mainStage=stage;

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("loginEkran-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        String css=this.getClass().getResource("/styles.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("Hello!");
        stage.setScene(scene);

        stage.show();



    }


    public static void main(String[] args) {
        launch();
    }

    public static Stage getMainStage(){
        return mainStage;
    }
}