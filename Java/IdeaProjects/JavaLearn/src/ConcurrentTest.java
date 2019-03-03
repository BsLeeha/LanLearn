
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/*
 * Data structure for a web crawler. Keeps track of the visited sites and sites to be crawled.
 */
class WebCrawl {
    private List<String> crawledSites = new ArrayList<String>();
    private List<String> linkedSites = new ArrayList<String>();

    /*
     * Add the site if it has not been crawled.
     */
    public void add(String site) {
        if (!crawledSites.contains(site)) {
            synchronized (this) {
                crawledSites.add(site);
            }
        }
        // else do nothing
    }

    /*
     * Get next site to crawl. Nothing then return null.
     */
    public String next() {
        if (linkedSites.isEmpty()) return null;
        synchronized (this) {                              // use this object as a key to lock and synchronize the code block
            // check again if size has changed
            if (linkedSites.isEmpty()) return null;
            String site = linkedSites.get(0);
            crawledSites.add(site);
            linkedSites.remove(0);
            return site;
        }
    }
}

/*
 * ---------------- Multithreading Frameworks ---------------------------
 */

/*
 * Thread(worker) + Runnable(task)
 */
class Framework1 {
    public static void run() {
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            final int tmp = i;
            // access outer field: OuterClass.this.filed
            // access outer local variable: declare variable final
            Thread worker = new Thread(() -> {
                List<Integer> range = IntStream.rangeClosed(1, 10 + tmp).boxed().collect(Collectors.toList());
                long sum = range.stream()
                        .mapToInt(Integer::intValue)
                        .sum();
                System.out.println(Thread.currentThread().getName() + ": " + sum);
            });
//            worker.setName(String.valueOf(i));
            worker.start();
            threads.add(worker);
        }

        // count the threads that are still running
        long running = 0;

        do {
            running = 0;
            for (Thread thread : threads)
                if (thread.isAlive())
                    running++;
            System.out.println("There're " + running + " threads running now!");
        } while (running > 0);
    }
}

/*
 * Executor + Runnable(task) + Thread pool(workers), avoid being swamped with millions of clients
 */
class Framework2 {
    private static final int NTHREADs = 10;     // why int?

    public static void run() {
        // create a fixed thread pool managed and allocated by an executor
        ExecutorService executor = Executors.newFixedThreadPool(NTHREADs);
        for (int i = 0; i < 10; i++) {
            // create a job
            final int tmp = i;
            Runnable job = new Runnable() {
                @Override
                public void run() {
                    List<Integer> range = IntStream.rangeClosed(1, 10 + tmp).boxed().collect(Collectors.toList());
                    long sum = range.stream()
                            .mapToInt(Integer::intValue)
                            .sum();
                    System.out.println(sum);
                }
            };
            // allocate the job to the thread
            executor.execute(job);
        }

        // decline any job
        executor.shutdown();

        // block and wait until all jobs have finished
        try {
            executor.awaitTermination(10, TimeUnit.SECONDS);        // long time is ok
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Job finished!");
    }
}

/*
 * Executor + Callable(task) + Future(store return value) + Thread pool(workers): allow threads to return value to the caller
 */
class Framework3 {

    private static final int NTHREADs = 10;

    public static void run() {
        // create a fixed thread pool managed and allocated by an executor
        ExecutorService executor = Executors.newFixedThreadPool(NTHREADs);
        // use Future to store the return value of each
        List<Future<Long>> futures = new ArrayList<>();

        for (int i = 0; i < 10; i++) {

            final int tmp = i;

            var task = new Callable<Long>() {
                @Override
                public Long call() throws Exception {
                    List<Long> range = LongStream.range(1, 10 + tmp).boxed().collect(Collectors.toList());
                    return range.stream()
                            .mapToLong(Long::longValue)
                            .sum();
                }
            };

            Future<Long> future = executor.submit(task);
            futures.add(future);
        }

        // print the result
        futures.forEach(x -> {
            try {
                System.out.println(x.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });

        // decline any job
        executor.shutdown();

        // block and wait all jobs to be finished
        try {
            executor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Job finished!");
    }
}

public class ConcurrentTest {
    public static void main(String[] args) {
        Framework1.run();
    }
}
