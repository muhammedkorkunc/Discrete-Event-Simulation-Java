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

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.LinkedList;
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

public class AirportSecuritySimulation {
    static final double ARRIVAL_MEAN = 5.0;
    static final double ARRIVAL_VARIANCE = 2.0;
    static final double INSPECTION_MEAN = 10.0;
    static final int DENIED_ENTRY_TARGET = 100;
    static final double FAILURE_RATE = 0.05;

    PriorityQueue<Event> fel = new PriorityQueue<>();
    Queue<Event> queue = new LinkedList<>();
    Random random = new Random();
    int totalDenied = 0;
    int totalProcessed = 0;
    double currentTime = 0.0;
    double maxQueueLength = 0;
    double cumulativeQueueLength = 0;
    int queueLengthChanges = 0;

    public void startSimulation() {
        scheduleArrival();
        while(totalDenied < DENIED_ENTRY_TARGET) {
            Event event = fel.poll();
            currentTime = event.time;
            switch (event.type) {
                case ARRIVAL:
                    handleArrival();
                    break;
                case INSPECTION:
                    handleInspection();
                    break;
            }
        }
        generateReport();
    }

    private void scheduleArrival() {
        double arrivalTime = currentTime + getUniformArrivalTime();
        fel.add(new Event(arrivalTime, Event.Type.ARRIVAL));
        System.out.println("t=" + currentTime + ": initial arrival event generated and scheduled for t=" + arrivalTime);
    }

    private void handleArrival() {
        System.out.println("t=" + currentTime + ": passenger arrived for inspection.");
        if (queue.isEmpty() && fel.stream().noneMatch(event -> event.type == Event.Type.INSPECTION)) {
            scheduleInspection();
        } else {
            queue.add(new Event(currentTime, Event.Type.INSPECTION));
            updateQueueStatistics();
        }
        scheduleArrival();
    }

    private void handleInspection() {
        System.out.println("t=" + currentTime + ": inspection completed.");
        if (random.nextDouble() < FAILURE_RATE) {
            totalDenied++;
            System.out.println("passenger inspection failed (failed no. " + totalDenied + ")");
        } else {
            totalProcessed++;
        }
        if (!queue.isEmpty()) {
            queue.poll();
            scheduleInspection();
            updateQueueStatistics();
        }
    }

    private void scheduleInspection() {
        double inspectionTime = currentTime + getExponentialInspectionTime();
        fel.add(new Event(inspectionTime, Event.Type.INSPECTION));
        System.out.println("inspection starts");
    }

    private double getUniformArrivalTime() {
        return ARRIVAL_MEAN + (random.nextDouble() * 2 - 1) * ARRIVAL_VARIANCE;
    }

    private double getExponentialInspectionTime() {
        return -Math.log(1 - random.nextDouble()) * INSPECTION_MEAN;
    }

    private void updateQueueStatistics() {
        maxQueueLength = Math.max(maxQueueLength, queue.size());
        cumulativeQueueLength += queue.size();
        queueLengthChanges++;
    }

    private void generateReport() {
        double averageQueueLength = cumulativeQueueLength / queueLengthChanges;
        System.out.println("\nSimulation finished.");
        System.out.println("Total simulation time: " + currentTime);
        System.out.println("Length and contents of FEL when simulation ends: " + fel.size());
        System.out.println("Average length of queue: " + averageQueueLength);
        System.out.println("Maximum length of queue: " + maxQueueLength);
    }

    public static void main(String[] args) {
        new AirportSecuritySimulation().startSimulation();
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