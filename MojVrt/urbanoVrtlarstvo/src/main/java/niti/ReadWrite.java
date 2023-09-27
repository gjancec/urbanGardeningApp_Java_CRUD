package niti;

import projekt.entitet.Promjene;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Klasa za sikronizaciju niti ReadWriteLock
 */
public class ReadWrite {
    /*Kada jedna od niti piše u file, blokirano je čitanje i pisanje svih drugih niti.
    Čitat iz file-a može više niti u isto vrijeme*/

    private final ReadWriteLock rwl = new ReentrantReadWriteLock();
    private final Lock wl = rwl.writeLock();
    private final Lock rl = rwl.readLock();

    /**
     * Metoda za sinkoronizaciju nit pisanje u file
     * @param pr
     */
    public void WriteFile(ArrayList<Promjene> pr) {

        wl.lock();
        try {
            FileOutputStream fileOut = new FileOutputStream("dat/promjene.dat");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(pr);

            out.close();
            fileOut.close();
            System.out.println("\nSerialization Successful.\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            wl.unlock();
        }

    }

    /**
     * Metoda za sinkronizaciju niti čitanje iz filea
     * @return
     */
    public ArrayList<Object> ReadFile() {
        ArrayList<Object> list = new ArrayList<>();
        rl.lock();
        try (FileInputStream fis = new FileInputStream("dat/promjene.dat");
             ObjectInputStream ois = new ObjectInputStream(fis);) {

            list = (ArrayList) ois.readObject();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
        }finally {
            rl.unlock();
        }

        return list;



    }
}
