# Design

1. Declare resource constraints (executors, containers, etc)
2. Group nodes into basic blocks, essentially several nodes that are all required to complete for another basic block to begin
3. Create interference graph for each basic block
4. Mark node interferences based on dependencies
5. Colour graph based on resources
6. Execute graph with resources

## Components

* Instruction Buffer Array (IBA)
* Instruction Shuffle Unit (ISU)
* Pending Lane Buffer (PLB)

## Execution Behaviour

![Scheduling For A Control Flow Graph](./cfg_scheduling.png)

1. Statically analyse graph, categorise branch paths by length into $k$ groups via k-means clustering. A branch path is from divergence to re-convergence.
2. Encode branch paths according to their size and push them into the associated buffers in IBA.

Note: This is essentially the [strip-packing problem](https://en.wikipedia.org/wiki/Strip_packing_problem), which is known
to be strongly-NP hard. and there exists no polynomial time approximation algorithm with a ratio smaller than $\frac{3}{2}$
unless P = NP. However, the best approximation ratio achieved so far (by a polynomial time algorithm by Harren et al.) is
(\frac{5}{3}+\varepsilon), imposing an open question whether there is an algorithm with approximation ratio $\frac{3}{2}$.

## References

[Iteration Interleaving-Based SIMD Lane Partitioning](https://dl.acm.org/doi/pdf/10.1145/2847253)