# Design

1. Declare resource constraints (executors, containers, etc)
2. Group nodes into basic blocks, essentially several nodes that are all required to complete for another basic block to begin
3. Create interference graph for each basic block
4. Mark node interferences based on dependencies
5. Colour graph based on resources
6. Execute graph with resources
