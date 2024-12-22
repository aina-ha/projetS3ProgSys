package reseau;

import java.io.FileInputStream;
import java.net.ServerSocket;
import java.util.Properties;
import java.util.Vector;

public class Server {
    int port;
    ServerSocket serverSocket;
    Vector socket;

    public Server() {
    }

    public Server(int po) {
        this.setPort(po);
        try {
            this.setServerSocket(new ServerSocket(this.getPort()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.setSocket(new Vector());
    }

    // Setter
    public void setPort(int po) {
        this.port = po;
    }

    public void setServerSocket(ServerSocket ser) {
        this.serverSocket = ser;
    }

    public void setSocket(Vector vec) {
        this.socket = vec;
    }

    // Getter
    public ServerSocket getServerSocket() {
        return this.serverSocket;
    }

    public Vector getSocket() {
        return this.socket;
    }

    public int getPort() {
        return this.port;
    }

    // Fonction
    public void startServer() {
        try {
            new Thread(new ReceptionClient(this)).run();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void stopServer() {
        /*try {
            // Fermer le ServerSocket pour arrêter l'acceptation de nouvelles connexions
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }

            // Terminer les threads de gestion des clients existants
            for(int i=0;i<this.socket.size();i++)
            {
                Socket sock=(Socket)this.socket.get(i);
                if(!sock.isClosed())
                {
                    sock.close();
                }
            }

            // Clear le vecteur des sockets clients
            socket.clear();
        } catch (Exception ex) {
            ex.printStackTrace();
        }*/
    }

    // Charger la configuration du serveur
    public static int loadServerConfig(String filePath) throws Exception {
        Properties props = new Properties();
        props.load(new FileInputStream(filePath));

        return Integer.parseInt(props.getProperty("Serveur.port"));
    }

    public static void main(String[] args) {
        try {
            String configFilePath = "fichier.conf";
            int serverPort = loadServerConfig(configFilePath);

            Server server = new Server(serverPort);
            server.startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


