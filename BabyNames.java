import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

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
        FileResource fr = new FileResource("us_babynames/us_babynames_by_year/yob" + year +".csv");
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
        FileResource fr = new FileResource("us_babynames/us_babynames_by_year/yob" + year +".csv");
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
    
    public String whatIsNameInYear(String name, int year, int newYear, String gender){
        int rank = getRank(year, name, gender);
        return getName(newYear, rank, gender);
    }
    
    public int yearOfHighestRank(String name, String gender){
       DirectoryResource dr = new DirectoryResource();
       int year1 = -1;
       double lowRank = Double.POSITIVE_INFINITY;
       for (File f : dr.selectedFiles()){
           String namef = f.getName();
           int year = Integer.parseInt(namef.substring(3,7));
           int rank = getRank(year, name, gender);
           if (rank < lowRank){
               lowRank = rank;
               year1 = year;
           }
       }
       return year1;
    }
    
    public double getAverageRank(String name, String gender){
       DirectoryResource dr = new DirectoryResource();
       int somme = 0;
       int count = 0;
       for (File f : dr.selectedFiles()){
           String namef = f.getName();
           int year = Integer.parseInt(namef.substring(3,7));
           int rank = getRank(year, name, gender);
           if (rank != -1){
               somme += rank;
               count += 1;
           }
       }
       if (somme <= 0){
           return -1;
       }
       return (double)somme/count;
    }
    public int getTotalBirthsRankedHigher(int year, String name, String gender){
        int rank=getRank(year,name,gender);
        String fileName="us_BabyNames/us_babynames_by_year/yob"+year+".csv";
        FileResource fr=new FileResource(fileName);
        int counter=1,births=0,totalBirths=0;
        for(CSVRecord rec:fr.getCSVParser(false))
        {
            if(counter<rank && gender.equalsIgnoreCase(rec.get(1)))
            {
                counter++;
                births=Integer.parseInt(rec.get(2));
                totalBirths+=births;
            }
        }
        return totalBirths;
    }
    public void testTotalBirths () {
        //FileResource fr = new FileResource();
        FileResource fr = new FileResource("us_babynames/us_babynames_by_year/yob1905.csv");
        totalBirths(fr);
    }
    public void testRank(){
        System.out.println("the rank is : " + getRank(1971,"Frank", "M"));
    }
    
    public void testName(){
        System.out.println("the rank is : " + getName(1980,350, "F"));
        System.out.println("the rank is : " + getName(1982,450, "M"));
    }
    
    public void testwhatNameinYear(){
        System.out.println("Susan born in 1972 would be " + whatIsNameInYear("Suzan",1972, 2014, "F") + " if he was born in 2014");
        System.out.println("Owen born in 1974 would be " + whatIsNameInYear("Owen",1974, 2014, "M") + " if he was born in 2014");
    }
        
    public void testwhatyear(){
        System.out.println("THE YEAR OF THE HIGHEST RANK IS : " + yearOfHighestRank("Genevieve", "F"));
        System.out.println("THE YEAR OF THE HIGHEST RANK IS : " + yearOfHighestRank("Mich", "M")); 
    }
    
    public void testAverageRank(){
        System.out.println("The average of these files is: " + getAverageRank("Susan", "F"));
        System.out.println("The average of these files is: " + getAverageRank("Robert", "M"));
    }
    
    public void testHigher(){
        System.out.println("The sum of the names ranked higher than the currentrank is : " + getTotalBirthsRankedHigher(1990,"Emily", "F"));
    }
}