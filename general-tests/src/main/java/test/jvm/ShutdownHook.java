package test.jvm;

/**
 * Runtime.getRuntime().addShutdownHook() is a method in Java that allows you to add a shutdown hook to the Java Virtual Machine (JVM). A shutdown hook is a thread that runs when the JVM is shutting down, either because the program has finished executing or because an external event (such as the user pressing Ctrl-C) has terminated the program.
 * <p>
 * You can use the addShutdownHook() method to register a thread to be executed when the JVM is shutting down. This can be useful if you need to perform some cleanup or resource deallocation before your program exits. For example, you might use a shutdown hook to close database connections, release file handles, or write a log message indicating that the program is shutting down.
 * <p>
 * The addShutdownHook() method takes a single argument: a Thread object that represents the shutdown hook. The thread you provide should implement the Runnable interface and define its run() method to perform the cleanup or other tasks you want to execute on shutdown. Once you have created your shutdown hook thread, you can register it with the JVM by calling the addShutdownHook() method.
 *
 * @author dewu.de
 * @date 2023-05-05 10:20 上午
 */
public class ShutdownHook {

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("JVM Shutdown....");
        }));
        int sleepTimes = 0;
        while (sleepTimes < 15) {
            try {
                Thread.sleep(1000);
                sleepTimes++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("sleep times = " + sleepTimes);
    }
}
