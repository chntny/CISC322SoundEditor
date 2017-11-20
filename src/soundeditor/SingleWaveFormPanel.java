package soundeditor;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.*;
import java.io.*;
import javax.sound.sampled.*;

public class SingleWaveFormPanel extends JPanel {

    long[] drawTheseNumbers;
    long max;

    public SingleWaveFormPanel(long[] b) {
        super();
        drawTheseNumbers = b;
        if (drawTheseNumbers != null) {
            findAbsMax();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.BLACK);
        g.setColor(Color.BLUE);
        this.setSize(1000, 400);
        int middleHeight = 200;
        //g.fillRect(23, 23, 20, 30);

        if (drawTheseNumbers != null){
            int sectionLength = drawTheseNumbers.length / 1000;
            for (int i = 0; i < 1000; i++) {
                int start = i * sectionLength;
                int end = start + sectionLength;
                int length = (int) (drawTheseNumbers[maxIndexSection(start, end)] * 200 / max);
                if (length >= 0) {
                    g.fillRect(i, middleHeight, 1, length);
                } else {
                    g.fillRect(i,middleHeight + length, 1, -length);
                }
            }
        }
    }

    private int maxIndexSection(int start, int end) {
        int index = start;
        long maxVal = 0;
        for (int i = start; i < end; i++) {
            if (Math.abs(drawTheseNumbers[i]) > maxVal) {
                maxVal = Math.abs(drawTheseNumbers[i]);
                index = i;
            }
        }
        return index;
    }

    void setDocument(SoundContents contents) {
        if (contents.getChannels() == 0) {
            drawTheseNumbers = null;
        } else {
            drawTheseNumbers = contents.getChannel(0);
        }
    }

    private void findAbsMax() {
        max = 0;
        for (long l : drawTheseNumbers) {
            max = Math.max(max, Math.abs(l));
        }
        if (max == 0) {
            max = 1;
        }
    }

}
