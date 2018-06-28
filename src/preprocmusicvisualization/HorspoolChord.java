/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package preprocmusicvisualization;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author aurea
 */

public class HorspoolChord {

    public static final String[] noteNames = {"C","C#","D","D#","E","F","F#","G","G#","A","A#","B","Cm","C#m","Dm","D#m","Em","Fm","F#m","Gm","G#m","Am","A#m","Bm","C7","D7","E7","G7","A7","B7"};
    public List<String> noteList;
    public List<Integer> positionsMatches;
    public List<String> pattern;//, text;       // pattern, text
    private int patternSize;//, textSize;          // pattern length, text length
    private static int alphabetsize=30;//256;
    private int[] occurrences;         // occurence function


    public HorspoolChord()
    {
        positionsMatches = new ArrayList<Integer>();
        pattern = new ArrayList<String>();
        //text = new ArrayList<String>();
        noteList = Arrays.asList(noteNames);
        occurrences=new int[alphabetsize];
    }
       
    /** searches the text tt for the pattern pp
     */ 
    public void search(List<String> tt, List<String> pp)
    {
      //  setText(tt);
        setPattern(pp);
        horspoolSearch(tt);
    }

    /** sets the text
     */ 
    /*private void setText(List<String> tt)
    {
        textSize=tt.size();
        text=tt;
    }*/
    
    /** sets the pattern
     */ 
    private void setPattern(List<String> pp)
    {
        patternSize=pp.size();
        pattern=pp;
        horspoolInitocc();
    }

    
    /** computation of the occurrence function
     */ 
    private void horspoolInitocc()
    {
        int a, j;

        for (a=0; a<alphabetsize; a++)
            occurrences[a]=-1;

        for (j=0; j<patternSize-1; j++)
        {
            //a=pattern[j];
            a = noteList.indexOf(pattern.get(j));//Character.toString(pattern[j]));
            occurrences[a]=j;
        }
    }

    /** searches the text for all occurences of the pattern
     */ 
    private void horspoolSearch(List<String> text)
    {
        int textSize = text.size();
        int i=0, j;
        while (i<=textSize-patternSize)
        {
            j=patternSize-1;
            while (j>=0 && pattern.get(j).equals(text.get(i+j)))
            {
                j--;
            }
            if (j<0) 
            {
                if(positionsMatches.size()>0)
                {
                    if((positionsMatches.get(positionsMatches.size()-1)+patternSize)<i)
                    {
                        positionsMatches.add(i);
                    }
                }
                else
                {
                    positionsMatches.add(i);
                }
                
            }
            i+=patternSize-1;
            try{
                i-=occurrences[noteList.indexOf(text.get(i))];
            }
            catch(Exception e)
            {
                i = textSize-patternSize+2;
            }
            
          
        }
    }
}