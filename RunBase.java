package fenetre;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import reseau.Server;
import reseau.Client;
import java.util.ArrayList;
import java.util.Vector;
import base.Database;
import reseau.Resultat;
public class RunBase implements ActionListener{
    String button;
    JFrame index;
    public RunBase(String but,JFrame i)
    {
        this.button=but;
        this.index=i;
    }
    public void actionPerformed(ActionEvent e)
    {
        if(this.button.compareToIgnoreCase("Lancer Serveur")==0)
        {
            int port=0;
            try
            {
                port=Integer.parseInt(((Index)this.index).getJTextField()[0].getText());
                this.index.setVisible(false);
                Server server=new Server(port);
                ServeurFen serveur=new ServeurFen(""+port,server);
                System.out.println("Serveur lance");
            }
            catch(Exception ex)
            {
                System.out.println(ex);
            }
            //server.startServer();
        }
        else if(this.button.compareToIgnoreCase("Connecter")==0)
        {
            int port=0;
            String addresse=((Index)this.index).getJTextField()[1].getText();
            try
            {
                port=Integer.parseInt(((Index)this.index).getJTextField()[2].getText());
                Client cliente=new Client(addresse,port);
                ArrayList list=(ArrayList)cliente.getMessage("databases");
                Tableau tab=new Tableau(list,cliente);
                tab.setVisible(true);
                this.index.setVisible(false);
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
                this.index.setVisible(true);
            }
            System.out.println("Connected");
        }
        else if(this.button.compareToIgnoreCase("Arreter le serveur")==0)
        {
            this.index.setVisible(false);
            ServeurFen serveurFen=(ServeurFen)this.index;
            serveurFen.getServer().stopServer();
            System.out.println("Serveur stopped");
            serveurFen.dispose();
            new Index();
        }
        else if(this.button.compareToIgnoreCase("Valider")==0)
        {
            String requete=((Tableau)this.index).getJTextField().getText();
            ((Tableau)this.index).getClient().writeMessage("dataUsed",((Tableau)this.index).getDataAffichage().getDataUsed());
            ((Tableau)this.index).getClient().writeMessage("relUsed",((Tableau)this.index).getDataAffichage().getRelUsed());
            ((Tableau)this.index).getClient().writeMessage(requete);
            ((Tableau)this.index).getJTextField().setText("");
            try
            {
                Resultat vec=(Resultat)((Tableau)this.index).getClient().getLecteur().readObject();
                System.out.println(vec.reponse);
                ((Tableau)this.index).miseAjour(tabToArray(vec.list),vec.indexRoot,vec.indexTable);
                //((Database)((ArrayList)vec.get(1)).get(0)).printDataBase();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
        else if(this.button.compareToIgnoreCase("Commit")==0)
        {
            ((Tableau)this.index).getClient().writeMessage("commit");
            ((Tableau)this.index).getJTextField().setText("");
            try
            {
                Resultat vec=(Resultat)((Tableau)this.index).getClient().getLecteur().readObject();
                ((Tableau)this.index).miseAjour(tabToArray(vec.list),vec.indexRoot,vec.indexTable);
                //((Database)((ArrayList)vec.get(1)).get(0)).printDataBase();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
        else if(this.button.compareToIgnoreCase("Rollback")==0)
        {
            ((Tableau)this.index).getClient().writeMessage("rollback");
            ((Tableau)this.index).getJTextField().setText("");
            try
            {
                Resultat vec=(Resultat)((Tableau)this.index).getClient().getLecteur().readObject();
                ((Tableau)this.index).miseAjour(tabToArray(vec.list),vec.indexRoot,vec.indexTable);
                //((Database)((ArrayList)vec.get(1)).get(0)).printDataBase();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }
    public static ArrayList tabToArray(Database[] data)
    {
        ArrayList valiny=new ArrayList();
        for(int i=0;i<data.length;i++)
        {
            valiny.add(data[i]);
        }
        return valiny;
    }
}