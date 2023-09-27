package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import projekt.entitet.Sensor;
import projekt.entitet.TemperaturaZrakaSensor;
import projekt.entitet.VlaznostTlaSensor;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Klasa početne stranice
 */
public class PocetnaController {
    @FXML
    Button pocetnaButtonCvjetnjak;

    @FXML
    Button pocetnaButtonVertikalanVrt;
    @FXML
    Label labelVlaga = new Label();
    @FXML
    Label labelTemperatura = new Label();


    @FXML
    Button pocetnaButtonPovrtnjak;

    public void showUnosCvijecaScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("unosCvijeca_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 950, 550);
        HelloApplication.getMainStage().setTitle("Cvijeće");
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();
    }

    public void showPovrtnjakScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("povrtnjak-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 950, 550);
        HelloApplication.getMainStage().setTitle("Povrće");
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();
    }

    public void showSjetveniKalendarScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("SjetveniKalendar-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 950, 550);
        HelloApplication.getMainStage().setTitle("Sjetveni kalendar");
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();
    }

    public void showVertikalanVrtScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("vertikalanVrt-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 950, 550);
        HelloApplication.getMainStage().setTitle("Zelena fasada");
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();
    }

    public void initialize() {


        Sensor vlag = new VlaznostTlaSensor(30);
        Sensor temp = new TemperaturaZrakaSensor(22);
        System.out.println(vlag.getReading());
        labelVlaga.setText("Trenutna vlažnost tla: " + vlag.getReading() + " %");
        labelTemperatura.setText("Trenutna temperatura zraka: " + temp.getReading() + " °");
    }
}


