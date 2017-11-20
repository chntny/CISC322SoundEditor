/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soundeditor;

import ca.queensu.cs.dal.edfmwk.doc.DocumentException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.sound.sampled.AudioInputStream;

/**
 *
 * @author User
 */
public class SoundContents {
    private int frameLength;
    private int frameSize;
    private int numChannels;
    long[][] sampleArray;

    void open(AudioInputStream in) throws IOException {
        try {
            frameLength = (int) in.getFrameLength();
            frameSize = in.getFormat().getFrameSize();
            numChannels = in.getFormat().getChannels();
            System.out.println("Number of channels " + Integer.toString(numChannels));
            System.out.println("FrameSize is "+Integer.toString(frameSize));
            if (frameSize != 2*numChannels) {
                throw new IOException("Samples not 16 bit");
            }
            byte[] data = new byte[frameLength * frameSize];
            in.read(data);
            sampleArray = new long[numChannels][frameLength];
            int sampleIndex = 0;
            for (int t = 0; t < data.length;) {
                for (int channel = 0; channel < numChannels; channel++) {
                    int low = (int) data[t++];
                    //System.out.print("low: ");
                    //System.out.println(low);
                    int high = (int) data[t++];
                    //System.out.print("high: ");
                    //System.out.println(high);
                    int sample = getSixteenBitSample(high, low);
                    //System.out.print("sample: ");
                    //System.out.println(sample);
                    sampleArray[channel][sampleIndex] = sample;
                    //System.out.println(sampleArray[channel][sampleIndex]);
                }
                sampleIndex++;
            }
        } catch (Exception e) {
            
            throw new IOException(e.getLocalizedMessage());
        }
    }

    private static int getSixteenBitSample(int high, int low) {
        high = high << 8;
        high = high | low;
        return high;
    }
    
    void save(OutputStream out) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public int getChannels(){
        return numChannels;
    }

    public long[] getChannel(int c){
        return sampleArray[c];
    }
}
