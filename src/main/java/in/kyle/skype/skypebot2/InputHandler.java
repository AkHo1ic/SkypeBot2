package in.kyle.skype.skypebot2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/**
 * Created by Kyle on 11/28/2015.
 */
public class InputHandler {
    
    private List<Consumer<String>> consumers;
    
    private AtomicBoolean running;
    
    public InputHandler() {
        consumers = new ArrayList<>();
        running = new AtomicBoolean(true);
        InputHandlerThread inputHandlerThread = new InputHandlerThread(running, consumers);
        new Thread(inputHandlerThread).start();
    }
    
    public void addHook(Consumer<String> stringConsumer) {
        consumers.add(stringConsumer);
    }
    
    public void removeHook(Consumer<String> stringConsumer) {
        consumers.remove(stringConsumer);
    }
    
    public void shutdown() {
        running.set(false);
    }
    
    private class InputHandlerThread implements Runnable {
        
        private final AtomicBoolean running;
        private final List<Consumer<String>> consumers;
        
        public InputHandlerThread(AtomicBoolean running, List<Consumer<String>> consumers) {
            this.running = running;
            this.consumers = consumers;
        }
        
        public void run() {
            Scanner scanner = new Scanner(System.in);
            while (running.get() && scanner.hasNextLine()) {
                String line = scanner.nextLine();
                synchronized (consumers) {
                    consumers.forEach(c -> c.accept(line));
                }
            }
        }
    }
}
