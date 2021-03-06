/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soundeditor;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Action;

/**
 *
 * @author User
 */
public class AdjustVolumeAction extends SoundAction {

    public AdjustVolumeAction() {
        super("AdjustVolume");
    }

//    @Override
//    public Object getValue(String key) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void putValue(String key, Object value) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void setEnabled(boolean b) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public boolean isEnabled() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void addPropertyChangeListener(PropertyChangeListener listener) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void removePropertyChangeListener(PropertyChangeListener listener) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//    
    @Override
    protected void changeSound(SoundContents con, int start, int end, double scale) {
        long[] tempArray = new long[end - start];
        long[] contentArray = con.getChannel(0);
        int k = -1;
        for (int i = start; i < end ; i++) {
            tempArray[++k] = (long) (contentArray[i] * scale);
            if (tempArray[k] > 0xffff){
                tempArray[k] = 0xffff;
            }
            if (tempArray[k] < -0xffff){
                tempArray[k] = -0xffff;
            }
        }
        con.replace(start, end, tempArray);
    }

}
