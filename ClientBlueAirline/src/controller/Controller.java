/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import objects.Flight;
import objects.HoldLuggage;
import objects.Insurance;
import objects.Meal;
import objects.Reservation;
import objects.Route;
import objects.Ticket;

/**
 *
 * @author Giovanni
 */
public class Controller {

    private Socket clientSocket;
    private int PortNumber = 8888;
    private PrintWriter out;
    private BufferedReader in;
    private Gson gson = new Gson();

    public boolean connect(String ipServer) throws IOException{
        try {
            clientSocket = new Socket(ipServer, PortNumber);
        }catch(UnknownHostException a){
            return false;
        } 
        catch (ConnectException ex) {
            //Logger.getLogger(ClientBlueAirline.class.getName()).log(Level.SEVERE, "ERROR", ex);
            return false;
        }
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        return true;
    }

    public void hello() throws IOException {
        out.println("HI!");
        if (in.readLine().equals("HI!")) {
            System.out.println("RICEVUTA RISPOSTA DAL SERVER, IL SERVER E' ATTIVO");
        }
    }

    public Flight[] searchFlights(Flight flight) throws IOException {
        out.println("RICERCAVOLI " + gson.toJson(flight));
        Flight[] flights = gson.fromJson(in.readLine(), Flight[].class);
        return flights;
    }
    
    public Flight searchFlight(Flight flight) throws IOException {
        out.println("RICERCAVOLOCODICE " + gson.toJson(flight));
        Flight f = gson.fromJson(in.readLine(), Flight.class);
        return f;
    }   

    public Reservation makeReservation(Reservation res) throws IOException {
        out.println("RESERVATION " + gson.toJson(res));
        return gson.fromJson(in.readLine(), Reservation.class);
    }
    
    public Ticket editSeatTicketPassenger(Ticket tp) throws IOException{
        out.println("EDITSEATTICKETPASSENGER " + gson.toJson(tp));
        return gson.fromJson(in.readLine(), Ticket.class);
    }

    public Route[] checkRoute(Route rotta) throws IOException {
        out.println("ROTTE " + gson.toJson(rotta));
        String serverout = in.readLine();
        Route[] rotte = gson.fromJson(serverout, Route[].class);        
        return rotte;
    }
    
    public String[] listOfCity() throws IOException{
        out.println("GETALLCITY");
        String[] cities = gson.fromJson(in.readLine(), String[].class);
        return cities;
    }

    public Flight[] calendar(Route rotta) throws IOException {
        out.println("CALENDAR " + gson.toJson(rotta));
        Flight[] flights = gson.fromJson(in.readLine(), Flight[].class);
        return flights;
    }
    
    public Meal[] getAllMeals() throws IOException{
        out.println("PASTI ");
        Meal[] meals = gson.fromJson(in.readLine(), Meal[].class);
        return meals;
    }
    
    public HoldLuggage[] getAllHoldLuggages() throws IOException{
        out.println("BAGAGLI ");
        HoldLuggage[] holdLuggages = gson.fromJson(in.readLine(), HoldLuggage[].class);
        return holdLuggages;
    }
    
    public Insurance[] getAllInsurances() throws IOException{
        out.println("ASSICURAZIONI ");
        Insurance[] insurances = gson.fromJson(in.readLine(), Insurance[].class);
        return insurances;
    }
    
    public Ticket checkIn(Ticket tp) throws IOException{
        out.println("CHECKIN " + gson.toJson(tp));
        Ticket tp1 = gson.fromJson(in.readLine(), Ticket.class);
        return tp1;
    }
    
    public boolean isCheckIn(Ticket tp) throws IOException{
        out.println("ISCHECKIN " + gson.toJson(tp));
        Boolean isCheckIn = gson.fromJson(in.readLine(), Boolean.class);
        return isCheckIn;
    }
    
    public Ticket getTicketPassenger(Ticket tp) throws IOException{
        out.println("PASSEGGERO " + gson.toJson(tp));
        Ticket tp1 = gson.fromJson(in.readLine(), Ticket.class);
        return tp1;
    }
    
    public Ticket editTicketPassenger(Ticket ticketPassenger) throws IOException {
        out.println("EDITTICKET " + gson.toJson(ticketPassenger));
        Ticket tp = gson.fromJson(in.readLine(), Ticket.class);
        return tp;
    }
    
    public Reservation getReservation(Reservation res) throws IOException{
        out.println("PRENOTAZIONE " + gson.toJson(res));
        Reservation res1 = gson.fromJson(in.readLine(), Reservation.class);
        return res1;
    }
    
    /*
    public void scrittura() throws IOException {
        System.out.println("RICEVUTO DAL SERVER: " + in.readLine());
    }
    */

  
}