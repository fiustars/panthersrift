/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panthersrift2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author Giovanni
 */
public class PanthersRift2 {
    
    private ArrayList<Player> competitors;
    private Player[][] teams;
    
    public PanthersRift2() throws FileNotFoundException {
        competitors = new ArrayList<>();
        teams = new Player[16][5];
        readTextFile();
        assignTeams();
    }
    
    private void readTextFile() throws FileNotFoundException {
        try (Scanner in = new Scanner(new File("players.txt"))) {
            int i = 0;
            while(in.hasNext()) {
                competitors.add(new Player(in.next(),in.next()));
                i++;
            }
        }
    }
    
    private void assignTeams() {
        final int TEAM_SIZE = 5;
        int counter = 0;
        
        Collections.shuffle(competitors);
        
        for (Player[] team : teams) {
            for (int y = 0; y < TEAM_SIZE; y++) {
                team[y] = competitors.get(counter);
                counter++;
            }
        }
        
    }
    
    public void balanceTeams(Player[][] teams, double avgSum,double threshold) {
//        double lowerBounds = avgSum - (threshold/2),
//            upperBounds = avgSum + (threshold/2);
        while(true) {
            int highTeamIndex = maxWeight(teams),
                     lowTeamIndex = minWeight(teams);
            if( threshold < Math.sqrt(standardDeviation(teams,avgSum))) {
                int highIndex = maxWeight(teams[highTeamIndex]),
                    lowIndex = (int) (Math.random()*teams[lowTeamIndex].length);
                        
                // SWAP
                Player highPlayer = teams[highTeamIndex][highIndex];
                teams[highTeamIndex][highIndex] = teams[lowTeamIndex][lowIndex];
                teams[lowTeamIndex][lowIndex] = highPlayer;
            }
            else {
               break;
            }
        }
        
        System.out.println("Balanced Teams:");
        printTeams(teams);
    }
    
    public double standardDeviation(Player[][] teams,double avg) {
        double deviation = 0;
        
        for (Player[] team : teams) {
            deviation += Math.pow(sumTeam(team) - avg, 2);
        }
        
        deviation /= teams.length;
        deviation = Math.sqrt(deviation);
        
        return deviation;
    }
    
    public void printPlayers() {
        for (Player player : competitors) {
            System.out.println(player);
        }
    }
    
    public void printTeam(Player[] team) {
        for (Player player : team) {
            System.out.print(player + ", ");
        }
        System.out.print("Team Weight: " + sumTeam(team));
        System.out.println();
    }
    
    public void printTeams(Player[][] teams) {
        for (int x = 0; x < teams.length; x++) {
            for(int y = 0; y < teams[x].length; y++) {
                System.out.print(teams[x][y] + ", ");
            }
            System.out.print("Team Weight: " + sumTeam(teams[x]));
            System.out.println();
        }
        System.out.println();
    }
    
    public double sumTeam(Player[] team) {
        double sumWeight = 0;
        for (Player player : team) {
            sumWeight += player.getWeight();
        }
        return sumWeight;
    }
    
    public int minWeight(Player[] team) {
        int[] mins;
        int size = 0, count = 0;
        double min = Integer.MAX_VALUE;
        
        for (Player player : team) {
            min = Math.min(min, player.getWeight());
        }
        
        for (Player player : team) {
            if(player.getWeight() == min) size++;
        }
        
        mins = new int[size];
        
        for (int i = 0; i < team.length; i++) {
            if(team[i].getWeight() == min) {
                mins[count] = i;
                count++;
            }
        }
        
        return mins[(int) (Math.random()*size)];
    }
    
    public int minWeight(Player[][] teams) {
        int[] mins;
        int size = 0, count = 0;
        double min = Integer.MAX_VALUE;
        
        for (Player[] team : teams) {
            min = Math.min(min, sumTeam(team));
        }
        
        for (Player[] team : teams) {
            if(sumTeam(team) == min) size++;
        }
        
        mins = new int[size];
        
        for(int i = 0; i < teams.length; i++) {
            if(min == sumTeam(teams[i])) {
                mins[count] = i;
                count++;
            }
        }
        
        return mins[(int) (Math.random()*size)];
    }
    
    public int maxWeight(Player[] team) {
        int[] maxs;
        int size = 0, count = 0;
        double max = Integer.MIN_VALUE;
        
        for (Player player : team) {
            max = Math.max(max, player.getWeight());
        }
        
        for (Player player : team) {
            if(player.getWeight() == max) size++;
        }
        
        maxs = new int[size];
        
        for (int i = 0; i < team.length; i++) {
            if(team[i].getWeight() == max) {
                maxs[count] = i;
                count++;
            }
        }
        
        return maxs[(int) (Math.random()*size)];
    }
    
    public int maxWeight(Player[][] teams) {
        int[] maxs;
        int size = 0, count = 0;
        double max = Integer.MIN_VALUE;
        
        for (Player[] team : teams) {
            max = Math.max(max, sumTeam(team));
        }
        
        for (Player[] team : teams) {
            if(sumTeam(team) == max) size++;
        }
        
        maxs = new int[size];
        
        for(int i = 0; i < teams.length; i++) {
            if(max == sumTeam(teams[i])) {
                maxs[count] = i;
                count++;
            }
        }
        
        return maxs[(int) (Math.random()*size)];
    }
    
    public double avgTeamSum(Player[][] teams) {
        double avg = 0;
        for (Player[] team : teams) {
            avg += sumTeam(team);
        }
        avg /= teams.length;
        return avg;
    }
    
    public void matchUps() {
        ArrayList<Player[]> tempTeams = new ArrayList<>();
        tempTeams.addAll(Arrays.asList(teams));
        Collections.shuffle(tempTeams);
        while(!tempTeams.isEmpty()) {
            int r1 = (int)(Math.random()*tempTeams.size());
            Player[] t1 = tempTeams.get(r1);
            tempTeams.remove(t1);
            int r2 = (int)(Math.random()*tempTeams.size());
            Player[] t2 = tempTeams.get(r2);
            printTeam(t1);
            System.out.println("Against");
            printTeam(t2);
            System.out.println("Difference between teams:" 
                    + (Math.abs(sumTeam(t1) - sumTeam(t2))));
            System.out.println();
            tempTeams.remove(t2);
        }
    }
    
    public static void main(String[] args) throws FileNotFoundException {
        // CREATE TEST
        PanthersRift2 test = new PanthersRift2();
        
        // PRINT TEAMS
        System.out.println("Original Teams:");
        test.printTeams(test.teams);
        
        // CREATE PLAYER TEAM
        Player[] minTeam = test.teams[test.minWeight(test.teams)];
        Player[] maxTeam = test.teams[test.maxWeight(test.teams)];
        
        // PRINT LOWEST TEAM DATA
        System.out.println("\nMinimum Team Total: " + test.sumTeam(minTeam));
        test.printTeam(minTeam);
        
        // PRINT HIGHEST TEAM DATA
        System.out.println("\nMaximum Team Total: " + test.sumTeam(maxTeam));
        test.printTeam(maxTeam);
        
        double avg = test.avgTeamSum(test.teams);
        
        // PRINT AVERAGE TEAM SUM
        System.out.println("\nAverage Team Sum: " + avg + "\n");
        
        String threshold = JOptionPane.showInputDialog(
            "What is your ideal threshold?"
        );
        
        // RUN BALANCING ALGORITHM
        test.balanceTeams(test.teams, avg, Double.parseDouble(threshold));
        
        // CREATE PLAYER TEAM
        minTeam = test.teams[test.minWeight(test.teams)];
        maxTeam = test.teams[test.maxWeight(test.teams)];
        
        // PRINT LOWEST TEAM DATA
        System.out.println("\nMinimum Team Total: " + test.sumTeam(minTeam));
        test.printTeam(minTeam);
        
        // PRINT HIGHEST TEAM DATA
        System.out.println("\nMaximum Team Total: " + test.sumTeam(maxTeam));
        test.printTeam(maxTeam);
        System.out.println();

                
        // DISPLAY MATCH UPS
        test.matchUps();
        
        // END PROGRAM
        System.exit(0);
    }
}
