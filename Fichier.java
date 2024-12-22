package log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public final class Fichier {
    public static void write(String message){
        File file = new File("save/replication.txt");

        // Ouvre le fichier en mode ajout
        file.setWritable(true);
        try (FileOutputStream fos = new FileOutputStream(file, true)) {
            // Ajouter du contenu à la fin du fichier
            fos.write(message.getBytes());

            System.out.println("Données ajoutées sans supprimer le contenu existant.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        file.setWritable(false);
    }
}
