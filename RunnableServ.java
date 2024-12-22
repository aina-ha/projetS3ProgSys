package fenetre;
import reseau.Server;
public class RunnableServ implements Runnable{
    Server server;
    public RunnableServ(Server se)
    {
        this.server=se;
    }
    public void run()
    {
        if(!this.server.getServerSocket().isClosed())
        {
            this.server.startServer();
        }
    }
}