/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soundeditor;

import ca.queensu.cs.dal.edfmwk.Application;
import ca.queensu.cs.dal.edfmwk.Menus;
import ca.queensu.cs.dal.edfmwk.act.NewAction;
import ca.queensu.cs.dal.edfmwk.doc.DocumentManager;
import ca.queensu.cs.dal.edfmwk.doc.DocumentType;
import ca.queensu.cs.dal.edfmwk.menu.MenuDescriptor;
import ca.queensu.cs.dal.edfmwk.menu.MenuElement;

/**
 *
 * @author Tony
 */
public class SoundEditor extends Application {

    private MenuDescriptor menu;
    MainPanel mainPanel;
    private static String title = "Simple Sound File Editor";

    public MenuDescriptor getMainMenu() {
        if (menu == null) {
            menu = new MenuDescriptor(Menus.getStandardMenu());
            try {
                menu.addPath(Menus.getLanguageMenu());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return menu;
    }

    public SoundEditor() {
        super(title);
        mainPanel = new MainPanel();
        DocumentType fac = new SoundType();
        DocumentManager manager = getDocumentManager();
        if (manager != null){
            manager.addExtension(fac);
        }
        
        MenuDescriptor mainMenu = getMainMenu();

//        MenuElement newAction = mainMenu.getElement("New");
//        if (newAction != null){
//            newAction.setAction(new NewAction(fac));
//        }
        setup(mainPanel, mainMenu);
        finishSetup();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new SoundEditor();
    }
    
    public static SoundEditor getApplication(){
        return (SoundEditor) Application.getApplication();
    }

}
