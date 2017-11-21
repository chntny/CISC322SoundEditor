/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soundeditor;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.*;

/**
 *
 * @author User
 */
public class LowPassAction extends SoundAction {

    public LowPassAction() {
        super("LowPass");
    }

    @Override
    protected void changeSound(SoundContents con, int start, int end, double scale) {
        long[] sampleArray = con.getChannel(0);
        double[] doubleCon = new double[end - start];
        int k = 0;
        for (int i = start; i< end; i++){
            doubleCon[k++] = (double) sampleArray[i];
        }
        double[] result = fourierLowPassFilter(doubleCon, scale, con.getFrameRate());
        long[] resultLong = new long[end-start];
        for (int i = 0; i< result.length; i++){
            resultLong[i] = (long) result[i];
        }
        con.replace(start,end,resultLong);
        
    }

    public double[] fourierLowPassFilter(double[] data, double lowPass, double frequency) {
        //data: input data, must be spaced equally in time.
        //lowPass: The cutoff frequency at which 
        //frequency: The frequency of the input data.

        //The apache Fft (Fast Fourier Transform) accepts arrays that are powers of 2.
        int minPowerOf2 = 1;
        while (minPowerOf2 < data.length) {
            minPowerOf2 = 2 * minPowerOf2;
        }

        //pad with zeros
        double[] padded = new double[minPowerOf2];
        for (int i = 0; i < data.length; i++) {
            padded[i] = data[i];
        }

        FastFourierTransformer transformer = new FastFourierTransformer(DftNormalization.STANDARD);
        Complex[] fourierTransform = transformer.transform(padded, TransformType.FORWARD);

        //build the frequency domain array
        double[] frequencyDomain = new double[fourierTransform.length];
        for (int i = 0; i < frequencyDomain.length; i++) {
            frequencyDomain[i] = frequency * i / (double) fourierTransform.length;
        }

        //build the classifier array, 2s are kept and 0s do not pass the filter
        double[] keepPoints = new double[frequencyDomain.length];
        keepPoints[0] = 1;
        for (int i = 1; i < frequencyDomain.length; i++) {
            if (frequencyDomain[i] < lowPass) {
                keepPoints[i] = 2;
            } else {
                keepPoints[i] = 0;
            }
        }

        //filter the fft
        for (int i = 0; i < fourierTransform.length; i++) {
            fourierTransform[i] = fourierTransform[i].multiply((double) keepPoints[i]);
        }

        //invert back to time domain
        Complex[] reverseFourier = transformer.transform(fourierTransform, TransformType.INVERSE);

        //get the real part of the reverse 
        double[] result = new double[data.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = reverseFourier[i].getReal();
        }

        return result;
    }

}
