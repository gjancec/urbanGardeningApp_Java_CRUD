package com.example.demo;

import iznimke.BiljkaVacPostojiException;
import iznimke.NazivNijeUnesen;
import iznimke.PogresanNazivException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import niti.ReadWrite;
import org.slf4j.LoggerFactory;
import projekt.entitet.*;

import java.io.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static baza.BazaPodataka.*;

/**
 * Glavna klasa za kontrolu Povrtnjaka
 */
public class PovrtnjakController {

    private static final org.slf4j.Logger loggerPogreske = LoggerFactory.getLogger(UnosCvijecaController.class);
    @FXML
    private TextArea textArea;
    @FXML
    private TextField nazivTextField;

    @FXML
    private TextField mjesecSjetveTextField;

    @FXML
    private TextField mjeseciBerbeTextField;
    @FXML
    private TextField mjestoTextField;

    @FXML
    private TableView povrceTableView;

    @FXML
    private TableColumn<Povrce, String> nazivTableColumn;


    @FXML
    private TableColumn<Povrce, String> mjesecSjetveTableColumn;

    @FXML
    private TableColumn<Povrce, String> mjeseciBerbeTableColumn;


    @FXML
    private TableColumn<Povrce, String> mjestoTableColumn;



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
    public void showSjetveniKalendarScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("SjetveniKalendar-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 950, 550);
        HelloApplication.getMainStage().setTitle("Sjetveni kalendar");
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();
    }

    public static List<Povrce> listaPovrca;

    public static List<Povrce> getListaPovrca() {
        return listaPovrca;
    }

    public ObservableList<Povrce> oListaPovrca;
    /* Creating a combobox with default constructor */
    StringBuilder errorMessages;

    String vrst = "Jednogodišnje";
    int index = -1;
    Povrce onMouse;
    @FXML
    void getSelected(MouseEvent event) {
        index = povrceTableView.getSelectionModel().getSelectedIndex();
        if (index <= -1) {

            return;
        }
        System.out.println(index);
        nazivTextField.setText(nazivTableColumn.getCellData(index).toString()); //@FXML private TableColumn<users, Integer> col_id;
        mjesecSjetveTextField.setText(mjesecSjetveTableColumn.getCellData(index).toString());
        mjeseciBerbeTextField.setText(mjeseciBerbeTableColumn.getCellData(index).toString());
        mjestoTextField.setText(mjestoTableColumn.getCellData(index));

        String naziv = nazivTextField.getText();
        String mjesecSjetve = mjesecSjetveTextField.getText();
        String mjeseciBerbe = mjeseciBerbeTextField.getText();
        String mjesto = mjestoTextField.getText();

        onMouse = new PovrceBuilder().setNaziv(naziv).setMjesecSjetve(mjesecSjetve).setMjeseciBerbe(mjeseciBerbe).setMjesto(mjesto).createPovrce();


    }



    ArrayList<Promjene> pr =new ArrayList<>() ;

    public void initialize() throws SQLException, IOException {



        listaPovrca = getAllPovrceFromDatabase();
        oListaPovrca = FXCollections.observableArrayList(listaPovrca);

        /*ObservableList<String> vrste = FXCollections.observableArrayList();
        vrste.addAll("Jednogodisnje",
                "Dvogodisnje",
                "Trajnica");


        vrstaComboBox.setEditable(false);
        vrstaComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldvalue, String newvalue) {
                vrst = newvalue;
            }
        });
        vrstaComboBox.setItems(vrste);*/


        nazivTableColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getNaziv());
        });



        mjesecSjetveTableColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getMjesecSjetve());
        });

        mjeseciBerbeTableColumn.setCellValueFactory(cellData -> {

            return new SimpleStringProperty(cellData.getValue().getMjeseciBerbe());
        });
       /* vrstaTableColumn.setCellValueFactory(cellData -> {

            return new SimpleStringProperty(cellData.getValue().getVrsta().toString());
        });*/
        mjestoTableColumn.setCellValueFactory(cellData -> {

            return new SimpleStringProperty(cellData.getValue().getMjesto());
        });


        //cvijeceTableView.setItems(FXCollections.observableList(listaCvijeca));
        povrceTableView.setItems(oListaPovrca);





    }
    public void pretraziPovrce() {

        String naziv = nazivTextField.getText();
        String mjesecSjetve = mjesecSjetveTextField.getText();
        String mjeseciBerbe = mjeseciBerbeTextField.getText();
        String mjesto = mjestoTextField.getText();


        List<Povrce> filtriraniPovrceNaziv = listaPovrca.stream()
                .filter(s -> s.getNaziv().contains(naziv)
                        && s.getMjesecSjetve().contains(mjesecSjetve)
                        && s.getMjeseciBerbe().contains(mjeseciBerbe)
                        && s.getMjesto().contains(mjesto))
                .toList();
        povrceTableView.setItems(FXCollections.observableList(filtriraniPovrceNaziv));

    }
    //UnosCvijecaController obj=new UnosCvijecaController();
    public void spremiPovrce() throws IOException, SQLException {
        Promjene promjene = new Promjene();
        ReadWrite obj = new ReadWrite();
        errorMessages = new StringBuilder();

        String naziv=null;

        try {
            naziv = nazivTextField.getText();
            duploUnesenoPovrce(naziv);
        } catch(BiljkaVacPostojiException e) {
            errorMessages.append("vec postoji to povrće, molimo ponovite unos!");
            loggerPogreske.info(e.getMessage());
        }

        try {
            naziv = nazivTextField.getText();
            pogresanNaziv(naziv);
            prazanNaziv(naziv);
        } catch(PogresanNazivException e) {
            errorMessages.append("Naziv biljke sadrži nedozvoljene znakove!");
            loggerPogreske.info(e.getMessage());
        } catch(NazivNijeUnesen e) {
            errorMessages.append("Naziv biljke prazan!");
            loggerPogreske.info(e.getMessage());
        }



        String mjesecSjetve = mjesecSjetveTextField.getText();

        if (mjesecSjetve.isEmpty()) {
            errorMessages.append("Mjesec sjetve not be empty!\n");
        }
        String mjeseciBerbe = mjeseciBerbeTextField.getText();

        if (mjeseciBerbe.isEmpty()) {
            errorMessages.append("Mjeseci berbe should not be empty!\n");
        }


        String mjesto = mjestoTextField.getText();

        if (mjesto.isEmpty()) {
            errorMessages.append("Mjesto sadnje should not be empty!\n");
        }


        if (errorMessages.isEmpty()) {

            Long idd = getLastId();
            Long id = getLastIdPovrca() + 1;

            Povrce newPovrce = new PovrceBuilder().setId(id).setNaziv(naziv).setMjesecSjetve(mjesecSjetve).setMjeseciBerbe(mjeseciBerbe).setMjesto(mjesto).createPovrce();


            try {

                insertNewPovrceToDatabase(newPovrce);
                clear();
                showData();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            promjene.setDatum(LocalDateTime.now());
            promjene.setClassName(PovrtnjakController.class.getSimpleName());
            promjene.setNazivBiljke(newPovrce.getNaziv()+ " je dodan.");

            pr.add(promjene);

            System.out.println("metoda unos "+pr);


            obj.WriteFile(pr);

            /*Alert alertDelete = new Alert(Alert.AlertType.INFORMATION);
            alertDelete.setTitle("Save action succeded!");
            alertDelete.setHeaderText("Povrce data saved!");
            alertDelete.setContentText("Povrce " + naziv + " saved to the database!");
            alertDelete.showAndWait();*/

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Save action succeded!");
            alert.setHeaderText("Povrce data saved!");
            alert.setContentText("Povrce " + naziv + " saved to the database!");
            alert.showAndWait();




        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Save action failed!");
            alert.setHeaderText("Povrce data not saved!");
            alert.setContentText(errorMessages.toString());

            alert.showAndWait();
        }
    }

    private void prazanNaziv(String naziv) {
        if (naziv.isEmpty()){
            throw new NazivNijeUnesen("Naziv nije unesen");
        }
    }
    private void pogresanNaziv(String naziv) throws PogresanNazivException {
        Pattern p = Pattern.compile("\\p{L}+", Pattern.UNICODE_CHARACTER_CLASS);
        Matcher m = p.matcher(naziv);
        boolean nadjeno = m.find();
        if (!nadjeno){
            System.out.println("Unjeli ste nedozvoljeni znak");
            throw new PogresanNazivException("Naziv sadrži nedozvoljeni znak");
        }

    }

    private void duploUnesenoPovrce(String naziv) throws BiljkaVacPostojiException,SQLException, IOException {
        listaPovrca = getAllPovrceFromDatabase();
        oListaPovrca = FXCollections.observableArrayList(listaPovrca);
        boolean istina= oListaPovrca.stream().anyMatch(o -> o.getNaziv().equals(naziv));
        if (istina) {
            System.out.println("Povrce pronađeno");
            throw new BiljkaVacPostojiException("Povrće "+naziv+ " već postoji");
        }

    }


    public void obrisiPovrce() throws IOException, SQLException {
        Promjene promjene1 = new Promjene();
        ReadWrite obj1 = new ReadWrite();
        errorMessages = new StringBuilder();
        String naziv = nazivTextField.getText();
        String mjesecSjetve = mjesecSjetveTextField.getText();
        String mjeseciBerbe = mjeseciBerbeTextField.getText();
        String mjesto = mjestoTextField.getText();


        Alert alertDelete = new Alert(Alert.AlertType.CONFIRMATION);
        alertDelete.setTitle("Delete!");
        alertDelete.setHeaderText("Delete povrće!");
        alertDelete.setContentText("Da li želite obrisati povrće " + naziv + "?");


        Optional<ButtonType> result = alertDelete.showAndWait();

        if (result.get() == ButtonType.OK) {

            Povrce povrceToDelete = new PovrceBuilder().setNaziv(naziv).setMjesecSjetve(mjesecSjetve).setMjeseciBerbe(mjeseciBerbe).setMjesto(mjesto).createPovrce();

            deletePovrce(povrceToDelete);

            promjene1.setDatum(LocalDateTime.now());
            promjene1.setClassName(PovrtnjakController.class.getSimpleName());
            promjene1.setNazivBiljke(povrceToDelete.getNaziv() + " je obrisan.");

            pr.add(promjene1);

            obj1.WriteFile(pr);
            showData();

            clear();
        }

    }

    public void updatePovrtnjak() throws IOException, SQLException {
        Promjene promjene2 = new Promjene();
        ReadWrite obj2 = new ReadWrite();
        String naziv = nazivTextField.getText();

        String mjesecSjetve = mjesecSjetveTextField.getText();
        String mjeseciBerbe = mjeseciBerbeTextField.getText();

        String mjesto = mjestoTextField.getText();

        Povrce povrceToUpdate = new PovrceBuilder().setNaziv(naziv).setMjesecSjetve(mjesecSjetve).setMjeseciBerbe(mjeseciBerbe).setMjesto(mjesto).createPovrce();

        updatePovrce(povrceToUpdate);

        promjene2.setDatum(LocalDateTime.now());
        promjene2.setClassName(PovrtnjakController.class.getSimpleName());

        if(!onMouse.getMjesecSjetve().equals(povrceToUpdate.getMjesecSjetve())) {
            promjene2.setNazivBiljke(povrceToUpdate.getNaziv()+" Izmjenjen MJESEC SJETVE cvijeta IZ "+
                    onMouse.getMjesecSjetve() + " U " + povrceToUpdate.getMjesecSjetve());
            pr.add(promjene2);
            obj2.WriteFile(pr);

        }
        if(!onMouse.getMjeseciBerbe().equals(povrceToUpdate.getMjeseciBerbe())) {
            promjene2.setNazivBiljke(povrceToUpdate.getNaziv()+" Izmjenjen MJESECI CVATNJE cvijeta IZ "
                    +onMouse.getMjeseciBerbe() + " U " + povrceToUpdate.getMjeseciBerbe());

            pr.add(promjene2);
            obj2.WriteFile(pr);

        }

        if(!onMouse.getMjesto().equals(povrceToUpdate.getMjesto())) {
            promjene2.setNazivBiljke(povrceToUpdate.getNaziv()+" Izmjenjeno MJESTO SADNJE cvijeta IZ " +
                    onMouse.getMjesto() + " U " + povrceToUpdate.getMjesto());
            pr.add(promjene2);
            obj2.WriteFile(pr);

        }


        showData();
        clear();
    }

    public void clear() {
        nazivTextField.setText("");
        mjesecSjetveTextField.setText("");
        mjeseciBerbeTextField.setText("");
        mjestoTextField.setText("");
    }


    public void showData() throws SQLException, IOException {

        listaPovrca = getAllPovrceFromDatabase();


        nazivTableColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getNaziv());
        });



        mjesecSjetveTableColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getMjesecSjetve());
        });

        mjeseciBerbeTableColumn.setCellValueFactory(cellData -> {

            return new SimpleStringProperty(cellData.getValue().getMjeseciBerbe());
        });

        mjestoTableColumn.setCellValueFactory(cellData -> {

            return new SimpleStringProperty(cellData.getValue().getMjesto());
        });


        povrceTableView.setItems(FXCollections.observableList(listaPovrca));


    }



}
