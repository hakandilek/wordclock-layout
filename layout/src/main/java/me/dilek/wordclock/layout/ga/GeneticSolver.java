package me.dilek.wordclock.layout.ga;

import java.util.Collection;
import java.util.stream.Collectors;

import org.jenetics.AnyChromosome;
import org.jenetics.AnyGene;
import org.jenetics.Genotype;
import org.jenetics.engine.Engine;
import org.jenetics.engine.EvolutionResult;
import org.jenetics.util.Factory;

import me.dilek.wordclock.layout.Intervals;
import me.dilek.wordclock.layout.Layout;

public class GeneticSolver {

	private final Engine<AnyGene<Layout>, Integer> engine;
	private final Intervals intervals;

	public GeneticSolver(Collection<String> dictionary, int initialPopulation, Intervals intervals) {
		this.intervals = intervals;
		// 1.) Define the genotype factory
		Factory<Genotype<AnyGene<Layout>>> genFactory = Genotype
				.of(AnyChromosome.of(() -> LayoutGenerator.of(11, 10, dictionary), initialPopulation));

		// 3.) Create the execution environment.
		this.engine = Engine.builder(this::eval, genFactory).build();
	}

	// 2.) Definition of the fitness function.
	private Integer eval(Genotype<AnyGene<Layout>> gt) {
		return gt.getChromosome().stream().map(AnyGene::getAllele).collect(Collectors.summingInt(this::eval));
	}

	private int eval(Layout layout) {
		// TODO: evaluate layout for the given interval
		return 0;
	}

	public void solve() {
		// 4.) Start the execution (evolution) and
		// collect the result.
		Genotype<AnyGene<Layout>> result = engine.stream().limit(100).collect(EvolutionResult.toBestGenotype());

		System.out.println("--- solution ---");
		AnyGene<Layout> gene = result.getGene();
		Layout solution = gene.getAllele();
		System.out.println(gene);
		int eval = eval(solution);
		System.out.println(String.format("covering %d of %d intervals (%d %%)", eval, intervals.getValues().size(), (100 * eval / intervals.getValues().size())));
	}

}
