/*
 * This file is part of JGAP.
 *
 * JGAP offers a dual license model containing the LGPL as well as the MPL.
 *
 * For licensing information please see the file license.txt included with JGAP
 * or have a look at the top of class org.jgap.Chromosome which representatively
 * includes the JGAP license policy applicable for any file delivered with JGAP.
 */
package geneticalgorithmjgap;

import java.util.*;
import org.jgap.*;

/**
 * The Greedy Crossover is a specific type of crossover. It can only be is
 * applied if
 * <ul>
 * <li>
 * 1. All genes in the chromosome are different and
 * </li>
 * <li>
 * 2. The set of genes for both chromosomes is identical and only their order in
 * the chromosome can vary.
 * </li>
 * </ul>
 *
 * After the GreedyCrossover, these two conditions always remain true, so it can
 * be applied again and again.
 *
 * The algorithm throws an assertion error if the two initial chromosomes does
 * not satisfy these conditions.
 *
 * Greedy crossover can be best explained in the terms of the Traveling Salesman
 * Problem:
 *
 * The algorithm selects the first city of one parent, compares the cities
 * leaving that city in both parents, and chooses the closer one to extend the
 * tour. If one city has already appeared in the tour, we choose the other city.
 * If both cities have already appeared, we randomly select a non-selected city.
 *
 * See J. Grefenstette, R. Gopal, R. Rosmaita, and D. Gucht.
 * <i>Genetic algorithms for the traveling salesman problem</i>. In Proceedings
 * of the Second International Conference on Genetic Algorithms. Lawrence
 * Eribaum Associates, Mahwah, NJ, 1985. and also
 * <a href="http://ecsl.cs.unr.edu/docs/techreports/gong/node3.html">
 * Sushil J. Louis & Gong Li</a>}
 *
 * @author Audrius Meskauskas
 * @author <font size=-1>Neil Rotstan, Klaus Meffert (reused code from
 * {@link org.jgap.impl.CrossoverOperator CrossoverOperator})</font>
 * @since 2.0
 */
public class MyGreedyCrossover
        extends BaseGeneticOperator {

    /**
     * String containing the CVS revision. Read out via reflection!
     */
    private static final String CVS_REVISION = "$Revision: 1.30 $";

    /**
     * Switches assertions on/off. Must be true during tests and debugging.
     */
    boolean ASSERTIONS = true;

    private int m_startOffset = 1;

    /**
     * Default constructor for dynamic instantiation.<p>
     * Attention: The configuration used is the one set with the static method
     * Genotype.setConfiguration.
     *
     * @throws InvalidConfigurationException
     *
     * @author Klaus Meffert
     * @since 2.6
     * @since 3.0 (since 2.0 without a_configuration)
     */
    public MyGreedyCrossover()
            throws InvalidConfigurationException {
        super(Genotype.getStaticConfiguration());
    }

    /**
     * Using the given configuration.
     *
     * @param a_configuration the configuration to use
     * @throws InvalidConfigurationException
     *
     * @author Klaus Meffert
     * @since 3.0 (since 2.6 without a_configuration)
     */
    public MyGreedyCrossover(Configuration a_configuration)
            throws InvalidConfigurationException {
        super(a_configuration);
    }

    public void operate(final Population a_population,
            final List a_candidateChromosomes) {
        int size = Math.min(getConfiguration().getPopulationSize(),
                a_population.size());
        int numCrossovers = (2 * size) / 3;
        RandomGenerator generator = getConfiguration().getRandomGenerator();
        // For each crossover, grab two random chromosomes and do what
        // Grefenstette et al say.
        // --------------------------------------------------------------
        for (int i = 0; i < numCrossovers; i++) {
            IChromosome origChrom1 = a_population.getChromosome(generator.
                    nextInt(size));
            IChromosome firstMate = (IChromosome) origChrom1.clone();
            IChromosome origChrom2 = a_population.getChromosome(generator.
                    nextInt(size));
            IChromosome secondMate = (IChromosome) origChrom2.clone();
            // In case monitoring is active, support it.
            // -----------------------------------------
            if (m_monitorActive) {
                firstMate.setUniqueIDTemplate(origChrom1.getUniqueID(), 1);
                firstMate.setUniqueIDTemplate(origChrom2.getUniqueID(), 2);
                secondMate.setUniqueIDTemplate(origChrom1.getUniqueID(), 1);
                secondMate.setUniqueIDTemplate(origChrom2.getUniqueID(), 2);
            }

            operate(firstMate, secondMate);
            // Add the modified chromosomes to the candidate pool so that
            // they'll be considered for natural selection during the next
            // phase of evolution.
            // -----------------------------------------------------------
            a_candidateChromosomes.add(firstMate);
            a_candidateChromosomes.add(secondMate);
        }
    }

    /**
     * Performs a greedy crossover for the two given chromosoms.
     *
     * @param a_firstMate the first chromosome to crossover on
     * @param a_secondMate the second chromosome to crossover on
     * @throws Error if the gene set in the chromosomes is not identical
     *
     * @author Audrius Meskauskas
     * @since 2.1
     */
    public void operate(final IChromosome a_firstMate,
            final IChromosome a_secondMate) {
        Gene[] g1 = a_firstMate.getGenes();
        Gene[] g2 = a_secondMate.getGenes();
        Gene[] c1, c2;
        try {

//TODO Evitar que se modifiquen g1 y/o g2
            c1 = operate(g1, g2);
            c2 = operate(g2, g1);

            a_firstMate.setGenes(c1);
            a_secondMate.setGenes(c2);
        } catch (InvalidConfigurationException cex) {
            throw new Error("Error occured while operating on:"
                    + a_firstMate + " and "
                    + a_secondMate
                    + ". First " + m_startOffset + " genes were excluded "
                    + "from crossover. Error message: "
                    + cex.getMessage());
        }
    }

    protected Gene[] operate(final Gene[] a_g1, final Gene[] a_g2) {

        Gene[] aux_a_g2 = new Gene[4];
        for (int j = 0; j < a_g2.length; j++) {
            aux_a_g2[j] = a_g2[j].newGene();
            aux_a_g2[j].setAllele(a_g2[j].getAllele());
        }

        //Obtengo la posicion en el segundo cromosoma en la que se situa el tercer gen del segundo cormosoma
        int i = 0;
        while (!a_g2[i].getAllele().toString().equals(a_g1[2].getAllele().toString())) {
            i = i + 1;
        }

        Gene[] auxGenes = new Gene[4];
        for (int j = 0; j < a_g2.length; j++) {
            auxGenes[j] = aux_a_g2[j].newGene();
            auxGenes[j].setAllele(aux_a_g2[j].getAllele());
        };

        if (i == 3) {
            
            Map map = new Map();
            RandomGenerator generator = getConfiguration().getRandomGenerator();
            ArrayList<String> aux = new ArrayList<String>();
            for (int j = 0; j < aux_a_g2.length; j++) {
                aux_a_g2[j].setToRandomValue(generator);
                aux.add(aux_a_g2[j].getAllele().toString());

                if (aux_a_g2[j].getAllele().toString().equals("") || !map.validTrip(aux)) {
                    aux_a_g2[j].setToRandomValue(generator);
                    aux.remove(j);
                    j = j - 1;
                }
            }
            
        } else if (!a_g1[3].getAllele().toString().equals(a_g2[i + 1].getAllele().toString())) {

            if (i == 0) {
                aux_a_g2[i].setAllele(a_g1[2].getAllele());
                aux_a_g2[i + 1].setAllele(a_g1[3].getAllele());
                if(aux_a_g2[i + 2].getAllele().toString().equals(a_g1[3].getAllele().toString())){
                    aux_a_g2[i + 2].setAllele(auxGenes[i + 1].getAllele());
                } else {
                    aux_a_g2[i + 3].setAllele(auxGenes[i + 1].getAllele());
                }
            } else if (i == 1) {
                if (a_g1[3].getAllele().toString().equals(a_g2[i - 1].getAllele().toString())) {
                    aux_a_g2[i].setAllele(a_g1[2].getAllele());
                    aux_a_g2[i + 1].setAllele(a_g1[3].getAllele());
                    aux_a_g2[i - 1].setAllele(auxGenes[i + 1].getAllele());
                } else {
                    aux_a_g2[i].setAllele(a_g1[2].getAllele());
                    aux_a_g2[i + 1].setAllele(a_g1[3].getAllele());
                    aux_a_g2[i + 2].setAllele(auxGenes[i + 1].getAllele());
                }
            } else if (i == 2) {
                aux_a_g2[i].setAllele(a_g1[2].getAllele());
                aux_a_g2[i + 1].setAllele(a_g1[3].getAllele());
                if(aux_a_g2[i - 2].getAllele().toString().equals(a_g1[3].getAllele().toString())){
                    aux_a_g2[i - 2].setAllele(auxGenes[i + 1].getAllele());
                } else {
                    aux_a_g2[i - 1].setAllele(auxGenes[i + 1].getAllele());
                }
            }

        }

        return aux_a_g2;
    }

    protected Gene findNext(final Gene[] a_g, final Gene a_x) {
        for (int i = m_startOffset; i < a_g.length - 1; i++) {
            if (a_g[i].equals(a_x)) {
                return a_g[i + 1];
            }
        }
        return null;
    }

    /**
     * Sets a number of genes at the start of chromosome, that are excluded from
     * the swapping. In the Salesman task, the first city in the list should
     * (where the salesman leaves from) probably should not change as it is part
     * of the list. The default value is 1.
     *
     * @param a_offset the start offset to use
     */
    public void setStartOffset(int a_offset) {
        m_startOffset = a_offset;
    }

    /**
     * Gets a number of genes at the start of chromosome, that are excluded from
     * the swapping. In the Salesman task, the first city in the list should
     * (where the salesman leaves from) probably should not change as it is part
     * of the list. The default value is 1.
     *
     * @return the start offset used
     */
    public int getStartOffset() {
        return m_startOffset;
    }

    /**
     * Compares the given GeneticOperator to this GeneticOperator.
     *
     * @param a_other the instance against which to compare this instance
     * @return a negative number if this instance is "less than" the given
     * instance, zero if they are equal to each other, and a positive number if
     * this is "greater than" the given instance
     *
     * @author Klaus Meffert
     * @since 2.6
     */
    public int compareTo(final Object a_other) {
        if (a_other == null) {
            return 1;
        }
        MyGreedyCrossover op = (MyGreedyCrossover) a_other;
        if (getStartOffset() < op.getStartOffset()) {
            // start offset less, meaning more to do --> return 1 for "is greater than"
            return 1;
        } else if (getStartOffset() > op.getStartOffset()) {
            return -1;
        } else {
            // Everything is equal. Return zero.
            // ---------------------------------
            return 0;
        }
    }
}
