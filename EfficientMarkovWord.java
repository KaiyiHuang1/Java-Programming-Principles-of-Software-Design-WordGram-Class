
/**
 * Write a description of EfficientMarkovWord here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;

public class EfficientMarkovWord implements IMarkovModel{
    private String[] myText;
    private Random myRandom;
    private int myOrder;
    private HashMap<WordGram, ArrayList<String>> HM;
    
    public EfficientMarkovWord(int order) {
        myRandom = new Random();
        myOrder = order;
        HM = new HashMap<WordGram, ArrayList<String>>();
    }
    
    public void setTraining(String text){
        myText = text.split("\\s+");
        buildMap();
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
        ArrayList<String> result = null;
        if(HM.get(kGram) != null){
        return HM.get(kGram);
      }
      return result;
    }
    
    public String getRandomText(int numWords){
        StringBuilder sb = new StringBuilder();
        int Last = myRandom.nextInt(myText.length-myOrder);  
        WordGram key = new WordGram(myText,Last,myOrder);
        System.out.println("WordGram in getRandomText: "+getFollows(key).toString());
        sb.append(key.toString());
        sb.append(" ");
        for(int i=0; i < numWords-myOrder; i++){
            ArrayList<String> follows = getFollows(key);
            if (follows.size() == 0 ||follows == null) {
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
    
    public void buildMap(){
        for(int i = 0; i < myText.length - myOrder; i ++){
            WordGram wg = new WordGram(myText, i , myOrder);
            if(HM.containsKey(wg)){
                HM.get(wg).add(myText[ i + myOrder]);
            }
            else{
                ArrayList<String> newAL = new ArrayList<String>();
                newAL.add(myText[ i + myOrder]);
                HM.put(wg, newAL);
            }
        }
    }
    
    
     public void printHashMapInfo(){
        /*for(WordGram wg: HM.keySet()){
            System.out.println("WordGram: "+wg.toString());
            System.out.println("ArrayList: "+HM.get(wg).toString());
        }*/
        
        System.out.println("Number of keys in HM is: "+HM.keySet().size());
        int MaxSize = 0;
        for(WordGram wg: HM.keySet()){
            if(HM.get(wg).size() > MaxSize){
                MaxSize = HM.get(wg).size();
                WordGram wgMax = wg;
            }
        }
        
        System.out.println("Size of the largest value in the HashMap: "+MaxSize);
        System.out.println("Keys that have the maximum size value: ");
           for (WordGram wg : HM.keySet()) {
            if (HM.get(wg).size()==MaxSize)
                System.out.println(wg.toString());
        }
    }

}
