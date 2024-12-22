package reseau;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;

public class Client {
    String addresse;
    int port;
    Socket socket;
    Hashtable message;
    ObjectInputStream lecteur;
    ObjectOutputStream stylo;

    public Client(String ad, int po) throws Exception {
        this.setAddresse(ad);
        this.setPort(po);
        this.message = new Hashtable();
        try {
            this.setSocket(new Socket(this.addresse, this.port));
            this.stylo = new ObjectOutputStream(socket.getOutputStream());
            this.lecteur = new ObjectInputStream(socket.getInputStream());
            this.getMessage();
        } catch (Exception ex) {
            throw new Exception("Mauvaise addresse IP ou port");
        }
    }

    // Setter
    public void setAddresse(String ad) {
        this.addresse = ad;
    }

    public void setPort(int po) {
        this.port = po;
    }

    public void setSocket(Socket s) {
        this.socket = s;
    }

    public Socket getSocket() {
        return this.socket;
    }

    // Fonction
    public void writeMessage(String ms) {
        Vector vec = new Vector();
        this.message.put("requete", ms);
        try {
            vec.add(this.message.get("requete"));
            vec.add(this.message.get("dataUsed"));
            vec.add(this.message.get("relUsed"));
            this.stylo.writeObject(vec);
            this.stylo.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void writeMessage(String key, Object ob) {
        this.message.put(key, ob);
    }

    public void getMessage() {
        try {
            if (this.socket != null && this.socket.isConnected()) {
                this.message = (Hashtable) this.lecteur.readObject();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Object getMessage(String key) {
        return this.message.get(key);
    }

    public ObjectOutputStream getStylo() {
        return this.stylo;
    }

    public ObjectInputStream getLecteur() {
        return this.lecteur;
    }

    // Charger la configuration du client
    public static String[] loadClientConfig(String filePath) throws Exception {
        Properties props = new Properties();
        props.load(new FileInputStream(filePath));

        String serverAddress = props.getProperty("Client.adresse");
        String clientPort = props.getProperty("Client.port");

        return new String[]{serverAddress, clientPort};
    }

    public static void main(String[] args) {
        try {
            String configFilePath = "fichier.conf";
            String[] clientConfig = loadClientConfig(configFilePath);

            String serverAddress = clientConfig[0];
            int serverPort = Integer.parseInt(clientConfig[1]);

            Client client = new Client(serverAddress, serverPort);
            System.out.println("Connexion réussie au serveur : " + serverAddress + ":" + serverPort);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
