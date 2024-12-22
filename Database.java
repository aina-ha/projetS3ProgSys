package base;
import java.util.ArrayList;
import traitement.Text;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.File;
public class Database implements Serializable, Cloneable{
    String nom;
    ArrayList table;
    //Constructeur
    public Database(String n,ArrayList tabl)
    {
        this.setNom(n);
        this.table=new ArrayList();
        this.setTable(tabl);
    }
    public Database(String no)
    {
        this.setNom(no);
        this.table=new ArrayList();
    }
    public Database()
    {
        this.table=new ArrayList();
    }
    //Setter
    public void setNom(String n)
    {
        this.nom=n;
    }
    public void setTable(ArrayList l)
    {
        this.table=l;
    }
    //Getter
    public String getNom()
    {
        return this.nom;
    }
    public ArrayList getTable()
    {
        return this.table;
    }
    //fonction
    public void save()
    {
        try
        {
            ObjectOutputStream oos= new ObjectOutputStream(new FileOutputStream("save/"+this.nom+".txt"));
            oos.writeObject(this);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public static Database load(String nom)throws Exception
    {
        Database valiny=new Database();
        try
        {
            ObjectInputStream ois= new ObjectInputStream(new FileInputStream("save/"+nom+".txt"));
            valiny=(Database)ois.readObject();
        }
        catch(Exception ex)
        {
            throw new Exception(ex.getMessage()+"\nLa database "+nom+" n'a pas encore ete creer");
        }
        return valiny;
    }
    public void addRelation(Relation re)
    {
        this.table.add(re);
    }
    public Relation getRelation(String n)throws Exception
    {
        Relation relation=new Relation();
        for(int i=0;i<this.table.size();i++)
        {
            relation=(Relation)this.table.get(i);
            if(relation.getNom().compareToIgnoreCase(n)==0)
            {
                return relation;
            }
        }
        throw new Exception("La table "+n+" n'existe pas");
    }
    public void dropRelation(String n)throws Exception
    {
        Relation relation=new Relation();
        for(int i=0;i<this.table.size();i++)
        {
            relation=(Relation)this.table.get(i);
            if(relation.getNom().compareToIgnoreCase(n)==0)
            {
                this.table.remove(i);
                break;
            }
            if(i==this.table.size()-1)
            {
                throw new Exception("La table "+n+" n'existe pas");
            }
        }
    }
    public void printDataBase()
    {
        Relation relation=new Relation();
        for(int i=0;i<this.table.size();i++)
        {
            relation=(Relation)this.table.get(i);
            relation.printRelation();
        }
    }
    public void setDefault()
    {
        Relation relation=new Relation();
        try
        {
            relation=Text.analyse("creer table personne int idPersonne String Nom String Prenom LocalDate dateNaissance boolean majeur;",this);
            relation=Text.analyse("inserer dans personne les valeurs 12 'RAKOTO' 'Bema' 2004-08-12 true;",this);
            relation=Text.analyse("creer table police int idPolice int idPersonne;",this);
            relation=Text.analyse("creer table banquier int idBanquier int idPersonne;",this);
            relation=Text.analyse("inserer dans police les valeurs 1 12;",this);
            relation=Text.analyse("inserer dans banquier les valeurs 1 12;",this);
            relation=Text.analyse("inserer dans banquier les valeurs 2 15;",this);
            relation=Text.analyse("inserer dans personne les valeurs 10 'RASOA' 'Lita' 2004-10-10 true;",this);
            relation=Text.analyse("inserer dans personne Nom Prenom idPersonne dateNaissance majeur les valeurs 'RASOA' 'Rebecca' 15 2012-11-10 false;",this);
            //inserer dans personne les valeurs 20 'qsdRASOA' 'Lizkqsda' 2004-10-10 true;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void assemble(Database vaovao)throws Exception
    {
        Relation relation1=new Relation();
        Relation relation2=new Relation();
        boolean exist=false;
        if(this.nom.compareToIgnoreCase(vaovao.nom)!=0)
        {
            throw new Exception("Erreur");
        }
        for(int i=0;i<vaovao.getTable().size();i++)
        {
            exist=false;
            relation1=(Relation)vaovao.getTable().get(i);
            for(int e=0;e<this.getTable().size();e++)
            {
                relation2=(Relation)this.getTable().get(e);
                if(relation1.getNom().compareToIgnoreCase(relation2.getNom())==0)
                {
                    relation2.assemble(relation1);
                    exist=true;
                    break;
                }
            }
            if(!exist)
            {
                this.addRelation(relation1);
            }
        }
    }
    public static ArrayList getDataBaseExistant(){
        ArrayList valiny=new ArrayList();
        ArrayList list=getAllFiles();
        for(int i=0;i<list.size();i++)
        {
            try
            {
                valiny.add(Database.load((String)list.get(i)));
            }
            catch(Exception ex)
            {
                
            }
        }
        return valiny;
    }
    public static ArrayList getAllFiles() {
        File folder=new File("save/");
        ArrayList valiny=new ArrayList();
        for (File file : folder.listFiles()) {
            if (!file.isDirectory()) {
                valiny.add(file.getName().split("\\.")[0]);
            }
        }
        return valiny;
    }
    public Object clone() throws CloneNotSupportedException {
        try {
            Database clonedDatabase = (Database) super.clone();
            clonedDatabase.table = new ArrayList<>();

            // Clonage profond de chaque relation dans la liste
            for (Object obj : this.table) {
                if (obj instanceof Relation) {
                    Relation clonedRelation = (Relation) ((Relation) obj).clone();
                    clonedDatabase.table.add(clonedRelation);
                } else {
                    // Gérer le cas où l'objet n'est pas une instance de Relation
                    // Vous devrez peut-être ajuster cela selon votre implémentation
                    // ou lever une exception.
                }
            }

            return clonedDatabase;
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }
}