/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soundeditor;

import ca.queensu.cs.dal.edfmwk.act.DefaultAction;

/**
 *
 * @author User
 */
public abstract class SoundAction extends DefaultAction {
    private SoundAction(){
        super("Sound");
    }
    
    protected SoundAction(String name){
        super(name);
    }
    
    protected abstract void changeSound(SoundContents con, int start, int end, int scale);
    
}
