package com.example.demo;

import iznimke.BiljkaVacPostojiException;
import iznimke.KriviFormatException;
import iznimke.NazivNijeUnesen;
import iznimke.PogresanNazivException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;


import niti.ReadWrite;
import org.slf4j.LoggerFactory;
import projekt.entitet.Cvijece;
import projekt.entitet.CvijeceBuilder;
import projekt.entitet.Promjene;
import projekt.entitet.Vrsta;

import java.io.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static baza.BazaPodataka.*;

import static java.lang.Long.valueOf;

/**
 * Glavna klasa ekrana Cvijetnjak
 */
public class UnosCvijecaController{


    private static final org.slf4j.Logger loggerPogreske = LoggerFactory.getLogger(UnosCvijecaController.class);
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();

    @FXML
    private TextField nazivTextField;
    @FXML
    private TextField bojaCvijetaTextField;
    @FXML
    private TextField mjesecSjetveTextField;

    @FXML
    private TextField mjeseciCvatnjeTextField;
    @FXML
    private TextField mjestoTextField;

    @FXML
    private TableView cvijeceTableView;

    @FXML
    private TableColumn<Cvijece, String> nazivTableColumn;
    @FXML
    private TableColumn<Cvijece, String> bojaCvijetaTableColumn;

    @FXML
    private TableColumn<Cvijece, String> mjesecSjetveTableColumn;

    @FXML
    private TableColumn<Cvijece, String> mjeseciCvatnjeTableColumn;

    @FXML
    private TableColumn<Cvijece, String> vrstaTableColumn;
    @FXML
    private TableColumn<Cvijece, String> mjestoTableColumn;




    public static List<Cvijece> listaCvijeca;
    public static List<Cvijece> getListaCvijeca() {
        return listaCvijeca;
    }

    public ObservableList<Cvijece> oListaCvijeca;
    /* Creating a combobox with default constructor */
    @FXML
    private ComboBox<String> vrstaComboBox = new ComboBox<>();

    String vrst = "Jednogodišnje";
    int index = -1;
    Cvijece onMouse;


    @FXML
    void getSelected(MouseEvent event) {
        index = cvijeceTableView.getSelectionModel().getSelectedIndex();
        if (index <= -1) {

            return;
        }
        System.out.println(index);
        nazivTextField.setText(nazivTableColumn.getCellData(index).toString()); //@FXML private TableColumn<users, Integer> col_id;

        bojaCvijetaTextField.setText(bojaCvijetaTableColumn.getCellData(index).toString());

        mjesecSjetveTextField.setText(mjesecSjetveTableColumn.getCellData(index).toString());
        mjeseciCvatnjeTextField.setText(mjeseciCvatnjeTableColumn.getCellData(index).toString());
        vrstaComboBox.setValue(vrstaTableColumn.getCellData(index).toString());
        //vrstaComboBox.getSelectionModel().clearSelection();
        mjestoTextField.setText(mjestoTableColumn.getCellData(index));

        String naziv = nazivTextField.getText();
        String boja = bojaCvijetaTextField.getText();
        String mjesecSjetve = mjesecSjetveTextField.getText();
        String mjeseciCvatnje = mjeseciCvatnjeTextField.getText();
        String vrstaString = vrst;
        String mjesto = mjestoTextField.getText();

        onMouse = new CvijeceBuilder().setNaziv(naziv).setBojaCvijeta(boja).setMjesecSjetve(mjesecSjetve).setMjeseciCvatnje(mjeseciCvatnje).setVrsta(Vrsta.valueOf(vrstaString)).setMjesto(mjesto).createCvijece();


    }

    StringBuilder errorMessages;
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
    public void showUnosCvijecaScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("unosCvijeca_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 950, 550);
        HelloApplication.getMainStage().setTitle("Cvijeće");
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
    public void showPovrtnjakScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("povrtnjak-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 950, 550);
        HelloApplication.getMainStage().setTitle("Povrće");
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();
    }


    ArrayList<Promjene> pr =new ArrayList<>() ;



    public void initialize() throws SQLException, IOException {

        listaCvijeca = getAllCvijeceFromDatabase();
        oListaCvijeca = FXCollections.observableArrayList(listaCvijeca);

        ObservableList<String> vrste = FXCollections.observableArrayList();
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
        vrstaComboBox.setItems(vrste);


        nazivTableColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getNaziv());
        });

        bojaCvijetaTableColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getBojaCvijeta());
        });

        mjesecSjetveTableColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getMjesecSjetve());
        });

        mjeseciCvatnjeTableColumn.setCellValueFactory(cellData -> {

            return new SimpleStringProperty(cellData.getValue().getMjeseciCvatnje());
        });
        vrstaTableColumn.setCellValueFactory(cellData -> {

            return new SimpleStringProperty(cellData.getValue().getVrsta().toString());
        });
        mjestoTableColumn.setCellValueFactory(cellData -> {

            return new SimpleStringProperty(cellData.getValue().getMjesto());
        });


        //cvijeceTableView.setItems(FXCollections.observableList(listaCvijeca));
        cvijeceTableView.setItems(oListaCvijeca);



    }

    /**
     * Metoda za pretragu Cvijeća
     */
    public void pretraziCijece() {

            String naziv = nazivTextField.getText();
            String boja = bojaCvijetaTextField.getText();
            String mjesecSjetve = mjesecSjetveTextField.getText();
            String mjeseciCvatnje = mjeseciCvatnjeTextField.getText();
            String vrsta = vrstaComboBox.getValue();
            String mjesto = mjestoTextField.getText();


            List<Cvijece> filtriraniCvijetNaziv = listaCvijeca.stream()
                    .filter(s -> s.getNaziv().contains(naziv) && s.getBojaCvijeta().contains(boja)
                            && s.getMjesecSjetve().contains(mjesecSjetve)
                            && s.getMjeseciCvatnje().contains(mjeseciCvatnje)
                            && s.getMjesto().contains(mjesto))
                    .toList();
        cvijeceTableView.setItems(FXCollections.observableList(filtriraniCvijetNaziv));

        listaCvijeca.stream()
                .sorted(Comparator.comparing(Cvijece::getNaziv).thenComparing(Cvijece::getBojaCvijeta))
                .forEach(System.out::print);

            /*List<Cvijece> filtriraniCvijetVrsta = listaCvijeca.stream()
                    .filter(s -> s.getVrsta().toString().contains(vrsta))
                    .toList();
            cvijeceTableView.setItems(FXCollections.observableList(filtriraniCvijetVrsta));*/


    }

    /**
     * Metoda za unos novog cvijeta
     * @throws IOException
     * @throws SQLException
     */
    public void spremiCvijet() throws IOException, SQLException {
        Promjene promjene = new Promjene();
        ReadWrite obj = new ReadWrite();
        errorMessages = new StringBuilder();

        String naziv = null;


        try {
            naziv = nazivTextField.getText();
            pogresanNaziv(naziv);
            duploUnesenCvijet(naziv);
            prazanNaziv(naziv);
        } catch (PogresanNazivException e) {
            errorMessages.append("Naziv biljke sadrži nedozvoljene znakove!");
            loggerPogreske.info(e.getMessage());
        } catch (BiljkaVacPostojiException e) {
            errorMessages.append("vec postoji taj cvijet, molimo ponovite unos!");
            loggerPogreske.info(e.getMessage());

        } catch (NazivNijeUnesen e) {
            errorMessages.append("Naziv biljke prazan!");
            loggerPogreske.info(e.getMessage());
        }
        //String boja = bojaCvijetaTextField.getText();
        String boja = null;
        try {
            boja = bojaCvijetaTextField.getText();
            isNumeric(boja);

        } catch (KriviFormatException e) {
            errorMessages.append("Unesen je broj!");
            loggerPogreske.info(e.getMessage());
        }

        String vrstaString=vrst;
       try {
            vrstaString = vrst;
            prazanNaziv(vrstaString);
        }
        catch(NazivNijeUnesen e) {
                errorMessages.append("Vrsta biljke prazan!");
                loggerPogreske.info(e.getMessage());

            }


        if (boja.isEmpty()) {
            errorMessages.append("Boja should not be empty!\n");
        }

        String mjesecSjetve = mjesecSjetveTextField.getText();

        if (mjesecSjetve.isEmpty()) {
            errorMessages.append("Mjesec sjetve not be empty!\n");
        }
        String mjeseciCvatnje = mjeseciCvatnjeTextField.getText();

        if (mjeseciCvatnje.isEmpty()) {
            errorMessages.append("Mjeseci cvatnje should not be empty!\n");
        }



        String mjesto = mjestoTextField.getText();

        if (mjesto.isEmpty()) {
            errorMessages.append("Mjesto sadnje should not be empty!\n");
        }


        if (errorMessages.isEmpty()) {

            Long idd = getLastId();
            Long id = getLastId() + 1;

            Cvijece newCvijece = new CvijeceBuilder().setId(id).setNaziv(naziv).setBojaCvijeta(boja).setMjesecSjetve(mjesecSjetve).setMjeseciCvatnje(mjeseciCvatnje).setVrsta(Vrsta.valueOf(vrstaString)).setMjesto(mjesto).createCvijece();


            try {

                insertNewCvijetToDatabase(newCvijece);
                clear();
                showData();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            promjene.setDatum(LocalDateTime.now());
            promjene.setClassName(UnosCvijecaController.class.getSimpleName());
            promjene.setNazivBiljke(newCvijece.getNaziv() + " je dodan.");


            pr.add(promjene);

            System.out.println("metoda unos " + pr);
            obj.WriteFile(pr);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Save action succeded!");
            alert.setHeaderText("Cvijet data saved!");
            alert.setContentText("Cvijet " + naziv + " saved to the database!");

            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Save action failed!");
            alert.setHeaderText("Cvijet data not saved!");
            alert.setContentText(errorMessages.toString());

            alert.showAndWait();
        }

    }

    /**
     * Metoda koja provjerava da li je uneseni podatak broj. Ako je broj baca unchecked exception
     * @param boja
     */
    public void isNumeric(String boja) {
        String regex = "[0-9]+";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(boja);
        if (m.matches()==true) {
            System.out.println("greška");
            throw new KriviFormatException("Unesen je broj");
        }
    }


    /**
     * Metoda provjerava da li je naziv unesen. Ukoliko naziv nije unesen baca unchecked exception
     * @param naziv
     */
    private void prazanNaziv(String naziv) {
        if (naziv.isEmpty()){
            throw new NazivNijeUnesen("Naziv nije unesen");
        }
    }


    /**
     * @param naziv
     * @throws PogresanNazivException baca checked exception kad je unesen nedozvoljeni znak
     */
    private void pogresanNaziv(String naziv) throws PogresanNazivException {

        Pattern p = Pattern.compile("\\p{L}+", Pattern.UNICODE_CHARACTER_CLASS);
        Matcher m = p.matcher(naziv);
        boolean nadjeno = m.find();
        if (!nadjeno){
            System.out.println("Unjeli ste nedozvoljeni znak");
            throw new PogresanNazivException("Naziv sadrži nedozvoljeni znak");
        }

    }

    /**
     * Metoda za provjeru da li uneseni podatak već postoji
     * @param naziv
     * @throws BiljkaVacPostojiException - custom checked exception
     * @throws SQLException
     * @throws IOException
     */
    private void duploUnesenCvijet(String naziv) throws BiljkaVacPostojiException, SQLException, IOException {
        listaCvijeca = getAllCvijeceFromDatabase();
        oListaCvijeca = FXCollections.observableArrayList(listaCvijeca);
        boolean istina= oListaCvijeca.stream().anyMatch(o -> o.getNaziv().equals(naziv));
        if (istina) {
            System.out.println("Cvijet pronađen");
            throw new BiljkaVacPostojiException("Cvijet "+naziv+ " već postoji");
        }

    }


    /**
     * Metoda koja brise odabrani cvijet
     * @throws IOException
     * @throws SQLException
     */
    public void obrisiCvijet() throws IOException, SQLException {
        Promjene promjene1 = new Promjene();
        ReadWrite obj1 = new ReadWrite();
        String naziv = nazivTextField.getText();
        String boja = bojaCvijetaTextField.getText();
        String mjesecSjetve = mjesecSjetveTextField.getText();
        String mjeseciCvatnje = mjeseciCvatnjeTextField.getText();
        String vrstaString = vrst;
        String mjesto = mjestoTextField.getText();


        Alert alertDelete = new Alert(Alert.AlertType.CONFIRMATION);
        alertDelete.setTitle("Delete!");
        alertDelete.setHeaderText("Delete Cvijeće!");
        alertDelete.setContentText("Da li želite obrisati cvijeće " + naziv + "?");


        Optional<ButtonType> result = alertDelete.showAndWait();
        if (result.get() == ButtonType.OK) {

            Cvijece cvijetToDelete = new CvijeceBuilder().setNaziv(naziv).setBojaCvijeta(boja).setMjesecSjetve(mjesecSjetve).setMjeseciCvatnje(mjeseciCvatnje).setVrsta(Vrsta.valueOf(vrstaString)).setMjesto(mjesto).createCvijece();

            deleteCvijet(cvijetToDelete);

            promjene1.setDatum(LocalDateTime.now());
            promjene1.setClassName(UnosCvijecaController.class.getSimpleName());
            promjene1.setNazivBiljke(cvijetToDelete.getNaziv() + " je obrisan.");

            pr.add(promjene1);
            obj1.WriteFile(pr);
            showData();

            clear();
        }
    }

    /**
     * Metoda koja mijenja podatke o cvijetu
     * @throws IOException
     * @throws SQLException
     */
    public void updateCvijet() throws IOException, SQLException {
        Promjene promjene2 = new Promjene();
        ReadWrite obj2 = new ReadWrite();
        String naziv = nazivTextField.getText();
        String boja = bojaCvijetaTextField.getText();
        String mjesecSjetve = mjesecSjetveTextField.getText();
        String mjeseciCvatnje = mjeseciCvatnjeTextField.getText();
        String vrstaString = vrst;
        String mjesto = mjestoTextField.getText();

        Cvijece cvijetToUpdate = new CvijeceBuilder().setNaziv(naziv).setBojaCvijeta(boja).setMjesecSjetve(mjesecSjetve).setMjeseciCvatnje(mjeseciCvatnje).setVrsta(Vrsta.valueOf(vrstaString)).setMjesto(mjesto).createCvijece();

        updateCvijece(cvijetToUpdate);

        promjene2.setDatum(LocalDateTime.now());
        promjene2.setClassName(UnosCvijecaController.class.getSimpleName());


        if(!onMouse.getBojaCvijeta().equals(cvijetToUpdate.getBojaCvijeta())) {
            promjene2.setNazivBiljke(cvijetToUpdate.getNaziv()+" Boja promijenjena iz "+onMouse.getBojaCvijeta() + " U " + cvijetToUpdate.getBojaCvijeta());
            pr.add(promjene2);
            obj2.WriteFile(pr);
        }
        if(!onMouse.getMjesecSjetve().equals(cvijetToUpdate.getMjesecSjetve())) {
            promjene2.setNazivBiljke(cvijetToUpdate.getNaziv()+" Izmjenjen MJESEC SJETVE cvijeta IZ "+
                    onMouse.getMjesecSjetve() + " U " + cvijetToUpdate.getMjesecSjetve());
            pr.add(promjene2);
            obj2.WriteFile(pr);

        }
        if(!onMouse.getMjeseciCvatnje().equals(cvijetToUpdate.getMjeseciCvatnje())) {
            promjene2.setNazivBiljke(cvijetToUpdate.getNaziv()+" Izmjenjen MJESECI CVATNJE cvijeta IZ "
                    +onMouse.getMjeseciCvatnje() + " U " + cvijetToUpdate.getMjeseciCvatnje());

            pr.add(promjene2);
            obj2.WriteFile(pr);

        }
        if(!onMouse.getVrsta().equals(cvijetToUpdate.getVrsta())) {
            promjene2.setNazivBiljke(cvijetToUpdate.getNaziv()+" Izmjenjena VRSTA cvijeta IZ  " +
                    onMouse.getVrsta().toString() + " U " + cvijetToUpdate.getVrsta().toString());
            pr.add(promjene2);
            obj2.WriteFile(pr);
        }
        if(!onMouse.getMjesto().equals(cvijetToUpdate.getMjesto())) {
            promjene2.setNazivBiljke(cvijetToUpdate.getNaziv()+" Izmjenjeno MJESTO SADNJE cvijeta IZ " +
                    onMouse.getMjesto() + " U " + cvijetToUpdate.getMjesto());
            pr.add(promjene2);
            obj2.WriteFile(pr);
        }


        showData();
        clear();
    }

    public void clear() {
        nazivTextField.setText("");
        bojaCvijetaTextField.setText("");
        mjesecSjetveTextField.setText("");
        mjeseciCvatnjeTextField.setText("");
        vrstaComboBox.getSelectionModel().clearSelection();
        mjestoTextField.setText("");
    }


    public void showData() throws SQLException, IOException {

        listaCvijeca = getAllCvijeceFromDatabase();

        ObservableList<String> vrste = FXCollections.observableArrayList();
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
        vrstaComboBox.setItems(vrste);


        nazivTableColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getNaziv());
        });

        bojaCvijetaTableColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getBojaCvijeta());
        });

        mjesecSjetveTableColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getMjesecSjetve());
        });

        mjeseciCvatnjeTableColumn.setCellValueFactory(cellData -> {

            return new SimpleStringProperty(cellData.getValue().getMjeseciCvatnje());
        });
        vrstaTableColumn.setCellValueFactory(cellData -> {

            return new SimpleStringProperty(cellData.getValue().getVrsta().toString());
        });
        mjestoTableColumn.setCellValueFactory(cellData -> {

            return new SimpleStringProperty(cellData.getValue().getMjesto());
        });


        cvijeceTableView.setItems(FXCollections.observableList(listaCvijeca));


    }




}
