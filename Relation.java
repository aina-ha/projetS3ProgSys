package base;
import traitement.Text;
import java.time.LocalDate;
import java.util.ArrayList;
import java.io.Serializable;
public class Relation implements Serializable,Cloneable{
    String nom;
    ArrayList listeAttribut=new ArrayList();    //Les attributs de la relation
    ArrayList listeDomaine=new ArrayList();     //Le domaine des attributs 
    ArrayList valeur=new ArrayList();           //Les valeurs qui constituent un tableau a 2 dimension: sur les colonnes, puis sur les lignes
    //Constructeur
    public Relation()
    {

    }
    public Relation(String name,ArrayList listeA,ArrayList listeD)
    {
        this.setNom(name);
        this.setListeAttribut(listeA);
        this.setListeDomaine(listeD);
    }
    public Relation(String name,ArrayList listeA,ArrayList listeD,ArrayList vale)
    {
        this.setNom(name);
        this.setListeAttribut(listeA);
        this.setListeDomaine(listeD);
        this.setValeur(vale);
    }
    //Setter
    public String getNom()
    {
        return this.nom;
    }
    public ArrayList getListeAttribut()
    {
        return this.listeAttribut;
    }
    public ArrayList getListeDomaine()
    {
        return this.listeDomaine;
    }
    public ArrayList getValeur()
    {
        return this.valeur;
    }
    //Getter
    public void setNom(String na)
    {
        this.nom=na;
    }
    public void setListeAttribut(ArrayList a)
    {
        this.listeAttribut=a;
    }
    public void setListeDomaine(ArrayList a)
    {
        this.listeDomaine=a;
    }
    public void setValeur(ArrayList a)
    {
        this.valeur=a;
    }
    //Fonction
    public static boolean isTheSame(ArrayList list,ArrayList other)
    {
        int intGauche=0,intDroite=0;
        LocalDate dateGauche=LocalDate.now();
        LocalDate dateDroite=LocalDate.now();
        String gauche="",droite="";
        boolean boolGauche=false,boolDroite=false;
        //System.out.println(list+" "+other);
        try
        {
            for(int i=0;i<list.size();i++)
            {
                gauche=list.get(i).toString();
                droite=other.get(i).toString();
                if(gauche.compareToIgnoreCase(droite)!=0)
                {
                    //System.out.println(i);
                    return false;
                }
            }
        }
        catch(Exception ex)
        {
            return false;
        }
        return true;
    }
    public void assemble(Relation vaovao)
    {
        ArrayList list1=new ArrayList();
        ArrayList original=new ArrayList();
        boolean deleted=true;
        boolean added=false;
        for(int i=0;i<this.getValeur().size();i++)
        {
            deleted=false;
            original=(ArrayList)this.getValeur().get(i);
            for(int e=0;e<vaovao.getValeur().size();e++)
            {
                list1=(ArrayList)vaovao.getValeur().get(e);
                if(Relation.isTheSame(original,list1))
                {
                    break;
                }
                if(e==vaovao.getValeur().size()-1)
                {
                    deleted=true;
                }
            }
            if(deleted)
            {
                System.out.println(this.getValeur().get(i)+" deleted "+i);
                this.getValeur().remove(i);
            }
        }
        for(int i=0;i<vaovao.getValeur().size();i++)
        {
            added=false;
            list1=(ArrayList)vaovao.getValeur().get(i);
            for(int e=0;e<this.getValeur().size();e++)
            {
                original=(ArrayList)this.getValeur().get(e);
                if(Relation.isTheSame(original,list1))
                {
                    break;
                }
                if(e==this.getValeur().size()-1)
                {
                    added=true;
                }
            }
            if(added)
            {
                this.getValeur().add(list1);
            }
        }
    }
    public int getAttribute(String attributs)throws Exception
    {
        int valiny=0;
        attributs=attributs.replace("!","");
        for(int i=0;i<this.getListeAttribut().size();i++)
        {
            if(attributs.compareToIgnoreCase((String)this.getListeAttribut().get(i))==0)
            {
                valiny=i;
                return valiny;
            }
        }
        throw new Exception("L'attributs "+attributs+" n'existe pas");
    }
    public boolean sameSize(Relation other)
    {
        if(this.listeAttribut.size()!=other.listeAttribut.size())
        {
            return false;
        }
        if(this.listeDomaine.size()!=other.listeDomaine.size())
        {
            return false;
        }
        return true;
    }
    public void addValeur(ArrayList l)
    {
        this.valeur.add(l);
    }
    public Relation getDifference(Relation other)
    {
        boolean exist=false;
        Relation relation=new Relation();
        relation.setNom(this.getNom()+" difference "+other.getNom());
        relation.setListeDomaine(this.getListeDomaine());
        relation.setListeAttribut(this.getListeAttribut());
        for(int i=0;i<this.getValeur().size();i++)
        {
            exist=false;
            for(int e=0;e<other.getValeur().size();e++)
            {
                if(Relation.isTheSame((ArrayList)other.getValeur().get(e),(ArrayList)this.getValeur().get(i)))
                {
                    exist=true;
                    break;
                }
            }
            if(!exist)
            {
                relation.addValeur((ArrayList)this.getValeur().get(i));
            }
        }
        for(int i=0;i<other.getValeur().size();i++)
        {
            exist=false;
            for(int e=0;e<this.getValeur().size();e++)
            {
                if(Relation.isTheSame((ArrayList)this.getValeur().get(e),(ArrayList)other.getValeur().get(i)))
                {
                    exist=true;
                    break;
                }
            }
            if(!exist)
            {
                for(int e=0;e<relation.getValeur().size();e++)
                {
                    if(Relation.isTheSame((ArrayList)relation.getValeur().get(e),(ArrayList)other.getValeur().get(i)))
                    {
                        exist=true;
                        break;
                    }
                }
                if(!exist)
                {
                    relation.addValeur((ArrayList)other.getValeur().get(i));
                }
            }
        }
        return relation;
    }
    public Relation getUnion(Relation other)throws Exception
    {
        Relation valiny=new Relation();
        ArrayList list1=new ArrayList();
        ArrayList list2=new ArrayList();
        if(!this.sameSize(other))
        {
            throw new Exception("Ils doivent avoir les memes nombres de colonnes");
        }
        valiny.nom=this.nom+" Union "+other.nom;
        valiny.listeAttribut=this.listeAttribut;
        valiny.listeDomaine=this.listeDomaine;
        for(int i=0;i<this.valeur.size();i++)
        {
            valiny.addValeur((ArrayList)this.valeur.get(i));
        }
        boolean doIt=true;
        for(int i=0;i<other.valeur.size();i++)
        {
            list1=(ArrayList)other.valeur.get(i);
            for(int e=0;e<valiny.valeur.size();e++)
            {
                list2=(ArrayList)valiny.valeur.get(e);
                if(Relation.isTheSame(list1,list2))
                {
                    doIt=false;
                    break;
                }
            }
            if(doIt)
            {
                valiny.addValeur(list1);
            }
            doIt=true;
        }
        return valiny;
    }
    public Relation getIntersection(Relation other)throws Exception
    {
        Relation valiny=new Relation();
        if(!this.sameSize(other))
        {
            throw new Exception("Ils doivent avoir les memes nombres de colonnes");
        }
        valiny.listeAttribut=other.listeAttribut;
        valiny.listeDomaine=other.listeDomaine;
        valiny.nom=this.nom+" Intersection "+other.nom;
        ArrayList recep1=new ArrayList();
        ArrayList recep2=new ArrayList();
        for(int i=0;i<this.valeur.size();i++)
        {
            recep1=(ArrayList)this.valeur.get(i);
            for(int j=0;j<other.valeur.size();j++)
            {
                recep2=(ArrayList)other.valeur.get(j);
                if(Relation.isTheSame(recep1, recep2))
                {
                    valiny.addValeur(recep1);
                }
            }
        }
        return valiny;
    }
    public Relation getNaturalJoin(Relation other,Database database)
    {
        Relation valiny=new Relation();
        String left="";
        String right="";
        ArrayList list=new ArrayList();
        valiny.setNom(this.getNom()+"."+other.getNom());
        for(int i=0;i<this.getListeAttribut().size();i++)
        {
            left=(String)this.getListeAttribut().get(i);
            for(int e=0;e<other.getListeAttribut().size();e++)
            {
                right=(String)other.getListeAttribut().get(e);
                if(right.compareToIgnoreCase(left)==0)
                {
                    list.add(right);
                    break;
                }
            }
        }
        String requete="Prendre ";
        for(int i=0;i<this.getListeAttribut().size();i++)
        {
            left=(String)this.getListeAttribut().get(i);
            requete+=this.getNom()+"."+left+" ";
        }
        boolean add=true;
        for(int i=0;i<other.getListeAttribut().size();i++)
        {
            left=(String)other.getListeAttribut().get(i);
            for(int e=0;e<list.size();e++)
            {
                right=(String)list.get(e);
                if(right.compareToIgnoreCase(left)==0)
                {
                    add=false;
                    break;
                }
            }
            if(add)
            {
                requete+=other.getNom()+"."+left+" ";
            }
            add=true;
        }
        requete+="dans "+this.getNom()+" joindre "+other.getNom()+" sur ";
        for(int i=0;i<list.size();i++)
        {
            left=(String)list.get(i);
            if(i==list.size()-1)
            {
                requete+=this.getNom()+"."+left+" = "+other.getNom()+"."+left+";";
            }
            else
            {
                requete+=this.getNom()+"."+left+" = "+other.getNom()+"."+left+" && ";
            }
        }
        try
        {
            valiny=Text.analyse(requete,database);
        }
        catch(Exception ex)
        {
            System.out.println(ex);
        }
        //System.out.println(valiny.getListeAttribut().size());
        try
        {
            for(int i=0;i<valiny.getListeAttribut().size();i++)
            {
                left=(String)valiny.getListeAttribut().get(i);
                valiny.listeAttribut.remove(left);
                valiny.listeAttribut.add(i,Relation.getNameAttribut(left));
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return valiny;
    }
    public static String getNameAttribut(String left)
    {
        String[] mots=left.split("\\.");
        return mots[1];
    } 
    public int getTailleMaxTab(int e)
    {
        ArrayList list=new ArrayList();
        int valiny=0;
        String receptacle="";
        for(int i=0;i<this.valeur.size();i++)
        {
            list=(ArrayList)this.valeur.get(i);
            receptacle=list.get(e).toString();
            if(valiny<receptacle.length())
            {
                valiny=receptacle.length();
            }
        }
        for(int i=0;i<this.listeAttribut.size();i++)
        {
            receptacle=(String)this.listeAttribut.get(i);
            if(valiny<receptacle.length())
            {
                valiny=receptacle.length();
            }
        }
        return valiny;
    }
    public Relation getProduit(Relation other)
    {
        ArrayList attribu=new ArrayList();
        ArrayList doma=new ArrayList();
        for(int i=0;i<this.getListeAttribut().size();i++)
        {
            attribu.add(this.getNom()+"."+this.getListeAttribut().get(i));
        }
        for(int i=0;i<other.getListeAttribut().size();i++)
        {
            attribu.add(other.getNom()+"."+other.getListeAttribut().get(i));
        }
        for(int i=0;i<this.getListeDomaine().size();i++)
        {
            doma.add(this.getListeDomaine().get(i));
        }
        for(int i=0;i<other.getListeDomaine().size();i++)
        {
            doma.add(other.getListeDomaine().get(i));
        }
        ArrayList valeurs=new ArrayList();
        ArrayList valeur=new ArrayList();
        ArrayList list1=new ArrayList();
        ArrayList list2=new ArrayList();
        for(int i=0;i<this.valeur.size();i++)
        {
            for(int e=0;e<other.valeur.size();e++)
            {
                list1=(ArrayList)this.valeur.get(i);
                for(int k=0;k<list1.size();k++)
                {
                    valeur.add(list1.get(k));
                }
                list2=(ArrayList)other.valeur.get(e);
                for(int k=0;k<list2.size();k++)
                {
                    valeur.add(list2.get(k));
                }
                valeurs.add(valeur);
                valeur=new ArrayList();
            }
        }
        return new Relation(this.getNom()+" produit "+other.getNom(),attribu,doma,valeurs);
    }
    public static String putSpaceString(String utilise,int taille)
    {
        String valiny=utilise;
        for(int i=utilise.length();i<taille+5;i++)
        {
            valiny+=" ";
        }
        return valiny;
    }
    public static String putSpaceStringTitle(String utilise,int taille)
    {
        String valiny=utilise;
        String spaceLeft=" ";
        String spaceRight=" ";
        for(int i=valiny.length();i<taille+5;)
        {
            valiny=spaceLeft+utilise+spaceRight;
            i=valiny.length();
            if(i%2==0)
            {
                spaceRight+=" ";
            }
            if(i%2==1)
            {
                spaceLeft+=" ";
            }
        }
        return valiny;
    }
    public static String putLineString(int taille)
    {
        String valiny="";
        for(int i=0;i<taille;i++)
        {
            valiny+="-";
        }
        return valiny;
    }
    public void printRelation()
    {
        int[] tailleTab=new int[this.listeAttribut.size()];
        int maxTab=0;
        String letter="";
        for(int i=0;i<this.listeAttribut.size();i++)
        {
            tailleTab[i]=this.getTailleMaxTab(i);
            letter=putSpaceStringTitle((String)this.listeAttribut.get(i),tailleTab[i])+"|";
            System.out.printf(letter);
            maxTab+=letter.length();
        }
        System.out.printf("\n");
        System.out.println(putLineString(maxTab));
        System.out.printf("\n");
        ArrayList list=new ArrayList();
        for(int i=0;i<this.valeur.size();i++)
        {
            list=(ArrayList)this.valeur.get(i);
            for(int e=0;e<list.size();e++)
            {
                System.out.printf(putSpaceString(list.get(e).toString(),tailleTab[e])+"|");
            }
            System.out.printf("\n");
            System.out.println(putLineString(maxTab));
            System.out.printf("\n");
        }
    }
    public String getPrintRelation()
    {
        int[] tailleTab=new int[this.listeAttribut.size()];
        int maxTab=0;
        String letter="";
        String valiny="";
        for(int i=0;i<this.listeAttribut.size();i++)
        {
            tailleTab[i]=this.getTailleMaxTab(i);
            letter=putSpaceStringTitle((String)this.listeAttribut.get(i),tailleTab[i])+"|";
            valiny+=letter;
            maxTab+=letter.length();
        }
        valiny+="\n";
        valiny+=putLineString(maxTab)+"\n";
        //System.out.printf("\n");
        ArrayList list=new ArrayList();
        for(int i=0;i<this.valeur.size();i++)
        {
            list=(ArrayList)this.valeur.get(i);
            for(int e=0;e<list.size();e++)
            {
                valiny+=putSpaceString(list.get(e).toString(),tailleTab[e])+"|";
            }
            valiny+="\n";
            valiny+=putLineString(maxTab)+"\n";
            //System.out.printf("\n");
        }
        return valiny;
    }
    public void orderBy(String mot,String crois)throws Exception
    {
        int indice=-1;
        for(int i=0;i<this.listeAttribut.size();i++)
        {
            if(mot.compareToIgnoreCase((String)this.listeAttribut.get(i))==0)
            {
                indice=i;
                break;
            }
        }
        if(indice==-1)
        {
            throw new Exception("La colonne "+mot+" n'existe pas");
        }
        if((Class)this.getListeDomaine().get(indice)==LocalDate.class)
        {
            this.orderByDate(indice,crois);
        }
        if((Class)this.getListeDomaine().get(indice)==int.class)
        {
            this.orderByInt(indice,crois);
        }
        if((Class)this.getListeDomaine().get(indice)==double.class)
        {
            this.orderByDouble(indice,crois);
        }
        if((Class)this.getListeDomaine().get(indice)==float.class)
        {
            this.orderByFloat(indice, crois);
        }
    }
    public void orderByDate(int indice,String crois)
    {
        ArrayList list=new ArrayList();
        ArrayList list2=new ArrayList();
        LocalDate date1=LocalDate.now();
        LocalDate date2=LocalDate.now();
        for(int i=0;i<this.valeur.size()-1;i++)
        {
            list=(ArrayList)this.valeur.get(i);
            list2=(ArrayList)this.valeur.get(i+1);
            try
            {
                date1=(LocalDate)list.get(indice);
            }
            catch(Exception ex)
            {
                date1=LocalDate.now();
            }
            try
            {
                date2=(LocalDate)list2.get(indice);
            }
            catch(Exception ex)
            {
                date2=LocalDate.now();
            }
            if(date1.isBefore(date2) && crois.compareToIgnoreCase("decroissant")==0)
            {
                this.valeur.remove(i);
                this.valeur.remove(i);
                this.valeur.add(i,list2);
                this.valeur.add(i+1,list);
                i=-1;
            }
            if(date1.isAfter(date2) && crois.compareToIgnoreCase("croissant")==0)
            {
                this.valeur.remove(i);
                this.valeur.remove(i);
                this.valeur.add(i,list2);
                this.valeur.add(i+1,list);
                i=-1;
            }
        }
    }
    public void orderByInt(int indice,String crois)
    {
        ArrayList list=new ArrayList();
        ArrayList list2=new ArrayList();
        int c1=0;
        int c2=0;
        for(int i=0;i<this.valeur.size()-1;i++)
        {
            list=(ArrayList)this.valeur.get(i);
            list2=(ArrayList)this.valeur.get(i+1);
            try
            {
                c1=(int)list.get(indice);
            }
            catch(Exception ex)
            {
                c1=-56464321;
            }
            try
            {
                c2=(int)list2.get(indice);
            }
            catch(Exception ex)
            {
                c2=-56464321;
            }
            if(c1<c2 && crois.compareToIgnoreCase("decroissant")==0)
            {
                this.valeur.remove(i);
                this.valeur.remove(i);
                this.valeur.add(i,list2);
                this.valeur.add(i+1,list);
                i=-1;
            }
            if(c1>c2 && crois.compareToIgnoreCase("croissant")==0)
            {
                this.valeur.remove(i);
                this.valeur.remove(i);
                this.valeur.add(i,list2);
                this.valeur.add(i+1,list);
                i=-1;
            }
        }
    }
    public void orderByDouble(int indice,String crois)
    {
        ArrayList list=new ArrayList();
        ArrayList list2=new ArrayList();
        double c1=0;
        double c2=0;
        for(int i=0;i<this.valeur.size()-1;i++)
        {
            list=(ArrayList)this.valeur.get(i);
            list2=(ArrayList)this.valeur.get(i+1);
            try
            {
                c1=(double)list.get(indice);
            }
            catch(Exception ex)
            {
                c1=-56464321;
            }
            try
            {
                c2=(double)list2.get(indice);
            }
            catch(Exception ex)
            {
                c2=-56464321;
            }
            if(c1<c2 && crois.compareToIgnoreCase("decroissant")==0)
            {
                this.valeur.remove(i);
                this.valeur.remove(i);
                this.valeur.add(i,list2);
                this.valeur.add(i+1,list);
                i=-1;
            }
            if(c1>c2 && crois.compareToIgnoreCase("croissant")==0)
            {
                this.valeur.remove(i);
                this.valeur.remove(i);
                this.valeur.add(i,list2);
                this.valeur.add(i+1,list);
                i=-1;
            }
        }
    }
    public void orderByFloat(int indice,String crois)
    {
        ArrayList list=new ArrayList();
        ArrayList list2=new ArrayList();
        float c1=0;
        float c2=0;
        for(int i=0;i<this.valeur.size()-1;i++)
        {
            list=(ArrayList)this.valeur.get(i);
            list2=(ArrayList)this.valeur.get(i+1);
            try
            {
                c1=(float)list.get(indice);
            }
            catch(Exception ex)
            {
                c1=-56464321;
            }
            try
            {
                c2=(float)list2.get(indice);
            }
            catch(Exception ex)
            {
                c2=-56464321;
            }
            if(c1<c2 && crois.compareToIgnoreCase("decroissant")==0)
            {
                this.valeur.remove(i);
                this.valeur.remove(i);
                this.valeur.add(i,list2);
                this.valeur.add(i+1,list);
                i=-1;
            }
            if(c1>c2 && crois.compareToIgnoreCase("croissant")==0)
            {
                this.valeur.remove(i);
                this.valeur.remove(i);
                this.valeur.add(i,list2);
                this.valeur.add(i+1,list);
                i=-1;
            }
        }
    }
    public String[] getNomAttribut(){
        String[] valiny=new String[this.getListeAttribut().size()];
        for(int i=0;i<this.getListeAttribut().size();i++)
        {
            valiny[i]=(String)this.getListeAttribut().get(i);
        }
        return valiny;
    }
    public Object[][] getValeurTab(){
        Object[][] valiny=new Object[this.getValeur().size()][this.getListeAttribut().size()];
        ArrayList recep=new ArrayList();
        for(int i=0;i<this.getValeur().size();i++)
        {
            recep=(ArrayList)this.getValeur().get(i);
            for(int e=0;e<recep.size();e++)
            {
                valiny[i][e]=recep.get(e).toString();
            }
        }
        return valiny;
    }
    public Object clone() throws CloneNotSupportedException {
        try {
            // Clonage superficiel
            Relation clonedRelation = (Relation) super.clone();
    
            // Clonage profond des listes
            clonedRelation.listeAttribut = new ArrayList<>(this.listeAttribut);
            clonedRelation.listeDomaine = new ArrayList<>(this.listeDomaine);
            clonedRelation.valeur = new ArrayList<>();
    
            for (ArrayList<Object> row : (ArrayList<ArrayList<Object>>) this.valeur) {
                // Clonage profond de chaque ligne de valeurs
                ArrayList<Object> clonedRow = new ArrayList<>(row);
                clonedRelation.valeur.add(clonedRow);
            }
    
            return clonedRelation;
        } catch (CloneNotSupportedException e) {
            // Cette exception ne devrait pas se produire, car Relation implémente Cloneable
            throw new InternalError(e);
        }
    }
}