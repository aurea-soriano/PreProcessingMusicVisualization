/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package preprocmusicvisualization;


import java.util.List;

/**
 *
 * @author aurea
 */
public class PositionPatternChord implements Comparable<PositionPatternChord>{

    public Integer positionPattern;
    public List<String> pattern;//, text;       // pattern, text
    public Integer numberPattern;

    public PositionPatternChord(Integer positionPattern, List<String> pattern) {
        this.positionPattern = positionPattern;
        this.pattern = pattern;
        this.numberPattern = pattern.size();
    }

    @Override
    public int compareTo(PositionPatternChord o) {
        Integer positionPattern1 = ((PositionPatternChord) o).positionPattern; 
 
		return positionPattern.compareTo(positionPattern1);
 
    }
    
  
    
}
