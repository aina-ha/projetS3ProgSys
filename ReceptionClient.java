package reseau;
import java.util.Vector;
import java.io.File;
import java.net.Socket;
public class ReceptionClient implements Runnable{
    Server server;
    public ReceptionClient(Server serve)
    {
        this.setServer(serve);
    }
    public void setServer(Server ser)
    {
        this.server=ser;
    }
    public void run()
    {
        try
        {
            while(!this.server.getServerSocket().isClosed())
            {
                Socket socket=this.server.getServerSocket().accept();
                System.out.println("Un client est arrive");
                new Thread(new GestionClient(socket)).start();
                this.server.getSocket().add(socket);
            }
        }
        catch(Exception ex)
        {
            //ex.printStackTrace();
        }
    }
}