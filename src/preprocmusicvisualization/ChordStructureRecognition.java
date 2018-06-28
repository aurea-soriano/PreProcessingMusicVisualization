/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package preprocmusicvisualization;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;
import javax.sound.midi.Track;



/**
 *
 * @author aurea
 */
public final class ChordStructureRecognition {

    public static final int NOTE_ON = 0x90;
    public static final int NOTE_OFF = 0x80;
    public static final int PATCH_CHANGE = 0xC0;
    public static final int CONTROLLER = 0xFF;
    public static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
    public List<List<String>> matrixNotes = new ArrayList<>();
    public List<Long> listTicks = new ArrayList<>();
    public List<Integer> histogramNotes = new ArrayList<>();
    public List<String> listMainNotes = new ArrayList<>();
    public List<List<String>> listEquivalenceNotes = new ArrayList<>();
    public List<List<String>> listKeySignatureSharps = new ArrayList<>();
    public List<List<String>> listNamesKeySignatureSharps = new ArrayList<>();
    public List<List<String>> listKeySignatureFlaps = new ArrayList<>();
    public List<List<String>> listNamesKeySignatureFlaps = new ArrayList<>();
    public List<List<String>> listTriads = new ArrayList< >();
    public List<String> listNameTriads = new ArrayList<>();
    public List<String> listNotes = new ArrayList<>();
    public List<String> listNameHarmonicField = new ArrayList<>();
    public List<List<String>> listHarmonicField = new ArrayList< >();
    public List<Integer> listIndexPossibleChords = new ArrayList<>();
    public String tonalityStr = "";
    public Integer ticksPerBeat = 24;
    public boolean boolTicksPerBeat = false;
    public long tickLength;
    public float divisionType;
    public Integer resolution;
    public static final int TIMING_CLOCK = 0xF8;
    public Integer maxNumber = 0;

    public ChordStructureRecognition() {
        createEquivalenceNotes();
        createKeySignature();
        createTriads();
        createListHarmonicField();
    }

    public void createListHarmonicField() {
        //mayores
        // C
        listNameHarmonicField.add("C");
        List<String> harmonicField = new ArrayList<>();
        harmonicField.add("C");
        harmonicField.add("Dm");
        harmonicField.add("Em");
        harmonicField.add("F");
        harmonicField.add("G");
        harmonicField.add("Am");
        harmonicField.add("A#");
        harmonicField.add("C");
        this.listHarmonicField.add(harmonicField);

        // C#
        listNameHarmonicField.add("C#");
        harmonicField = new ArrayList<>();
        harmonicField.add("C#");
        harmonicField.add("D#m");
        harmonicField.add("Fm");
        harmonicField.add("F#");
        harmonicField.add("G#");
        harmonicField.add("A#m");
        harmonicField.add("B");
        harmonicField.add("C#");
        this.listHarmonicField.add(harmonicField);

        // D
        listNameHarmonicField.add("D");
        harmonicField = new ArrayList<>();
        harmonicField.add("D");
        harmonicField.add("Em");
        harmonicField.add("F#m");
        harmonicField.add("G");
        harmonicField.add("A");
        harmonicField.add("Bm");
        harmonicField.add("C");
        harmonicField.add("D");
        this.listHarmonicField.add(harmonicField);

        // D#
        listNameHarmonicField.add("D#");
        harmonicField = new ArrayList<>();
        harmonicField.add("D#");
        harmonicField.add("Fm");
        harmonicField.add("Gm");
        harmonicField.add("G#");
        harmonicField.add("A#");
        harmonicField.add("Cm");
        harmonicField.add("C#");
        harmonicField.add("D#");
        this.listHarmonicField.add(harmonicField);

        // E
        listNameHarmonicField.add("E");
        harmonicField = new ArrayList<>();
        harmonicField.add("E");
        harmonicField.add("F#m");
        harmonicField.add("G#m");
        harmonicField.add("A");
        harmonicField.add("B");
        harmonicField.add("C#m");
        harmonicField.add("D");
        harmonicField.add("E");
        this.listHarmonicField.add(harmonicField);

        // F
        listNameHarmonicField.add("F");
        harmonicField = new ArrayList<>();
        harmonicField.add("F");
        harmonicField.add("Gm");
        harmonicField.add("Am");
        harmonicField.add("A#");
        harmonicField.add("C");
        harmonicField.add("Dm");
        harmonicField.add("D#");
        harmonicField.add("F");
        this.listHarmonicField.add(harmonicField);

        // F#
        listNameHarmonicField.add("F#");
        harmonicField = new ArrayList<>();
        harmonicField.add("F#");
        harmonicField.add("G#m");
        harmonicField.add("A#m");
        harmonicField.add("B");
        harmonicField.add("C#");
        harmonicField.add("D#m");
        harmonicField.add("E");
        harmonicField.add("F#");
        this.listHarmonicField.add(harmonicField);

        // G
        listNameHarmonicField.add("G");
        harmonicField = new ArrayList<>();
        harmonicField.add("G");
        harmonicField.add("Am");
        harmonicField.add("Bm");
        harmonicField.add("C");
        harmonicField.add("D");
        harmonicField.add("Em");
        harmonicField.add("F");
        harmonicField.add("G");
        this.listHarmonicField.add(harmonicField);

        // G#
        listNameHarmonicField.add("G#");
        harmonicField = new ArrayList<>();
        harmonicField.add("G#");
        harmonicField.add("A#m");
        harmonicField.add("Cm");
        harmonicField.add("C#");
        harmonicField.add("D#");
        harmonicField.add("Fm");
        harmonicField.add("F#");
        harmonicField.add("G#");
        this.listHarmonicField.add(harmonicField);

        // A
        listNameHarmonicField.add("A");
        harmonicField = new ArrayList<>();
        harmonicField.add("A");
        harmonicField.add("Bm");
        harmonicField.add("C#m");
        harmonicField.add("D");
        harmonicField.add("E");
        harmonicField.add("F#m");
        harmonicField.add("G");
        harmonicField.add("A");
        this.listHarmonicField.add(harmonicField);


        // A#
        listNameHarmonicField.add("A#");
        harmonicField = new ArrayList<>();
        harmonicField.add("A#");
        harmonicField.add("Cm");
        harmonicField.add("Dm");
        harmonicField.add("D#");
        harmonicField.add("F");
        harmonicField.add("Gm");
        harmonicField.add("G#");
        harmonicField.add("A#");
        this.listHarmonicField.add(harmonicField);

        // B
        listNameHarmonicField.add("B");
        harmonicField = new ArrayList<>();
        harmonicField.add("B");
        harmonicField.add("C#m");
        harmonicField.add("D#m");
        harmonicField.add("E");
        harmonicField.add("F#");
        harmonicField.add("G#m");
        harmonicField.add("A");
        harmonicField.add("B");
        this.listHarmonicField.add(harmonicField);


        //mayores
        // Cm
        listNameHarmonicField.add("Cm");
        harmonicField = new ArrayList<>();
        harmonicField.add("Cm");
        harmonicField.add("C#");
        harmonicField.add("D#");
        harmonicField.add("Fm");
        harmonicField.add("Gm");
        harmonicField.add("G#");
        harmonicField.add("A#");
        harmonicField.add("Cm");
        this.listHarmonicField.add(harmonicField);

        // C#m
        listNameHarmonicField.add("C#m");
        harmonicField = new ArrayList<>();
        harmonicField.add("C#m");
        harmonicField.add("D");
        harmonicField.add("E");
        harmonicField.add("F#m");
        harmonicField.add("G#m");
        harmonicField.add("A");
        harmonicField.add("B");
        harmonicField.add("C#m");
        this.listHarmonicField.add(harmonicField);

        // Dm
        listNameHarmonicField.add("Dm");
        harmonicField = new ArrayList<>();
        harmonicField.add("Dm");
        harmonicField.add("D#");
        harmonicField.add("F");
        harmonicField.add("Gm");
        harmonicField.add("Am");
        harmonicField.add("A#");
        harmonicField.add("C");
        harmonicField.add("Dm");
        this.listHarmonicField.add(harmonicField);

        // D#m
        listNameHarmonicField.add("D#m");
        harmonicField = new ArrayList<>();
        harmonicField.add("D#m");
        harmonicField.add("E");
        harmonicField.add("F#");
        harmonicField.add("G#m");
        harmonicField.add("A#m");
        harmonicField.add("B");
        harmonicField.add("C#");
        harmonicField.add("D#m");
        this.listHarmonicField.add(harmonicField);

        // Em
        listNameHarmonicField.add("Em");
        harmonicField = new ArrayList<>();
        harmonicField.add("Em");
        harmonicField.add("F");
        harmonicField.add("G");
        harmonicField.add("Am");
        harmonicField.add("Bm");
        harmonicField.add("C");
        harmonicField.add("D");
        harmonicField.add("Em");
        this.listHarmonicField.add(harmonicField);

        // Fm
        listNameHarmonicField.add("Fm");
        harmonicField = new ArrayList<>();
        harmonicField.add("Fm");
        harmonicField.add("F#");
        harmonicField.add("G#");
        harmonicField.add("A#m");
        harmonicField.add("Cm");
        harmonicField.add("C#");
        harmonicField.add("D#");
        harmonicField.add("Fm");
        this.listHarmonicField.add(harmonicField);

        // F#m
        listNameHarmonicField.add("F#m");
        harmonicField = new ArrayList<>();
        harmonicField.add("F#m");
        harmonicField.add("G");
        harmonicField.add("A");
        harmonicField.add("Bm");
        harmonicField.add("C#m");
        harmonicField.add("D");
        harmonicField.add("E");
        harmonicField.add("F#m");
        this.listHarmonicField.add(harmonicField);

        // Gm
        listNameHarmonicField.add("Gm");
        harmonicField = new ArrayList<>();
        harmonicField.add("Gm");
        harmonicField.add("G#");
        harmonicField.add("A#");
        harmonicField.add("Cm");
        harmonicField.add("Dm");
        harmonicField.add("D#");
        harmonicField.add("F");
        harmonicField.add("Gm");
        this.listHarmonicField.add(harmonicField);

        // G#m
        listNameHarmonicField.add("G#m");
        harmonicField = new ArrayList<>();
        harmonicField.add("G#m");
        harmonicField.add("A");
        harmonicField.add("B");
        harmonicField.add("C#m");
        harmonicField.add("D#m");
        harmonicField.add("E");
        harmonicField.add("F#");
        harmonicField.add("G#m");
        this.listHarmonicField.add(harmonicField);

        // Am
        listNameHarmonicField.add("Am");
        harmonicField = new ArrayList<>();
        harmonicField.add("Am");
        harmonicField.add("A#");
        harmonicField.add("C");
        harmonicField.add("Dm");
        harmonicField.add("Em");
        harmonicField.add("F");
        harmonicField.add("G");
        harmonicField.add("Am");
        this.listHarmonicField.add(harmonicField);

        // A#m
        listNameHarmonicField.add("A#m");
        harmonicField = new ArrayList<>();
        harmonicField.add("A#m");
        harmonicField.add("B");
        harmonicField.add("C#");
        harmonicField.add("D#m");
        harmonicField.add("Fm");
        harmonicField.add("F#");
        harmonicField.add("G#");
        harmonicField.add("A#m");
        this.listHarmonicField.add(harmonicField);

        // Bm
        listNameHarmonicField.add("Bm");
        harmonicField = new ArrayList<>();
        harmonicField.add("Bm");
        harmonicField.add("C");
        harmonicField.add("D");
        harmonicField.add("Em");
        harmonicField.add("F#m");
        harmonicField.add("G");
        harmonicField.add("A");
        harmonicField.add("Bm");
        this.listHarmonicField.add(harmonicField);


    }

    public void createTriads() {
        //mayores
        // C
        listNameTriads.add("C");
        List<String> triad = new ArrayList<>();
        triad.add("C");
        triad.add("E");
        triad.add("G");
        this.listTriads.add(triad);

        // C#
        listNameTriads.add("C#");
        triad = new ArrayList<>();
        triad.add("C#");
        triad.add("F");
        triad.add("G#");
        this.listTriads.add(triad);

        // D
        listNameTriads.add("D");
        triad = new ArrayList<>();
        triad.add("D");
        triad.add("F#");
        triad.add("A");
        this.listTriads.add(triad);

        // D#
        listNameTriads.add("D#");
        triad = new ArrayList<>();
        triad.add("D#");
        triad.add("G");
        triad.add("A#");
        this.listTriads.add(triad);

        // E
        listNameTriads.add("E");
        triad = new ArrayList<>();
        triad.add("E");
        triad.add("G#");
        triad.add("B");
        this.listTriads.add(triad);

        // F
        listNameTriads.add("F");
        triad = new ArrayList<>();
        triad.add("F");
        triad.add("A");
        triad.add("C");
        this.listTriads.add(triad);

        // F#
        listNameTriads.add("F#");
        triad = new ArrayList<>();
        triad.add("F#");
        triad.add("A#");
        triad.add("C#");
        this.listTriads.add(triad);

        // G
        listNameTriads.add("G");
        triad = new ArrayList<>();
        triad.add("G");
        triad.add("B");
        triad.add("D");
        this.listTriads.add(triad);

        // G#
        listNameTriads.add("G#");
        triad = new ArrayList<>();
        triad.add("G#");
        triad.add("C");
        triad.add("D#");
        this.listTriads.add(triad);

        // A
        listNameTriads.add("A");
        triad = new ArrayList<>();
        triad.add("A");
        triad.add("C#");
        triad.add("E");
        this.listTriads.add(triad);


        // A#
        listNameTriads.add("A#");
        triad = new ArrayList<>();
        triad.add("A#");
        triad.add("D");
        triad.add("F");
        this.listTriads.add(triad);

        // B
        listNameTriads.add("B");
        triad = new ArrayList<>();
        triad.add("B");
        triad.add("D#");
        triad.add("F#");
        this.listTriads.add(triad);


        //mayores
        // Cm
        listNameTriads.add("Cm");
        triad = new ArrayList<>();
        triad.add("C");
        triad.add("D#");
        triad.add("G");
        this.listTriads.add(triad);

        // C#m
        listNameTriads.add("C#m");
        triad = new ArrayList<>();
        triad.add("C#");
        triad.add("E");
        triad.add("G#");
        this.listTriads.add(triad);

        // Dm
        listNameTriads.add("Dm");
        triad = new ArrayList<>();
        triad.add("D");
        triad.add("F");
        triad.add("A");
        this.listTriads.add(triad);

        // D#m
        listNameTriads.add("D#m");
        triad = new ArrayList<>();
        triad.add("D#");
        triad.add("F#");
        triad.add("A#");
        this.listTriads.add(triad);

        // Em
        listNameTriads.add("Em");
        triad = new ArrayList<>();
        triad.add("E");
        triad.add("G");
        triad.add("B");
        this.listTriads.add(triad);

        // Fm
        listNameTriads.add("Fm");
        triad = new ArrayList<>();
        triad.add("F");
        triad.add("G#");
        triad.add("C");
        this.listTriads.add(triad);

        // F#m
        listNameTriads.add("F#m");
        triad = new ArrayList<>();
        triad.add("F");
        triad.add("G#");
        triad.add("C");
        this.listTriads.add(triad);

        // Gm
        listNameTriads.add("Gm");
        triad = new ArrayList<>();
        triad.add("G");
        triad.add("A#");
        triad.add("D");
        this.listTriads.add(triad);

        // G#m
        listNameTriads.add("G#m");
        triad = new ArrayList<>();
        triad.add("G#");
        triad.add("B");
        triad.add("D#");
        this.listTriads.add(triad);

        // Am
        listNameTriads.add("Am");
        triad = new ArrayList<>();
        triad.add("A");
        triad.add("C");
        triad.add("E");
        this.listTriads.add(triad);

        // A#m
        listNameTriads.add("A#m");
        triad = new ArrayList<>();
        triad.add("A#");
        triad.add("C#");
        triad.add("F");
        this.listTriads.add(triad);

        // Bm
        listNameTriads.add("Bm");
        triad = new ArrayList<>();
        triad.add("B");
        triad.add("D");
        triad.add("F#");
        this.listTriads.add(triad);


        //séptimas
        // C7
        listNameTriads.add("C7");
        triad = new ArrayList<>();
        triad.add("E");
        triad.add("C");
        triad.add("A#");
        triad.add("G#");
        this.listTriads.add(triad);

        // D7
        listNameTriads.add("D7");
        triad = new ArrayList<>();
        triad.add("F#");
        triad.add("C");
        triad.add("A");
        triad.add("D");
        this.listTriads.add(triad);

        // E7
        listNameTriads.add("E7");
        triad = new ArrayList<>();
        triad.add("E");
        triad.add("B");
        triad.add("G#");
        triad.add("D");
        this.listTriads.add(triad);

        // G7
        listNameTriads.add("G7");
        triad = new ArrayList<>();
        triad.add("F");
        triad.add("B");
        triad.add("G");
        triad.add("D");
        this.listTriads.add(triad);

        // A7
        listNameTriads.add("A7");
        triad = new ArrayList<>();
        triad.add("C#");
        triad.add("G");
        triad.add("E");
        triad.add("A");
        this.listTriads.add(triad);

        // B7
        listNameTriads.add("B7");
        triad = new ArrayList<>();
        triad.add("F#");
        triad.add("B");
        triad.add("A");
        triad.add("R#");
        this.listTriads.add(triad);

        /*
         //Sus4
         // CSus4
         listNameTriads.add("CSus4");
         triad = new ArrayList<>();
         triad.add("C");
         triad.add("F");
         triad.add("G");
         this.listTriads.add(triad);

         // C#Sus4
         listNameTriads.add("C#Sus4");
         triad = new ArrayList<>();
         triad.add("C#");
         triad.add("F#");
         triad.add("G#");
         this.listTriads.add(triad);

         //DSus4 
         listNameTriads.add("DSus4");
         triad = new ArrayList<>();
         triad.add("D");
         triad.add("G");
         triad.add("A");
         this.listTriads.add(triad);

         // D#Sus4
         listNameTriads.add("D#Sus4");
         triad = new ArrayList<>();
         triad.add("D#");
         triad.add("G#");
         triad.add("A#");
         this.listTriads.add(triad);

         // ESus4
         listNameTriads.add("ESus4");
         triad = new ArrayList<>();
         triad.add("E");
         triad.add("A");
         triad.add("B");
         this.listTriads.add(triad);

         // FSus4
         listNameTriads.add("FSus4");
         triad = new ArrayList<>();
         triad.add("F");
         triad.add("A#");
         triad.add("C");
         this.listTriads.add(triad);

         // F#Sus4
         listNameTriads.add("F#Sus4");
         triad = new ArrayList<>();
         triad.add("F#");
         triad.add("B");
         triad.add("C#");
         this.listTriads.add(triad);


         // GSus4
         listNameTriads.add("GSus4");
         triad = new ArrayList<>();
         triad.add("G");
         triad.add("C");
         triad.add("D");
         this.listTriads.add(triad);

         // G#Sus4
         listNameTriads.add("G#Sus4");
         triad = new ArrayList<>();
         triad.add("G#");
         triad.add("C#");
         triad.add("D#");
         this.listTriads.add(triad);

         // ASus4
         listNameTriads.add("ASus4");
         triad = new ArrayList<>();
         triad.add("A");
         triad.add("D");
         triad.add("E");
         this.listTriads.add(triad);

         // A#Sus4
         listNameTriads.add("A#Sus4");
         triad = new ArrayList<>();
         triad.add("A#");
         triad.add("D#");
         triad.add("F");
         this.listTriads.add(triad);

         // BSus4
         listNameTriads.add("BSus4");
         triad = new ArrayList<>();
         triad.add("B");
         triad.add("E");
         triad.add("F#");
         this.listTriads.add(triad);*/

    }

    public void createKeySignature() {
        //Flapssss
        listKeySignatureFlaps = new ArrayList<>();
        listNamesKeySignatureFlaps = new ArrayList<>();

        List<String> nameKeySignature = new ArrayList<>();
        List<String> keySignature = new ArrayList<>();

        nameKeySignature.add("Cb");
        nameKeySignature.add("Abm");
        listNamesKeySignatureFlaps.add(nameKeySignature);

        keySignature.add("Bb");
        keySignature.add("Eb");
        keySignature.add("Ab");
        keySignature.add("Db");
        keySignature.add("Gb");
        keySignature.add("Cb");
        keySignature.add("Fb");
        listKeySignatureFlaps.add(keySignature);

        //**********************

        nameKeySignature = new ArrayList<>();
        keySignature = new ArrayList<>();

        nameKeySignature.add("Gb");
        nameKeySignature.add("Ebm");
        listNamesKeySignatureFlaps.add(nameKeySignature);

        keySignature.add("Bb");
        keySignature.add("Eb");
        keySignature.add("Ab");
        keySignature.add("Db");
        keySignature.add("Gb");
        keySignature.add("Cb");
        listKeySignatureFlaps.add(keySignature);


        //**********************

        nameKeySignature = new ArrayList<>();
        keySignature = new ArrayList<>();

        nameKeySignature.add("Db");
        nameKeySignature.add("Bbm");
        listNamesKeySignatureFlaps.add(nameKeySignature);

        keySignature.add("Bb");
        keySignature.add("Eb");
        keySignature.add("Ab");
        keySignature.add("Db");
        keySignature.add("Gb");
        listKeySignatureFlaps.add(keySignature);


        //**********************

        nameKeySignature = new ArrayList<>();
        keySignature = new ArrayList<>();

        nameKeySignature.add("Ab");
        nameKeySignature.add("Fm");
        listNamesKeySignatureFlaps.add(nameKeySignature);

        keySignature.add("Bb");
        keySignature.add("Eb");
        keySignature.add("Ab");
        keySignature.add("Db");
        listKeySignatureFlaps.add(keySignature);

        //**********************

        nameKeySignature = new ArrayList<>();
        keySignature = new ArrayList<>();

        nameKeySignature.add("Eb");
        nameKeySignature.add("Cm");
        listNamesKeySignatureFlaps.add(nameKeySignature);

        keySignature.add("Bb");
        keySignature.add("Eb");
        keySignature.add("Ab");
        listKeySignatureFlaps.add(keySignature);

        //**********************

        nameKeySignature = new ArrayList<>();
        keySignature = new ArrayList<>();

        nameKeySignature.add("Bb");
        nameKeySignature.add("Gm");
        listNamesKeySignatureFlaps.add(nameKeySignature);

        keySignature.add("Bb");
        keySignature.add("Eb");
        listKeySignatureFlaps.add(keySignature);

        //**********************

        nameKeySignature = new ArrayList<>();
        keySignature = new ArrayList<>();

        nameKeySignature.add("F");
        nameKeySignature.add("Dm");
        listNamesKeySignatureFlaps.add(nameKeySignature);

        keySignature.add("Bb");
        listKeySignatureFlaps.add(keySignature);

        //Sharps
        listNamesKeySignatureSharps = new ArrayList<>();
        listKeySignatureSharps = new ArrayList<>();

        nameKeySignature = new ArrayList<>();
        keySignature = new ArrayList<>();

        nameKeySignature.add("C#");
        nameKeySignature.add("A#m");
        listNamesKeySignatureSharps.add(nameKeySignature);

        keySignature.add("F#");
        keySignature.add("C#");
        keySignature.add("G#");
        keySignature.add("D#");
        keySignature.add("A#");
        keySignature.add("E#");
        keySignature.add("B#");
        listKeySignatureSharps.add(keySignature);

        //**********************
        nameKeySignature = new ArrayList<>();
        keySignature = new ArrayList<>();

        nameKeySignature.add("F#");
        nameKeySignature.add("D#m");
        listNamesKeySignatureSharps.add(nameKeySignature);

        keySignature.add("F#");
        keySignature.add("C#");
        keySignature.add("G#");
        keySignature.add("D#");
        keySignature.add("A#");
        keySignature.add("E#");
        listKeySignatureSharps.add(keySignature);

        //**********************

        nameKeySignature = new ArrayList<>();
        keySignature = new ArrayList<>();

        nameKeySignature.add("B");
        nameKeySignature.add("G#m");
        listNamesKeySignatureSharps.add(nameKeySignature);

        keySignature.add("F#");
        keySignature.add("C#");
        keySignature.add("G#");
        keySignature.add("D#");
        keySignature.add("A#");
        listKeySignatureSharps.add(keySignature);

        //**********************

        nameKeySignature = new ArrayList<>();
        keySignature = new ArrayList<>();

        nameKeySignature.add("E");
        nameKeySignature.add("C#m");
        listNamesKeySignatureSharps.add(nameKeySignature);

        keySignature.add("F#");
        keySignature.add("C#");
        keySignature.add("G#");
        keySignature.add("D#");
        listKeySignatureSharps.add(keySignature);

        //**********************

        nameKeySignature = new ArrayList<>();
        keySignature = new ArrayList<>();

        nameKeySignature.add("A");
        nameKeySignature.add("F#m");
        listNamesKeySignatureSharps.add(nameKeySignature);

        keySignature.add("F#");
        keySignature.add("C#");
        keySignature.add("G#");
        listKeySignatureSharps.add(keySignature);

        //**********************

        nameKeySignature = new ArrayList<>();
        keySignature = new ArrayList<>();

        nameKeySignature.add("D");
        nameKeySignature.add("Bm");
        listNamesKeySignatureSharps.add(nameKeySignature);

        keySignature.add("F#");
        keySignature.add("C#");
        listKeySignatureSharps.add(keySignature);

        //**********************

        nameKeySignature = new ArrayList<>();
        keySignature = new ArrayList<>();

        nameKeySignature.add("G");
        nameKeySignature.add("Em");
        listNamesKeySignatureSharps.add(nameKeySignature);

        keySignature.add("F#");
        listKeySignatureSharps.add(keySignature);

        //**********************
    }

    public void createEquivalenceNotes() {
        listEquivalenceNotes = new ArrayList<>();

        List<String> equivalenceNote = new ArrayList<>();
        equivalenceNote.add("C#");
        equivalenceNote.add("Db");
        listEquivalenceNotes.add(equivalenceNote);

        equivalenceNote = new ArrayList<>();
        equivalenceNote.add("D#");
        equivalenceNote.add("Eb");
        listEquivalenceNotes.add(equivalenceNote);

        equivalenceNote = new ArrayList<>();
        equivalenceNote.add("E#");
        equivalenceNote.add("F");
        listEquivalenceNotes.add(equivalenceNote);

        equivalenceNote = new ArrayList<>();
        equivalenceNote.add("F#");
        equivalenceNote.add("Gb");
        listEquivalenceNotes.add(equivalenceNote);

        equivalenceNote = new ArrayList<>();
        equivalenceNote.add("G#");
        equivalenceNote.add("Ab");
        listEquivalenceNotes.add(equivalenceNote);

        equivalenceNote = new ArrayList<>();
        equivalenceNote.add("A#");
        equivalenceNote.add("Bb");
        listEquivalenceNotes.add(equivalenceNote);

        equivalenceNote = new ArrayList<>();
        equivalenceNote.add("B#");
        equivalenceNote.add("C");
        listEquivalenceNotes.add(equivalenceNote);

        //menores ***
        equivalenceNote = new ArrayList<>();
        equivalenceNote.add("C#m");
        equivalenceNote.add("Dbm");
        listEquivalenceNotes.add(equivalenceNote);

        equivalenceNote = new ArrayList<>();
        equivalenceNote.add("D#m");
        equivalenceNote.add("Ebm");
        listEquivalenceNotes.add(equivalenceNote);

        equivalenceNote = new ArrayList<>();
        equivalenceNote.add("E#m");
        equivalenceNote.add("Fm");
        listEquivalenceNotes.add(equivalenceNote);

        equivalenceNote = new ArrayList<>();
        equivalenceNote.add("F#m");
        equivalenceNote.add("Gbm");
        listEquivalenceNotes.add(equivalenceNote);

        equivalenceNote = new ArrayList<>();
        equivalenceNote.add("G#m");
        equivalenceNote.add("Abm");
        listEquivalenceNotes.add(equivalenceNote);

        equivalenceNote = new ArrayList<>();
        equivalenceNote.add("A#m");
        equivalenceNote.add("Bbm");
        listEquivalenceNotes.add(equivalenceNote);

        equivalenceNote = new ArrayList<>();
        equivalenceNote.add("B#m");
        equivalenceNote.add("Cm");
        listEquivalenceNotes.add(equivalenceNote);

        //*** bemoles

        equivalenceNote = new ArrayList<>();
        equivalenceNote.add("Cb");
        equivalenceNote.add("B");
        listEquivalenceNotes.add(equivalenceNote);

        equivalenceNote = new ArrayList<>();
        equivalenceNote.add("Db");
        equivalenceNote.add("C#");
        listEquivalenceNotes.add(equivalenceNote);

        equivalenceNote = new ArrayList<>();
        equivalenceNote.add("Eb");
        equivalenceNote.add("D#");
        listEquivalenceNotes.add(equivalenceNote);

        equivalenceNote = new ArrayList<>();
        equivalenceNote.add("Fb");
        equivalenceNote.add("E");
        listEquivalenceNotes.add(equivalenceNote);

        equivalenceNote = new ArrayList<>();
        equivalenceNote.add("Gb");
        equivalenceNote.add("F#");
        listEquivalenceNotes.add(equivalenceNote);

        equivalenceNote = new ArrayList<>();
        equivalenceNote.add("Ab");
        equivalenceNote.add("G#");
        listEquivalenceNotes.add(equivalenceNote);

        equivalenceNote = new ArrayList<>();
        equivalenceNote.add("Bb");
        equivalenceNote.add("A#");
        listEquivalenceNotes.add(equivalenceNote);

        //menores ***
        equivalenceNote = new ArrayList<>();
        equivalenceNote.add("Cbm");
        equivalenceNote.add("Bm");
        listEquivalenceNotes.add(equivalenceNote);

        equivalenceNote = new ArrayList<>();
        equivalenceNote.add("Dbm");
        equivalenceNote.add("C#m");
        listEquivalenceNotes.add(equivalenceNote);

        equivalenceNote = new ArrayList<>();
        equivalenceNote.add("Ebm");
        equivalenceNote.add("D#m");
        listEquivalenceNotes.add(equivalenceNote);

        equivalenceNote = new ArrayList<>();
        equivalenceNote.add("Fbm");
        equivalenceNote.add("Em");
        listEquivalenceNotes.add(equivalenceNote);

        equivalenceNote = new ArrayList<>();
        equivalenceNote.add("Gbm");
        equivalenceNote.add("F#m");
        listEquivalenceNotes.add(equivalenceNote);

        equivalenceNote = new ArrayList<>();
        equivalenceNote.add("Abm");
        equivalenceNote.add("G#m");
        listEquivalenceNotes.add(equivalenceNote);

        equivalenceNote = new ArrayList<>();
        equivalenceNote.add("Bbm");
        equivalenceNote.add("A#m");
        listEquivalenceNotes.add(equivalenceNote);
    }

    public void calculateMainNotes() {
        int minimumValue = (int) (Collections.max(histogramNotes) * 0.1);
        for (int i = 0; i < histogramNotes.size(); i++) {
            if (histogramNotes.get(i) >= minimumValue) {
                listMainNotes.add(NOTE_NAMES[i]);
            }
        }
    }


    @SuppressWarnings("empty-statement")
    public void calculateTonality() {
        List<String> flats = new ArrayList<>();


        for (int i = 0; i < listMainNotes.size(); i++) {
            for (int j = 0; j < listEquivalenceNotes.size(); j++) {

                if (listEquivalenceNotes.get(j).contains(listMainNotes.get(i))) {
                    if (listEquivalenceNotes.get(j).get(0).equals(listMainNotes.get(i)) && listEquivalenceNotes.get(j).get(1).contains("b")) {
                        if (listEquivalenceNotes.get(j).get(1).contains("b")) {
                            flats.add(listEquivalenceNotes.get(j).get(1));
                            break;
                        }
                    } else {
                        if (listEquivalenceNotes.get(j).get(1).equals(listMainNotes.get(i)) && listEquivalenceNotes.get(j).get(0).contains("b")) {

                            if (listEquivalenceNotes.get(j).get(0).contains("b")) {
                                flats.add(listEquivalenceNotes.get(j).get(0));
                                break;
                            }
                        }
                    }

                }
            }
        }

        List<String> sharps = new ArrayList<>();
        for (int i = 0; i < listMainNotes.size(); i++) {
            if (listMainNotes.get(i).contains("#")) {

                sharps.add(listMainNotes.get(i));

            } else {
                for (int j = 0; j < listEquivalenceNotes.size(); j++) {


                    if (listEquivalenceNotes.get(j).contains(listMainNotes.get(i))) {


                        if (listEquivalenceNotes.get(j).get(0).equals(listMainNotes.get(i)) && listEquivalenceNotes.get(j).get(1).contains("#")) {

                            if (listEquivalenceNotes.get(j).get(1).contains("#"));
                            {


                                sharps.add(listEquivalenceNotes.get(j).get(1));
                                break;
                            }
                        } else {
                            if (listEquivalenceNotes.get(j).get(1).equals(listMainNotes.get(i)) && listEquivalenceNotes.get(j).get(0).contains("#")) {

                                if (listEquivalenceNotes.get(j).get(0).contains("#"));
                                {

                                    sharps.add(listEquivalenceNotes.get(j).get(0));
                                    break;
                                }
                            }
                        }

                    }
                }
            }
        }

        List<String> listPossibleTonality = new ArrayList<>();
        if (sharps.contains("F#")) {
            for (int i = 0; i < listKeySignatureSharps.size(); i++) {

                if (sharps.containsAll(listKeySignatureSharps.get(i))) {

                    listPossibleTonality = listNamesKeySignatureSharps.get(i);

                    break;
                }
            }
        } else {

            if (flats.contains("Bb")) {
                for (int i = 0; i < listKeySignatureFlaps.size(); i++) {
                    if (flats.containsAll(listKeySignatureFlaps.get(i))) {

                        listPossibleTonality = listNamesKeySignatureFlaps.get(i);

                        break;
                    }
                }
            } else {
                listPossibleTonality.add("C");
                listPossibleTonality.add("Am");
            }
        }

        if (flats.size() > sharps.size()) {
            this.tonalityStr = listPossibleTonality.get(1);

        } else {
            this.tonalityStr = listPossibleTonality.get(0);
        }

    }

    public String getApproximateNoteOfTriads(List<String> subListNote) {
        String approximateNote = "";
        int maxDistance = 0;
        for (int i = 0; i < listTriads.size(); i++) {

            if (!listNameTriads.get(i).contains("m") && !listNameTriads.get(i).contains("7")) {
                int count = 0;
                Iterator it = subListNote.iterator();
                while (it.hasNext()) {
                    String s = (String) it.next();
                    if (listTriads.get(i).contains(s)) {
                        count++;
                    }
                }
                if (count > maxDistance) {
                    maxDistance = count;
                    approximateNote = listNameTriads.get(i);
                }
            }
        }
        return approximateNote;

    }

    public String getApproximateNoteOfTriads(List<String> subListNote, List<Integer> subListPossible) {
        String approximateNote = "";
        int maxDistance = 0;

        for (int i = 0; i < subListPossible.size(); i++) {
            //if (!listNameTriads.get(subListPossible.get(i)).contains("m") && !listNameTriads.get(subListPossible.get(i)).contains("7")) {
            int count = 0;
            Iterator it = subListNote.iterator();
            while (it.hasNext()) {
                String s = (String) it.next();
                if (listTriads.get(subListPossible.get(i)).contains(s)) {
                    count++;
                }
            }
            if (count > maxDistance) {
                maxDistance = count;
                approximateNote = listNameTriads.get(subListPossible.get(i));
            }
            // }
        }
        return approximateNote;

    }

    public int getNumberNote(String name) {
        for (int i = 0; i < NOTE_NAMES.length; i++) {
            if (name.equals(NOTE_NAMES[i])) {
                return i;
            }
        }
        return -1;
    }

    public void calculateListNotes() {
        for (int i = 0; i < 12; i++) {
            histogramNotes.add(0);
        }
        for (int i = 0; i < matrixNotes.size(); i++) {
            if (matrixNotes.get(i).size() > 0) {
                if (matrixNotes.get(i).size() == 1) {
                    String nameNote = matrixNotes.get(i).get(0);
                    listNotes.add(nameNote);
                    int numberNote = getNumberNote(nameNote);
                    histogramNotes.set(numberNote, histogramNotes.get(numberNote) + 1);
                } else {
                    String nameNote = getApproximateNoteOfTriads(matrixNotes.get(i));
                    listNotes.add(nameNote);
                    int numberNote = getNumberNote(nameNote);
                    histogramNotes.set(numberNote, histogramNotes.get(numberNote) + 1);
                }
            }
        }

    }

    public void calculatePossibleChords() {
        List<String> triadsTonality = new ArrayList<>();
        for (int i = 0; i < listNameHarmonicField.size(); i++) {
            if (tonalityStr.equals(listNameHarmonicField.get(i))) {
                triadsTonality = listHarmonicField.get(i);
                break;
            }
        }
        for (int i = 0; i < triadsTonality.size(); i++) {
            String noteStr = triadsTonality.get(i);
            for (int j = 0; j < listNameTriads.size(); j++) {
                if (noteStr.equals(listNameTriads.get(j)) && !listIndexPossibleChords.contains(j)) {

                    this.listIndexPossibleChords.add(j);
                    break;
                }
            }


        }
        String noteStr = triadsTonality.get(0) + "7";
        for (int j = 0; j < listNameTriads.size(); j++) {
            if (noteStr.equals(listNameTriads.get(j)) && !listIndexPossibleChords.contains(j)) {


                this.listIndexPossibleChords.add(j);
                break;
            }
        }
        Collections.sort(listIndexPossibleChords);
    }

    public List<Integer> getListIndex(String note, List<Integer> previousListIndex) {
        List<Integer> listIndex = new ArrayList<>();
        for (int i = 0; i < previousListIndex.size(); i++) {

            if (listTriads.get(previousListIndex.get(i)).contains(note)) {
                listIndex.add(previousListIndex.get(i));
            }
        }
        return listIndex;

    }

    public List<String> getListChords() {
        boolean statusAnalise = false; //false acabou de analizar ou nao esta analizando & true em processo.
        List<Integer> listIndex = new ArrayList<>();
        List<Integer> previousListIndex = new ArrayList<>();
        List<String> listIndexResult = new ArrayList<>();

        for (int i = 0; i < listNotes.size(); i++) {

            if (statusAnalise == false) {
                listIndex = new ArrayList<>();
                previousListIndex = new ArrayList<>();
                for (int j = 0; j < this.listIndexPossibleChords.size(); j++) {//this.listNameTriads.size();j++){//

                    //listIndex.add(j);
                    listIndex.add(listIndexPossibleChords.get(j));

                }
                previousListIndex = listIndex;
                statusAnalise = true;
            } else {

                listIndex = getListIndex(listNotes.get(i), previousListIndex);

                if (listIndex != null && listIndex.size() > 0) {
                    previousListIndex = listIndex;
                    statusAnalise = true;
                } else {
                    listIndexResult.add(listNameTriads.get(previousListIndex.get(0)));
                    statusAnalise = false;
                    i = i - 1;

                }
            }

        }
        return listIndexResult;
    }

    public List<String> getListChords2() {
        try {
            List<String> listIndexResult = new ArrayList<>();
            HashSet<String> subListNotes = new HashSet<>();
            long tam = (long) ticksPerBeat;

            for (int i = 0; i < listTicks.size();) {

                while ((i < listTicks.size()) && (listTicks.get(i) < tam)) {
                    for (int j = 0; j < matrixNotes.get(i).size(); j++) {
                        subListNotes.add(matrixNotes.get(i).get(j));
                    }
                    i++;
                }
                if (subListNotes.size() > 1) {
                    List<String> list = new ArrayList<>(subListNotes);
                    String note = this.getApproximateNoteOfTriads(list, listIndexPossibleChords);
                    listIndexResult.add(note);
                } else {
                    if (subListNotes.size() == 1) {
                        List<String> list = new ArrayList<>(subListNotes);
                        listIndexResult.add(list.get(0));
                    } else {
                        //listIndexResult.add("");
                    }
                }

                subListNotes = new HashSet<>();

                tam = tam + ticksPerBeat;

                if (tam > this.tickLength || tam == this.tickLength) {
                    if (tam == this.tickLength) {
                        break;
                    } else {
                        //tam = this.tickLength;
                        break;
                    }
                }
            }
            return listIndexResult;
        } catch (Exception e) {

            return null;
        }
    }

    private String messageInfotoString(MidiMessage message) {
        if (!(message instanceof ShortMessage)) {
            return message.toString();
        }
        ShortMessage m = (ShortMessage) message;
        return String.format("Channel %d  command: %d data %d %d", m.getChannel(), m.getCommand(), m.getData1(), m.getData2());
    }

    public Integer getPositionTick(long tick) {
        for (int i = 0; i < listTicks.size(); i++) {

            if (tick == listTicks.get(i)) {
                return i;
            }
        }
        return -1;
    }
    private static final String[] sm_astrKeyNames = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
    private static final String[] sm_astrKeySignatures = {"Cb", "Gb", "Db", "Ab", "Eb", "Bb", "F", "C", "G", "D", "A", "E", "B", "F#", "C#"};
    private static final String[] SYSTEM_MESSAGE_TEXT = {
        "System Exclusive (should not be in ShortMessage!)",
        "MTC Quarter Frame: ",
        "Song Position: ",
        "Song Select: ",
        "Undefined",
        "Undefined",
        "Tune Request",
        "End of SysEx (should not be in ShortMessage!)",
        "Timing clock",
        "Undefined",
        "Start",
        "Continue",
        "Stop",
        "Undefined",
        "Active Sensing",
        "System Reset"
    };
    private static final String[] QUARTER_FRAME_MESSAGE_TEXT = {
        "frame count LS: ",
        "frame count MS: ",
        "seconds count LS: ",
        "seconds count MS: ",
        "minutes count LS: ",
        "minutes count MS: ",
        "hours count LS: ",
        "hours count MS: "
    };
    private static final String[] FRAME_TYPE_TEXT = {
        "24 frames/second",
        "25 frames/second",
        "30 frames/second (drop)",
        "30 frames/second (non-drop)",};

    public static String getKeyName(int nKeyNumber) {
        if (nKeyNumber > 127) {
            return "illegal value";
        } else {
            int nNote = nKeyNumber % 12;
            int nOctave = nKeyNumber / 12;
            return sm_astrKeyNames[nNote] + (nOctave - 1);
        }
    }

    public static int get14bitValue(int nLowerPart, int nHigherPart) {
        return (nLowerPart & 0x7F) | ((nHigherPart & 0x7F) << 7);
    }

    private static int signedByteToUnsigned(byte b) {
        if (b >= 0) {
            return (int) b;
        } else {
            return 256 + (int) b;
        }
    }

    public static String getHexString(byte[] aByte) {
        StringBuffer sbuf = new StringBuffer(aByte.length * 3 + 2);
        for (int i = 0; i < aByte.length; i++) {
            sbuf.append(' ');
            byte bhigh = (byte) ((aByte[i] & 0xf0) >> 4);
            sbuf.append((char) (bhigh > 9 ? bhigh + 'A' - 10 : bhigh + '0'));
            byte blow = (byte) (aByte[i] & 0x0f);
            sbuf.append((char) (blow > 9 ? blow + 'A' - 10 : blow + '0'));
        }
        return new String(sbuf);
    }

    public String decodeMessage(ShortMessage message) {
        String strMessage = null;
        switch (message.getCommand()) {
            case 0x80:
                strMessage = "note Off " + getKeyName(message.getData1()) + " velocity: " + message.getData2();
                break;

            case 0x90:
                strMessage = "note On " + getKeyName(message.getData1()) + " velocity: " + message.getData2();
                break;

            case 0xa0:
                strMessage = "polyphonic key pressure " + getKeyName(message.getData1()) + " pressure: " + message.getData2();
                break;

            case 0xb0:
                strMessage = "control change " + message.getData1() + " value: " + message.getData2();
                break;

            case 0xc0:
                strMessage = "program change " + message.getData1();
                break;

            case 0xd0:
                strMessage = "key pressure " + getKeyName(message.getData1()) + " pressure: " + message.getData2();
                break;

            case 0xe0:
                strMessage = "pitch wheel change " + get14bitValue(message.getData1(), message.getData2());
                break;

            case 0xF0:
                strMessage = SYSTEM_MESSAGE_TEXT[message.getChannel()];
                switch (message.getChannel()) {
                    case 0x1:
                        int nQType = (message.getData1() & 0x70) >> 4;
                        int nQData = message.getData1() & 0x0F;
                        if (nQType == 7) {
                            nQData = nQData & 0x1;
                        }
                        strMessage += QUARTER_FRAME_MESSAGE_TEXT[nQType] + nQData;
                        if (nQType == 7) {
                            int nFrameType = (message.getData1() & 0x06) >> 1;
                            strMessage += ", frame type: " + FRAME_TYPE_TEXT[nFrameType];
                        }
                        break;

                    case 0x2:
                        strMessage += get14bitValue(message.getData1(), message.getData2());
                        break;

                    case 0x3:
                        strMessage += message.getData1();
                        break;
                }
                break;

            default:
                strMessage = "unknown message: status = " + message.getStatus() + ", byte1 = " + message.getData1() + ", byte2 = " + message.getData2();
                break;
        }
        if (message.getCommand() != 0xF0) {
            int nChannel = message.getChannel() + 1;
            String strChannel = "channel " + nChannel + ": ";
            strMessage = strChannel + strMessage;
        }
        return strMessage;
    }

    public String decodeMessage(SysexMessage message) {
        byte[] abData = message.getData();
        String strMessage = null;
        if (message.getStatus() == SysexMessage.SYSTEM_EXCLUSIVE) {
            strMessage = "Sysex message: F0" + getHexString(abData);
        } else if (message.getStatus() == SysexMessage.SPECIAL_SYSTEM_EXCLUSIVE) {
            strMessage = "Special Sysex message (F7):" + getHexString(abData);
        }
        return strMessage;
    }

    public String decodeMessage(MetaMessage message) {
        byte[] abMessage = message.getMessage();
        byte[] abData = message.getData();
        int nDataLength = message.getLength();
        String strMessage = null;
        switch (message.getType()) {
            case 0:
                int nSequenceNumber = abData[0] * 256 + abData[1];
                strMessage = "Sequence Number: " + nSequenceNumber;
                break;

            case 1:
                String strText = new String(abData);
                strMessage = "Text Event: " + strText;
                break;

            case 2:
                String strCopyrightText = new String(abData);
                strMessage = "Copyright Notice: " + strCopyrightText;
                break;

            case 3:
                String strTrackName = new String(abData);
                strMessage = "Sequence/Track Name: " + strTrackName;
                break;

            case 4:
                String strInstrumentName = new String(abData);
                strMessage = "Instrument Name: " + strInstrumentName;
                break;

            case 5:
                String strLyrics = new String(abData);
                strMessage = "Lyric: " + strLyrics;
                break;

            case 6:
                String strMarkerText = new String(abData);
                strMessage = "Marker: " + strMarkerText;
                break;

            case 7:
                String strCuePointText = new String(abData);
                strMessage = "Cue Point: " + strCuePointText;
                break;

            case 0x20:
                int nChannelPrefix = abData[0];
                strMessage = "MIDI Channel Prefix: " + nChannelPrefix;
                break;

            case 0x2F:
                strMessage = "End of Track";
                break;

            case 0x51:
                int nTempo = signedByteToUnsigned(abData[0]) * 65536
                        + signedByteToUnsigned(abData[1]) * 256
                        + signedByteToUnsigned(abData[2]);
                strMessage = "Set Tempo (µs/quarter note): " + nTempo;
                break;

            case 0x54:
                strMessage = "SMTPE Offset: " + abData[0] + ":" + abData[1] + ":" + abData[2] + "." + abData[3] + "." + abData[4];
                break;

            case 0x58:
                strMessage = "Time Signature: " + abData[0] + "/" + (1 << abData[1]) + ", MIDI clocks per metronome tick: " + abData[2] + ", 1/32 per 24 MIDI clocks: " + abData[3];
                if (boolTicksPerBeat == false) {// || Integer.valueOf(abData[0]).equals(2)) {

                    int numerator = Integer.valueOf(1 << abData[1]);
                    int denominator = abData[0];//Integer.valueOf((int)Math.pow(2,abData[0]));
                  /* Nueva propuesta  */
                  //this.ticksPerBeat =((4* this.resolution)/denominator)*numerator;
                   
                   if (denominator == 4) {
                        this.ticksPerBeat = this.resolution * numerator;
                    } else {
                        if (denominator < 4) {
                            this.ticksPerBeat = (this.resolution) * numerator/denominator;
                        } else {
                            //vai ser maior ou seja corchea semicorchea fusa ou semidifusa
                            this.ticksPerBeat = (this.resolution)  * numerator *(denominator/4)/denominator;
                        }
                    }
                    /* ESTO HACÍA ANTES
                     this.ticksPerBeat = (int) this.tickLength / this.resolution;

                     float temp = Float.valueOf(1 << abData[1]) / Float.valueOf(abData[0]);
                     this.ticksPerBeat = (int) ((float) this.ticksPerBeat / (float) temp);
                     this.ticksPerBeat = (int) (this.ticksPerBeat / Integer.valueOf(abData[0]));
                     this.ticksPerBeat = (int) this.tickLength / (int) this.ticksPerBeat;*/
                }
                break;

            case 0x59:
                String strGender = (abData[1] == 1) ? "minor" : "major";
                strMessage = "Key Signature: " + sm_astrKeySignatures[abData[0] + 7] + " " + strGender;
                if (this.tonalityStr.equals("") || this.tonalityStr == null) {
                    this.tonalityStr = sm_astrKeySignatures[abData[0] + 7];
                    if (strGender.equals("minor")) {
                        this.tonalityStr = this.tonalityStr + "m";
                    }
                }
                break;

            case 0x7F:
                // TODO: decode vendor code, dump data in rows
                String strDataDump = "";
                for (int i = 0; i < abData.length; i++) {
                    strDataDump += abData[i] + " ";
                }
                strMessage = "Sequencer-Specific Meta event: " + strDataDump;
                break;

            default:
                String strUnknownDump = "";
                for (int i = 0; i < abData.length; i++) {
                    strUnknownDump += abData[i] + " ";
                }
                strMessage = "unknown Meta event: " + strUnknownDump;
                break;

        }
        return strMessage;
    }
    /**
     * Tempo-based timing. Resolution is specified in ticks per beat.
     */
    public static final float PPQ = 0.0f;
    /**
     * 24 frames/second timing. Resolution is specific in ticks per frame.
     */
    public static final float SMPTE_24 = 24.0f;
    /**
     * 25 frames/second timing. Resolution is specific in ticks per frame.
     */
    public static final float SMPTE_25 = 25.0f;
    /**
     * 30 frames/second timing. Resolution is specific in ticks per frame.
     */
    public static final float SMPTE_30 = 30.0f;
    /**
     * 29.97 frames/second timing. Resolution is specific in ticks per frame.
     */
    public static final float SMPTE_30DROP = 29.97f;

    public void readMidi(String midiName) throws Exception {

        Sequence sequence = MidiSystem.getSequence(new File(midiName));
        this.tickLength = sequence.getTickLength();
        this.divisionType = sequence.getDivisionType();
        this.resolution = sequence.getResolution();
        this.listTicks = new ArrayList<>();

        /*for (int i = 0; i <= sequence.getTickLength(); i++) {
         this.listTicks.add(String.valueOf(i));
         matrixNotes.add(new ArrayList<>());
         }*/
        int trackNumber = 0;
        for (Track track : sequence.getTracks()) {
            trackNumber++;
            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                MidiMessage message = event.getMessage();
                if (message instanceof MetaMessage) {
                    MetaMessage sm = (MetaMessage) message;
                    decodeMessage(sm);
                }
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;
                    if (sm.getCommand() == NOTE_ON) {
                        long number = event.getTick();

                        if (!listTicks.contains(number)) {
                            this.listTicks.add(number);
                            matrixNotes.add(new ArrayList<String>());
                        }
                    } else {
                        if (sm.getCommand() == NOTE_OFF) {
                            long number = event.getTick();
                            if (!listTicks.contains(number)) {
                                this.listTicks.add(number);
                                matrixNotes.add(new ArrayList<String>());
                            }
                        }
                    }
                }
            }
        }
        Collections.sort(listTicks);
        trackNumber = 0;


        for (Track track : sequence.getTracks()) {
            trackNumber++;
            for (int i = 0; i < track.size(); i++) {
                MidiEvent event = track.get(i);
                MidiMessage message = event.getMessage();

                if (message instanceof ShortMessage) {

                    ShortMessage sm = (ShortMessage) message;

                    if (sm.getCommand() == NOTE_ON) {
                        long number = event.getTick();

                        Integer position = getPositionTick(number);

                        int key = sm.getData1();
                        int octave = (key / 12) - 1;
                        int note = key % 12;
                        String noteName = NOTE_NAMES[note];
                        int velocity = sm.getData2();
                        int statusCurrent = sm.getStatus();
                        int channelCurrent = sm.getChannel();

                        matrixNotes.get(position).add(noteName);

                        //this.matrixNotes.get(sm.getChannel()).add(noteName);
                        //histogramNotes.set(note, histogramNotes.get(note) + 1);
                    } else if (sm.getCommand() == NOTE_OFF) {
                        long number = event.getTick();

                        Integer position = getPositionTick(number);
                        int key = sm.getData1();
                        int octave = (key / 12) - 1;
                        int note = key % 12;
                        String noteName = NOTE_NAMES[note];
                        int velocity = sm.getData2();
                        int statusCurrent = sm.getStatus();
                        int channelCurrent = sm.getChannel();
                        matrixNotes.get(position).add(noteName);


                        //this.matrixNotes.get(sm.getChannel()).add(noteName);
                        //histogramNotes.set(note, histogramNotes.get(note) + 1);


                    } else if (sm.getCommand() == PATCH_CHANGE) {
                    } else {
                    }
                } else {
                }
            }

        }


        calculateListNotes();
        calculateMainNotes();
        if (this.tonalityStr.equals("") || this.tonalityStr == null) {
            calculateTonality();
        }
        calculatePossibleChords();
    }

    public void printFile() throws IOException {
        FileWriter outFile = new FileWriter("prueba.txt");
        try (PrintWriter out = new PrintWriter(outFile)) {
            for (int i = 0; i < this.matrixNotes.size(); i++) {
                out.write(i + "-");
                for (int j = 0; j < this.matrixNotes.get(i).size(); j++) {
                    out.write(this.matrixNotes.get(i).get(j));
                    
                    out.write(" ");
                }
                out.write("\n");
            }
        }
    }


}
