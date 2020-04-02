package main;

import java.util.LinkedList;
import java.util.Scanner;

/**
 *  Gavin Horner
 *  Western Illinois University
 *  CS420 Networks
 *
 *  This program is used to simulate the data structure required to implement multiple virtual timers
 *  connected to one physical timer. The user inputs the timers they want in order with milliseconds in mind
 *  which then creates the timers in that order. Once the timer is started, each timer will run for the given milliseconds
 *  and once finished, will remove itself from the front of the list, and move on to the next user. Informing the user that
 *  it has finished and which timer it was.
 */
public class VirtualTimers {

    /**
     * startTimer   This method starts the timer and runs the process of executing what needs to be done
     *              throughout each timer, informing the user when the timer 1/4 of the way through, 1/3 of the way through,
     *              one half of the way through, and when it is finished and then removes it from the list.
     * @param timerList   takes in the list of the logical timers to be used
     * @throws InterruptedException
     */
    public static void startTimer(LinkedList<Integer> timerList, int totalTime){
        int timerNum = 1; // To keep track of which timer we are on
        int timeThisFar = 0;
        while(!timerList.isEmpty()){ // While the list is not empty, run the next timer in the list
            boolean oneFourth = false, oneThird = false, oneHalf = false; // To ensure only one instance of being a certain way through the timer is printed (As the program is very fast).
            int currentTimer = timerList.getFirst(); // Current timer always being the first in the list (As once it times out it is removed).
            timeThisFar = timeThisFar + currentTimer;
            long initialTime = System.currentTimeMillis();
            long currentTimeElapsed = System.currentTimeMillis() - initialTime;
            System.out.println("\n-------------------------------------------");
            System.out.println("|                 Timer " + timerNum + "                 |"); /* Purely for giving visualization to the user of which timer has done what. */
            System.out.println("-------------------------------------------");
            while(currentTimeElapsed != currentTimer) { // While the time elapsed since the initial time is not equal to the specified timer
                if(currentTimeElapsed == currentTimer / 4 && !oneFourth){ // If the time elapsed is equal to 1/4 of the given timer, and it hasn't already been printed. Then print out we are 1/4 of the way through the timer.
                    System.out.println("1/4 of the way through the current timer!");
                    oneFourth = true; // To ensure this only prints out one time, as this while loop can run several times in one millisecond.
                }
                else if(currentTimeElapsed == currentTimer / 3 && !oneThird){
                    System.out.println("1/3 of the way through the current timer!");
                    oneThird = true;
                }
                else if(currentTimeElapsed == currentTimer / 2 && !oneHalf){
                    System.out.println("1/2 of the way through the current timer!");
                    oneHalf = true;
                }
                currentTimeElapsed = System.currentTimeMillis() - initialTime; // Update the time elapsed

            }
            System.out.println("Timeout! Timer " + timerNum + " of: " + timerList.remove() + "ms has passed."); // Prints out the timer, and how many ms it was. Removing it from the list
            System.out.println("Currently: " + Math.round(((timeThisFar / (double)totalTime) * 100.0)) + "% of the way through the full length of the physical timer."); // Prints current % through the full physical timer
            timerNum++; // Onto the next timer in the list.
        }
    }

    /**
     * main     Responsible for taking in user input and creating the timer list
     *          and tying everything else together.
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args){
        LinkedList<Integer> virtualTimers = new LinkedList<>(); // Creates the list to hold the timers
        int totalTime = 0;
        Scanner scan = new Scanner(System.in);

        System.out.println("Welcome to my virtual timer simulator!");
        System.out.println("Please enter your times in milliseconds(separated by spaces), for the timers you would like:");
        String line = scan.nextLine(); // Prompts for users to enter their timers, and storing the line as a string
        String[] timers = line.trim().split(" "); // Trims the timers and puts them into their own individual index in an array
        for(int i=0; i<timers.length; i++){
            try {
                virtualTimers.add(Integer.parseInt(timers[i])); // Adds the timers from the array to the linked list
                totalTime = totalTime + Integer.parseInt(timers[i]);
            }
            catch(NumberFormatException e){
                System.out.println("Numbers only please. Restart program");
                System.exit(1);
            }
        }
        System.out.println("Your total timer is: " + totalTime);
        System.out.println("Please type \"Start\" to symbolize starting your \"Physical Timer\" that runs through your virtual timers one by one.");
        String start = scan.next();
        while(!start.equalsIgnoreCase("start")){
            start = scan.next();
        }

        startTimer(virtualTimers, totalTime);

    }
}
