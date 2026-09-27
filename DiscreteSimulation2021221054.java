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
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package discretesimulation2021221054;

/**
 *
 * @author muhammedeminkorkunc
 */


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DiscreteSimulation2021221054 {
    private int numPassengers;
    private Random random;
    private List<Double> queueLengths;

    public DiscreteSimulation2021221054(int numPassengers) {
        this.numPassengers = numPassengers;
        this.random = new Random();
        this.queueLengths = new ArrayList<>();
    }

    public void runSimulation() {
        double currentTime = 0;
        int passengersDeniedEntry = 0;
        List<Double> arrivalTimes = new ArrayList<>();
        List<Double> inspectionTimes = new ArrayList<>();

        for (int i = 0; i < numPassengers; i++) {
            double arrivalInterval = 3 + random.nextDouble() * 4; // Uniform distribution [3, 7]
            double inspectionTime = -10 * Math.log(1 - random.nextDouble()); // Exponential distribution with mean 10

            arrivalTimes.add(currentTime + arrivalInterval);
            inspectionTimes.add(inspectionTime);
            currentTime += arrivalInterval;
        }

        int passengerIndex = 0;
        double nextArrival = arrivalTimes.get(passengerIndex);
        double nextInspectionCompletion = Double.MAX_VALUE;

        while (passengerIndex < numPassengers || !queueLengths.isEmpty()) {
            double nextEventTime = Math.min(nextArrival, nextInspectionCompletion);

            if (nextArrival <= nextInspectionCompletion) {
                if (!queueLengths.isEmpty() && queueLengths.get(0) < currentTime) {
                    queueLengths.remove(0);
                }
                if (currentTime > nextArrival) {
                    queueLengths.add(currentTime);
                }
                currentTime = nextArrival;
                nextArrival = ++passengerIndex < numPassengers ? arrivalTimes.get(passengerIndex) : Double.MAX_VALUE;
            } else {
                if (queueLengths.size() > 0) {
                    queueLengths.remove(0);
                }
                passengersDeniedEntry++;
                nextInspectionCompletion = Double.MAX_VALUE;
            }

            if (queueLengths.size() > 0) {
                nextInspectionCompletion = currentTime + inspectionTimes.remove(0);
            }
        }

        System.out.println("Passengers denied entry: " + passengersDeniedEntry);
        System.out.println("Average queue length: " + averageQueueLength());
        System.out.println("Maximum queue length: " + maxQueueLength());
    }

    private double averageQueueLength() {
        double sum = 0;
        for (int i = 0; i < queueLengths.size(); i++) {
            sum += queueLengths.get(i);
        }
        return sum / queueLengths.size();
    }

    private int maxQueueLength() {
        int max = 0;
        for (int i = 0; i < queueLengths.size(); i++) {
            max = Math.max(max, queueLengths.size() - i);
        }
        return max;
    }

    public static void main(String[] args) {
        DiscreteSimulation2021221054 simulation = new DiscreteSimulation2021221054(100);
        simulation.runSimulation();
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