import edu.duke.*;
import org.apache.commons.csv.*;

public class BabyNames {
    public void printNames () {
        FileResource fr = new FileResource();
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            if (numBorn <= 100) {
                System.out.println("Name " + rec.get(0) +
                           " Gender " + rec.get(1) +
                           " Num Born " + rec.get(2));
            }
        }
    }

    public void totalBirths (FileResource fr) {
        int totalBirths = 0;
        int totalBoys = 0;
        int totalGirls = 0;
        int nbrGirls = 0;
        int nbrBoys = 0;
        int nmbrtotal = 0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            totalBirths += numBorn;
            nmbrtotal++;
            if (rec.get(1).equals("M")) {
                totalBoys += numBorn;
                nbrBoys++;
            }
            else {
                nbrGirls++;
                totalGirls += numBorn;
            }
        }
        System.out.println("total births = " + totalBirths);
        System.out.println("female girls = " + totalGirls);
        System.out.println("male boys = " + totalBoys);
        System.out.println("nbr total names = " + nmbrtotal);
        System.out.println("nbr female names = " + nbrGirls);
        System.out.println("nbr male names = " + nbrBoys);
    }
    
    public int getRank(int year, String name, String gender){
        int count = 0;
        FileResource fr = new FileResource("us_babynames_small/testing/yob" + year +"short.csv");
        for (CSVRecord record : fr.getCSVParser(false)){
            if (record.get(1).equals(gender)){
                count++;
                if (record.get(0).equals(name) && record.get(1).equals(gender)){
                    return count;
                } 
            }
        }
        return -1;      
    }
    
    public String getName(int year, int rank, String gender){
        int count = 0;
        FileResource fr = new FileResource("us_babynames_small/testing/yob" + year +"short.csv");
        for (CSVRecord record : fr.getCSVParser(false)){
            if (record.get(1).equals(gender)){
                count++;
                if (count == rank){
                    return record.get(0);
                }
            }
        }
        return "NO NAME";
    }
    public void testTotalBirths () {
        //FileResource fr = new FileResource();
        FileResource fr = new FileResource("us_babynames_small/testing/yob2014short.csv");
        totalBirths(fr);
    }
    public void testgetName(){
        System.out.println("the name is :" + getName(2014, 5, "F"));
    }
}