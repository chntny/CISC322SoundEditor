/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soundeditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.ImageObserver;
import java.io.Writer;
import java.text.AttributedCharacterIterator;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author User
 */
class MainPanel extends JPanel{
    
    private SingleWaveFormPanel mainArea;
    
    public MainPanel(){
        super();
        mainArea = new SingleWaveFormPanel(null);
        JScrollPane sc = new JScrollPane(mainArea);
        add(sc);
        mainArea.setVisible(true);
        
    }
    
    
    
}
