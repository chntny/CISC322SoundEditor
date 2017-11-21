/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soundeditor;

import ca.queensu.cs.dal.edfmwk.Application;
import ca.queensu.cs.dal.edfmwk.act.DefaultAction;
import ca.queensu.cs.dal.edfmwk.win.CommonWindow;
import ca.queensu.cs.dal.flex.log.Log;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public abstract class SoundAction extends DefaultAction {

    private SoundAction() {
        super("Sound");
    }
    
    protected SoundAction(String name) {
        super(name);
    }
    
    protected abstract void changeSound(SoundContents con, int start, int end, double scale);
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            Application app = Application.getApplication();
            SoundDocument doc = (SoundDocument) app.getActiveDocument();
            SoundContents con = doc.getContents();
            int start
                    = Integer.parseInt(JOptionPane.showInputDialog(
                            "Input Start Index"));
            int end
                    = Integer.parseInt(JOptionPane.showInputDialog(
                            "Input End Index"));
            double scale = Double.parseDouble(JOptionPane.showInputDialog(
                    "Input Scale Factor"));
            changeSound(con, start, end, scale);
            doc.changedUpdate(null);
//            if (doc.getWindow() == null){
//                System.out.println("window is null");
//            }
//            if (app.getActiveWindow() == null){
//                System.out.println("active window is null");
//            }
//            app.getActiveWindow().setContentPane(doc.getWindow());
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.error("Sound action error: " + ex.getLocalizedMessage());
        }
        
    }
}
