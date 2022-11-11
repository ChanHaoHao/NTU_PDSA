import edu.princeton.cs.algs4.MinPQ;

class CovidSimulation {
    private class Event implements Comparable<Event> {
        private final int date;
        // used as city and Fromcity
        private final int city1;
        private final int city2;
        private final int Traveller;
        private final int DepartDate;
        private final int ArriveDate;
        private final int recoveryDate;

        public Event(int d, int c1, int c2, int T, int DD, int AD, int RD) {
            date=d;
            city1=c1;
            city2=c2;
            Traveller=T;
            DepartDate=DD;
            ArriveDate=AD;
            recoveryDate=RD;
        }

        public int compareTo(Event that) {
            if (this.date != that.date)
                return this.date-that.date;
            else {
                // same date arrive first, then virus attack, departure the last
                if (this.date == this.ArriveDate)
                    return -1;
                else if (that.date == that.ArriveDate)
                    return 1;
                else if (this.city2 == -1)
                    return -1;
                else if (that.city2 == -1)
                    return 1;
                else
                    return -1;
            }
        }
    }
    private MinPQ<Event> pq;
    // used to store the date that the city is affected and recovered,
    // .x() store the date that the city is first affected,
    // .y() store the date that the city recover from the virus.
    private final int[] cityPopulation;
    private final int[][] ARDate;
    // record if the city is affected
    public CovidSimulation(int[] Num_Of_Citizen) {
        //The initial number of people in each city is defined here.
        cityPopulation = Num_Of_Citizen;
        ARDate = new int[Num_Of_Citizen.length][2];
        pq = new MinPQ<Event>();
    }

    public void virusAttackPlan(int city, int date){
        //Covid is a highly intelligent being, they plan their attacks carefully.
        //The date on which Covid attacks a specific city would be defined here
        // If the city is not affected before or it has already recovered.
        // since the city cannot be affected while it is not recovered.
        pq.insert(new Event(date, city, -1, -1, -1, -1,-1));
    }

    public void TravelPlan(int NumberOfTraveller, int FromCity, int ToCity, int DateOfDeparture, int DateOfArrival){
        //The information of travellers' plan would be written here.
        //Since everyone travel with different methods, the duration to travel from City A to B would not be constant (we tried our best to simplifty the problem instead of giving an array of data!)
        pq.insert(new Event(DateOfDeparture, FromCity, ToCity, NumberOfTraveller, DateOfDeparture, DateOfArrival, -1));
    }

    public int CityWithTheMostPatient(int date) {
        //return the index of city which has the most patients
        //if there are more than two cities with the same amount of patients, return the largest index value.         
        //if every city is clean, please return -1.
        while (!pq.isEmpty() && pq.min().date<=date) {
            Event event = pq.delMin();
            // this indicates the virus attack
            if (event.city2 == -1) {
                // if the city is not affected
                if (event.date>=ARDate[event.city1][1]) {
                    ARDate[event.city1][0] = event.date;
                    ARDate[event.city1][1] = event.date+4;
                }
            }
            // the rest event will be the traveller
            else {
                // deal with the departure date
                if (event.date == event.DepartDate) {
                    cityPopulation[event.city1] -= event.Traveller;
                    // if Fromcity is not affected
                    if (event.date>=ARDate[event.city1][1]) {
                        pq.insert(new Event(event.ArriveDate, event.city1, event.city2, event.Traveller, event.DepartDate, event.ArriveDate, -1));
                    }
                    // if Fromcity is affected
                    else {
                        // if the traveller depart after recovery or recovered before arriving city2, so the traveller won't affect city2
                        if (event.DepartDate >= ARDate[event.city1][1] || ARDate[event.city1][1] <= event.ArriveDate)
                            pq.insert(new Event(event.ArriveDate, event.city1, event.city2, event.Traveller, event.DepartDate, event.ArriveDate, -1));
                        else
                            pq.insert(new Event(event.ArriveDate, event.city1, event.city2, event.Traveller, event.DepartDate, event.ArriveDate, ARDate[event.city1][1]));
                    }
                }
                // deal with the arrival date
                else {
                    cityPopulation[event.city2] += event.Traveller;
                    // if the traveller will affect the recovery date of city2
                    if (event.recoveryDate != -1) {
                        // if city2 is not affected
                        if (event.date >= ARDate[event.city2][1]) {
                            ARDate[event.city2][0] = event.date;
                            ARDate[event.city2][1] = event.date+4;
                        }
                        else if(ARDate[event.city2][1] < event.recoveryDate){
                            ARDate[event.city2][1] = Math.min(ARDate[event.city2][0]+7, event.recoveryDate);
                        }
                    }
                }
            }
        }

        int max=-1;
        int ans=-1;
        // find the city with the most affections
        for (int i=0; i<cityPopulation.length; i++) {
            if (date<ARDate[i][1]) {
                if (cityPopulation[i] >= max) {
                    ans = i;
                    max = cityPopulation[i];
                }
            }
        }

        return ans;
    }

    public static void main(String[] args) {
        CovidSimulation sol = new CovidSimulation(new int[] {500, 100, 50, 250});
        sol.virusAttackPlan(0, 3);
        sol.TravelPlan(200, 0, 3, 1, 5);
        sol.TravelPlan(50, 1, 0, 1, 4);
        sol.TravelPlan(150, 0, 2, 1, 3);
        sol.virusAttackPlan(1, 1);
        sol.virusAttackPlan(1, 4);
        sol.TravelPlan(50, 3, 2, 1, 2);
        sol.TravelPlan(10, 1, 3, 1, 2);
        sol.TravelPlan(70, 3, 0, 2, 3);
        sol.TravelPlan(10, 1, 3, 4, 5);
        System.out.println("MaxRecoveryDate     |     NumOfCitizen");
        for(int i=1;i<10;i++){
            System.out.println(i+"： MostPatient: "+sol.CityWithTheMostPatient(i));
            //System.out.print(i+": ");
            for(int[] x:sol.ARDate){
                System.out.print(x[1]+", ");
            }
            System.out.print(" |  ");
            for(int x: sol.cityPopulation){
                System.out.print(x+", ");
            }

            System.out.println(" ");
            System.out.println(" ");
        }

    }
}