package soundeditor;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.*;
import java.io.*;
import javax.sound.sampled.*;

public class SingleWaveFormPanel extends JPanel {

    long[] drawTheseNumbers;

    public SingleWaveFormPanel(long[] b) {
        super();
        drawTheseNumbers = b;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.BLACK);
        g.setColor(Color.BLUE);
        this.setSize(5000, 800);
        int middleHeight = 400;
        //g.fillRect(23, 23, 20, 30);
        if (drawTheseNumbers != null) {
            for (int i = 0; i < drawTheseNumbers.length; i++) {
                g.fillRect(i, middleHeight, 1, (int) (drawTheseNumbers[i] / 100));
            }
        }
    }

    void setDocument(SoundContents contents) {
        if (contents.getChannels() == 0){
            drawTheseNumbers = null;
        } else {
            drawTheseNumbers = contents.getChannel(0);
        }
    }

}
