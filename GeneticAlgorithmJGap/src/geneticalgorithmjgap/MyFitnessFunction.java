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

import org.jgap.*;

/**
 * Fitness function for our example. It's without sense, it's just to
 * demonstrate how to evaluate a chromosome with 40 4-field-genes and one
 * 3-field gene. Each toplevel-gene is a CompositeGene, each "field" within
 * a CompositeGene is a BooleanGene here (arbitrarily chosen).
 *
 * @author Klaus Meffert
 * @since 3.0
 */
public class MyFitnessFunction
    extends FitnessFunction {

    public double evaluate(IChromosome a_subject) {
        Map map = new Map();
        int total = map.maxValue();

        total = total - map.tripLength(a_subject);

        return total;
  }
}
