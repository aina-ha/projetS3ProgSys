package fenetre;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JTabbedPane;
public class ChangeBase implements ChangeListener{
    int selected;
    DataAffichage dataAffichage;
    JTabbedPane p;
    String principal;
    public ChangeBase(String prin,DataAffichage da,JTabbedPane tab)
    {
        this.dataAffichage=da;
        this.p=tab;
        this.principal=prin;
    }
    public void stateChanged(ChangeEvent e) {
        if(this.principal.compareToIgnoreCase("root")==0)
        {
            int selectedIndex = p.getSelectedIndex();
            dataAffichage.setDataUsed(dataAffichage.getDataBaseExistant(selectedIndex));
        }
        else
        {
            int selectedIndex = p.getSelectedIndex();
            dataAffichage.setRelUsed(p.getTitleAt(selectedIndex));
        }
    }
}