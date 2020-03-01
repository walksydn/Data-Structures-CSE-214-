public class Process {

    private int size, progress;
    private String ID;

    /**
     * Stores the size of the process and begins the progress at zero
     * @param size the size of the process (amount of cycles to completion)
     */
    public Process(String Id, int size) {
        ID = Id;
        this.size = size;
        progress = 0;
    }

    /**
     * Gets the process ID for the user
     * @return the process ID
     */
    public String getProcessID(){
        return ID;
    }

    /**
     * Does work on the process through incrementing the progress by one
     */
    public void doWork() {
        progress++;
    }

    /**
     * Checks whether or not the process is completed
     * @return true if the process is finished
     */
    public boolean isFinished(){
        return progress == size;
    }
}