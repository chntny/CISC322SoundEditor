/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soundeditor;

import javax.swing.Action;

/**
 *
 * @author User
 */
public class DeleteAction extends SoundAction {

    public DeleteAction() {
        super("Delete");
    }

    @Override
    protected void changeSound(SoundContents con, int start, int end, int scale) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
