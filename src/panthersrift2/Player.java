/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package panthersrift2;

/**
 *
 * @author Giovanni
 */
public class Player {
    private String name;
    private String rank;
    private double weight;
    
    public Player(String name, String rank) {
        this.name = name;
        this.rank = rank;
        
        switch(rank) {
            case "UNRANKED":
                weight = 7.04;
                break;
            case "BRONZE":
                weight = 10.67;
                break;
            case "SILVER":
                weight = 19.2;
                break;
            case "GOLD":
                weight = 24;
                break;
            case "PLATINUM":
                weight = 48;
                break;
            case "DIAMOND":
                weight = 64;
                break;
        }
        
        this.weight *= 62.5; // FOR NOW WE SCALE BY 450
    }
    
    public String getName() {
        return name;
    }
    
    public String getRank() {
        return rank;
    }
    
    public double getWeight() {
        return weight;
    }
    
    @Override
    public String toString() {
        return name + " | " + rank + " | " + weight;
    }
}
