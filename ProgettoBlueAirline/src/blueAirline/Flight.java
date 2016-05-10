/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blueAirline;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

/**
 * La classe Flight permette la gestione dei voli, il prezzo e i posti dell'aereo su cui viene effettuato il volo.
 * 
 * @author cl418377
 */
public class Flight {
    
    private String code;
    private Airplane airplane;
    private int flightTime;
    private Calendar departureDate;
    private Route route;
    private HashMap<Seat,Boolean> seats; 
    private int seatFree;
    private double price;
    private int progressiveReservation;
    private int progressiveTicket;
    
    /**
     * Inizializza un nuovo volo.
     * 
     * @param code codice del volo
     * @param airplane Aereo su cui viene effettuato il volo
     * @param route Rotta che percorre l'aereo
     * @param departureDate Data di partenza
     * @param flightTime Tempo di volo in minuti
     * @param price Prezzo di un posto a sedere sul volo
     * 
     */
    public Flight(String code, Airplane airplane, Route route, GregorianCalendar departureDate,int flightTime, double price){
        this.code=code;
        this.airplane=airplane;
        this.route=route;
        this.departureDate = departureDate;
        this.flightTime= flightTime;
        this.price=price;
        this.seats = new HashMap<>();
        for(int i=0;i<airplane.getNumSeat();i++){
            seats.put(airplane.getSeats()[i], Boolean.FALSE);
        }
        this.seatFree=airplane.getNumSeat();
        this.progressiveReservation=0;
        this.progressiveTicket=0;
    }
    /**
     * 
     * @return codice identificativo del volo 
     */
    public String getCode(){
        return code;
    }
    
    /**
     * 
     * @return prezzo a persona del volo 
     */
    public double getPrice(){
        return price;
    }
    /**
     * 
     * @return rappresentazione descrittiva del volo
     */
    public String toString(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy HH:mm");
        return code+"\n"+route.toString()+"Departure Date: "+ sdf.format(this.departureDate.getTime())+"\nTempo di volo in minuti: "+flightTime+"\nPrice: "+Double.toString(price)+" €\nSeats Occcupied: "+(airplane.getNumSeat()-seatFree)+"/"+airplane.getNumSeat()+"\n";
        
    }
    /**  
    * @return data di partenza
    */
    public Calendar getDepartureDate() {
        return departureDate;
    }
    /**
     * 
     * @return rotta dell'aereo 
     */
    public Route getRoute() {
        return route;
    }
    /**
     * 
     * @return numero di posti a sedere liberi di un determinato volo
     */
    public int getSeatFree() {
        return seatFree;
    }
    /**
     * Incrementa la variabile progressiveReservation in modo da avere prenotazioni uniche
     */
    public void addProgressiveReservation(){
        this.progressiveReservation++;
    }
    /**
     * Incrementa la variabile progressiveTicket in modo da avere biglietti unici
     */
    public void addProgressiveTicket(){
        this.progressiveTicket++;
    }
    
    public int getProgressiveReservation(){
        return this.progressiveReservation;
    }
    
    public int getProgressiveTicket(){
        seatFree--;
        return this.progressiveTicket;
        
    }
    
    public int getFlightTime(){
        return flightTime;
    }
    
    public void insertSeat(int n){
        for (HashMap.Entry<Seat, Boolean> val : seats.entrySet()) {
            if(val.getKey().getNumber()==n){
               val.setValue(Boolean.TRUE);
            }
        }
    }
    
    public boolean seatIsOccuped(int n){
        for (HashMap.Entry<Seat, Boolean> val : seats.entrySet()) {
            if(val.getKey().getNumber()==n & val.getValue()==true){
               return true;
            } 
        }
        return false;
    }  
    
    public int automaticSeatOccuped(){
        int i=0;
        for (HashMap.Entry<Seat, Boolean> val : seats.entrySet()) {
            if(val.getValue()==false)
                return i;
            i++;            
        }
        return 0;
    }
    
    public Airplane getAirplane(){
        return airplane;
    }
       
}
    
    
