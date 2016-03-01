/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticalgorithmjenetics;

/**
 *
 * @author ernsferrari
 */
public class Map {
    
    private String cityA, cityB, cityC, cityD;
    private int AB, AC, AD, BC, BD, CD;
    
    public Map(){
        this.cityA = "A";
        this.cityB = "B";
        this.cityC = "C";
        this.cityD = "D";
        
        this.AB = 20;
        this.AC = 40;
        this.AD = 10;
        this.BC = 5;
        this.BD = 15;
        this.CD = 10;
    }
    
    public String printMap(){
        
        String map = "    20\n" +
                        "  A————B\n" +
                        "  |\\10/|\n" +
                        "40| \\/5|15\n" +
                        "  | /\\ |\n" +
                        "  C————D\n" +
                        "    10";
        
        return map;
    }
    
    public int tripLength(String [] trip){
        
        int length = 0;
        
        for(int i=0; i<trip.length-1; i++){
            
            if((trip[i].equals(this.cityA) && trip[i+1].equals(this.cityB)) || (trip[i].equals(this.cityB) && trip[i+1].equals(this.cityA))){
                length = length + this.AB;
            } else if ((trip[i].equals(this.cityA) && trip[i+1].equals(this.cityC)) || (trip[i].equals(this.cityC) && trip[i+1].equals(this.cityA))){
                length = length + this.AC;
            } else if ((trip[i].equals(this.cityA) && trip[i+1].equals(this.cityD)) || (trip[i].equals(this.cityD) && trip[i+1].equals(this.cityA))){
                length = length + this.AD;
            } else if ((trip[i].equals(this.cityB) && trip[i+1].equals(this.cityC)) || (trip[i].equals(this.cityC) && trip[i+1].equals(this.cityB))){
                length = length + this.BC;
            } else if ((trip[i].equals(this.cityB) && trip[i+1].equals(this.cityD)) || (trip[i].equals(this.cityD) && trip[i+1].equals(this.cityB))){
                length = length + this.BD;
            } else if ((trip[i].equals(this.cityC) && trip[i+1].equals(this.cityD)) || (trip[i].equals(this.cityD) && trip[i+1].equals(this.cityC))){
                length = length + this.CD;
            }
        }
                
        return length;
    }
    
    public int maxValue(){
        
        return this.AB + this.AC + this.AD + this.BC + this.BD + this.CD;
    }

    public String getCityA() {
        return cityA;
    }

    public String getCityB() {
        return cityB;
    }

    public String getCityC() {
        return cityC;
    }

    public String getCityD() {
        return cityD;
    }

    public int getAB() {
        return AB;
    }

    public int getAC() {
        return AC;
    }

    public int getAD() {
        return AD;
    }

    public int getBC() {
        return BC;
    }

    public int getBD() {
        return BD;
    }

    public int getCD() {
        return CD;
    }
    
    
}
