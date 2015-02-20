/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panthersrift;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author Giovanni
 */
public class PanthersRift {

    /**
     * Assigns players to random teams based on
     *
     * @return
     */
    public static int[][] assignRandomTeams(int diamond, int platinum, int gold,
            int silver, int bronze, int unranked) {
        Random rand = new Random();
        ArrayList<ArrayList<Integer>> input = new ArrayList<>();
        ArrayList<Integer> vals = new ArrayList<>();
        int i;
        for (i = 0; i < diamond; i++) {
            vals.add(2350);
        }
        for (i = i; i < diamond + platinum; i++) {
            vals.add(2025);
        }
        for (i = i; i < diamond + platinum + gold; i++) {
            vals.add(1675);
        }
        for (i = i; i < diamond + platinum + gold + silver; i++) {
            vals.add(1325);
        }
        for (i = i; i < diamond + platinum + gold + silver + bronze; i++) {
            vals.add(1025);
        }
        for (i = i; i < diamond + platinum + gold + silver + bronze + unranked; i++) {
            vals.add(875);
        }

        //int r = Math.abs(rand.nextInt()) % vals.size();
        int r = rand.nextInt( vals.size());
        int[][] arr = new int[i / 5][5]; //removed -1

        for (int j = 0; j < i / 5; j++) { // removed -1
            for (int k = 0; k < 5; k++) {
                arr[j][k] = vals.get(r);
                vals.remove(r);
                //r = rand.nextInt( vals.size() + 1) ;
                if ( vals.size() > 0) r = rand.nextInt( vals.size() );
            }

        }

        return arr;
    }

    public static double standardDeviation(int[] x, double average) {
        double varience = 0;
        System.out.println("average: " + average);
        for (int i = 0; i < x.length; i++) {
            varience += Math.pow(x[i] - average, 2);
        }
        varience = (int) Math.pow(varience / x.length, 0.5);
        return varience;
    }

    public static int[][] swap(int[][] x, int[] y, double deviation, double average) {
        Random rand = new Random();
        int index = 0;
        int index2 = 0;
        int index3 = 0;
        for (int i = 0; i < y.length; i++) {
            if (Math.abs(y[i] - average) > deviation) {
                index = Math.abs(rand.nextInt()) % 5;
                index2 = Math.abs(rand.nextInt()) % 5;
                index3 = Math.abs(rand.nextInt()) % 15;
                int z = x[i][index];
                x[i][index] = x[index3][index2];
                x[index3][index2] = z;

            }
        }
        return x;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int teams = Integer.parseInt(
                JOptionPane.showInputDialog("How many teams are entering?")
        );
        int threshold = Integer.parseInt(
                JOptionPane.showInputDialog("What is your ideal threshold?")
        );
        int[][] a = assignRandomTeams(
                Integer.parseInt(JOptionPane.showInputDialog("How many Diamonds?")),
                Integer.parseInt(JOptionPane.showInputDialog("How many Platinums?")),
                Integer.parseInt(JOptionPane.showInputDialog("How many Golds?")),
                Integer.parseInt(JOptionPane.showInputDialog("How many Silvers?")),
                Integer.parseInt(JOptionPane.showInputDialog("How many Bronzes?")),
                Integer.parseInt(JOptionPane.showInputDialog("How many Unrankeds?"))
        );
        double deviation = 0;
        int high = 0;
        int low = 2500;
        int[] average = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            int total = 0;
            for (int k = 0; k < 5; k++) {
                System.out.print(a[i][k] + " ");
                total = total + a[i][k];

            }
            System.out.println("TOTAL: " + (total / 5));
            deviation += total / 5;
            average[i] = total / 5;
        }
        double averageTeam = deviation / (teams);
        
        System.out.println("Average team: " + standardDeviation(average, averageTeam));
        while (standardDeviation(average, averageTeam) > threshold) {
            int[][] b = swap(a, average, standardDeviation(average, averageTeam), averageTeam);
            deviation = 0;
            for (int i = 0; i < b.length; i++) {
                int total = 0;
                for (int j = 0; j < 5; j++) {
                    System.out.print(b[i][j] + " ");
                    total += b[i][j];
                }
                deviation += total / 5;
                average[i] = total / 5;
               
                System.out.println("TOTAL: " + (average[i]));
            }
            
            for(int i = 0; i < average.length; i++){
                if(average[i] < low){
                    low = average[i];
                }
                
                if(average[i] > high){
                    high = average[i];
                }
            }
            averageTeam = deviation / (teams - 1);
            System.out.println("Average team: " + (int)standardDeviation(average, averageTeam) + ";");
            System.out.println("Team Range: " + (high - low) + "+");
            low = 2500;
            high = 0;
            System.out.println();
        }

    }

}
