/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import objects.Flight;
import objects.HoldLuggage;
import objects.Insurance;
import objects.Meal;
import objects.Reservation;
import objects.Route;
import objects.Ticket;

/**
 * Provides methods for the Database managment. This methods are those who the customers need.
 * 
 * @author Giovanni
 */
public interface InterfaceServer {

    /**
     * Modifies the seat number of a ticket.
     * 
     * @param tp Ticket to modify.
     * @return Ticket modified.
     * @throws SQLException If there is Database access error or other errors.
     */
    Ticket editSeatTicket(Ticket tp) throws SQLException;

    /**
     * Modifies a ticket.
     * 
     * @param tp Ticket to modify.
     * @return Ticket modified.
     * @throws SQLException If there is Database access error or other errors.
     */
    Ticket editTicket(Ticket tp) throws SQLException;

    /**
     * 
     * @return List of all hold luggages.
     */
    ArrayList<HoldLuggage> getAllHoldLuggages();

    /**
     * 
     * @return List of all insurances.
     */
    ArrayList<Insurance> getAllInsurances();

    /**
     * 
     * @return List of all meals.
     */
    ArrayList<Meal> getAllMeals() ;

    /**
     * 
     * @param codeReservation Code of the reservation.
     * @return Full reservation.
     * @throws SQLException If there is Database access error or other errors.
     */
    Reservation getReservtion(int codeReservation) throws SQLException;

    /**
     * 
     * @param codeTicket Code of the ticket.
     * @return Full ticket.
     * @throws SQLException If there is Database access error or other errors.
     */
    Ticket getTicket(String codeTicket) throws SQLException;

    /**
     * 
     * @param codeTicket Code of the ticket.
     * @return <code>true</code> if the check in is already done;
     *         <code>false</code> otherwise.
     * @throws SQLException If there is Database access error or other errors.
     */
    boolean isCheckIn(String codeTicket) throws SQLException;

    /**
     * Inserts a reservation in the database.
     * 
     * @param reservation Reservation to do.
     * @return Reservation done.
     * @throws SQLException If there is Database access error or other errors.
     */
    Reservation makeReservation(Reservation reservation) throws SQLException;

    /**
     * 
     * @return List of all cities.
     */
    ArrayList<String> searchAllCitys() ;

    /**
     * Search a flight in the database.
     * 
     * @param codeFlight Code of the flight.
     * @return Flight found.
     * @throws SQLException If there is Database access error or other errors.
     */
    Flight searchFlight(String codeFlight) throws SQLException;

    /**
     * Search flights corrispondences.
     * 
     * @param departure Departure city.
     * @param destination Destination city.
     * @param date Date of the flight.
     * @return Corrispondences found.
     * @throws SQLException If there is Database access error or other errors.
     */
    ArrayList<Flight> searchFlights(String departure, String destination, String date) throws SQLException;

    /**
     * 
     * @param route Route.
     * @return Flights that travel over this route.
     * @throws SQLException If there is Database access error or other errors.
     */
    ArrayList<Flight> searchFlights(Route route) throws SQLException;

    /**
     * 
     * @return List of routes.
     */
    ArrayList<Route> searchRoutes();

    /**
     * Makes the check in of a ticket.
     * 
     * @param codeTicket Code of the ticket.
     * @throws SQLException If there is Database access error or other errors.
     */
    void setCheckIn(String codeTicket) throws SQLException;
    
    /**
     * Inserts a flight in the Database.
     * 
     * @param flight Flight. 
     * @return Flight inserted.
     * @throws SQLException If there is Database access error or other errors.
     */
    Flight insertFlight(Flight flight) throws SQLException;
    
    /**
     * 
     * @param userpass Login information.
     * @return <code>true</code> if informations are correct.
 *   *          <code>false</code> otherwise.
     * @throws SQLException If there is Database access error or other errors.
     */
    Boolean checkLogin(String username) throws SQLException;
    /**
     * Send an email to the addressee.
     * 
     * @param sender Addressee of the email.
     * @param object Object of the email.
     * @param text Text of the email.
     */
    void sendMail(String sender, String object, String text);
    /**
     * Modifies a flight in the Database.
     * 
     * @param flight Flight to modify.
     * @return Flight modified.
     * @throws SQLException If there is Database access error or other errors.
     */
    Flight editFlight(Flight flight) throws SQLException;

    //numero posti liberi in un volo
    //int numberSeatFreeFlight(String codeFlight) throws SQLException;
    
    //dato volo e posto vedere se è libero
    //boolean seatIsFree(String codeFlight, int nseat) throws SQLException;
    
    //imposta array posti di un volo
    //Flight setSeatsFlight(Flight flight) throws SQLException;    

    //ritorna i bagagli di un ticket
    //ArrayList<HoldLuggage> getHoldLuggagesTicket(String codeTicket) throws SQLException;

    //ritorna le assicurazioni di un ticket
    //ArrayList<Insurance> getInsurancesTicket(String codeTicket) throws SQLException;

    //ritorna i pasti di un ticket
    //ArrayList<Meal> getMealsTicket(String codeTicket) throws SQLException;
    
}
