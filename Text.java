package traitement;
import resultat.*;
import java.util.ArrayList;
import base.*;
import java.time.LocalDate;
public class Text{
    public static String listToString(ArrayList li)
    {
        char[] letters=new char[li.size()-1];
        for(int i=0;i<li.size()-1;i++)
        {
            letters[i]=(char)li.get(i);
        }
        return new String(letters);
    }
    public static String[] phraseToMot(String s)throws Exception
    {
        ArrayList valiny=new ArrayList();
        ArrayList listChar=new ArrayList();
        char[] letters=s.toCharArray();
        for(int i=0;i<letters.length;i++)
        {
            listChar.add(letters[i]);
            if(letters[i]==' ' || letters[i]==',' || letters[i]==';')
            {
                valiny.add(Text.listToString(listChar));
                listChar=new ArrayList();
            }
        }
        int ind=0;
        for(int i=0;i<valiny.size();i++)
        {
            if(((String)valiny.get(i)).replaceAll(" ","").length()!=0)
            {
                ind++;
            }
        }
        String[] reponse=new String[ind];
        ind=0;
        for(int i=0;i<valiny.size();i++)
        {
            if(((String)valiny.get(i)).replaceAll(" ","").length()!=0)
            {
                reponse[ind]=(String)valiny.get(i);
                ind++;
            }
        }
        return reponse;
    }
    public static Relation analyse(String s,Database base)throws Exception
    {
        String[] mots=Text.phraseToMot(s);
        String nom;
        Relation relation=new Relation();
        ArrayList domaine=new ArrayList();
        ArrayList attribut=new ArrayList();
        ArrayList attributDemande=new ArrayList();
        if(mots[0].compareToIgnoreCase("creer")==0 && mots[1].compareToIgnoreCase("table")==0)
        {
            nom=mots[2];
            for(int i=3;i<mots.length;i+=2)
            {
                if(mots[i].compareToIgnoreCase("int")==0)
                {
                    domaine.add(int.class);
                }
                else if(mots[i].compareToIgnoreCase("String")==0)
                {
                    domaine.add(String.class);
                }
                else if(mots[i].compareToIgnoreCase("LocalDate")==0)
                {
                    domaine.add(LocalDate.class);
                }
                else if(mots[i].compareToIgnoreCase("double")==0)
                {
                    domaine.add(double.class);
                }
                else if(mots[i].compareToIgnoreCase("float")==0)
                {
                    domaine.add(float.class);
                }
                else if(mots[i].compareToIgnoreCase("boolean")==0)
                {
                    domaine.add(Boolean.class);
                }
                else
                {
                    throw new Exception("La classe: "+mots[i]+" n'existe pas");
                }
                if(Condition.isANumber(mots[i+1]) || mots[i+1].charAt(0)=='\'' || mots[i+1].charAt(mots[i+1].length()-1)=='\'')
                {
                    throw new Exception("Les noms des colonnes ne doivent pas etre numeriques ou entre \"'\"");
                }
                if(mots[i+1].compareToIgnoreCase("true")==0 || mots[i+1].compareToIgnoreCase("false")==0)
                {
                    throw new Exception("Les noms des colonnes ne doivent pas etre false ou true");
                }
                attribut.add(mots[i+1]);
            }
            relation=new Relation(nom,attribut,domaine);
            base.addRelation(relation);
            return relation;
        }
        ArrayList values=new ArrayList();
        if(mots[0].compareToIgnoreCase("inserer")==0 && mots[1].compareToIgnoreCase("dans")==0)
        {
            int i=0;
            ArrayList valeur=new ArrayList();
            try
            {
                relation=base.getRelation(mots[2]);
                for(i=3;mots[i].compareToIgnoreCase("les")!=0 && mots[i+1].compareToIgnoreCase("valeurs")!=0;i++)
                {
                    attributDemande.add(mots[i]);
                }
                for(i=i+2;i<mots.length;i++)
                {
                    if(mots[i].charAt(0)!='\'')
                    {
                        values.add(mots[i]);
                    }
                    else
                    {
                        String text=mots[i];
                        for(;mots[i].charAt(mots[i].length()-1)!='\'';)
                        {
                            i++;
                            if(i==mots.length)
                            {
                                throw new Exception("La \"'\" n'a pas ete ferme");
                            }
                            text+=" "+mots[i];
                        }
                        if(text.compareToIgnoreCase("dans")==0)
                        {
                            throw new Exception("les attributs ne peuvent pas porter le nom de "+text);
                        }
                        values.add(text);
                    }
                }
                if(attributDemande.size()!=0)
                {
                    valeur=Text.triValues(attributDemande,values,relation);
                }
                else
                {
                    valeur=values;
                    attributDemande=relation.getListeAttribut();
                }
            }
            catch(ArrayIndexOutOfBoundsException ex)
            {
                throw new Exception("'les valeurs' non retrouve dans le syntaxe");
            }
            catch(Exception ex)
            {
                throw ex;
            }
            if(attributDemande.size()!=values.size())
            {
                throw new Exception("Le nombre de valeur inserer est incorrecte");
            }
            ArrayList list=new ArrayList();
            Class classe=Relation.class;
            int entier=0;
            LocalDate date=LocalDate.now();
            double decimal=0;
            String demande="";
            String attributExistant="";
            float virgule=0;
            String valor="";
            boolean vraiFaux=false;
            for(i=0;i<valeur.size();i++)
            {
                classe=(Class)relation.getListeDomaine().get(i);
                attributExistant=(String)relation.getListeAttribut().get(i);
                valor=(String)valeur.get(i);
                if(classe==int.class)
                {
                    try
                    {
                        if(valor.compareTo("")==0)
                        {
                            list.add("");
                        }
                        else
                        {
                            entier=Integer.parseInt(valor);
                            list.add(entier);
                        }
                    }
                    catch(Exception ex)
                    {
                        throw new Exception("Vous devez mettre que des objets de type int dans la colonnes "+demande);
                    }
                }
                else if(classe==String.class)
                {
                    if(valor.compareTo("")==0)
                    {
                        list.add("");
                    }
                    else
                    {
                        if(valor.charAt(0)!='\'' || valor.charAt(valor.length()-1)!='\'')
                        {
                            throw new Exception("Vous devez mettre des \"'\" sur les valeurs de type String");
                        }
                        String test=valor.replace("'","");
                        list.add(test);
                    }
                }
                else if(classe==LocalDate.class)
                {
                    try
                    {
                        if(valor.compareTo("")==0)
                        {
                            list.add("");
                        }
                        else
                        {
                            date=LocalDate.parse(valor);
                            list.add(date);
                        }
                    }
                    catch(Exception ex)
                    {
                        throw new Exception("Vous devez mettre que des objets de type "+classe.toString()+" dans la colonnes "+demande);
                    }
                }
                else if(classe==double.class)
                {
                    try
                    {
                        if(valor.compareTo("")==0)
                        {
                            list.add("");
                        }
                        else
                        {
                            decimal=Double.parseDouble(valor);
                            list.add(decimal);
                        }
                    }
                    catch(Exception ex)
                    {
                        throw new Exception("Vous devez mettre que des objets de type "+classe.toString()+" dans la colonnes "+demande);
                    }
                }
                else if(classe==float.class)
                {
                    try
                    {
                        if(valor.compareTo("")==0)
                        {
                            list.add("");
                        }
                        else
                        {
                            virgule=Float.parseFloat(valor);
                            list.add(virgule);
                        }
                    }
                    catch(Exception ex)
                    {
                        throw new Exception("Vous devez mettre que des objets de type "+classe.toString()+" dans la colonnes "+demande);
                    }
                }
                else if(classe==Boolean.class)
                {
                    try
                    {
                        if(valor.compareTo("")==0)
                        {
                            list.add("");
                        }
                        else
                        {
                            vraiFaux=Boolean.parseBoolean(valor);
                            list.add(vraiFaux);
                        }
                    }
                    catch(Exception ex)
                    {
                        throw new Exception("Vous devez mettre que des objets de type "+classe.toString()+" dans la colonnes "+demande);
                    }
                }
            }
            relation.addValeur(list);
            return relation;
        }
        if(mots[0].compareToIgnoreCase("prendre")==0)
        {
            relation=Prendre.getSelect(mots,base);
            return relation;
        }
        if(mots[0].compareToIgnoreCase("supprimer")==0 && mots[1].compareToIgnoreCase("dans")==0)
        {
            relation=base.getRelation(mots[2]);
            Relation relation1=Condition.applyBoolean(relation,Condition.getCondition(mots),base);
            relation=relation.getDifference(relation1);
            relation.setNom(mots[2]);
            base.dropRelation(relation.getNom());
            base.addRelation(relation);
            return relation;
        }
        throw new Exception("Veuillez consultez le manuel Ma");
    }
    public static ArrayList triValues(ArrayList attribut,ArrayList values,Relation relation)throws Exception
    {
        ArrayList valiny=new ArrayList();
        String existant="";
        String demande="";
        ArrayList attributExistant=new ArrayList();
        for(int i=0;i<relation.getListeAttribut().size();i++)
        {
            valiny.add("");
        }
        for(int i=0;i<attribut.size();i++)
        {
            demande=(String)attribut.get(i);
            for(int e=0;e<relation.getListeAttribut().size();e++)
            {
                existant=(String)relation.getListeAttribut().get(e);
                if(existant.compareToIgnoreCase(demande)==0)
                {
                    valiny.remove(e);
                    valiny.add(e,values.get(i));
                }
            }
        }
        return valiny;
    }
}