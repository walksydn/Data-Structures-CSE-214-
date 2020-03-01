import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.time.MovingAverage;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.ui.ApplicationFrame;
import javax.swing.*;
import java.awt.*;

class SortingComparison extends ApplicationFrame {
    // use this for counting comparisons done by a sort
    // a global variable is not ideal, but we can use this for every sort without
    // worrying about return types for now
    // remember to reset it to 0 before sorting
    private static int comparisons;

    /**
     * Creates randomized arrays to test sorting functions
     * Saves number of comparisons to series (one per sorting algorithm)
     * Graphs the series to allow for comparisons of the runtimes
     *  of every sorting algorithm on two graphs, one for
     *  quadratic runtime algorithms and another for other runtimes
     * @param title
     */
    public SortingComparison(final String title) {

        super(title);

        // Collections that hold the series we are about to create
        final XYSeriesCollection divAndConqData = new XYSeriesCollection();
        final XYSeriesCollection quadraticData = new XYSeriesCollection();

        int maxSize = 5000;
        int dataRange = 1000;

        final XYSeries series1 = new XYSeries("quick sort k=0");
        // quick sort with no insertion sort
        // loop from 0 to maxSize
        // create an array of each size and fill it with random elements using
        // (int) (Math.random() * dataRange)
        // sort using the method for this series
        // add data point to series like series1.add(size, comparisons);
        // ...
        // ...
        // ...
        // after adding all the data points, (there should be maxSize of them) add the
        // series to the collection for this graph

        for (int i = 0; i < maxSize; i++) {
            int[] arr = new int[i];
            for(int j = 0; j < arr.length; j++) {
                arr[j] = (int) (Math.random()*dataRange);
            }
            comparisons = 0;
            quickSort(arr, 0, arr.length-1, 0);
            series1.add(i, comparisons);
        }
        divAndConqData.addSeries(series1);

        final XYSeries series2 = new XYSeries("quick sort k=10");
        // repeat above for quick sort with insertion sort for 10 or less elements
        for (int i = 0; i < maxSize; i++) {
            int[] arr = new int[i];
            for(int j = 0; j < arr.length; j++) {
                arr[j] = (int) (Math.random()*dataRange);
            }
            comparisons = 0;
            quickSort(arr, 0, arr.length-1, 10);
            series2.add(i, comparisons);
        }
        divAndConqData.addSeries(series2);

        final XYSeries series3 = new XYSeries("quick sort k=20");
        // repeat above...
        for (int i = 0; i < maxSize; i++) {
            int[] arr = new int[i];
            for(int j = 0; j < arr.length; j++) {
                arr[j] = (int) (Math.random()*dataRange);
            }
            comparisons = 0;
            quickSort(arr, 0, arr.length-1, 20);
            series3.add(i, comparisons);
        }
        divAndConqData.addSeries(series3);

        final XYSeries series4 = new XYSeries("quick sort k=50");
        // repeat above...
        for (int i = 0; i < maxSize; i++) {
            int[] arr = new int[i];
            for(int j = 0; j < arr.length; j++) {
                arr[j] = (int) (Math.random()*dataRange);
            }
            comparisons = 0;
            quickSort(arr, 0, arr.length-1, 50);
            series4.add(i, comparisons);
        }
        divAndConqData.addSeries(series4);

        final XYSeries series5 = new XYSeries("merge sort");
        // reminder: merge sort with out-of-place merge so that we get O(n log n)
        // runtime
        // repeat above
        for (int i = 0; i < maxSize; i++) {
            int[] arr = new int[i];
            for(int j = 0; j < arr.length; j++) {
                arr[j] = (int) (Math.random()*dataRange);
            }
            comparisons = 0;
            mergeSort(arr, 0, arr.length - 1);
            series5.add(i, comparisons);
        }
        divAndConqData.addSeries(series5);

        // max size for quadratic sorts. we needs these to be smaller so that it doesn't
        // take too long
        maxSize = 500;
        final XYSeries series6 = new XYSeries("insertion sort");
        // repeat above...

        for (int i = 0; i < maxSize; i++) {
            int[] arr = new int[i];
            for(int j = 0; j < arr.length; j++) {
                arr[j] = (int) (Math.random()*dataRange);
            }
            comparisons = 0;
            insertionSort(arr, 0, arr.length-1);
            series6.add(i, comparisons);
        }
        // adding to a different graph for quadtratic sort, but the process is the same
        quadraticData.addSeries(series6);

        final XYSeries series7 = new XYSeries("selection sort");
        // repeat above...
        for (int i = 0; i < maxSize; i++) {
            int[] arr = new int[i];
            for(int j = 0; j < arr.length; j++) {
                arr[j] = (int) (Math.random()*dataRange);
            }
            comparisons = 0;
            selectionSort(arr);
            series7.add(i, comparisons);
        }
        quadraticData.addSeries(series7);

        final XYSeries series8 = new XYSeries("bubble sort");
        // repeat above...
        for (int i = 0; i < maxSize; i++) {
            int[] arr = new int[i];
            for(int j = 0; j < arr.length; j++) {
                arr[j] = (int) (Math.random()*dataRange);
            }
            comparisons = 0;
            bubbleSort(arr);
            series8.add(i, comparisons);
        }
        quadraticData.addSeries(series8);

        // YOU DON'T REALLY NEED TO TOUCH THIS SECTION, BUT FEEL FREE TO LOOK THROUGH IT
        // =============================================================================================================
        // takes the data sets and turns them into a moving average so things are easier
        // to see
        XYDataset ma1 = MovingAverage.createMovingAverage(divAndConqData, "", 50, 0);
        XYDataset ma2 = MovingAverage.createMovingAverage(quadraticData, "", 50, 0);
        // takes the moving averages and plots them as line charts
        // if you want to see what it looks like without the moving average you can put
        // data1 and data2 directly in here instead of ma1 and ma2
        final JFreeChart chart1 = ChartFactory.createXYLineChart("Divide and conquer sorts", "Input size n",
                "Num comparisons", ma1, PlotOrientation.VERTICAL, true, true, false);
        final JFreeChart chart2 = ChartFactory.createXYLineChart("Quadratic sorts", "Input size n", "Num comparisons",
                ma2, PlotOrientation.VERTICAL, true, true, false);
        // panel/window setup
        // don't worry about this unless you want to mess with dimensions
        final ChartPanel chartPanel1 = new ChartPanel(chart1);
        final ChartPanel chartPanel2 = new ChartPanel(chart2);
        chartPanel1.setPreferredSize(new java.awt.Dimension(400, 270));
        chartPanel2.setPreferredSize(new java.awt.Dimension(400, 270));
        JPanel j = new JPanel();
        j.setLayout(new GridLayout());
        j.add(chartPanel1);
        j.add(chartPanel2);
        setContentPane(j);
        // =============================================================================================================
    }

    /**
     * Uses a selection sort algorithm to sort the entered int array
     *  Find the minimum value and places it at the end of the sorted portion
     *  (which is located in the front)
     *  Repeat until the list is sorted
     * @param data int[] to be sorted
     */
    public static void selectionSort(int[] data) {
        int n = data.length;
        int i, j;
        for (i=0; i<=n-2; i++)
            for (j=i+1; j<=n-1; j++){
                comparisons++;
                if (data[j] < data[i]) {
                    swap(data, i, j);
                }
            }
    }

    /**
     * Sorts the given portion (first-last) of the int array using insertion sort
     *  Iterates through the array from first to last, sorting
     *  each value into its proper location within the sorted section
     *  (which is located at the front of the section of the array
     *  being sorted)
     * @param data the int[] to be sorted
     * @param first the start of the section to be sorted
     * @param last the end of the section to be sorted
     */
    public static void insertionSort(int[] data, int first, int last) {
        int i, j, item, n = last-first+1;
        int start = first+1;
        if (first > 0){
            start = first+1;
        }
        for (i = start; i <= last; i++) {
            item = data[i]; j = i;
            comparisons++;
            while (j > first && data[j-1] > item) {
                data[j] = data[j-1];
                comparisons++;
                j--;
            }
            data[j] = item;
        }
    }

    /**
     * Sorts the entered int array using bubble sort
     *  Compare the first and second, then second and third, third and fourth, etc.
     *  elements, swapping those that need to be swapped
     *  Repeat until the list is sorted
     * @param data the int[] to be sorted
     */
    public static void bubbleSort (int[] data){
        int i, j, n = data.length;
        for (i=0; i <= n-2; i++)
            for (j = n-1; j > i; j--) {
                comparisons++;
                if (data[j] < data[j-1])
                    swap(data, j, j-1);
            }
    }

    /**
     * Sorts the entered int array from first to last index using
     * a merge sort algorithm
     *  Recursively splits the array into halves until there are
     *  many arrays of size 0-1
     *  Merges the arrays (putting whichever first array value is smaller
     *  into the array being sorted)
     * @param data the int[] to be sorted
     * @param first first index to be sorted
     * @param last last index to be sorted
     */
    public static void mergeSort(int[] data, int first, int last) {
        if (last-first > 0) {
            int mid = first + (last - first) / 2;
            mergeSort(data, first, mid);
            mergeSort(data, mid + 1, last);
            merge(data, first, mid, last);
        }
    }

    /**
     * Helper function for mergeSort that actually merges the arrays
     * through placing whichever first value is smallest (between the two
     * arrays being merged) into the original array to be sorted
     * @param data the int[] to be sorted
     * @param first the first index to be sorted
     * @param mid the middle of the two indexes to be sorted
     * @param last the last index to be sorted
     */
    private static void merge(int[] data, int first, int mid, int last) {
        int i = 0, j = 0, n = first;
        // create the arrays to the left and right of the mid-point that will
        // be merged
        int[] left = new int[mid+1 - first];
        int[] right = new int[last - mid];
        for (; i < left.length; i++){
            left[i] = data[n];
            n++;
        }
        n = mid+1;
        for (; j < right.length; j++){
            right[j] = data[n];
            n++;
        }
        i = 0; j = 0; n = first; //reset values for merging
        while(i < left.length || j < right.length){ //while an array has a value
            if(j >= right.length){ //if only the left array has values
                data[n] = left[i]; n++; i++;
            } else if (i >= left.length){ //if only the right array has values
                data[n] = right[j]; n++; j++;
            } else { //if both arrays have values
                comparisons++;
                if(left[i] < right[j]){ //if the left array is less, put it into the main array
                    data[n] = left[i]; n++; i++;
                } else { //otherwise, put the right in the main array
                    data[n] = right[j]; n++; j++;
                }
            }
        }
    }

    /**
     * Sorts the part of an int array between first and last
     * using a quick sort algorithm until the array is the
     * size of k or lower, then it switches to insertion sort
     *  Chooses an index to pivot around
     *  Recursively chooses indexes to pivot around until the resulting
     *  arrays are small enough to be insertion sorted, or continues
     *  splitting until they are size 0-1 (if k = 0)
     *  All values less than the pivot are places on the right side, and all
     *  values greater than the pivot are placed on the left
     *
     * @param data the int[] to be sorted
     * @param first the first index to be sorted
     * @param last the last index to be sorted
     * @param k the value that determines what size of an array indicates
     *          a switch to insertion sort
     */
    public static void quickSort(int[] data, int first, int last, int k) {
        // if the user does not want to switch to insertion sort
        if(k == 0) {
            if (last-first > 0) {
                int pivotIndex = partition(data, first, last);
                quickSort(data, first, pivotIndex - 1, k); // check the left of the pivot
                quickSort(data, pivotIndex + 1, last, k); // check the right of the pivot
            }
        } else { // if the user does want to switch to insertion sort
            if(last-first <= k){ // switch to insertion sort when the size is <= k
                insertionSort(data, first, last);
            } else { // do a quick sort
                if (last-first > 0) {
                    int pivotIndex = partition(data, first, last);
                    quickSort(data, first, pivotIndex - 1, k); // check the left of the pivot
                    quickSort(data, pivotIndex + 1, last, k); // check the right of the pivot
                }
            }
        }
    }

    /**
     * Recursive helper method for quickSort that sets the pivots and swaps values
     * to ensure every value less than the pivot is on the left side,
     * and every value greater than the pivot is on the right side
     * @param data the int[] to be sorted
     * @param first the first index to be sorted
     * @param last the last index to be sorted
     * @return the location of the pivot
     */
    private static int partition(int[] data, int first, int last) {
        // pick pivot to be first element and leave it there for now
        int pivot = data[first];
        int left = first + 1;
        int right = last;
        while (left < right) {
            // search left to right until we find something greater than pivot
            comparisons++;
            while (data[left] < pivot && left < right) {
                left++;
                comparisons++;
            }
            // search right to left until we find something less or equal to pivot
            comparisons++;
            while (data[right] >= pivot && left < right) {
                right--;
                comparisons++;
            }
            // swap left and right
            if (left < right) {
                int temp = data[right];
                data[right] = data[left];
                data[left] = temp;
            }
        }
        // make sure the element to be swapped with pivot belongs to the left
        comparisons++;
        if (data[left] >= pivot) {
            left--;
        }
        // swap pivot to its location
        data[first] = data[left];
        data[left] = pivot;
        return left; //return the location of the pivot
    }

    /**
     * Used to swap two values in an int array
     * @param data the int[] where values will be swapped
     * @param i the index of one value to be swapped
     * @param j the index of the other value to be swapped
     */
    private static void swap(int[] data, int i, int j){
        int temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    public static void main(String[] args) {
        // EXAMPLE TESTS
//        int[] arr1 = {1, 2, 3, 4, 5, 6, 7};
//        quickSort(arr1, 0, arr1.length-1, 3);
//        for (int x: arr1)
//            System.out.println(x);
//        System.out.println("*********************");
//
//        int[] arr2 = {7, 6, 5, 4, 3, 2, 1};
//        quickSort(arr2, 0, arr2.length-1, 3);
//        for (int x: arr2)
//            System.out.println(x);
//        System.out.println("*********************");
//
//        int[] arr3 = {56, 3, 78, 26, 1276, 123, 45, 34};
//        quickSort(arr3, 0, arr3.length-1, 3);
//        for (int x: arr3)
//            System.out.println(x);
//        System.out.println("*********************");

        // call constructor for our ApplicationFrame which will set up all the test
        // sorts and plot the results
        final SortingComparison window = new SortingComparison("Sorting comparison");
        window.pack();
        window.setVisible(true);
    }
}