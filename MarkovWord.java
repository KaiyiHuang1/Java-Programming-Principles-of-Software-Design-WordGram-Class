
/**
 * Write a description of MarkovWord here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
public class MarkovWord implements IMarkovModel{
    private String[] myText;
    private Random myRandom;
    private int myOrder;
    
    public MarkovWord(int order) {
        myRandom = new Random();
        myOrder = order;
    }
    
    public void setTraining(String text){
        myText = text.split("\\s+");
    }
    
    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }
    
    public int indexOf(String[] words, WordGram target, int start){
        for(int i = start; i < words.length - myOrder; i++){
            WordGram wg = new WordGram(words, i ,myOrder);
            if(wg.equals(target)) {
                return i;
            }
        }
        return -1;
        }
        
    private ArrayList<String> getFollows(WordGram kGram) {
        ArrayList<String> follows = new ArrayList<String>();
        
        int index =0;
        while(index < myText.length - myOrder){
            int start = indexOf(myText,kGram,index);
            if (start == -1){
               break;
            }
            if (start >= myText.length-1){
               break;  
            }
            String next = myText[start+myOrder];
            follows.add(next);
            index = start+1;
        }
        return follows;
    }
    
    public String getRandomText(int numWords){
        StringBuilder sb = new StringBuilder();
        int Last = myRandom.nextInt(myText.length-myOrder);  
        WordGram key = new WordGram(myText,Last,myOrder);
        sb.append(key.toString());
        sb.append(" ");
        for(int i=0; i < numWords-myOrder; i++){
            ArrayList<String> follows = getFollows(key);
            if (follows.size() == 0) {
                break;
            }
            Last = myRandom.nextInt(follows.size());
            String next = follows.get(Last);
            sb.append(next);
            sb.append(" ");
            key = key.shiftAdd(next);
        }
        return sb.toString().trim();
    }
}


