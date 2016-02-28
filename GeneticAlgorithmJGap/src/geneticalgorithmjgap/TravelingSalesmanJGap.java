/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticalgorithmjgap;

import java.util.ArrayList;
import org.jgap.*;
import org.jgap.impl.*;
import org.jgap.Chromosome;

/**
 *
 * @author ernsferrari
 */
public class TravelingSalesmanJGap {

    public static void main(String[] args) throws Exception {

        Configuration conf = new DefaultConfiguration();
        FitnessFunction myFunc = new MyFitnessFunction();
        Map map = new Map();
        System.out.println(map.printMap() + "\n");
        int populationSize = 5;

        Gene[] genes = new Gene[4];

        String validAlphabet = "ABCD";

        genes[0] = new StringGene(conf, 0, 1, validAlphabet);
        genes[1] = new StringGene(conf, 0, 1, validAlphabet);
        genes[2] = new StringGene(conf, 0, 1, validAlphabet);
        genes[3] = new StringGene(conf, 0, 1, validAlphabet);

        IChromosome chromosome = new Chromosome(conf, genes);

        conf.getGeneticOperators().clear();
        conf.setSampleChromosome(chromosome);
        conf.setPopulationSize(populationSize);
        conf.setFitnessFunction(myFunc);
        conf.addGeneticOperator(new MyGreedyCrossover(conf));
        conf.addGeneticOperator(new SwappingMutationOperator(conf, 5));
        conf.setKeepPopulationSizeConstant(true);

        IChromosome[] population = new IChromosome[populationSize];
        for (int i = 0; i < populationSize; i++) {

            Gene[] newGenes = new Gene[4];
            RandomGenerator generator = conf.getRandomGenerator();
            ArrayList<String> aux = new ArrayList<String>();
            for (int j = 0; j < genes.length; j++) {
                newGenes[j] = genes[j].newGene();
                newGenes[j].setToRandomValue(generator);
                aux.add(newGenes[j].getAllele().toString());

                if (newGenes[j].getAllele().toString().equals("") || !map.validTrip(aux)) {
                    newGenes[j].setToRandomValue(generator);
                    aux.remove(j);
                    j = j - 1;
                }
            }

            population[i] = new Chromosome(conf, newGenes);

        }

        Genotype genotype = new Genotype(conf, new Population(conf, population));

        genotype.evolve(10);
        IChromosome fittest = genotype.getFittestChromosome();
        System.out.println("Fittest Chromosome is "
                + fittest.getGene(0).getAllele().toString()
                + fittest.getGene(1).getAllele().toString()
                + fittest.getGene(2).getAllele().toString()
                + fittest.getGene(3).getAllele().toString()
                + " with value "
                + fittest.getFitnessValue());
    }
}
