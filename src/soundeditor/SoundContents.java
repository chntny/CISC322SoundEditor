/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soundeditor;

import ca.queensu.cs.dal.edfmwk.doc.DocumentException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.sound.sampled.AudioFileFormat.Type;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

/**
 *
 * @author User
 */
public class SoundContents {

    private int frameLength;
    private int frameSize;
    private int numChannels;
    private float frameRate;
    private boolean bigEndian;
    private long[][] sampleArray;

    void open(AudioInputStream in) throws IOException {
        try {
            frameLength = (int) in.getFrameLength();
            frameSize = in.getFormat().getFrameSize();
            numChannels = in.getFormat().getChannels();
            frameRate = in.getFormat().getFrameRate();
            bigEndian = in.getFormat().isBigEndian();
            System.out.println(in.getFormat());
            System.out.println(frameLength);
//            System.out.println("Number of channels " + Integer.toString(numChannels));
//            System.out.println("FrameSize is " + Integer.toString(frameSize));
            if (frameSize != 2 * numChannels) {
                throw new IOException("Samples not 16 bit");
            }
            if (bigEndian) {
                throw new IOException("Samples not little-Endian");
            }
            byte[] data = new byte[frameLength * frameSize];
            in.read(data);
//            printArray(data,20);
            sampleArray = new long[numChannels][frameLength];
            int sampleIndex = 0;
            for (int t = 0; t < data.length;) {
                for (int channel = 0; channel < numChannels; channel++) {
                    byte low = data[t++];
                    //System.out.print("low: ");
                    //System.out.println(low);
                    byte high = data[t++];
                    //System.out.print("high: ");
                    //System.out.println(high);
                    long sample = getSixteenBitSample(high, low);
                    //System.out.print("sample: ");
                    //System.out.println(sample);
                    sampleArray[channel][sampleIndex] = sample;
                    //System.out.println(sampleArray[channel][sampleIndex]);
                }
                sampleIndex++;
            }
//            printArray(sampleArray[0],10);
//            printArray(sampleArray[1],10);
        } catch (Exception e) {
            throw new IOException(e.getLocalizedMessage());
        }
    }

    private static long getSixteenBitSample(byte high, byte low) {
        long s = 0;
        s = (long) high;
        s = s << 8;
        s = s | (low & 0xff);
        return s;

    }

    public int getChannels() {
        return numChannels;
    }

    public long[][] getContents() {
        return sampleArray;
    }

    public long[] getChannel(int c) {
        return sampleArray[c];
    }

    private byte[] invertFrame(long s) {
        byte[] frame = new byte[2];
        frame[0] = (byte) (s & 0xff);
        s = s >> 8;
        frame[1] = (byte) (s & 0xff);

//        frame[1] = frame[1] | (i & 0x80);
//        for (int i = 0; i < frame.length; i++) {
//            frame[i] = (byte) (s & 0x00ff);
//            s = s >> 8;
//        }
        return frame;
    }

    public static void printArray(byte[] array, int max) {
        for (int i = 0; i < max; i++) {
            System.out.println(Byte.toString(array[i]));
        }
        System.out.println();
    }

    public static void printArray(long[] array, int max) {
        for (int i = 0; i < max; i++) {
            System.out.println(Long.toBinaryString(array[i]));
        }
        System.out.println();
    }

    byte[] invertSampleArray() {
        byte[] data = new byte[frameLength * frameSize];
        int dataIndex = 0;
        for (int col = 0; col < sampleArray[0].length; col++) {
            for (int row = 0; row < sampleArray.length; row++) {
                byte[] frame = invertFrame(sampleArray[row][col]);
                for (int i = 0; i < frame.length; i++) {
                    data[dataIndex++] = frame[i];
                }
            }
        }
        return data;
    }

    void save(OutputStream out) {
        AudioFormat format = new AudioFormat(frameRate, 16, numChannels, true, false);
        System.out.print("AudioFormat format: ");
        System.out.println(format);
        byte[] invertedData = invertSampleArray();
        System.out.println("");
//        printArray(invertedData, 20);

        ByteArrayInputStream bAIS = new ByteArrayInputStream(invertedData);

//        if (bAIS==null){
//            System.out.println("bAIS is NULL");
//        }
//        System.out.println("ck4");
//        System.out.println(bAIS);
        AudioInputStream stream = new AudioInputStream(bAIS, format, frameLength);
//        if (bAIS==null){
//            System.out.println("stream is NULL");
//        }
//        System.out.println("ck5");
//        System.out.println(stream);
        try {
            AudioSystem.write(stream, Type.WAVE, out);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        try{
//            stream = AudioSystem.getAudioInputStream(bAIS);
//        }catch(UnsupportedAudioFileException e){
//            System.out.println("ck6");
//            e.printStackTrace();
//        }catch (IOException e) {
//            System.out.println("ck7");
//            e.printStackTrace();
//        }
//        System.out.println("ck7");
        //(1) https://stackoverflow.com/questions/10991391/saving-audio-byte-to-a-wav-file
//        InputStream b_in = new ByteArrayInputStream(invertedData);
//        try{
//        DataOutputStream dos = new DataOutputStream(new FileOutputStream("C:\\filename.bin"));
//        dos.write(invertedData);
//        AudioFormat format = new AudioFormat(frameRate, 8*frameSize, numChannels, true, false);
//        AudioInputStream stream = new AudioInputStream(b_in, format,invertedData.length);
//        File file = new File("C:\\file.wav");
//        AudioSystem.write(stream, Type.WAVE, file);
//        }catch(Exception e){
//            System.out.println("Exception in save (1)");
//        }
        // end (1)
//        writeToFile(invertedData);
//        System.out.println("ck4");
//(2) https://stackoverflow.com/questions/17060346/how-to-convert-a-byte-array-into-an-audioinputstream-in-java
//        ByteArrayInputStream bAIS = new ByteArrayInputStream(invertedData);
//        
//        AudioInputStream in=null;
//        try {
//            in = AudioSystem.getAudioInputStream(bAIS);
//        } catch (UnsupportedAudioFileException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        //return in
        //end (2)
//        try{
//            in = AudioSystem.getAudioInputStream(bAIS);
//
//            //out.write(invertedData);
//        }
//        catch(Exception e){
//            System.out.println("Exception in save");
//        }
//        WaveFileWriter writer = new WaveFileWriter();
//        writer.write(in, AudioFileFormat.Type.WAVE, out);
        //AudioSystem.getAudioInputStream();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void replace(int start, int end, long[] givenArray) {
        System.out.println("replace");
        long[][] newSampleArray = new long[sampleArray.length][sampleArray[0].length - (end - start) + givenArray.length];
        for (int c = 0; c < sampleArray.length; c++) {
            int k = 0;
            for (int i = 0; i < start;) {
                newSampleArray[c][k++] = sampleArray[c][i++];
            }
            for (int i = 0; i < givenArray.length;) {
                newSampleArray[c][k++] = givenArray[i++];
            }
            for (int i = end; i < sampleArray[0].length;) {
                newSampleArray[c][k++] = sampleArray[c][i++];
            }
        }
        sampleArray = newSampleArray;
        frameLength = sampleArray[0].length;
        
    }

//        for (int i = 0; i < newSampleArray.length; i++) {
//            for (int j = 0; j < newSampleArray[0].length; j++) {
//                if (start <= j && j < end) {
//                    newSampleArray[i][j] = givenArray[j - start];
//                } else {
//                    newSampleArray[i][j] = sampleArray[i][j];
//                }
//            }
//        }//end outter for
//    sampleArray  = newSampleArray;
}//end public void replace
