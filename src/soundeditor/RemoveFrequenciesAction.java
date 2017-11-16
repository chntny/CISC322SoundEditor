/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soundeditor;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * @author User
 */
public class RemoveFrequenciesAction extends SoundAction {

    public RemoveFrequenciesAction() {
        super("RemoveFrequencies");
    }

    @Override
    protected void changeSound(SoundContents con, int start, int end, int scale) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
