/*
 * ============================================================================
 * DISCRETE-EVENT SIMULATION & STOCHASTIC MODELING COMPENDIUM
 * ============================================================================
 * Author      : Muhammed Emin Korkunç (Student ID: 2021221054)
 * Department  : Computer Engineering
 * Institution : Fatih Sultan Mehmet Vakif University
 * GitHub      : https://github.com/muhammedkorkunc
 * LinkedIn    : https://www.linkedin.com/in/muhammed-emin-korkun%C3%A7-100ba2215
 * Email       : muhammedemin.korkunc@gmail.com
 * License     : Proprietary - All Rights Reserved (c) 2026
 * ============================================================================
 * Unauthorized copying, distribution, or commercial/academic exploitation 
 * of this source code, algorithms, or models is strictly prohibited.
 * ============================================================================
 */


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package discretesimulation2021221054;

/**
 *
 * @author muhammedeminkorkunc
 */
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

class Event implements Comparable<Event> {
    enum Type { ARRIVAL, INSPECTION }
    double time;
    Type type;

    Event(double time, Type type) {
        this.time = time;
        this.type = type;
    }

    @Override
    public int compareTo(Event other) {
        return Double.compare(this.time, other.time);
    }
}

public class AirportSecuritySimulationGPSS {
    static final double ARRIVAL_MEAN = 5.0;
    static final double ARRIVAL_VARIANCE = 2.0;
    static final double INSPECTION_MEAN = 10.0;
    static final int DENIED_ENTRY_TARGET = 100;
    static final double FAILURE_RATE = 0.05;

    PriorityQueue<Event> fel = new PriorityQueue<>();
    Random random = new Random();
    int totalDenied = 0;
    int totalProcessed = 0;
    double currentTime = 0.0;
    double maxQueueLength = 0;
    double cumulativeQueueLength = 0;
    int queueLengthChanges = 0;
    int currentQueueLength = 0;

    public void startSimulation() {
        scheduleArrival();

        while(totalDenied < DENIED_ENTRY_TARGET) {
            Event event = fel.poll();
            currentTime = event.time;

            switch (event.type) {
                case ARRIVAL:
                    currentQueueLength++;
                    scheduleArrival();
                    scheduleInspection();
                    break;
                case INSPECTION:
                    if (currentQueueLength > 0) {
                        currentQueueLength--;
                        if (random.nextDouble() < FAILURE_RATE) {
                            totalDenied++;
                        } else {
                            totalProcessed++;
                        }
                        updateQueueStatistics();
                    }
                    break;
            }
        }
        generateReport();
    }

    private void scheduleArrival() {
        double nextArrivalTime = currentTime + ARRIVAL_MEAN + (random.nextDouble() * 2 - 1) * ARRIVAL_VARIANCE;
        fel.add(new Event(nextArrivalTime, Event.Type.ARRIVAL));
    }

    private void scheduleInspection() {
        double inspectionTime = currentTime + -Math.log(1 - random.nextDouble()) * INSPECTION_MEAN;
        fel.add(new Event(inspectionTime, Event.Type.INSPECTION));
    }

    private void updateQueueStatistics() {
        maxQueueLength = Math.max(maxQueueLength, currentQueueLength);
        cumulativeQueueLength += currentQueueLength;
        queueLengthChanges++;
    }

    private void generateReport() {
        double averageQueueLength = (queueLengthChanges == 0) ? 0 : cumulativeQueueLength / queueLengthChanges;
        System.out.println("\nSimulation finished.");
        System.out.println("Total simulation time: " + currentTime);
        System.out.println("Total passengers processed: " + totalProcessed);
        System.out.println("Total passengers denied entry: " + totalDenied);
        System.out.println("Average length of queue: " + averageQueueLength);
        System.out.println("Maximum length of queue: " + maxQueueLength);
    }

    public static void main(String[] args) {
        new AirportSecuritySimulationGPSS().startSimulation();
    }
}

/*
 * ============================================================================
 * DISCRETE-EVENT SIMULATION & STOCHASTIC MODELING COMPENDIUM
 * ============================================================================
 * Author      : Muhammed Emin Korkunç (Student ID: 2021221054)
 * Department  : Computer Engineering
 * Institution : Fatih Sultan Mehmet Vakif University
 * GitHub      : https://github.com/muhammedkorkunc
 * LinkedIn    : https://www.linkedin.com/in/muhammed-emin-korkun%C3%A7-100ba2215
 * Email       : muhammedemin.korkunc@gmail.com
 * License     : Proprietary - All Rights Reserved (c) 2026
 * ============================================================================
 * Unauthorized copying, distribution, or commercial/academic exploitation 
 * of this source code, algorithms, or models is strictly prohibited.
 * ============================================================================
 */