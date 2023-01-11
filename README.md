# OOP Course - Assignment

A project for an OOP course taken through a B.Sc in Computer Science.

The project covered the subjects of threads and threadpool. A thread is an execution run independent of the main code execution, and also can be used for async tasks. A threadpool is an array of threads used for executing tasks, it exist to lower the resources needed for creating and closing threads by reusing them.<br>
The project was split into two parts, the first we used threads and threadpool for reading lines from text files. the second part was about creating our own classes that wrap around Thread and Threadpool to support tasks with priority. 
The Tests have been written using JUnit 5. 


## First Part
The first part was a mission to create several text files, and read from them in 3 different methods: linearly, using threads, and using threadpool. then we compared the running time of all the reading options.<br> 
our functions that we wrote:
* **createTextFiles**(int n, int seed, int bound) -> String[]: <br>
a function to create n text files, in which each text file we write a random number of lines, generated from the seed received. we return a list of names of the files created.
* **getNumOfLines**(String[] fileNames) -> int: <br>
a function to read the lines from all the files and returns the sum of all the lines.
* **getNumOfLinesThreads(String[] fileNames)** -> int: <br>
a function to read the lines from all the files using threads and returns the sum of all the lines.
* **getNumOfLinesThreadPool(String[] fileNames)** -> int: <br>
a function to read the lines from all the files using a threadpool and returns the sum of all the lines.


### NumReaderThread class
a class extending the Thread class, used for calculating the number of lines in a text file.

### NumReaderCallable class
a class implementing the class Callable, used as a thread for the function  getNumOfLinesThreadPool, to calculate the number of lines in a text file.
### INSERT UML DIAGRAM

## Second Part
The second part was a mission to create a task that can be executed async, because in java we can only set priority to a certain thread and not a certain task. we used that task in a class that extended the threadpool class to support the task that we created.<br>

### Task class
a class extending the FutureTask class, and implementing Callable & Comparable interface. represents a task with a set priority. we used the factory design pattern - hiding the constructor and providing a method that creates and returns an object.

### TaskType enum
An enum that defined by 3 options for priority, 3 is Other, 2 is IO, and 1 is Computational. used for priority setting for tasks.
### CustomExecutor class
a class extending the class ThreadPoolExecutor, used as a custom thread pool for our tasks with a priority.

## FutureTaskCall class
a class extending the class FutureTask, used because our CustomExecutor uses by default a RunnableFuture in our blocking queue. this class helps us adapt our Callable tasks with priority so we can get that priority back after being passed to the pool.
### INSERT UML DIAGRAM

## Setup
**prerequisites:**
1. JDK version 19 and onwards
2. An IDE of choice which is capable of handling java projects with Maven

**dependencies:**
1. log4j-core
2. junit-jupiter-engine
3. jol-core
4. junit-jupiter-api

**Setup and run**
1. ```git clone https://github.com/zachibs/OOP-Matala2.git ```
2. Build the project
3. Run Tests.java / Use the classes in your code
## Folder Structure

The workspace contains two folders:

- `src`: the folder to maintain source code
    - `main`: holds all interfaces and implementations
    - `test`: holds the JvmUtilities.java file and the Tests.java test file.
- `target`: the folder to hold the compiled output files
