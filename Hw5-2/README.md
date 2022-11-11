111-1 PDSA HW5
---
Due date HW5-2: 11/8 21:00
HW5-2: Covid Simulation
---
Due to the covid situation, travelling is concerning, but it is obvious that some people care less than the others. To simulate the actual situation is rather difficult. Therefore, we are going to simplify the situation and simulate it according to the following rules:

The Virus would attack a specific 
C
i
t
y
i
 at 
D
a
y
i
 randomly according to the function ‘virusAttackPlan’
The whole city would be infected immediately, if the city was attacked by the virus or an infected traveller entered the city.
The recovery rules are as follows:
An infected city will recover after 4 days by default.
For an infected city, if there are travellers from another city which was infected more recently, the recovery date for the city would be extended according to the travellers. If the travellers were infected earlier, the recovery date of the city wouldn’t be changed instead.
The recovery time for any given city, would be 7 days at max, regardless of any recent infected traveller, but it is possible for the city to be infected on the 8th day, which would be a new cycle.
If a virus attacks a city which was already infected, there will be no effect on the recovery date.
For examples:
C1 is infected on day 1. All the citizens should recover on day 1+4 -> day 5.
However, if on day 4 a traveller infected on day 3 travels to C1, the recovery of the city C1 would be extended to day 3+4 -> day 7.
If another traveller infected on day 5 enters C1 on day 6, the recovery of the city would be on day 1+7 regardless. (The specific traveller would be quarantined and isolated from other people.)
Not so important definition:
If a virus attack/infected traveller enter the city on the departure date of a traveller, assume that the traveller would always be infected before departure.
Any traveller on the road is not considered part of any city.
A traveller is not considered part of the city on the date of departure but would be considered part the destination city on the date of arrival.
Assume no traveller would be infected by another traveller on the road.
Our Calendar has neither months nor years, it only counts from day 1 to day N.
We guaranteed that no TravelPlan & virusAttackPlan would have dates before the last CityWithTheMostPatient date.
We also guarantee that date of CityWithTheMostPatient would be strictly increasing.
We guarantee that the number of citizens in a city wouldn’t be less than one after TravelPlan.
Please return the city with the most patients on a certain day with the given information when the function ‘CityWithTheMostPatient’ is called.
If there are more than two cities with the same amount of patients, return the one with the larger index.

Hint
---
Priority Queue Example Code

Event-Driven.
Study about Comparable if using Priority Queue.
Try to list out all the events
Find the chronological order between all the events
(eg. infection would always happen before departure.)
Template
---
class CovidSimulation {
    private int Date = 1;
    public CovidSimulation(int[] Num_Of_Citizen) {
        //The initial number of people in each city is defined here.
    }

    public void virusAttackPlan(int city, int date){
        //Covid is a highly intelligent being, they plan their attacks carefully.
        //The date on which Covid attacks a specific city would be defined here
    }

    public void TravelPlan(int NumberOfTraveller, int FromCity, int ToCity, int DateOfDeparture, int DateOfArrival){
        //The information of travellers' plan would be written here.
        //Since everyone travel with different methods, the duration to travel from City A to B would not be constant (we tried our best to simplifty the problem instead of giving an array of data!)
    }

    public int CityWithTheMostPatient(int date){
        //return the index of city which has the most patients
        
        //if there are more than two cities with the same amount of patients, return the largest index value.         
        //if every city is clean, please return -1.
        return -1;
    }
  
    public static void main(String[] args) {
        CovidSimulation sol = new CovidSimulation(new int[] {10, 100, 15, 25, 10, 13});
        
        sol.virusAttackPlan(0, 1);
        sol.virusAttackPlan(4, 3);
        sol.TravelPlan(3, 0, 3, 3, 4);
        sol.TravelPlan(3, 4, 0, 3, 4); 
        
        System.out.println(sol.CityWithTheMostPatient(2));
        // output = 0
        
        sol.virusAttackPlan(5, 5);
        sol.TravelPlan(1, 5, 0, 5, 6); 
       
        System.out.println(sol.CityWithTheMostPatient(4));
        // output = 3
        System.out.println(sol.CityWithTheMostPatient(8));
        // output = 5
        
        //day 1:{10, 100, 15, 25, 10, 13}
        //infectedList:{1, 0, 0, 0, 0, 0}
        //day 2：{10, 100, 15, 25, 10, 13}
        //infectedList:{1, 0, 0, 0, 0, 0}
        //day 3：{7, 100, 15, 25, 7, 13}
        //infectedList:{1, 0, 0, 0, 1, 0}
        //day 4：{10, 100, 15, 28, 7, 13}
        //infectedList:{1, 0, 0, 1, 1, 0}
        //day 5：{10, 100, 15, 28, 7, 12}
        //infectedList:{1, 0, 0, 1, 1, 1}
        //day 6：{11, 100, 15, 28, 7, 12}
        //infectedList:{1, 0, 0, 1, 1, 1}
        //day 7：{11, 100, 15, 28, 7, 12}
        //infectedList:{1, 0, 0, 1, 0, 1}
        //day 8：{11, 100, 15, 28, 7, 12}
        //infectedList:{0, 0, 0, 0, 0, 1}
    }
}
Case by case Test Data

Test Data
---
Time Limit: 10ms

case1: NumOfCity < 100, MaxDate < 1000, MaxPeople < 1000
case2: NumOfCity < 200, MaxDate < 2000, MaxPeople < 2000
case3: NumOfCity < 1000, MaxDate < 10000, MaxPeople < 10000
case4: NumOfCity < 2000, MaxDate < 20000, MaxPeople < 20000
case5: NumOfCity < 20000, MaxDate < 30000, MaxPeople < 200000
