/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soundeditor;

import ca.queensu.cs.dal.data.tree.TreeException;
import ca.queensu.cs.dal.edfmwk.doc.Document;
import ca.queensu.cs.dal.edfmwk.doc.DocumentType;
import ca.queensu.cs.dal.edfmwk.menu.MenuDescriptor;
import ca.queensu.cs.dal.edfmwk.menu.MenuElement;
import ca.queensu.cs.dal.flex.log.Log;
import java.awt.Component;
import java.util.HashMap;
import javax.swing.Action;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Tony
 */
public class SoundType implements DocumentType {

    public SoundType() {
    }

    public String getName() {
        return "Sound File";
    }

    @Override
    public Document newDocument() {
        return new SoundDocument(this);
    }

    @Override
    public MenuDescriptor getMenu(Document doc) {
        System.out.println("MenuDescriptor");
        MenuDescriptor desc = getStaticMenu().copy();
        return desc;
    }

    private static String[][] actionPairs = {
    };

    private HashMap<Object, Action> actions;

    private Action getNamedAction(Object name) {
        return actions.get(name);
    }



    @Override
    public String[] getExtensions() {
        return extensions;
    }

    private static String[] extensions = {"wav"};

    private MenuDescriptor getStaticMenu() {
        if (menu == null) {
            menu = new MenuDescriptor();
            try {
                menu.addElement(new MenuElement("Edit/Adjust Volume", new AdjustVolumeAction()));
                menu.addElement(new MenuElement("Edit/Transpose", new TransposeAction()));
                menu.addElement(new MenuElement("Edit/Delete", new DeleteAction()));
                menu.addElement(new MenuElement("Edit/Lowpass Filter", new LowPassAction()));

            } catch (Exception e) {
                Log.internalError("Menu element error " + e.getLocalizedMessage());
            }
        }
        return menu;
    }

    private static MenuDescriptor menu;

}
