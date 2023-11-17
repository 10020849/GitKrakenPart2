import java.util.*;
public class CallableThreadCount implements Callable<Long> 
{
    private final int threads = 1000000;
    private int threadNum;

    public CallableThreadCount() 
    {
        System.out.println("In thread");
        threadNum = 0;
    }

    public Long call() 
    {
        for (int i = 0; i < threads; i++) 
        {
            threadNum++;
        }
        return (long) threadNum;
    }

    public static void main(String[] args) 
    {
        // Number of threads
        int numThreads = 100;

        // Create a list to hold Callable tasks
        List<Callable<Long>> tasks = new ArrayList<>();

        for (int i = 0; i < numThreads; i++) 
        {
            tasks.add(new CallableThreadCount());
        }

        // Create an ExecutorService with a fixed thread pool
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        // Record the start time
        Instant startTime = Instant.now();
        int epoch1 = startTime.getNano();

        try 
        {
            // Submit all tasks and store the futures in a list
            List<Future<Long>> futures = executorService.invokeAll(tasks);

            // Process the results
            long totalSum = 0;
            for (Future<Long> future : futures) 
            {
                // Retrieve the result from each task and sum them up
                totalSum += future.get();
            }

            // Print the total sum
            System.out.println("Total Sum: " + totalSum);
        } 
        catch (InterruptedException | ExecutionException e) 
        {
            e.printStackTrace();
        } 
        finally 
        {
            // Shut down the executor service
            executorService.shutdown();
        }

        // Record the end time
        Instant endTime = Instant.now();
        int epoch2 = endTime.getNano();

        // Time between start to end
        double timeInSec = (double) (epoch2 - epoch1)/1000000000;
        System.out.println("Time: " + timeInSec + " seconds");
    }
}
