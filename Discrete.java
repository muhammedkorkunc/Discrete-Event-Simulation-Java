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
import java.util.Queue;
import java.util.Random;

public class Discrete {
    static final double ARRIVAL_MEAN = 5.0;
    static final double ARRIVAL_VARIANCE = 2.0;
    static final double INSPECTION_MEAN = 10.0;
    static final int DENIED_ENTRY_TARGET = 100;
    
    Queue<Double> queue = new LinkedList<>();
    double currentTime = 0.0;
    int totalDenied = 0;
    int totalProcessed = 0;
    double maxQueueLength = 0;
    Random random = new Random();
    
    public void startSimulation() {
        while(totalDenied < DENIED_ENTRY_TARGET) {
            // Determine next event type (arrival or inspection)
            double nextArrivalTime = currentTime + getUniformArrivalTime();
            double nextInspectionTime = queue.isEmpty() ? Double.MAX_VALUE : currentTime + getExponentialInspectionTime();
            
            if (nextArrivalTime < nextInspectionTime) {
                // Handle arrival
                currentTime = nextArrivalTime;
                queue.add(currentTime);
                maxQueueLength = Math.max(maxQueueLength, queue.size());
            } else {
                // Handle inspection
                currentTime = nextInspectionTime;
                processInspection();
            }
        }
        generateReport();
    }
    
    private void processInspection() {
        queue.poll(); // Remove the passenger being inspected
        if (random.nextDouble() < 0.05) {
            // 5% chance of being denied
            totalDenied++;
        } else {
            totalProcessed++;
        }
    }
    
    private double getUniformArrivalTime() {
        return ARRIVAL_MEAN + (random.nextDouble() * 2 - 1) * ARRIVAL_VARIANCE;
    }
    
    private double getExponentialInspectionTime() {
        return -Math.log(1 - random.nextDouble()) * INSPECTION_MEAN;
    }
    
    private void generateReport() {
        System.out.println("Simulation finished.");
        System.out.println("Total passengers denied entry: " + totalDenied);
        System.out.println("Total passengers processed: " + totalProcessed);
        System.out.println("Maximum queue length: " + maxQueueLength);
    }
    
    public static void main(String[] args) {
        new Discrete().startSimulation();
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