/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preprocmusicvisualization;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.imageio.ImageIO;

/**
 *
 * @author aurea
 */
public class HorspoolTestChord {

    boolean findPattern(List<HorspoolChord> listHorspool, List<String> pattern) {
        for (HorspoolChord horspool : listHorspool) {
            if (horspool.pattern.equals(pattern)) {
                return true;
            }
        }
        return false;
    }

    public List<HorspoolChord> patternRecognition(List<String> notes) {
        int sizeStep = (int) Math.floor((double) notes.size() / 2.0);
        // System.out.println("Step"+sizeStep);
        List<HorspoolChord> listHorspool = new ArrayList<>();

        for (int i = sizeStep; i > 0; i--) {
            //System.out.println("i Step"+i);
            for (int j = 0; j <= notes.size() - i; j++) {
                List<String> pp = new ArrayList<>();

                for (int k = 0; k < i; k++) {
                    pp.add(notes.get(k + j));
                }
                //System.out.println("sizeStep "+i+" padroes"+pp);
                if (!findPattern(listHorspool, pp)) {
                    HorspoolChord horspool = new HorspoolChord();
                    horspool.search(notes, pp);
                    if (horspool.pattern.size() > 1 && horspool.positionsMatches.size() > 1) {
                        listHorspool.add(horspool);
                        j = j + pp.size();
                    } else {
                        if (horspool.pattern.size() == 1 && !findPattern(listHorspool, pp)) {
                            listHorspool.add(horspool);
                            j = j + pp.size();
                        }
                    }

                }

            }
        }
        return listHorspool;
    }

    void print(List<HorspoolChord> listHorspool) {
        for (HorspoolChord horspool : listHorspool) {
            System.out.println("*****");
            System.out.println(horspool.pattern);
            //System.out.println(horspool.text);
            System.out.println(horspool.positionsMatches);
        }
        System.out.println("*****");
    }

    public List<Integer> calculateColor(List<HorspoolChord> listHorspool, List<String> text) {
        List<Integer> colors = new ArrayList<>();
        int color = 0;
        for (int i = 0; i < text.size(); i++) {
            colors.add(0);
        }
        int numberPaintedColors = 0;
        for (HorspoolChord horspool : listHorspool) {

            //System.out.println("pattern " + horspool.pattern + "positions " + horspool.positionsMatches);
            //estoy viendo si no hay cruce
            int numberStringWithoutCross = 0;
            int patternSize = horspool.pattern.size();

            for (int i = 0; i < horspool.positionsMatches.size(); i++) {
                int numberSubStringWithoutCross = 0;
                int position = horspool.positionsMatches.get(i);

                //System.out.println("positions "+horspool.positionsMatches +" size Pattern " + patternSize + " position previous " + positionPrevious + " position" + position);
                for (int j = position; j < (position + patternSize); j++) {
                    if (colors.get(j).equals(0)) {
                        numberSubStringWithoutCross++;
                    }
                }

                if (numberSubStringWithoutCross == patternSize) {
                    numberStringWithoutCross++;

                    //System.out.println("substring "+numberSubStringWithoutCross + "patternSize"+ patternSize);
                }

            }

            //System.out.println("pattern " + horspool.pattern + " number " + numberStringWithoutCross);
            if (numberStringWithoutCross >= 2 || patternSize == 1) {

                //System.out.println("pattern " + horspool.pattern + " number " + numberStringWithoutCross);
                color++;
                for (int i = 0; i < horspool.positionsMatches.size(); i++) {

                    int numberSubStringWithoutCross = 0;
                    int position = horspool.positionsMatches.get(i);

                    for (int j = position; j < (position + patternSize); j++) {
                        if (colors.get(j).equals(0)) {
                            numberSubStringWithoutCross++;
                        }
                    }
                    if (numberSubStringWithoutCross == patternSize) {
                        for (int j = position; j < (position + horspool.pattern.size()); j++) {
                            colors.set(j, color);
                            numberPaintedColors++;
                            if (numberPaintedColors == colors.size()) {
                                return colors;
                            }
                        }
                    }
                }
            }
        }

        return colors;
    }

    public List<PositionPatternChord> calculateColorPositions(List<HorspoolChord> listHorspool, List<String> text) {
        List<Integer> valuePosition = new ArrayList<>();
        int color = 0;
        for (int i = 0; i < text.size(); i++) {
            valuePosition.add(-1);
        }
        List<PositionPatternChord> listPosPat = new ArrayList<>();
        int numberPaintedColors = 0;
        for (HorspoolChord horspool : listHorspool) {

            //System.out.println("pattern " + horspool.pattern + "positions " + horspool.positionsMatches);
            //estoy viendo si no hay cruce
            int numberStringWithoutCross = 0;
            int patternSize = horspool.pattern.size();

            for (int i = 0; i < horspool.positionsMatches.size(); i++) {
                int numberSubStringWithoutCross = 0;
                int position = horspool.positionsMatches.get(i);

                //System.out.println("positions "+horspool.positionsMatches +" size Pattern " + patternSize + " position previous " + positionPrevious + " position" + position);
                for (int j = position; j < (position + patternSize); j++) {
                    if (valuePosition.get(j).equals(-1)) {
                        numberSubStringWithoutCross++;
                    }
                }

                if (numberSubStringWithoutCross == patternSize) {
                    numberStringWithoutCross++;

                    //System.out.println("substring "+numberSubStringWithoutCross + "patternSize"+ patternSize);
                }

            }

            //System.out.println("pattern " + horspool.pattern + " number " + numberStringWithoutCross);
            if (numberStringWithoutCross >= 2 || patternSize == 1) {

                //System.out.println("pattern " + horspool.pattern + " number " + numberStringWithoutCross);
                color++;
                for (int i = 0; i < horspool.positionsMatches.size(); i++) {

                    int numberSubStringWithoutCross = 0;
                    int position = horspool.positionsMatches.get(i);

                    for (int j = position; j < (position + patternSize); j++) {
                        if (valuePosition.get(j).equals(-1)) {
                            numberSubStringWithoutCross++;
                        }
                    }
                    if (numberSubStringWithoutCross == patternSize) {
                        PositionPatternChord posPat = new PositionPatternChord(position, horspool.pattern);
                        listPosPat.add(posPat);
                        for (int j = position; j < (position + horspool.pattern.size()); j++) {
                            valuePosition.set(j, 1);

                            numberPaintedColors++;
                            if (numberPaintedColors == valuePosition.size()) {
                                return listPosPat;
                            }
                        }
                    }
                }
            }
        }
        return listPosPat;
    }

    public BufferedImage createImageWithDominantChord(List<PositionPatternChord> listPositionPattern) {
        Collections.sort(listPositionPattern);
        int sizeSquare = 30;
        int sizeWidth = 0;
        for (int i = 0; i < listPositionPattern.size(); i++) {
            sizeWidth += (listPositionPattern.get(i).numberPattern * 2);
        }
        int cols = sizeWidth + (5 * listPositionPattern.size()) + 5;//listPositionPattern.size();
        int rows = 1;
        int width = cols;
        int height = rows * sizeSquare + 10;

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        img.createGraphics();
        Graphics2D g = (Graphics2D) img.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        Integer max = 12;
        Integer min = 0;

        //int col = 0;
        //int row = 0;
        //int posCol = 0;
        //int posRow = 0;
        List<String> listChords = new ArrayList<>();
        listChords.add("C");
        listChords.add("C#");
        listChords.add("D");
        listChords.add("D#");
        listChords.add("E");
        listChords.add("F");
        listChords.add("F#");
        listChords.add("G");
        listChords.add("G#");
        listChords.add("A");
        listChords.add("A#");
        listChords.add("B");

        List<Integer> listHistogram = new ArrayList<>();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 5, sizeSquare + 10);

        g.setColor(Color.BLACK);
//        g.fillRect(0, 0, cols, 5);

        g.fillRect(0, 0, cols, 40);

        int position = 5;

        for (int i = 0; i < listPositionPattern.size(); i++) {
            listHistogram = new ArrayList<>();

            for (int j = 0; j < 12; j++) {
                listHistogram.add(0);
            }

            for (int j = 0; j < listPositionPattern.get(i).pattern.size(); j++) {
                for (int k = 0; k < listChords.size(); k++) {
                    if (listPositionPattern.get(i).pattern.get(j).equals(listChords.get(k))) {
                        listHistogram.set(k, listHistogram.get(k) + 1);
                    }
                }
            }
            //verificar se tds sao iguais
            Boolean verifyGray = true;

            Integer maxValueHistogram = listHistogram.get(0);
            Integer maxIndexHistogram = 0;
            for (int j = 1; j < listHistogram.size(); j++) {
                if (!listHistogram.get(j - 1).equals(listHistogram.get(j)) && verifyGray.equals(true)) {
                    verifyGray = false;
                }
                if (listHistogram.get(j) > maxValueHistogram) {
                    maxValueHistogram = listHistogram.get(j);
                    maxIndexHistogram = j;
                }

            }
            int color = ((maxIndexHistogram - min) * 200 / (max - min)) + 55;

            Rainbow colorRainbow = new Rainbow();
           if(color%2==0)
            {
                color = 255-color;
            }
           if(color>255)
           {
               color =255;
           }
           if(color<0)
           {
               color =0;
           }
            Color[] rgb = new Color[256];
            rgb = colorRainbow.rgb;
            if (verifyGray.equals(true)) {
                g.setColor(Color.GRAY);
            } else {
                g.setColor(rgb[color]);
            }
            g.fillRect(position, 5, listPositionPattern.get(i).numberPattern * 2, sizeSquare);

            position = position + listPositionPattern.get(i).numberPattern * 2;

            g.setColor(Color.BLACK);
            g.fillRect(position, 0, 5, sizeSquare + 10);
            position += 5;

        }
        return img;

    }

    public BufferedImage createImageWithComplexity(List<PositionPatternChord> listPositionPattern) {

        Collections.sort(listPositionPattern);
        int sizeSquare = 30;
        int sizeWidth = 0;
        for (int i = 0; i < listPositionPattern.size(); i++) {
            sizeWidth += (listPositionPattern.get(i).numberPattern * 2);
        }
        int cols = sizeWidth + (5 * listPositionPattern.size()) + 5;//listPositionPattern.size();
        int rows = 1;
        int width = cols;
        int height = rows * sizeSquare + 10;

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        img.createGraphics();
        Graphics2D g = (Graphics2D) img.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        List<String> listChords = new ArrayList<>();
        listChords.add("C");
        listChords.add("C7");
        listChords.add("C#");
        listChords.add("C#7");
        listChords.add("D");
        listChords.add("D7");
        listChords.add("D#");
        listChords.add("D#7");
        listChords.add("E");
        listChords.add("E7");
        listChords.add("F");
        listChords.add("F7");
        listChords.add("F#");
        listChords.add("F#7");
        listChords.add("G");
        listChords.add("G7");
        listChords.add("G#");
        listChords.add("G#7");
        listChords.add("A");
        listChords.add("A7");
        listChords.add("A#");
        listChords.add("A#7");
        listChords.add("B");
        listChords.add("B7");
        listChords.add("Cm");
        listChords.add("C7m");
        listChords.add("C#m");
        listChords.add("C#7m");
        listChords.add("Dm");
        listChords.add("D7m");
        listChords.add("D#m");
        listChords.add("D#7m");
        listChords.add("Em");
        listChords.add("E7m");
        listChords.add("Fm");
        listChords.add("F7m");
        listChords.add("F#m");
        listChords.add("F#7m");
        listChords.add("Gm");
        listChords.add("G7m");
        listChords.add("G#m");
        listChords.add("G#7m");
        listChords.add("Am");
        listChords.add("A7m");
        listChords.add("A#m");
        listChords.add("A#7m");
        listChords.add("Bm");
        listChords.add("B7m");

        Integer max = 9;//maximo numero que aparece//listChords.size();
        Integer min = 1;

        List<Integer> listHistogram = new ArrayList<>();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 5, sizeSquare + 10);

        g.setColor(Color.BLACK);
//        g.fillRect(0, 0, cols, 5);

        g.fillRect(0, 0, cols, 40);

        int position = 5;

        for (int i = 0; i < listPositionPattern.size(); i++) {
            listHistogram = new ArrayList<>();

            for (int j = 0; j < listChords.size(); j++) {
                listHistogram.add(0);
            }

            for (int j = 0; j < listPositionPattern.get(i).pattern.size(); j++) {
                for (int k = 0; k < listChords.size(); k++) {
                    // System.out.println(listPositionPattern.get(i).pattern.get(j)+"*  "+listChords.get(k)+"*");
                    if (listPositionPattern.get(i).pattern.get(j).equals(listChords.get(k))) {
                        listHistogram.set(k, listHistogram.get(k) + 1);
                    }
                }
            }

            Integer countChords = 0;
            for (int j = 0; j < listHistogram.size(); j++) {
                if (listHistogram.get(j) > 0) {
                    countChords++;
                }
            }
            //System.out.println("item: "+i+" - countChords "+countChords);

            //System.out.println("size chords "+listChords.size()+ " count "+countChords+" segment "+segment);
            int color = ((countChords - min) * 200 / (max - min)) + 55;
            if (countChords.equals(0)) {
                System.out.println("pattern " + listPositionPattern.get(i).pattern);
            }
            if(color%2==0)
            {
                color = 255-color;
            }
             if(color>255)
           {
               color =255;
           }
           if(color<0)
           {
               color =0;
           }
            Rainbow colorRainbow = new Rainbow();
          

            Color[] rgb = new Color[256];
            rgb = colorRainbow.rgb;

            g.setColor(rgb[color]);
            g.fillRect(position, 5, listPositionPattern.get(i).numberPattern * 2, sizeSquare);

            position = position + listPositionPattern.get(i).numberPattern * 2;

            g.setColor(Color.BLACK);
            g.fillRect(position, 0, 5, sizeSquare + 10);
            position += 5;

        }
        return img;

    }

    public Integer getNumberColorSegment(Integer minValue, Integer maxValue, Float sizeSegment, Integer numberOfColorSegments, Integer value) {

        return Math.round(((value - minValue) * 1.f) * (numberOfColorSegments - 1.f) / (maxValue - minValue) * 1.f);
    }

    public BufferedImage createImageByColorSegment(List<PositionPatternChord> listPositionPattern, Integer numberOfColorSegments) {
        Collections.sort(listPositionPattern);
        Integer minValue = Integer.MAX_VALUE;
        Integer maxValue = Integer.MIN_VALUE;
        for (int i = 0; i < listPositionPattern.size(); i++) {
            if (minValue > listPositionPattern.get(i).numberPattern) {
                minValue = listPositionPattern.get(i).numberPattern;
            }
         //   if (maxValue < listPositionPattern.get(i).numberPattern) {
            //     maxValue = listPositionPattern.get(i).numberPattern;
            // }
        }
        maxValue = 162; //189
        minValue = 1;

        Float sizeSegment = (maxValue - minValue) / numberOfColorSegments * 1.f;
        int sizeSquare = 30;
        int sizeWidth = 0;
        for (int i = 0; i < listPositionPattern.size(); i++) {
            sizeWidth += (listPositionPattern.get(i).numberPattern * 2);
        }
        int cols = sizeWidth + (5 * listPositionPattern.size()) + 5;//listPositionPattern.size();
        int rows = 1;
        int width = cols;
        int height = rows * sizeSquare + 10;
        int min = 0;
        int max = numberOfColorSegments - 1;

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        img.createGraphics();
        Graphics2D g = (Graphics2D) img.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 5, sizeSquare + 10);

        g.setColor(Color.BLACK);
//        g.fillRect(0, 0, cols, 5);

        g.fillRect(0, 0, cols, 40);

        int position = 5;

        for (int i = 0; i < listPositionPattern.size(); i++) {

            Integer index = this.getNumberColorSegment(minValue, maxValue, sizeSegment, numberOfColorSegments, listPositionPattern.get(i).numberPattern);

            int color = ((index - min) * 200 / (max - min)) + 55;
            if(color%2==0)
            {
                color = 255-color;
            }
             if(color>255)
           {
               color =255;
           }
           if(color<0)
           {
               color =0;
           }
            Rainbow colorRainbow = new Rainbow();
           

            Color[] rgb = new Color[256];
            rgb = colorRainbow.rgb;

            g.setColor(rgb[color]);

            g.fillRect(position, 5, listPositionPattern.get(i).numberPattern * 2, sizeSquare);

            position = position + listPositionPattern.get(i).numberPattern * 2;

            g.setColor(Color.BLACK);
            g.fillRect(position, 0, 5, sizeSquare + 10);
            position += 5;

        }
        return img;

    }

    public static void saveImage(BufferedImage img, String ref) {
        try {
            String format = (ref.endsWith(".png")) ? "png" : "jpg";
            ImageIO.write(img, format, new File(ref));
        } catch (IOException e) {
        }
    }

    public BufferedImage createImage(List<Integer> colors) {
        int sizeSquare = 4;

        int cols = (int) Math.sqrt((double) colors.size());
        int rows = (int) Math.round((double) colors.size() / cols);
        int width = cols * sizeSquare;
        int height = rows * sizeSquare;

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        img.createGraphics();
        Graphics2D g = (Graphics2D) img.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        Integer max = Collections.max(colors);
        Integer min = Collections.min(colors);
        //System.out.println("max "+max);
        //System.out.println("min "+min);

        int col = 0;
        int row = 0;
        int posCol = 0;
        int posRow = 0;

        System.out.println("rows " + rows);
        System.out.println("cols " + cols);

        for (int i = 0; i < colors.size(); i++) {
            int color = ((colors.get(i) - min) * 200 / (max - min)) + 55;
            if(color%2==0)
            {
                color = 255-color;
            }
             if(color>255)
           {
               color =255;
           }
           if(color<0)
           {
               color =0;
           }
            Rainbow colorRainbow = new Rainbow();

            Color[] rgb = new Color[256];
            rgb = colorRainbow.rgb;
            g.setColor(rgb[color]);
            //System.out.println("color "+colors.get(i));

            //System.out.println("("+posCol+", "+posRow+") - ("+(posCol+4)+", "+(posRow+4)+")");
            g.fillRect(posCol, posRow, posCol + sizeSquare, posRow + sizeSquare);
            col++;
            if (col == cols) {
                col = 0;
                row++;
                posRow = posRow + sizeSquare;
                posCol = 0;
            } else {
                posCol = posCol + sizeSquare;
                posRow = posRow;
            }

        }
        return img;
    }

    public BufferedImage createImage2(List<Integer> colors) {
        int sizeSquare = 30;

        int cols = colors.size();
        int rows = 1;
        int width = cols;
        int height = rows * sizeSquare;

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        img.createGraphics();
        Graphics2D g = (Graphics2D) img.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        Integer max = Collections.max(colors);
        Integer min = Collections.min(colors);
        //System.out.println("max "+max);
        //System.out.println("min "+min);

        int col = 0;
        int row = 0;
        int posCol = 0;
        int posRow = 0;

        // System.out.println("rows " + rows);
        // System.out.println("cols " + cols);
        for (int i = 0; i < colors.size(); i++) {
            int color = ((colors.get(i) - min) * 200 / (max - min)) + 55;
            if(color%2==0)
            {
                color = 255-color;
            }
             if(color>255)
           {
               color =255;
           }
           if(color<0)
           {
               color =0;
           }
            Rainbow colorRainbow = new Rainbow();

            Color[] rgb = new Color[256];
            rgb = colorRainbow.rgb;
            g.setColor(rgb[color]);
            //System.out.println("color "+colors.get(i));

            //System.out.println("("+posCol+", "+posRow+") - ("+(posCol+4)+", "+(posRow+4)+")");
            g.fillRect(posCol, posRow, posCol, posRow + sizeSquare);
            posCol++;
            //if (col == cols) {
            //    col = 0;
            //  row++;
            //  posRow = posRow + sizeSquare;
            //  posCol = 0;
            //} else {
            //  posCol = posCol + sizeSquare;
            //  posRow = posRow;
            //}

        }
        return img;
    }

    public static List<Integer> getListWithoutOutliers(List<Integer> listColors) {
        List<Integer> listSequence = new ArrayList<>();
        int leftValue, rightValue, leftDistance, rightDistance;
        for (int i = 0; i < listColors.size(); i++) {
            try {
                leftValue = listColors.get(i - 1);
                if (leftValue == listColors.get(i)) {
                    leftDistance = 1;
                } else {
                    leftDistance = -1;
                }
            } catch (Exception e) {
                leftDistance = -1;
            }

            try {
                rightValue = listColors.get(i + 1);
                if (rightValue == listColors.get(i)) {
                    rightDistance = 1;
                } else {
                    rightDistance = -1;
                }
            } catch (Exception e) {
                rightDistance = -1;
            }

            if ((leftDistance + rightDistance) >= 0) {
                listSequence.add(listColors.get(i));

            }
        }
        return listSequence;
    }

    public static List<Integer> getListOnlySequence(List<Integer> listColors) {
        List<Integer> listSequence = new ArrayList<Integer>();
        for (int i = 0; i < listColors.size(); i++) {
            int previous;
            try {
                previous = listColors.get(i - 1);
                if (previous != listColors.get(i)) {
                    listSequence.add(listColors.get(i));
                }

            } catch (Exception e) {
                listSequence.add(listColors.get(i));
            }
        }
        return listSequence;
    }

    public static List<Integer> getSequence(List<Integer> listColors) {
        List<Integer> listSequence = new ArrayList<Integer>();
        int leftValue, rightValue, leftDistance, rightDistance;
        for (int i = 0; i < listColors.size(); i++) {
            try {
                leftValue = listColors.get(i - 1);
                if (leftValue == listColors.get(i)) {
                    leftDistance = 1;
                } else {
                    leftDistance = -1;
                }
            } catch (Exception e) {
                leftDistance = -1;
            }

            try {
                rightValue = listColors.get(i + 1);
                if (rightValue == listColors.get(i)) {
                    rightDistance = 1;
                } else {
                    rightDistance = -1;
                }
            } catch (Exception e) {
                rightDistance = -1;
            }

            if ((leftDistance + rightDistance) >= 0) {
                int previous;
                try {
                    previous = listColors.get(i - 1);
                    if (previous != listColors.get(i)) {
                        listSequence.add(listColors.get(i));
                    }

                } catch (Exception e) {
                    listSequence.add(listColors.get(i));
                }
            }
        }
        return listSequence;
    }

}
