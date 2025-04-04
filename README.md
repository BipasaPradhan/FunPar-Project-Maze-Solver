# Parallel Maze Solver

## Overview
The goal of this project is to develop a simple backend application that allows users to submit mazes and have them solved efficiently using parallelized BFS & A* algorithms. Users can input mazes directly into the console, and the application will return the solved paths. The project uses Scala's parallel data structures to solve mazes in parallel while comparing execution times between sequential and parallel versions.

## Objectives
- Read mazes from console input and translate them properly
- Display the correct steps and shortest path
- Utilize parallelism to improve program efficiency

## Set-Up
1. User inputs:
   - Number of rows and columns
   - Maze layout using `1`s (walkable path) and `0`s (walls)
2. The maze is converted to an adjacency list
3. Movement options: 4 directions (up, down, left, right)

## Running the Program
1. Execute the Main file
2. Input your maze row by row
3. Select algorithm:
   - Sequential BFS
   - Sequential A*
   - Parallel BFS
4. Output includes:
   - Solution steps
   - Visual path representation
   - Node-by-node path

## Results

### 1. Execution Time
Median execution times across 10 trials (all-open maze):

![Execution Time Table](https://github.com/user-attachments/assets/e31e2714-a06d-4990-bdef-c9181cf19265)

### 2. Solution Steps
All algorithms produced identical optimal paths:

![Steps Table](https://github.com/user-attachments/assets/2a79cd6c-09a5-4de0-9861-90555f1be394)

### 3. Performance Comparison
![Performance Plot](https://github.com/user-attachments/assets/c5a0440c-1af4-4cb8-8518-07ed9787b1b6)

## Conclusion
- Successfully implemented sequential BFS and A*
- Parallel BFS works but has performance overhead
- Parallel A* was abandoned due to incorrect results
- Key findings:
  - Parallel processing showed no speed advantage in current implementation
  - All algorithms find optimal paths despite time differences
  - Best performance: Sequential A* for small mazes, Sequential BFS for large ones

## Future Improvements
- Optimize parallel node exploration
- Implement correct parallel A* version
- Add maze weighting support



     





