/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soundeditor;

import ca.queensu.cs.dal.edfmwk.doc.AbstractDocument;
import ca.queensu.cs.dal.edfmwk.doc.DocumentType;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.event.DocumentEvent;

/**
 *
 * @author Tony
 */
public class SoundDocument
        extends AbstractDocument
        implements javax.swing.event.DocumentListener {

    private SoundContents contents;

    public SoundDocument(DocumentType dt) {
        super(dt);
        System.out.println("new");
        contents = new SoundContents();
        window = new SingleWaveFormPanel(null);
    }

    @Override
    public void insertUpdate(DocumentEvent de) {
        setChanged();
    }

    @Override
    public void removeUpdate(DocumentEvent de) {
        setChanged();
    }

    @Override
    public void changedUpdate(DocumentEvent de) {
        setChanged();
    }

    @Override
    public void save(OutputStream out) throws IOException {
        contents.save(out);
        setChanged(false);
    }

    SoundContents getContents() {
        return contents;
    }

    @Override
    public void open(InputStream in) throws IOException {
        System.out.println("open");
        try {
            contents.open(AudioSystem.getAudioInputStream(in));
            window = new SingleWaveFormPanel(contents.sampleArray[0]);
            setChanged(false);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(SoundDocument.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
