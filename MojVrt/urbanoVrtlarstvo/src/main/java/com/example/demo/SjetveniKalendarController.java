package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import projekt.entitet.Cvijece;
import projekt.entitet.Povrce;
import projekt.entitet.SjetveniKalendarGeneric;
import projekt.entitet.SjetveniKalendarGenericMap;


import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static baza.BazaPodataka.getAllCvijeceFromDatabase;
import static baza.BazaPodataka.getAllPovrceFromDatabase;
import static java.util.stream.Collectors.groupingBy;

/**
 * Klasa ekrana koji prikazuje cvijece i povrće po mjesecima
 */
public class SjetveniKalendarController {

    @FXML
    private TextArea textArea;



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
    public void showPocetnaScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("pocetna-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 950, 550);
        HelloApplication.getMainStage().setTitle("Pocetna");
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();
    }



    public static List<Cvijece> listaCvijeca;
    public static List<Cvijece> getListaCvijeca() {
        return listaCvijeca;
    }
    public static List<Povrce> listaPovrca;
    public static List<Povrce> getListaPovrca() {
        return listaPovrca;
    }




    public void initialize() throws SQLException, IOException {
        listaCvijeca = getAllCvijeceFromDatabase();
        listaPovrca= getAllPovrceFromDatabase();

        SjetveniKalendarGeneric<Cvijece> cvijeceKalendar = new SjetveniKalendarGeneric<>();
        cvijeceKalendar.setList(listaCvijeca);
        //List<Cvijece> cvijeceKalendarList=cvijeceKalendar.getList();
        //cvijeceKalendarList.forEach(System.out::println);

        SjetveniKalendarGeneric<Povrce> povrceKalendar = new SjetveniKalendarGeneric<>();
        povrceKalendar.setList(listaPovrca);



        System.out.println("Povrće: ");

        Map<String, List<Povrce>> resultMapPovrce = listaPovrca.stream()
                .collect(groupingBy(Povrce::getMjesecSjetve));

        Map<String, List<Cvijece>> resultMapCvijece = listaCvijeca.stream()
                .collect(groupingBy(Cvijece::getMjesecSjetve));




        textArea.appendText("POVRĆE:\r\n");
        for( Map.Entry<String, List<Povrce>> entry : resultMapPovrce.entrySet() ){
            System.out.println( entry.getKey() + " = " + entry.getValue());
            textArea.appendText(entry.getKey() + " = " + entry.getValue()+"\r\n");
        }

        textArea.appendText("CVIJEĆE:\r\n");
        for( Map.Entry<String, List<Cvijece>> entry : resultMapCvijece.entrySet() ){
            System.out.println( entry.getKey() + " = " + entry.getValue());
            textArea.appendText(entry.getKey() + " = " + entry.getValue()+"\r\n");
        }



        SjetveniKalendarGenericMap<String, List<Cvijece>> cvijeceKalendarMap = new SjetveniKalendarGenericMap<>();
        cvijeceKalendarMap.setMapa(resultMapCvijece);

        SjetveniKalendarGenericMap<String, List<Povrce>> povrceKalendarMap = new SjetveniKalendarGenericMap<>();
        povrceKalendarMap.setMapa(resultMapPovrce);

    }


}
