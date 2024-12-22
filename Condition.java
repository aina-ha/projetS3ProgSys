package traitement;
import java.time.LocalDate;
import java.util.ArrayList;
import base.Database;
import base.Relation;
import resultat.Prendre;
public class Condition{
    public static boolean isANumber(String number)
    {
        try
        {
            Double.parseDouble(number);
            return true;
        }
        catch(Exception ex)
        {
            return false;
        }
    }
    public static void addValeur(String text,ArrayList list)
    {
        char operateur='q';
        if((text.contains("<") || text.contains("=") || text.contains(">")) && text.length()!=1)
        {
            if(text.charAt(text.length()-1)=='<' || text.charAt(text.length()-1)=='>' || text.charAt(text.length()-1)=='=')
            {
                operateur=text.charAt(text.length()-1);
                text=text.replace("<","");
                text=text.replace(">","");
                text=text.replace("=","");
                list.add(text);
                list.add(operateur);
            }
            else if(text.charAt(0)=='<' || text.charAt(0)=='>' || text.charAt(0)=='=')
            {
                operateur=text.charAt(0);
                text=text.replace("<","");
                text=text.replace(">","");
                text=text.replace("=","");
                list.add(operateur);
                list.add(text);
            }
            else
            {

            }
        }
    }
    public static int getNextIndice(String[] text)throws Exception
    {
        boolean dont=false;
        int indice=0;
        int ind=0;
        int i=0;
        for(i=0;i<text.length;i++)
        {
            if(text[i].compareToIgnoreCase("sur")==0)
            {
                dont=true;
                indice=i+1;
                break;
            }
        }
        if(!dont)
        {
            throw new Exception("Il n'y a pas de condition");
        }
        ArrayList recep=new ArrayList();
        for(i=indice;i<text.length && text[i].compareToIgnoreCase("dont")!=0;i++)
        {
            if(text[i].charAt(0)=='(')
            {
                if(text[i].length()==1)
                {
                    recep.add(text[i]);
                }
                else
                {
                    recep.add("(");
                    recep.add(text[i].replace("(",""));
                }
                i++;
                while(text[i].charAt(text[i].length()-1)!=')')
                {
                    recep.add(text[i]);
                    i++;
                }
                if(text[i].length()==1)
                {
                    recep.add(text[i]);
                }
                else
                {
                    recep.add(text[i].replace(")",""));
                    recep.add(")");
                }
            }
            else
            {
                recep.add(text[i]);
            }
        }
        return i;
    }
    public static String[] getConditionSur(String[] text)throws Exception
    {
        boolean dont=false;
        int indice=0;
        int ind=0;
        for(int i=0;i<text.length;i++)
        {
            if(text[i].compareToIgnoreCase("sur")==0)
            {
                dont=true;
                indice=i+1;
                break;
            }
        }
        if(!dont)
        {
            throw new Exception("Il n'y a pas de condition");
        }
        ArrayList recep=new ArrayList();
        for(int i=indice;i<text.length && text[i].compareToIgnoreCase("dont")!=0;i++)
        {
            if(text[i].charAt(0)=='(')
            {
                if(text[i].length()==1)
                {
                    recep.add(text[i]);
                }
                else
                {
                    recep.add("(");
                    recep.add(text[i].replace("(",""));
                }
                i++;
                while(text[i].charAt(text[i].length()-1)!=')')
                {
                    recep.add(text[i]);
                    i++;
                }
                if(text[i].length()==1)
                {
                    recep.add(text[i]);
                }
                else
                {
                    recep.add(text[i].replace(")",""));
                    recep.add(")");
                }
            }
            else
            {
                recep.add(text[i]);
            }
        }
        String[] valiny=new String[recep.size()];
        for(int i=0;i<recep.size();i++)
        {
            valiny[i]=(String)recep.get(i);
        }
        return valiny;
    }
    public static String[] getCondition(String[] text)throws Exception
    {
        boolean dont=false;
        int indice=0;
        int ind=0;
        for(int i=0;i<text.length;i++)
        {
            if(text[i].compareToIgnoreCase("dont")==0)
            {
                dont=true;
                indice=i+1;
                break;
            }
        }
        if(!dont)
        {
            throw new Exception("Il n'y a pas de condition");
        }
        ArrayList recep=new ArrayList();
        for(int i=indice;i<text.length;i++)
        {
            if(text[i].charAt(0)=='(')
            {
                if(text[i].length()==1)
                {
                    recep.add(text[i]);
                }
                else
                {
                    recep.add("(");
                    recep.add(text[i].replace("(",""));
                }
                i++;
                while(text[i].charAt(text[i].length()-1)!=')')
                {
                    recep.add(text[i]);
                    i++;
                }
                if(text[i].length()==1)
                {
                    recep.add(text[i]);
                }
                else
                {
                    recep.add(text[i].replace(")",""));
                    recep.add(")");
                }
            }
            else
            {
                recep.add(text[i]);
            }
        }
        String[] valiny=new String[recep.size()];
        for(int i=0;i<recep.size();i++)
        {
            valiny[i]=(String)recep.get(i);
        }
        return valiny;
    }
    public static Class getClassString(String droite,String gauche)
    {
        Class classe=String.class;
        try
        {
            Double.parseDouble(droite);
            Double.parseDouble(gauche);
            return double.class;
        }
        catch(Exception ex)
        {

        }
        try
        {
            LocalDate.parse(droite);
            LocalDate.parse(gauche);
            return LocalDate.class;
        }
        catch(Exception ex)
        {

        }
        return classe;
    }
    public static String[] getTextSelect(String[] utilise,int i)
    {
        ArrayList list=new ArrayList();
        int countPar=1;
        for(;true;i++)
        {
            if(utilise[i].charAt(0)=='(')
            {
                countPar++;
            }
            if(utilise[i].charAt(utilise[i].length()-1)==')')
            {
                countPar--;
            }
            if(countPar==0)
            {
                break;
            }
            list.add(utilise[i]);
        }
        String[] valiny=new String[list.size()];
        for(i=0;i<list.size();i++)
        {
            valiny[i]=(String)list.get(i);
        }
        return valiny;
    }
    public static boolean compareNumber(String links,String rechts,String sign)
    {
        double gauche=0,droite=0;
        try
        {
            gauche=Double.parseDouble(links);
            droite=Double.parseDouble(rechts);
            if(sign.compareTo("<")==0)
            {
                return gauche<droite;
            }
            if(sign.compareTo(">")==0)
            {
                return gauche>droite;
            }
            if(sign.compareTo("=")==0)
            {
                return gauche==droite;
            }
            if(sign.compareTo("<=")==0)
            {
                return gauche<=droite;
            }
            if(sign.compareTo(">=")==0)
            {
                return gauche>=droite;
            }
            if(sign.compareTo("!=")==0)
            {
                return gauche!=droite;
            }
        }
        catch(Exception ex){
        
        }
        return false;
    }
    public static boolean compareLocalDate(String links,String rechts,String sign)
    {
        LocalDate dateGauche=LocalDate.now();
        LocalDate dateDroite=LocalDate.now();
        try
        {
            dateGauche=LocalDate.parse(links);
            dateDroite=LocalDate.parse(rechts);
            if(sign.compareTo("<")==0)
            {
                return dateGauche.isBefore(dateDroite);
            }
            if(sign.compareTo(">")==0)
            {
                return dateGauche.isAfter(dateDroite);
            }
            if(sign.compareTo("=")==0)
            {
                return dateGauche.equals(dateDroite);
            }
            if(sign.compareTo("<=")==0)
            {
                return dateGauche.equals(dateDroite)||dateGauche.isBefore(dateDroite);
            }
            if(sign.compareTo(">=")==0)
            {
                return dateGauche.equals(dateDroite)||dateGauche.isAfter(dateDroite);
            }
            if(sign.compareTo("!=")==0)
            {
                return !dateGauche.equals(dateDroite);
            }
        }
        catch(Exception ex){
        
        }
        return false;
    }
    public static boolean compareString(String links,String rechts,String sign)
    {
        if(sign.compareTo("=")==0)
        {
            return rechts.compareTo(links)==0;
        }
        if(sign.compareTo("!=")==0)
        {
            return rechts.compareTo(links)!=0;
        }
        return false;
    }
    public static boolean parseBoolean(String[] utilise,Database base)throws Exception
    {
        ArrayList right=new ArrayList();
        ArrayList left=new ArrayList();
        ArrayList signe=new ArrayList();
        ArrayList etOu=new ArrayList();
        ArrayList classe=new ArrayList();
        ArrayList conditionPar=new ArrayList();
        ArrayList condicions=new ArrayList();
        ArrayList zustand=new ArrayList();
        ArrayList relations=new ArrayList();
        Class type=String.class;
        String rechts="",links="",sign="";
        boolean valiny=true;
        Relation relation=new Relation();
        ArrayList valeur=new ArrayList();
        int indicePar=0;
        int parOuvert=0;
        int indiceSel=0;
        for(int i=0;i<utilise.length;i++)
        {
            if(utilise[i].compareToIgnoreCase("true")==0 || utilise[i].compareToIgnoreCase("false")==0)
            {
                signe.add("no");
                left.add(utilise[i]);
                right.add(utilise[i]);
                classe.add(Boolean.class);
            }
            if(utilise[i].compareToIgnoreCase("dans")==0)
            {
                signe.add(utilise[i]);
                left.add(utilise[i-1]);
                right.add(utilise[i+1]);
                boolean parouver=true;
                try
                {
                    relation=Prendre.getSelect(Condition.getTextSelect(utilise,i+2),base);
                    relations.add(relation);
                }
                catch(Exception ex)
                {
                    throw ex;
                }
                i+=2;
                classe.add(Relation.class);
                try
                {
                    while(utilise[i].charAt(utilise[i].length()-1)!=')' || !parouver)
                    {
                        if(utilise[i].charAt(0)=='(')
                        {
                            indicePar++;
                        }
                        if(utilise[i].charAt(utilise[i].length()-1)==')')
                        {
                            indicePar--;
                        }
                        if(indicePar==0)
                        {
                            parouver=true;
                        }
                        else
                        {
                            parouver=false;
                        }
                        i++;
                    }
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
            }
            else if(utilise[i].compareTo("(")==0)
            {
                i++;
                indicePar=0;
                boolean parouver=false;
                try
                {
                    while(utilise[i].charAt(utilise[i].length()-1)!=')' || !parouver)
                    {
                        if(utilise[i].charAt(0)=='(')
                        {
                            indicePar++;
                        }
                        if(utilise[i].charAt(utilise[i].length()-1)==')')
                        {
                            indicePar--;
                        }
                        if(indicePar==0)
                        {
                            parouver=true;
                        }
                        else
                        {
                            parouver=false;
                        }
                        conditionPar.add(utilise[i]);
                        i++;
                    }
                }
                catch(Exception ex)
                {
                    throw new Exception("la parenthese n'a pas ete ferme");
                }
                signe.add("()");
                left.add("(");
                right.add(")");
                classe.add(ArrayList.class);
                condicions.add(conditionPar);
            }
            else if(utilise[i].compareTo("!=")==0 || utilise[i].compareTo("<")==0 || utilise[i].compareTo(">")==0 || utilise[i].compareTo("=")==0 || utilise[i].compareTo(">=")==0 || utilise[i].compareTo("<=")==0)
            {
                if(utilise[i+1].compareToIgnoreCase("true")==0 || utilise[i+1].compareToIgnoreCase("false")==0)
                {
                    throw new Exception("Signe incorrecte avec un boolean");
                }
                if(utilise[i-1].compareToIgnoreCase("true")==0 || utilise[i-1].compareToIgnoreCase("false")==0)
                {
                    throw new Exception("Signe incorrecte avec un boolean");
                }
                signe.add(utilise[i]);
                right.add(utilise[i+1].replace("'",""));
                left.add(utilise[i-1].replace("'",""));
                classe.add(Condition.getClassString(utilise[i+1].replace("'",""),utilise[i-1].replace("'","")));
            }
            else if(utilise[i].compareToIgnoreCase("et")==0 || utilise[i].compareToIgnoreCase("ou")==0)
            {
                etOu.add(utilise[i]);
            }
        }
        boolean[] conditions=new boolean[etOu.size()+1];
        for(int i=0;i<classe.size();i++)
        {
            type=(Class)classe.get(i);
            rechts=(String)right.get(i);
            links=(String)left.get(i);
            sign=(String)signe.get(i);
            if(type==Boolean.class)
            {
                try
                {
                    conditions[i]=Boolean.parseBoolean(rechts);
                }catch(Exception ex)
                {

                }
            }
            if(type==Relation.class)
            {
                relation=(Relation)relations.get(indiceSel);
                boolean reponse=false;
                for(int e=0;e<relation.getValeur().size();e++)
                {
                    valeur=(ArrayList)relation.getValeur().get(e);
                    rechts=valeur.get(0).toString();
                    if(valeur.get(0).getClass()==LocalDate.class)
                    {
                        if(Condition.compareLocalDate(links, rechts, "="))
                        {
                            reponse=true;
                            break;
                        }
                    }
                    else if(valeur.get(0).getClass()==String.class)
                    {
                        if(Condition.compareString(links, rechts, "="))
                        {
                            reponse=true;
                            break;
                        }
                    }
                    else
                    {
                        if(Condition.compareNumber(links, rechts, "="))
                        {
                            reponse=true;
                            break;
                        }
                    }
                }
                conditions[i]=reponse;
                indiceSel++;
            }
            if(type==ArrayList.class)
            {
                zustand=(ArrayList)condicions.get(indicePar);
                String[] newCondition=new String[zustand.size()];
                for(int e=0;e<zustand.size();e++)
                {
                    newCondition[e]=(String)zustand.get(e);
                }
                conditions[i]=parseBoolean(newCondition,base);
                indicePar++;
            }
            if(type==double.class)
            {
                conditions[i]=Condition.compareNumber(links, rechts, sign);
            }
            if(type==LocalDate.class)
            {
                conditions[i]=Condition.compareLocalDate(links, rechts, sign);
            }
            if(type==String.class)
            {
                conditions[i]=Condition.compareString(links, rechts, sign);
            }
        }
        valiny=conditions[0];
        for(int i=0;i<etOu.size();i++)
        {
            rechts=(String)etOu.get(i);
            if(rechts.compareToIgnoreCase("ou")==0)
            {
                valiny=valiny||conditions[i+1];
            }
            if(rechts.compareToIgnoreCase("et")==0)
            {
                valiny=valiny&&conditions[i+1];
            }
        }
        return valiny;
    }
    public static boolean isNameattributs(String letter)
    {
        if(letter.charAt(0)=='\'' && letter.charAt(letter.length()-1)=='\'')
        {
            return false;
        }
        if(letter.compareTo("<")==0 || letter.compareTo("<=")==0 || letter.compareTo(">")==0 || letter.compareTo(">=")==0 || letter.compareTo("=")==0 || letter.compareTo("!=")==0)
        {
            return false;
        }
        if(letter.compareToIgnoreCase("et")==0 || letter.compareToIgnoreCase("ou")==0 || letter.compareToIgnoreCase("dans")==0)
        {
            return false;
        }
        if(letter.compareTo("(")==0 || letter.compareTo(")")==0)
        {
            return false;
        }
        if(letter.compareTo("true")==0 || letter.compareTo("false")==0)
        {
            return false;
        }
        if(isANumber(letter))
        {
            return false;
        }
        return true;
    }
    public static Relation applyBoolean(Relation relation,String[] mots,Database base)throws Exception
    {
        String[] texts=new String[mots.length];
        for(int i=0;i<mots.length;i++)
        {
            texts[i]=mots[i];
        }
        int indice=0,j=0;
        int countPar=0;
        ArrayList list=new ArrayList();
        Relation valiny=new Relation(relation.getNom(),relation.getListeAttribut(),relation.getListeDomaine());
        try
        {
            for(int e=0;e<relation.getValeur().size();e++)
            {
                list=(ArrayList)relation.getValeur().get(e);
                for(int i=0;i<mots.length;i++)
                {
                    if(mots[i].compareToIgnoreCase("dans")==0 && mots[i+1].compareToIgnoreCase("(")==0)
                    {
                        for(j=i+2;mots[j].compareTo(")")!=0 && countPar==0;j++)
                        {
                            if(mots[j].compareTo("(")==0)
                            {
                                countPar++;
                            }
                            if(mots[j].compareTo(")")==0)
                            {
                                countPar--;
                            }
                        }
                        i=j;
                    }
                    if(isNameattributs(mots[i]))
                    {
                        indice=relation.getAttribute(mots[i]);
                        if(mots[i].charAt(0)=='!')
                        {
                            if(list.get(indice).toString().compareToIgnoreCase("true")==0)
                            {
                                texts[i]="false";
                            }
                            else
                            {
                                texts[i]="true";
                            }
                        }
                        else
                        {
                            texts[i]=list.get(indice).toString();
                        }
                    }
                }
                if(parseBoolean(texts,base))
                {
                    valiny.addValeur(list);
                }
            }
        }
        catch(Exception ex)
        {
            throw ex;
        }
        return valiny;
    }
}