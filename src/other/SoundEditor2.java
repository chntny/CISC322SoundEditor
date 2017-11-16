package other;

import soundeditor.SingleWaveFormPanel;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.*;
import java.io.*;
import javax.sound.sampled.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class SoundEditor2 extends JFrame {

    private void drawWave(byte[] data) {

    }

    public static void main(String[] args) {
        JFrame disp = new JFrame("TITLE");
        disp.setTitle("Sound Editor");
        disp.setSize(10000, 800);
        disp.setLocationRelativeTo(null);
        disp.setDefaultCloseOperation(EXIT_ON_CLOSE);
        File file = new File("D:\\Downloads\\siren.wav");
        try {
            AudioInputStream audioIS = AudioSystem.getAudioInputStream(file);
            //codeidol
            int frameLength = (int) audioIS.getFrameLength();
            int frameSize = (int) audioIS.getFormat().getFrameSize();
            byte[] data = new byte[frameLength * frameSize];
            int result = 0;
            result = audioIS.read(data);
            int numChannels = audioIS.getFormat().getChannels();
            //int frameLength = (int) audioIS.getFrameLength();
            long[][] toReturn = new long[numChannels][frameLength];
            int sampleIndex = 0;
            for (int t = 0; t < data.length;) {
                for (int channel = 0; channel < numChannels; channel++) {
                    int low = (int) data[t];
                    t++;
                    int high = (int) data[t];
                    t++;
                    int sample = getSixteenBitSample(high, low);
                    toReturn[channel][sampleIndex] = sample;
                }
                sampleIndex++;
            }
            System.out.println(toReturn.length);
            SingleWaveFormPanel s = new SingleWaveFormPanel(toReturn[0]);
            disp.add(s);
            disp.setVisible(true);
//			for (byte b:eightBitByteArray) {
//				System.out.println(b);
//			}
//			

        } catch (UnsupportedAudioFileException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private static int getSixteenBitSample(int high, int low) {
        high = high << 8;
        high += low & 0x00ff;
        return high;
    }

}
