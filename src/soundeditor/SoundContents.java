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
//            if (frameSize != 2) {
//                throw new IOException("Samples not 16 bit");
//            }
            byte[] data = new byte[frameLength * frameSize];
            in.read(data);
            sampleArray = new long[numChannels][frameLength];
            int sampleIndex = 0;
            long sample;
            for (int t = 0; t < data.length;) {
                for (int channel = 0; channel < numChannels; channel++) {
                    sample = 0;
                    for (int b = 0; b < frameSize; b++) {
                        if (b == 0) {
                            sample += (long) (data[t++] & 0x00ff);
                        } else {
                            long s = (long) data[t++];
                            s = s << 8*b;
                            sample += s;
                        }
                    }
                    sampleArray[channel][sampleIndex] = sample;
                }
                sampleIndex++;
            }
        } catch (Exception e) {
            
            throw new IOException(e.getLocalizedMessage());
        }
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
