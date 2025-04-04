#**Parallel Maze Solver**
Overview: 
The goal of this project is to develop a simple backend application that allows users to submit mazes and have them solved efficiently using parallelized BFS & A* algorithm. Users can input mazes directly into the console, and the application will return the solved paths. The project will use Scala’s parallel data structures like threads to solve mazes in parallel and simultaneously. Then, the execution time of the sequential & parallel versions will be compared. 

##**Objectives:**
Read the mazes from the console & translate them properly.
Display the correct steps it takes & the shortest path.
Utilize parallelism to speed up the lookup time and efficiency of the program.
Set-Up:
The user inputs the numbers of rows & columns their maze will be. The maze inputs are a bunch of 1s (walkable path) or 0s (walls) that the users input row by row. The inputs then get turned into an adjacency list with the Node as the key and a list of its neighbors as the value. For this implementation, there are 4 possible directions to walk: up, down, left and right. 

##**Running the Program:**
Once the user clicks run on the Main file, the user can input the maze in and select whichever algorithm(s) they want to run, the output will be the steps taken, the path in maze representation, and the path in nodes. 

##**Results:**
  **1. Execution Time**
      I took the median execution time for each implementation after 10 trials. Each maze size was tested on a maze filled with 1’s (all open) for fairness. It turns out that the parallel version of  the algorithm runs the slowest out of all implementations.
     <img width="470" alt="image" src="https://github.com/user-attachments/assets/e31e2714-a06d-4990-bdef-c9181cf19265" />
     
  **2. Steps**
     Though the execution times differ, all algorithms lead to the correct results and the same amount of steps
     <img width="470" alt="image" src="https://github.com/user-attachments/assets/2a79cd6c-09a5-4de0-9861-90555f1be394" />

  **3. Plot**
    <img width="455" alt="image" src="https://github.com/user-attachments/assets/c5a0440c-1af4-4cb8-8518-07ed9787b1b6" />
    
##**Conclusion:**
I was able to implement the sequential versions of the two algorithms, however I struggled with the parallel implementation. For A*, the parallel version did not return the correct answer, so I decided to scrap it. For the parallel version of BFS, it isn’t fully parallel as the neighbors are still explored sequentially, however, the nodes are processed in batches using threads. There is a lot of overhead, which causes it to run slow. Also, for the A*, it runs fast for smaller mazes and becomes slower for bigger ones and it uses a priority queue and the maze is unweighted. All in all, I wasn’t able to get the parallel version to be optimized, but was still able to return the correct paths. 



     





