
/**
 * Write a description of class QuakeSortInPlace here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;
import edu.duke.*;

public class QuakeSortInPlace {
    public QuakeSortInPlace() {
        // TODO Auto-generated constructor stub
    }
   
    public int getSmallestMagnitude(ArrayList<QuakeEntry> quakes, int from) {
        int minIdx = from;
        for (int i=from+1; i< quakes.size(); i++) {
            if (quakes.get(i).getMagnitude() < quakes.get(minIdx).getMagnitude()) {
                minIdx = i;
            }
        }
        return minIdx;
    }
    
    public int getLargestDepth(ArrayList<QuakeEntry> quakeData, int from) {
        int idx = from;
        for (int i=from+1;i<quakeData.size();i++) {
            if (quakeData.get(i).getDepth() > quakeData.get(idx).getDepth()) {
                idx = i;
            }
        }
        return idx;
    }
    
    public void onePassBubbleSort(ArrayList<QuakeEntry> quakeData, int numSorted) {
        for(int i = 0;i<quakeData.size()-numSorted;i++) {
            if(quakeData.get(i).getMagnitude() > quakeData.get(i+1).getMagnitude()) {
                QuakeEntry swap1 = quakeData.get(i);
                QuakeEntry swap2 = quakeData.get(i+1);
                quakeData.set(i,swap2);
                quakeData.set(i+1,swap1);
            }
        }
    }
    
    public void sortByMagnitudeWithBubbleSort(ArrayList<QuakeEntry> quakeData) {
        for(int iter = 1;iter<quakeData.size();iter++) {
            System.out.println("Pass "+iter);
            onePassBubbleSort(quakeData, iter);
            for (QuakeEntry qe: quakeData) { 
                System.out.println(qe);
            }
        }
    }
    
    public void sortByLargestDepth(ArrayList<QuakeEntry> quakeData) {
        for (int i=0;i<50;i++) {
            int maxIdx = getLargestDepth(quakeData,i);
            QuakeEntry qi = quakeData.get(i);
            QuakeEntry qmax = quakeData.get(maxIdx);
            quakeData.set(i,qmax);
            quakeData.set(maxIdx,qi);
        }
    }
    
    public void sortByMagnitude(ArrayList<QuakeEntry> in) {
       
       for (int i=0; i<in.size(); i++) {
            int minIdx = getSmallestMagnitude(in,i);
            QuakeEntry qi = in.get(i);
            QuakeEntry qmin = in.get(minIdx);
            in.set(i,qmin);
            in.set(minIdx,qi);
        }
        
    }

    public void testSort() {
        EarthQuakeParser parser = new EarthQuakeParser(); 
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/earthQuakeDataWeekDec6sample2.atom";
        //String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);  
        System.out.println("read data for "+list.size()+" quakes");    
        //sortByMagnitude(list);
        //sortByLargestDepth(list);
        sortByMagnitudeWithBubbleSortWithCheck(list);
        sortByMagnitudeWithCheck(list);
        System.out.println("-----");
        for (QuakeEntry qe: list) { 
            System.out.println(qe);
        } 
        
    }
    
    public boolean checkInSortedOrder(ArrayList<QuakeEntry> quakeData) {
        for (int i = 0;i<quakeData.size()-1;i++) {
            if(quakeData.get(i).getMagnitude() > quakeData.get(i+1).getMagnitude()) {
                return false;
            }
        }
        return true;
    }
    
    public void sortByMagnitudeWithCheck(ArrayList<QuakeEntry> in) {
        for (int i=0; i<in.size(); i++) {
            int minIdx = getSmallestMagnitude(in,i);
            QuakeEntry qi = in.get(i);
            QuakeEntry qmin = in.get(minIdx);
            in.set(i,qmin);
            in.set(minIdx,qi);
            if (checkInSortedOrder(in)) {
                System.out.println("Success after "+(i+1)+" tries. ");
                for (QuakeEntry qe: in) { 
                System.out.println(qe);
                }
                break;
            }
        }
    }
    
    public void sortByMagnitudeWithBubbleSortWithCheck(ArrayList<QuakeEntry> quakeData) {
        for(int iter = 1;iter<quakeData.size();iter++) {
            System.out.println("Pass "+iter);
            onePassBubbleSort(quakeData, iter);
            if (checkInSortedOrder(quakeData)) {
                System.out.println("Success after "+iter+" tries. ");
                for (QuakeEntry qe: quakeData) { 
                System.out.println(qe);
                }
                break;
            }
        }
    }
    
    public void createCSV() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "data/nov20quakedata.atom";
        String source = "data/nov20quakedatasmall.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: " + list.size());
    }
    
    public void dumpCSV(ArrayList<QuakeEntry> list) {
        System.out.println("Latitude,Longitude,Magnitude,Info");
        for(QuakeEntry qe : list){
            System.out.printf("%4.2f,%4.2f,%4.2f,%s\n",
                              qe.getLocation().getLatitude(),
                              qe.getLocation().getLongitude(),
                              qe.getMagnitude(),
                              qe.getInfo());
        }
        
    }
}
