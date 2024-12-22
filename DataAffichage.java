package fenetre;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.util.ArrayList;
import base.Database;
import base.Relation;
public class DataAffichage{
    JTabbedPane root;
    JTabbedPane[] database;
    String[] databaseExistant;
    Database dataUsed;
    String relUsed;
    ArrayList data;
    public DataAffichage(ArrayList list){
        this.data=list;
        this.relUsed="";
        this.database=new JTabbedPane[data.size()];
        this.databaseExistant=new String[data.size()];
        this.root=new JTabbedPane();
        for(int i=0;i<data.size();i++)
        {
            this.database[i]=new JTabbedPane();
            Database dabase=(Database)data.get(i);
            this.databaseExistant[i]=dabase.getNom();
            for(int e=0;e<dabase.getTable().size();e++)
            {
                final Relation relation=(Relation)dabase.getTable().get(e);
                JTable table=new JTable();
                table.setModel(new javax.swing.table.DefaultTableModel(relation.getValeurTab(),relation.getNomAttribut()) {
                    final Class[] types = DataAffichage.type(relation.getListeAttribut().size());
        
                    public Class getColumnClass(int columnIndex) {
                        return types [columnIndex];
                    }
                });
                table.setCellSelectionEnabled(true);
                table.setShowGrid(true);
                JScrollPane jScrollPane=new JScrollPane();
                jScrollPane.setViewportView(table);
                this.database[i].addTab(relation.getNom(),jScrollPane);
            }
            this.database[i].addChangeListener(new ChangeBase("index",this,this.database[i]));
        }
        for(int i=0;i<data.size();i++)
        {
            Database dabase=(Database)data.get(i);
            this.root.addTab(dabase.getNom(),this.database[i]);
        }
        this.dataUsed=(Database)data.get(0);
    }
    public static Class[] type(int nb){
        Class[] valiny=new Class[nb];
        for(int i=0;i<nb;i++){
            valiny[i]=java.lang.String.class;
        }
        return valiny;
    }
    public JTabbedPane getRoot()
    {
        return this.root;
    }
    public void setDataUsed(String d)
    {
        Database da=new Database();
        for(int i=0;i<this.data.size();i++){
            da=(Database)this.data.get(i);
            if(da.getNom().compareToIgnoreCase(d)==0)
            {
                this.dataUsed=da;
                break;
            }
        }
    }
    public String getDataBaseExistant(int i)
    {
        return this.databaseExistant[Math.abs(i%this.databaseExistant.length)];
    }
    public Database getDataUsed()
    {
        return this.dataUsed;
    }
    public void setRelUsed(String d)
    {
        this.relUsed=d;
    }
    public String getRelUsed()
    {
        return this.relUsed;
    }
    public void miseAjour()
    {
        this.database=new JTabbedPane[data.size()];
        this.databaseExistant=new String[data.size()];
        this.root=new JTabbedPane();
        for(int i=0;i<data.size();i++)
        {
            this.database[i]=new JTabbedPane();
            Database dabase=(Database)data.get(i);
            this.databaseExistant[i]=dabase.getNom();
            for(int e=0;e<dabase.getTable().size();e++)
            {
                final Relation relation=(Relation)dabase.getTable().get(e);
                JTable table=new JTable();
                table.setModel(new javax.swing.table.DefaultTableModel(relation.getValeurTab(),relation.getNomAttribut()) {
                    final Class[] types = DataAffichage.type(relation.getListeAttribut().size());
        
                    public Class getColumnClass(int columnIndex) {
                        return types [columnIndex];
                    }
                });
                table.setCellSelectionEnabled(true);
                table.setShowGrid(true);
                JScrollPane jScrollPane=new JScrollPane();
                jScrollPane.setViewportView(table);
                this.database[i].addTab(relation.getNom(),jScrollPane);
                this.database[i].addTab(relation.getNom(),jScrollPane);
            }
        }
        for(int i=0;i<data.size();i++)
        {
            Database dabase=(Database)data.get(i);
            this.root.addTab("Tab",this.database[i]);
            //System.out.println("Tab");
        }
        this.dataUsed=(Database)data.get(0);
    }
}