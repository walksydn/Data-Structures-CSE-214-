import java.util.NoSuchElementException;
import java.util.Scanner;

public class CpuSimulator {
    private CpuCore[] cores;
    private static Queue<Process> processQueue;
    private int maxSteps, numProcesses, numCores;
    private static int overallTime, sumProcessTime, productiveSteps;

    /**
     * Constructor for the CpuSimulator class
     * Initializes cores[] and assigns coreID's
     * Sets all initial values
     * @param numCores the number of cores in the CPU
     * @param cutoff the number of cycles a core can work on one process
     *               for in a row
     * @param pq the queue of processes to be completed
     */
    public CpuSimulator(int numCores, int cutoff, Queue<Process> pq) {
        cores = new CpuCore[numCores];
        processQueue = pq;
        numProcesses = processQueue.length();
        this.numCores = numCores;
        maxSteps = cutoff;
        productiveSteps = overallTime = 0;

        //initialize and assign core IDs to each core in cores[]
        for(int i = 0; i < numCores; i++){
            cores[i] = new CpuCore(i, cutoff);
        }
    }

    /**
     * Runs the CPU simulator, stepping through each core once each loop and
     *  counting the number of times the loop runs
     * Checks if the cores are finished or not as they are stepped through
     * The loop runs whenever there are processes in the queue or the cores
     *  are currently processing
     */
    public void run() {
        boolean coresFinished = false;
        while(!processQueue.isEmpty() || !coresFinished){
            coresFinished = true;
            for(int i = 0; i < cores.length; i++){
                cores[i].step();
                if(!cores[i].isFinished()){
                    coresFinished = false;
                }
            }
            overallTime++;
        }
    }

    /**
     * Adds the amount of time it took for a process to be completed
     * @param time the processing time taken for the process completed
     */
    public static void addProcessTime(int time){
        sumProcessTime += time;
    }

    /**
     * Calculates the average time for a process to be completed in the
     *  previous simulation ran (the sum of the times taken to finish each process
     *  and the total number of processes finished)
     * @return the average turn around time
     */
    public int avgTurnaroundTime(){
        return sumProcessTime/numProcesses;
    }

    /**
     * Increments the productive step counter
     * To be used by a core when they do work
     */
    public static void addProductiveStep(){
        productiveSteps++;
    }

    /**
     * Calculates the utilization of the CPU (the number of productive steps divided by the CPU time)
     * The CPU Time is the total amount of steps taken on all cores (numCores*overallTime)
     * @return the proportion of steps the CPU took that were productive
     */
    public double getUtilization(){
        int cpuTime = numCores*overallTime;
        return (double)productiveSteps/cpuTime;
    } //TODO should this be rounded?

    /**
     * Getter method for the total number of cycles that have been completed by the CPU
     * @return the number of cycles of the CPU (number of times the while loop has run)
     */
    public static int getOverallTime(){
        return overallTime;
    }

    /**
     * Accessor method for the next process stored in the processesQueue
     * Used by CpuCore.java to collect the next process to be worked on
     * @return the next process in the queue
     */
    public static Process getNextProcess() {
        if(processQueue.length() > 0){
            return processQueue.dequeue();
        } else {
            return null;
        }
    }

    /**
     * Used by CpuCore.java to return processes to the queue after they have been worked on for the maximum
     *  number of steps
     * @param p the process being returned to the queue
     */
    public static void returnToQueue(Process p){
        processQueue.enqueue(p);
    }

    /**
     * Takes in inputs and creates a queue of the inputted processes to feed into
     *  an iteration of CpuSimulator it creates
     * Runs the created CpuSimulator
     * Prints the average turnaround time and utilization for the last run of the cpu
     */
    public static void main(String[] args) {
        //create Scanner and collect the number of processes, number of cores, and maximum step limit per process
        // in a row
        Scanner input = new Scanner(System.in);
        int numProcesses = input.nextInt();
        int numCores = input.nextInt();
        int cutoff = input.nextInt();

        input.skip("\n");
        //creates a queue to store the entered processes in
        Queue<Process> processes = new Queue<>();
        for(int i = 0; i < numProcesses; i++){
            //collects the String entered by the user
            String process = input.nextLine();
            //parses the entered process information into the ID and the size
            String[] processParameters = process.split(",");
            String processID = processParameters[0];
            int processSize = Integer.parseInt(processParameters[1]);
            //creates a process using the entered information and adds it to the queue
            Process temp = new Process(processID, processSize);
            processes.enqueue(temp);
        }

        //creates a CpuSimulator with the entered information and runs it, printing average
        // turnaround time and utilization data for the cycle run
        CpuSimulator cpu = new CpuSimulator(numCores, cutoff, processes);
        cpu.run();
        System.out.println("Turnaround: " + cpu.avgTurnaroundTime());
        System.out.println("Utilization: " + cpu.getUtilization());
    }

}
