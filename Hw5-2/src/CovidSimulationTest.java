import edu.princeton.cs.algs4.MinPQ;
class WorldEvent implements Comparable<WorldEvent> {

    public int numChange;
    public int city;
    public int departcity;
    public int date;
    public int recovery;
    public int arrival_date;
    public int arrival_city;
    public boolean ifVirus;
    public WorldEvent(int num,int dat,int City,boolean IfVirus,int departCity,int Recoveryday){
        departcity=departCity;
        numChange=num;
        date=dat;
        city=City;
        ifVirus=IfVirus;
        recovery=Recoveryday;
    }
    public WorldEvent(int num,int dat,int City,int arrival_t,int arrival_c){
        numChange=num;
        date=dat;
        city=City;
        arrival_date=arrival_t;
        arrival_city=arrival_c;

    }
    public int compareTo(WorldEvent that){
        if(this.date!=that.date){
            return this.date-that.date;
        }else{
            if(this.numChange>0){
                return -1;
            }
            else if(that.numChange>0){
                return 1;
            }
            else if(this.numChange==0){
                return -1;
            }
            else if(that.numChange==0){
                return 1;
            }
            else {
                return -1;
            }


        }
    }


}
class CovidSimulation2 {
    MinPQ<WorldEvent> C_Event;
    private int Date = 1;
    private int[] Num_Citizen;
    private int[] RecoveryDate;
    private int[] MaxDate;
    public CovidSimulation2(int[] Num_Of_Citizen) {
        Num_Citizen=Num_Of_Citizen;
        RecoveryDate=new int[Num_Citizen.length];
        MaxDate=new int[Num_Citizen.length];
        C_Event=new MinPQ<>();
        //The initial number of people in each city is defined here.
    }

    public void virusAttackPlan(int city, int date){
        WorldEvent VEvent=new WorldEvent(0,date,city,true,city,0);
        C_Event.insert(VEvent);
        //Covid is a highly intelligent being, they plan their attacks carefully.
        //The date on which Covid attacks a specific city would be defined here
    }

    public void TravelPlan(int NumberOfTraveller, int FromCity, int ToCity, int DateOfDeparture, int DateOfArrival){
        WorldEvent TEvent=new WorldEvent(-NumberOfTraveller,DateOfDeparture,FromCity,DateOfArrival,ToCity);
        C_Event.insert(TEvent);
        //The information of travellers' plan would be written here.
        //Since everyone travel with different methods, the duration to travel from City A to B would not be constant (we tried our best to simplifty the problem instead of giving an array of data!)
    }
    public int CityWithTheMostPatient(int date){
        WorldEvent event;
        int temp_day;
        //System.out.println(" ");
        while(C_Event.isEmpty()!=true ){
            event=C_Event.delMin();
            if(event.date>date){
                C_Event.insert(event);
                break;
            }
            Date=event.date;
            if(event.numChange==0 ){                         //virus attack
                if(Date>=RecoveryDate[event.city]){
                    RecoveryDate[event.city]=Date+4;
                    MaxDate[event.city]=Date+7;
                }
            }
            // arrive
            else if(event.numChange>0){
                Num_Citizen[event.city]=Num_Citizen[event.city]+event.numChange;
                //System.out.println("==="+RecoveryDate[event.city]+"====="+MaxDate[event.city]);
                if(event.ifVirus==true){
                    if(Date>=RecoveryDate[event.city]){
                        RecoveryDate[event.city]=Date+4;
                        MaxDate[event.city]=Date+7;
                    }
                    else if(RecoveryDate[event.city]<event.recovery){
                        if(event.recovery<MaxDate[event.city]){
                            RecoveryDate[event.city]=event.recovery;
                        }else{
                            RecoveryDate[event.city]=MaxDate[event.city];
                        }
                    }
                }
            }
            // depart
            else{
                Num_Citizen[event.city]=Num_Citizen[event.city]+event.numChange;
                if(Date>=RecoveryDate[event.city]|| RecoveryDate[event.city]<=event.arrival_date){
                    C_Event.insert(new WorldEvent(-event.numChange,event.arrival_date,event.arrival_city,false,event.city,0));
                }else{
                    C_Event.insert(new WorldEvent(-event.numChange,event.arrival_date,event.arrival_city,true,event.city,RecoveryDate[event.city]));
                }
            }
        }
        //return the index of city which has the most patients
        //if there are more than two cities with the same amount of patients, return the largest index value.         
        //if every city is clean, please return -1.
        int max=-1;int index=-1;
        //System.out.println("DAY "+date+":");
        // System.out.print("Inf:");
        for(int i=0;i<Num_Citizen.length;i++){
            if(date<RecoveryDate[i]){
                if(Num_Citizen[i]>=max){
                    index=i;
                    max=Num_Citizen[i];
                }
                //   System.out.print(i+" ");
            }
        }
        //System.out.println("");
        //System.out.print("Num:");
       /* for(int i=0;i<Num_Citizen.length;i++){
                System.out.print(Num_Citizen[i]+" ");
        }*/
        return index;
    }

    public static void main(String[] args) {
        CovidSimulation2 sol = new CovidSimulation2(new int[] {11,11, 11, 11, 11});
        sol.virusAttackPlan(0, 1);
        sol.virusAttackPlan(1, 2);
        sol.virusAttackPlan(2, 3);
        sol.virusAttackPlan(3, 4);
        sol.virusAttackPlan(4, 5);
        for(int i=1;i<13;i++){
            System.out.println("嚗?MostPatient: "+sol.CityWithTheMostPatient(i));

        }
        // output = 0


        // output = 5

        //day 1:{10, 100, 15, 25, 10, 13}
        //infectedList:{1, 0, 0, 0, 0, 0}
        //day 2嚗10, 100, 15, 25, 10, 13}
        //infectedList:{1, 0, 0, 0, 0, 0}
        //day 3嚗7, 100, 15, 25, 7, 13}
        //infectedList:{1, 0, 0, 0, 1, 0}
        //day 4嚗10, 100, 15, 28, 7, 13}
        //infectedList:{1, 0, 0, 1, 1, 0}
        //day 5嚗10, 100, 15, 28, 7, 12}
        //infectedList:{1, 0, 0, 1, 1, 1}
        //day 6嚗11, 100, 15, 28, 7, 12}
        //infectedList:{1, 0, 0, 1, 1, 1}
        //day 7嚗11, 100, 15, 28, 7, 12}
        //infectedList:{1, 0, 0, 1, 0, 1}
        //day 8嚗11, 100, 15, 28, 7, 12}
        //infectedList:{0, 0, 0, 0, 0, 1}
    }
}