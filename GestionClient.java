package reseau;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.EOFException;
import java.net.Socket;
import java.util.Vector;
import java.util.Hashtable;
import java.util.ArrayList;
import base.Database;
import base.Relation;
import traitement.Text;
import fenetre.Tableau;
import log.Fichier;
public class GestionClient implements Runnable{
    Socket socket;
    Database database;
    boolean dataSelected;
    Hashtable message;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    ArrayList list;
    String indRel;
    public GestionClient(Socket so)
    {
        this.setSocket(so);
        this.database=null;
        this.setDataSelected(true);
        this.message=new Hashtable();
        this.indRel="";
        this.list=Database.getDataBaseExistant();
        try
        {
            this.message.put("databases",Database.getDataBaseExistant());
            this.oos= new ObjectOutputStream(socket.getOutputStream());
            this.ois=new ObjectInputStream(socket.getInputStream());
            this.oos.writeObject(this.message);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void setSocket(Socket s)
    {
        this.socket=s;
    }
    public void setDataSelected(boolean d)
    {
        this.dataSelected=d;
    }
    public boolean getDataSelected()
    {
        return this.dataSelected;
    }
    public void run()
    {
        FileOutputStream writer=null;
        try
        {
            while(socket.isConnected())
            {
                Vector objetRecu = (Vector) this.ois.readObject();
                String requete = (String)objetRecu.get(0)+"\n";
                this.database = (Database)objetRecu.get(1);
                this.indRel = (String)objetRecu.get(2);
                this.dataSelected=true;
                Fichier.write(requete);
                String reponse = this.treatRequest(requete);
                this.message.put("reponse",reponse);
                this.message.put("databases",this.list);
                Database[] base=arrayToTab(this.list);
                Thread.sleep(2000);
                //System.out.println(((Database)this.database).getTable().size()+" Voasoratra");
                Resultat resultat=new Resultat(base,reponse,((int[])this.message.get("indice"))[0],((int[])this.message.get("indice"))[1]);
                this.oos.writeObject((Resultat)resultat.clone());
                this.oos.flush();
            }
        }
        catch(EOFException ex)
        {
            ex.printStackTrace();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public String treatRequest(String requete)
    {
        String valiny="";
        Relation relation=new Relation();
        try
        {
            String[] mots=Text.phraseToMot(requete);
            if(mots.length==0){
                mots=new String[1];
                mots[0]=requete;
            }
            if(this.dataSelected)
            {
                System.out.println(this.dataSelected);
                this.database=this.getDataUsed(this.database.getNom());
                System.out.println("tonga");
                if(mots[0].compareToIgnoreCase("Creer")==0)
                {
                    relation=Text.analyse(requete,this.database);
                    valiny="Table Creee";
                }
                if(mots[0].compareToIgnoreCase("Inserer")==0)
                {
                    relation=Text.analyse(requete,this.database);
                    valiny="Insertion reussi";
                    //System.out.println(relation.getPrintRelation());
                }
                if(mots[0].compareToIgnoreCase("Supprimer")==0)
                {
                    relation=Text.analyse(requete,this.database);
                    valiny="ligne(s) supprimee";
                }
                if(mots[0].compareToIgnoreCase("Prendre")==0)
                {
                    Database data=Database.load(this.database.getNom());
                    this.database.assemble(data);
                    relation=Text.analyse(requete,this.database);
                    relation.setNom("__Select");
                    try{
                        this.database.dropRelation("__Select");
                    }
                    catch(Exception ex){

                    }
                    this.database.addRelation(relation);
                    valiny=relation.getPrintRelation()+"\n"+relation.getValeur().size()+" ligne(s) trouvees";
                }
                System.out.println(mots[0].compareToIgnoreCase("commit")+" "+mots[0]);
                if(mots[0].compareToIgnoreCase("commit")==0)
                {
                    Database data=Database.load(this.database.getNom());
                    data.getRelation("personne").printRelation();
                    data.assemble(this.database);
                    data.save();
                    this.database=data;
                    System.out.println("Commit effectue");
                    valiny="Commit effectue";
                }
                if(mots[0].compareToIgnoreCase("rollback")==0)
                {
                    System.out.println("Rollback IT");
                    this.database=Database.load(this.database.getNom());
                    System.out.println(this.database.getRelation("personne").getValeur().size());
                    valiny="Rollback effectue";
                }
                int[] ind=new int[2];
                ind[0]=getIndexDatabase(this.database.getNom());
                ind[1]=getIndexRelation(this.database,this.indRel);
                this.message.put("indice",ind);
                this.list=removeData(this.list,this.database.getNom());
                this.list.add(ind[0],this.database);
            }
            else
            {
                if(mots[0].compareToIgnoreCase("utiliser")==0)
                {
                    this.database=Database.load(mots[1]);
                    this.setDataSelected(true);
                    valiny="Database choisi";
                }
                else if(mots[0].compareToIgnoreCase("creer")==0 && mots[1].compareToIgnoreCase("database")==0)
                {
                    boolean exist=true;
                    try
                    {
                        Database base=Database.load(mots[2]);
                    }
                    catch(Exception ex)
                    {
                        exist=false;
                    }
                    if(!exist)
                    {
                        this.database=new Database(mots[2]);
                        this.setDataSelected(true);
                        this.database.save();
                        valiny="Database "+mots[2]+" creer";
                    }
                }
                else
                {
                    return "Erreur: Veuillez lire la manuel MaSQL";
                }
            }
        }
        catch(ArrayIndexOutOfBoundsException ex)
        {
            return "Erreur: Veuillez lire la manuel MaSQL";
        }
        catch(Exception ex)
        {
            return "Erreur: "+ex.getMessage();
        }
        return valiny;
    }
    public static Database[] arrayToTab(ArrayList list)
    {
        Database[] database=new Database[list.size()];
        for(int i=0;i<list.size();i++)
        {
            database[i]=(Database)list.get(i);
        }
        return database;
    }
    public static int getIndexRelation(Database da,String nomRel)
    {
        for(int i=0;i<da.getTable().size();i++)
        {
            Relation rel=(Relation)da.getTable().get(i);
            if(rel.getNom().compareToIgnoreCase(nomRel)==0)
            {
                return i;
            }
        }
        return 0;
    }
    public Database getDataUsed(String name)
    {
        for(int i=0;i<this.list.size();i++)
        {
            Database valiny=(Database)this.list.get(i);
            if(valiny.getNom().compareToIgnoreCase(name)==0)
            {
                return valiny;
            }
        }
        return null;
    }
    public int getIndexDatabase(String name)
    {
        for(int i=0;i<this.list.size();i++)
        {
            Database valiny=(Database)this.list.get(i);
            if(valiny.getNom().compareToIgnoreCase(name)==0)
            {
                return i;
            }
        }
        return 0;
    }
    public ArrayList removeData(ArrayList list,String databaseName){
        for(int i=0;i<list.size();i++){
            Database database=(Database)list.get(i);
            if(database.getNom().compareToIgnoreCase(databaseName)==0){
                list.remove(i);
            }
        }
        return list;
    }
}