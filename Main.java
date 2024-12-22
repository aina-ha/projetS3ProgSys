package run;
import fenetre.Index;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.StandardOpenOption;

import base.Database;
import traitement.Text;
public class Main{
    public static void main(String[] asdasd)
    {
        /*Database base=new Database("CIN");
        base.setDefault();
        base.save();
        Database base1=new Database("TpFb");
        try
        {
            Text.analyse("creer table publication int idPersonne String publication LocalDate datePub;",base1);
            Text.analyse("inserer dans publication les valeurs 1 'Tsy mety kosa ranga zany a' 2022-07-30;",base1);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        base1.save();*/
        new Index();
        //Database.getAllFiles();
    }
}