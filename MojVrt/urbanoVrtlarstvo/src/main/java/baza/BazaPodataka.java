package baza;

import projekt.entitet.*;


import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Glavna klasa H2 bazu podataka
 */
public class BazaPodataka {

    /**
     * Metoda za uspostavu konekcije na bazu podataka
     * @return konekciju na bazu podataka
     * @throws SQLException
     * @throws IOException
     */
    public static Connection spajanjeNaBazu() throws SQLException, IOException {

        Properties configuration = new Properties();
        configuration.load(new FileReader("dat/database.properties"));

        String databaseURL = configuration.getProperty("databaseURL");
        String databaseUsername = configuration.getProperty("databaseUsername");
        String databasePassword = configuration.getProperty("databasePassword");

        Connection connection = DriverManager
                .getConnection(databaseURL, databaseUsername, databasePassword);
        return connection;
    }

    /*---------------------------------CVIJEĆE-----------------------------------------------*/


    /**
     * Metoda za dohvaćanje svih podataka o cvijeću iz baze
     * @return listu cvijeća
     * @throws SQLException
     * @throws IOException
     */
    public static List<Cvijece> getAllCvijeceFromDatabase() throws SQLException, IOException {

        Connection connection = spajanjeNaBazu();

        List<Cvijece> cvijeceList = new ArrayList<>();

        Statement sqlStatement = connection.createStatement();

        ResultSet cvijeceResultSet = sqlStatement.executeQuery("SELECT * FROM CVIJECE;");

        while(cvijeceResultSet.next()) {
            Cvijece newCvijece = getCvijeceFromResultSet(cvijeceResultSet);
            cvijeceList.add(newCvijece);
        }

        connection.close();

        return cvijeceList;
    }


    private static Cvijece getCvijeceFromResultSet(ResultSet cvijeceResultSet) throws SQLException {

        String naziv = cvijeceResultSet.getString("NAZIV");
        String bojaCvijeta = cvijeceResultSet.getString("BOJA_CVIJETA");
        String mjesecSjetve = cvijeceResultSet.getString("MJESEC_SIJANJA");
        String mjeseciCvatnje = cvijeceResultSet.getString("MJESECI_CVATNJE");
        String vrsta = cvijeceResultSet.getString("VRSTA");
        String mjesto = cvijeceResultSet.getString("MJESTO");

        Vrsta vrstav = Vrsta.valueOf(vrsta);

        return new CvijeceBuilder().setNaziv(naziv).setBojaCvijeta(bojaCvijeta).setMjesecSjetve(mjesecSjetve).setMjeseciCvatnje(mjeseciCvatnje).setVrsta(vrstav).setMjesto(mjesto).createCvijece();
    }

    /**
     * Metoda koja sprema novi cvijet u bazu
     * @param cvijece
     * @throws SQLException
     * @throws IOException
     */
    public static void insertNewCvijetToDatabase(Cvijece cvijece) throws SQLException, IOException {
        Connection connection = spajanjeNaBazu();

        PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO CVIJECE (NAZIV, BOJA_CVIJETA, MJESEC_SIJANJA, MJESECI_CVATNJE,VRSTA, MJESTO) VALUES(?, ?, ?, ?, ?,?)");

        stmt.setString(1, cvijece.getNaziv());
        stmt.setString(2, cvijece.getBojaCvijeta());
        stmt.setString(3, cvijece.getMjesecSjetve());
        stmt.setString(4, cvijece.getMjeseciCvatnje());
        stmt.setString(5, cvijece.getVrsta().toString());
        stmt.setString(6, cvijece.getMjesto());


        stmt.executeUpdate();

        connection.close();
    }

    /**
     * Metoda koja vraća max id iz baze
     * @return maximalan Id
     * @throws SQLException
     * @throws IOException
     */
    public static Long getLastId() throws SQLException, IOException {
        Connection connection = spajanjeNaBazu();
        Long idMax = null;
               

        Statement sqlStatement = connection.createStatement();

        sqlStatement.executeQuery("SELECT MAX (id) FROM CVIJECE;");
        ResultSet idResultSet = sqlStatement.getResultSet();
        while(idResultSet.next()) {
            idMax = idResultSet.getLong(1);

        }

        connection.close();

        return idMax;
    }

    /**
     * Metoda koja briše cvijet iz baze
     * @param cvijetToDelete
     * @throws SQLException
     * @throws IOException
     */
    public static void deleteCvijet(Cvijece cvijetToDelete) throws SQLException, IOException {
        Connection connection = spajanjeNaBazu();

        PreparedStatement deleteCvijet =
                connection.prepareStatement(
                        "DELETE FROM CVIJECE WHERE NAZIV= ?");

        deleteCvijet.setString(1, cvijetToDelete.getNaziv());

        deleteCvijet.executeUpdate();

        connection.close();
    }

    /**
     * Metoda koja mijenja podatke o cvijetu u bazi
     * @param cvijetToUpdate
     * @throws SQLException
     * @throws IOException
     */
    public static void updateCvijece(Cvijece cvijetToUpdate) throws SQLException, IOException {
        Connection connection = spajanjeNaBazu();

        PreparedStatement updateCvije =
                connection.prepareStatement(
                        "UPDATE CVIJECE SET BOJA_CVIJETA=?, MJESEC_SIJANJA=?, MJESECI_CVATNJE=?, VRSTA=?, MJESTO=? WHERE NAZIV = ?");



        updateCvije.setString(1, cvijetToUpdate.getBojaCvijeta());
        updateCvije.setString(2, cvijetToUpdate.getMjesecSjetve());
        updateCvije.setString(3, cvijetToUpdate.getMjeseciCvatnje());
        updateCvije.setString(4, cvijetToUpdate.getVrsta().toString());
        updateCvije.setString(5, cvijetToUpdate.getMjesto());
        updateCvije.setString(6, cvijetToUpdate.getNaziv());

        updateCvije.executeUpdate();

        connection.close();
    }

    /*---------------------------------POVRCE-----------------------------------------------*/



    public static List<Povrce> getAllPovrceFromDatabase() throws SQLException, IOException {

        Connection connection = spajanjeNaBazu();

        List<Povrce> povrceList = new ArrayList<>();

        Statement sqlStatement = connection.createStatement();

        ResultSet povrceResultSet = sqlStatement.executeQuery("SELECT * FROM POVRCE;");

        while(povrceResultSet.next()) {
            Povrce newPovrce = getPovrceFromResultSet(povrceResultSet);
            povrceList.add(newPovrce);
        }

        connection.close();

        return povrceList;
    }

    private static Povrce getPovrceFromResultSet(ResultSet povrceResultSet) throws SQLException {

        String naziv = povrceResultSet.getString("NAZIV");
        String mjesecSjetve = povrceResultSet.getString("MJESEC_SIJANJA");
        String mjeseciBerbe = povrceResultSet.getString("MJESECI_BERBE");
        String mjesto = povrceResultSet.getString("MJESTO");



        return new PovrceBuilder().setNaziv(naziv).setMjesecSjetve(mjesecSjetve).setMjeseciBerbe(mjeseciBerbe).setMjesto(mjesto).createPovrce();
    }

    public static void insertNewPovrceToDatabase(Povrce povrce) throws SQLException, IOException {
        Connection connection = spajanjeNaBazu();

        PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO POVRCE (NAZIV, MJESEC_SIJANJA, MJESECI_BERBE, MJESTO) VALUES(?, ?, ?, ?)");

        stmt.setString(1, povrce.getNaziv());
        stmt.setString(2, povrce.getMjesecSjetve());
        stmt.setString(3, povrce.getMjeseciBerbe());
        stmt.setString(4, povrce.getMjesto());


        stmt.executeUpdate();

        connection.close();
    }

    public static Long getLastIdPovrca() throws SQLException, IOException {
        Connection connection = spajanjeNaBazu();
        Long idMax = null;


        Statement sqlStatement = connection.createStatement();

        sqlStatement.executeQuery("SELECT MAX (id) FROM POVRCE;");
        ResultSet idResultSet = sqlStatement.getResultSet();
        while(idResultSet.next()) {
            idMax = idResultSet.getLong(1);

        }

        connection.close();

        return idMax;
    }

    public static void deletePovrce(Povrce povrceToDelete) throws SQLException, IOException {
        Connection connection = spajanjeNaBazu();

        PreparedStatement deletePovrce =
                connection.prepareStatement(
                        "DELETE FROM POVRCE WHERE NAZIV= ?");

        deletePovrce.setString(1, povrceToDelete.getNaziv());

        deletePovrce.executeUpdate();

        connection.close();
    }
    public static void updatePovrce(Povrce povrceToUpdate) throws SQLException, IOException {
        Connection connection = spajanjeNaBazu();

        PreparedStatement updatePovrc =
                connection.prepareStatement(
                        "UPDATE POVRCE SET MJESEC_SIJANJA=?, MJESECI_BERBE=?, MJESTO=? WHERE NAZIV = ?");




        updatePovrc.setString(1, povrceToUpdate.getMjesecSjetve());
        updatePovrc.setString(2, povrceToUpdate.getMjeseciBerbe());
        updatePovrc.setString(3, povrceToUpdate.getMjesto());
        updatePovrc.setString(4, povrceToUpdate.getNaziv());

        updatePovrc.executeUpdate();

        connection.close();
    }

}