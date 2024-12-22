package resultat;
import base.*;
import traitement.Condition;

import java.util.ArrayList;
public class Prendre{
    static boolean joinNaturel=false;
    public static Relation getSelect(String[] mots,Database base)throws Exception
    {
        Relation relation=new Relation();
        Relation valiny=new Relation();
        ArrayList list=new ArrayList();
        ArrayList attribut=new ArrayList();
        ArrayList domaine=new ArrayList();
        ArrayList indice=new ArrayList();
        ArrayList valeur=new ArrayList();
        ArrayList valeurs=new ArrayList();
        int ind=0;
        int id=0;
        String demande=new String();
        String existante=new String();
        boolean print=true;
        for(int i=1;mots[i].compareToIgnoreCase("dans")!=0;i++)
        {
            list.add(mots[i]);
            ind=i+2;
            if(i==mots.length-1)
            {
                throw new Exception("Vous devez preciser dans quelle table il faut prendre");
            }
        }
        relation=base.getRelation(mots[ind]);
        Relation relation1=new Relation();
        try{
            while(ind<mots.length)
            {
                if(mots[ind+1].compareToIgnoreCase("Union")==0)
                {
                    relation1=base.getRelation(mots[ind+2]);
                    relation=relation.getUnion(relation1);
                    ind=ind+2;
                }
                else if(mots[ind+1].compareToIgnoreCase("Intersection")==0)
                {
                    relation1=base.getRelation(mots[ind+2]);
                    relation=relation.getIntersection(relation1);
                    ind=ind+2;
                }
                else if(mots[ind+1].compareToIgnoreCase("Ordre")==0)
                {
                    relation.orderBy(mots[ind+2],mots[ind+3]);
                    ind=ind+3;
                }
                else if(mots[ind+1].compareToIgnoreCase("Produit")==0)
                {
                    relation1=base.getRelation(mots[ind+2]);
                    relation=relation.getProduit(relation1);
                    ind=ind+2;
                }
                else if(mots[ind+1].compareToIgnoreCase("joindre")==0 && mots[ind+2].compareToIgnoreCase("naturel")==0)
                {
                    relation1=base.getRelation(mots[ind+3]);
                    relation=base.getRelation(mots[ind]);
                    Prendre.joinNaturel=true;
                    relation=relation.getNaturalJoin(relation1,base);
                    ind+=3;
                }
                else if(mots[ind+1].compareToIgnoreCase("joindre")==0 && mots[ind+2].compareToIgnoreCase("naturel")!=0)
                {
                    if(mots[ind+3].compareToIgnoreCase("sur")!=0)
                    {
                        throw new Exception("joindre sur quel colonnes");
                    }
                    relation1=base.getRelation(mots[ind+2]);
                    relation=relation.getProduit(relation1);
                    relation=Condition.applyBoolean(relation,Condition.getConditionSur(mots),base);
                    ind=Condition.getNextIndice(mots)-1;
                    if(Prendre.joinNaturel)
                    {
                        Prendre.joinNaturel=false;
                        print=false;
                    }
                }
                else if(mots[ind+1].compareToIgnoreCase("dont")==0)
                {
                    relation=Condition.applyBoolean(relation,Condition.getCondition(mots),base);
                    break;
                }
                else
                {
                    throw new Exception("Syntaxe non reconnue "+mots[ind+1]);
                }
            }
        }
        catch(ArrayIndexOutOfBoundsException ex)
        {
            
        }
        catch(NullPointerException ex)
        {
            
        }
        catch(Exception ex)
        {
            if(ex.getMessage().compareTo("Il n'y a pas de condition")!=0)
            {
                throw ex;
            }
        }
        if(mots[1].compareToIgnoreCase("tous")!=0)
        {
            relation1=new Relation(relation.getNom(),relation.getListeAttribut(),relation.getListeDomaine(),relation.getValeur());
            for(int i=0;i<list.size();i++)
            {
                demande=(String)list.get(i);
                for(int e=0;e<relation.getListeAttribut().size();e++)
                {
                    existante=(String)relation1.getListeAttribut().get(e);
                    if(demande.compareToIgnoreCase(existante)==0)
                    {
                        attribut.add(demande);
                        domaine.add((Class)relation1.getListeDomaine().get(e));
                        indice.add(e);
                        break;
                    }
                    if(e==relation.getListeAttribut().size()-1)
                    {
                        throw new Exception("la colonne "+demande+" n'existe pas");
                    }
                }
            }
            valiny.setListeAttribut(attribut);
            valiny.setListeDomaine(domaine);
            for(int i=0;i<relation1.getValeur().size();i++)
            {
                valeur=(ArrayList)relation1.getValeur().get(i);
                for(int e=0;e<indice.size();e++)
                {
                    id=(int)indice.get(e);
                    valeurs.add(valeur.get(id));
                }
                valiny.addValeur(valeurs);
                valeurs=new ArrayList();
            }
            valiny.setNom(relation1.getNom());
        }
        else
        {
            valiny=relation;
        }
        return valiny;
    }
}