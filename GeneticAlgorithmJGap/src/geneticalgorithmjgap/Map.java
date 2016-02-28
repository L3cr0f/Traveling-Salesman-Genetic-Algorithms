/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticalgorithmjgap;

import java.util.ArrayList;
import org.jgap.*;

/**
 *
 * @author ernsferrari
 */
public class Map {

    private String cityA, cityB, cityC, cityD;
    private int AB, AC, AD, BC, BD, CD;

    public Map() {
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

    public String printMap() {

        String map = "    20\n"
                + "  A————B\n"
                + "  |\\10/|\n"
                + "40| \\/5|15\n"
                + "  | /\\ |\n"
                + "  C————D\n"
                + "    10";

        return map;
    }

    public int tripLength(IChromosome trip) {

        int length = 0;

        ArrayList<String> chromosome = new ArrayList<String>();
        for (int i = 0; i < trip.getGenes().length; i++) {
            chromosome.add(trip.getGene(i).getAllele().toString());
        }

        if (this.validTrip(chromosome)) {

            for (int i = 0; i < trip.size() - 1; i++) {

                if ((trip.getGene(i).getAllele().toString().equals(this.cityA) && trip.getGene(i + 1).getAllele().toString().equals(this.cityB))
                        || (trip.getGene(i).getAllele().toString().equals(this.cityB) && trip.getGene(i + 1).getAllele().toString().equals(this.cityA))) {
                    length = length + this.AB;
                } else if ((trip.getGene(i).getAllele().toString().equals(this.cityA) && trip.getGene(i + 1).getAllele().toString().equals(this.cityC))
                        || (trip.getGene(i).getAllele().toString().equals(this.cityC) && trip.getGene(i + 1).getAllele().toString().equals(this.cityA))) {
                    length = length + this.AC;
                } else if ((trip.getGene(i).getAllele().toString().equals(this.cityA) && trip.getGene(i + 1).getAllele().toString().equals(this.cityD))
                        || (trip.getGene(i).getAllele().toString().equals(this.cityD) && trip.getGene(i + 1).getAllele().toString().equals(this.cityA))) {
                    length = length + this.AD;
                } else if ((trip.getGene(i).getAllele().toString().equals(this.cityB) && trip.getGene(i + 1).getAllele().toString().equals(this.cityC))
                        || (trip.getGene(i).getAllele().toString().equals(this.cityC) && trip.getGene(i + 1).getAllele().toString().equals(this.cityB))) {
                    length = length + this.BC;
                } else if ((trip.getGene(i).getAllele().toString().equals(this.cityB) && trip.getGene(i + 1).getAllele().toString().equals(this.cityD))
                        || (trip.getGene(i).getAllele().toString().equals(this.cityD) && trip.getGene(i + 1).getAllele().toString().equals(this.cityB))) {
                    length = length + this.BD;
                } else if ((trip.getGene(i).getAllele().toString().equals(this.cityC) && trip.getGene(i + 1).getAllele().toString().equals(this.cityD))
                        || (trip.getGene(i).getAllele().toString().equals(this.cityD) && trip.getGene(i + 1).getAllele().toString().equals(this.cityC))) {
                    length = length + this.CD;
                }
            }
        }

        return length;
    }

    public boolean validTrip(ArrayList<String> trip) {
        boolean isValid = true;

        for (int i = 0; i < trip.size() - 1; i++) {
            for (int j = i + 1; j < trip.size(); j++) {
                if (trip.get(i).equals(trip.get(j))) {
                    isValid = false;
                    break;
                }
            }
            if (!isValid) {
                break;
            }
        }

        return isValid;
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
