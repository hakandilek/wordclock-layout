package me.dilek.wordclock.layout.ga;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jenetics.AnyChromosome;
import org.jenetics.AnyGene;
import org.jenetics.Genotype;
import org.jenetics.Phenotype;
import org.jenetics.engine.Engine;
import org.jenetics.engine.EvolutionResult;
import org.jenetics.util.Factory;

import me.dilek.wordclock.layout.Intervals;
import me.dilek.wordclock.layout.Layout;
import me.dilek.wordclock.layout.Reading;
import me.dilek.wordclock.layout.TimeValue;

public class GeneticSolver {

	private final Engine<AnyGene<Layout>, Integer> engine;
	private final Intervals intervals;
	private int limitGenerations;
	private Phenotype<AnyGene<Layout>, Integer> best;
	private int totalIntervals;

	public GeneticSolver(Collection<String> dictionary, int initialPopulation, int limitGenerations,
			Intervals intervals, int cols, int rows) {
		this.limitGenerations = limitGenerations;
		this.intervals = intervals;
		totalIntervals = intervals.getValues().size();

		// 1.) Define the genotype factory
		Factory<Genotype<AnyGene<Layout>>> genFactory = Genotype
				.of(AnyChromosome.of(() -> LayoutGenerator.of(cols, rows, dictionary), initialPopulation));

		// 3.) Create the execution environment.
		this.engine = Engine.builder(this::eval, genFactory).build();
	}

	// 2.) Definition of the fitness function.
	private Integer eval(Genotype<AnyGene<Layout>> gt) {
		return gt.getChromosome().stream().map(AnyGene::getAllele).collect(Collectors.summingInt(this::eval));
	}

	private int eval(Layout layout) {
		int matches = 0;
		List<TimeValue> values = intervals.getValues();
		for (TimeValue timeValue : values) {
			List<Reading> readings = timeValue.getReadings();
			for (Reading reading : readings) {
				if (layout.contains(reading)) {
					matches++;
					continue;
				}
			}
		}
		return matches;
	}

	private void update(final EvolutionResult<AnyGene<Layout>, Integer> result) {
		if (best == null || best.compareTo(result.getBestPhenotype()) < 0) {
			best = result.getBestPhenotype();
			System.out.println(MessageFormat.format("Best [{0}] : {1}", result.getGeneration(), best.getFitness()));
			Optional<Layout> collect = best.getGenotype().getChromosome().stream().map(AnyGene::getAllele)
					.collect(Collectors.maxBy((a, b) -> {
						return eval(a) - eval(b);
					}));
			Integer eval = collect.map((l) -> eval(l)).orElse(0);
			System.out.println(MessageFormat.format(" with matches {0} / {1} : {2}%", eval, totalIntervals,
					(100 * eval / totalIntervals)));
		} else {
			System.out.println(MessageFormat.format("Same [{0}]", result.getGeneration()));
		}
	}

	public void solve() {
		// 4.) Start the execution (evolution) and
		// collect the result.
		Genotype<AnyGene<Layout>> result = engine.stream().peek(this::update).limit(limitGenerations)
				.collect(EvolutionResult.toBestGenotype());

		Optional<Layout> collect = result.getChromosome().stream().map(AnyGene::getAllele)
				.collect(Collectors.maxBy((a, b) -> {
					return eval(a) - eval(b);
				}));
		Integer eval = collect.map((l) -> eval(l)).orElse(0);

		System.out.println("--- solution ---");
		System.out.println(collect);
		System.out.println(String.format("covering %d of %d intervals (%d %%)", eval, totalIntervals,
				(100 * eval / totalIntervals)));
	}

}
