/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticalgorithmjenetics;

import org.jenetics.EnumGene;
import org.jenetics.Genotype;
import org.jenetics.PartiallyMatchedCrossover;
import org.jenetics.PermutationChromosome;
import org.jenetics.Phenotype;
import org.jenetics.RouletteWheelSelector;
import org.jenetics.SwapMutator;
import org.jenetics.TournamentSelector;
import org.jenetics.engine.Engine;
import static org.jenetics.engine.EvolutionResult.toBestPhenotype;
import org.jenetics.engine.EvolutionStatistics;
import org.jenetics.util.Factory;
import org.jenetics.util.ISeq;

/**
 *
 * @author ernsferrari
 */
public class TravelingSalesmanJenetics {

    //Definition of the fitness function.
    private static int evaluate(final Genotype<EnumGene<Character>> gt) {
        
        Map map = new Map();
        String [] trip = new String[4];
        
        for(int i=0; i<gt.getChromosome().length(); i++){
            trip [i] = gt.getChromosome(0).getGene(i).toString();
        }
              
        return map.maxValue() - map.tripLength(trip);
        
    }

    public static void main(String[] args) throws Exception {

        System.out.println(new Map().printMap());
        
        ISeq<Character> alleles = ISeq.of('A', 'B', 'C', 'D');
        Factory<Genotype<EnumGene<Character>>> gtf = Genotype.of(
            PermutationChromosome.of(alleles)
        );
        
        final Engine<EnumGene<Character>, Integer> engine = Engine
        	.builder(
                        TravelingSalesmanJenetics::evaluate,
                        gtf)
		.populationSize(5)
                .maximalPhenotypeAge(10)
                .survivorsSelector(new RouletteWheelSelector<>())
		.offspringSelector(new TournamentSelector<>())
		.alterers(
			new PartiallyMatchedCrossover<>(0.6),
                        new SwapMutator<>(0.2))
		.build();

	// Create evolution statistics consumer.
	final EvolutionStatistics<Integer, ?>
            statistics = EvolutionStatistics.ofNumber();
        
        final Phenotype<EnumGene<Character>, Integer> best =
            engine.stream()
            // The evolution will stop after maximal 20
            // generations.
            .limit(20)
            // Update the evaluation statistics after
            // each generation
            .peek(statistics)
            // Collect (reduce) the evolution stream to
            // its best phenotype.
            .collect(toBestPhenotype());

	System.out.println(statistics);
	System.out.println(best);
    }
}
