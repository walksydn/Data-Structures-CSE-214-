import java.util.NoSuchElementException;

public class CpuCore {

    private int coreID;

    private Process currentProcess;
    private String processID;
    private boolean maxStepsHit;
    private int finishedTime, workDone, maxSteps;

    /**
     * Creates the CpuCore object, storing the entered ID number and cutoff for maximum number of steps
     *  working on one process in a row
     * Initializes all necessary variables for the rest of the class
     * @param ID ID number of the core
     * @param cutoff maximum number of steps the core can take on one process in a row
     */
    public CpuCore(int ID, int cutoff) {
        coreID = ID;
        currentProcess = null;
        processID = "";
        finishedTime = workDone = 0;
        maxSteps = cutoff;
        maxStepsHit = false;
    }

    /**
     * Takes a step within the core, either retrieving a process from the queue if it does not have one to work on,
     *  working on the process it has, or returning its current process to queue if it has worked on it for the
     *  maximum amount of steps in a row
     */
    public void step() {
        //get a new process if the core does not have one
        if(currentProcess == null) {
            workDone = 0;
            maxStepsHit = false;
            claimProcess();
        //if the core is not finished and has not reached its maximum steps, it does work on the current process
        } else if(!isFinished() && !maxStepsHit){
            doWork();
        //if the core is not finished, but the maximum step count has been met it returns the current
        // process to the queue
        } else if (!isFinished() && maxStepsHit){
            returnToQueue();
            maxStepsHit = false;
        }
    }

    /**
     * Checks if the core has completed its task
     * @return true if the current process is complete or there is currently no process assigned to the core
     */
    public boolean isFinished(){
        if(currentProcess != null){
            return currentProcess.isFinished();
        }
        return true;
    }

    /**
     * Gets a process from the processQueue held by the CpuSimulator and resets all necessary variables
     * Catches the exception thrown if there is no process to get from the processQueue
     * Used when the core does not have a process to work on
     */
    private void claimProcess() {
        Process temp = CpuSimulator.getNextProcess();
        if(temp != null){
            currentProcess = temp;
            processID = currentProcess.getProcessID();
            finishedTime = workDone = 0;
            maxStepsHit = false;
        }
    }

    /**
     * Returns the current process back to the process queue in CpuSimulator
     * Used when the maximum step count in a row has been met for a process
     */
    private void returnToQueue(){
        CpuSimulator.returnToQueue(currentProcess);
        currentProcess = null;
    }

    /**
     * Does work on the current process through increasing workDone, adding a productive step to CpuSimulator
     * Checks if the core has hit the maximum step count in a row on the current process, or if the process
     *  is complete
     * If the process is complete, prints the coreID, processId, finishedTime and sends the finished time
     *  to the CpuSimulator so it can calculate the averageTurnaroundTime for the run
     * Used when the core has a process to work on and has not hit its maximum steps
     */
    private void doWork() {
        //do work on the process and add a productive step to CpuSimulator
        currentProcess.doWork();
        workDone++;
        CpuSimulator.addProductiveStep();
        //check if the core has hit the cutoff for steps in a row on one process
        if(workDone == maxSteps){
            maxStepsHit = true;
        } else {
            maxStepsHit = false;
        }
        //checks if the process is finished and sends/prints necessary information if it is
        if(isFinished()){
            workDone = 0;
            finishedTime = CpuSimulator.getOverallTime()+1;
            CpuSimulator.addProcessTime(finishedTime);
            System.out.println(coreID + ", " + processID + ", " + finishedTime);
            currentProcess = null;
        }
    }
}