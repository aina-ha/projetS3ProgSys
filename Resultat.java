package reseau;
import java.util.ArrayList;
import base.Database;
import java.io.Serializable;
public class Resultat implements Serializable,Cloneable{
    public String reponse;
    public Database[] list;
    public int indexRoot;
    public int indexTable;
    public Resultat(Database[] a,String r,int ir,int it){
        this.reponse=r;
        this.list=a;
        this.indexRoot=ir;
        this.indexTable=it;
    }
    @Override
    protected Object clone() throws CloneNotSupportedException {
        Resultat clonedResultat = (Resultat) super.clone();
        clonedResultat.list = new Database[this.list.length];

        for (int i = 0; i < this.list.length; i++) {
            clonedResultat.list[i] = (Database) this.list[i].clone();
        }

        return clonedResultat;
    }
}