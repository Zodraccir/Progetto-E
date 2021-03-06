/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import objects.Flight;
import objects.HoldLuggage;
import objects.Insurance;
import objects.Meal;
import objects.Reservation;
import objects.Route;
import objects.Seat;
import objects.Ticket;

/**
 *Provides the query written in SQL language in order to modify/add new elements in the Database.
 * 
 * @author Giovanni
 */
public class ParserSQL {
    /**
     * Inserts the routes located in the databdase in an ArrayList.
     * 
     * @param resultQuery Result of the query.
     * @return List of all routes found in the database.
     * @throws SQLException if occurs a database access error or other errors.
     */
    public static ArrayList<Route> parseRoutes(ResultSet resultQuery) throws SQLException{
        ArrayList<Route> routes = new ArrayList<>();
        while (resultQuery.next()) {
            String departureAirport = resultQuery.getString("AEROPORTOPARTENZA");
            String destinationAirport = resultQuery.getString("AEROPORTOARRIVO");
            String departureCity = resultQuery.getString("CITTAPARTENZA");
            String destinationCity = resultQuery.getString("CITTAARRIVO");
            routes.add(new Route(departureAirport, destinationAirport, departureCity, destinationCity));
        }
        return routes;        
    }
    /**
     * Inserts the flights located in the databdase in an ArrayList.
     * 
     * @param resultQuery Result of the query.
     * @return List of all flights found in the database.
     * @throws SQLException if occurs a database access error or other errors.
     */
    public static ArrayList<Flight> parseFlights(ResultSet resultQuery) throws SQLException{
        ArrayList<Flight> flights = new ArrayList<>();
        while (resultQuery.next()) {
            String code = resultQuery.getString("COD_VOLO");
            String departureCity = resultQuery.getString("CITTAPARTENZA");
            String departureAirport = resultQuery.getString("AEROPORTOPARTENZA");
            String destinationCity = resultQuery.getString("CITTAARRIVO");
            String destinationAirport = resultQuery.getString("AEROPORTOARRIVO");
            String departureDate  = resultQuery.getString("DATAPARTENZA"); 
            String departureTime = resultQuery.getString("ORAPARTENZA");
            String destinationDate  = resultQuery.getString("DATAARRIVO"); 
            String destinationTime = resultQuery.getString("ORAARRIVO");
            double price = resultQuery.getDouble("PREZZO");
            
            Route r = new Route(departureAirport, destinationAirport, departureCity, destinationCity);
            Calendar departureCalendar = ParserSQL.returnCalendar(departureDate, departureTime);
            Calendar destinationCalendar = ParserSQL.returnCalendar(destinationDate, destinationTime);
            
            flights.add(new Flight(code,r,departureCalendar,destinationCalendar,price));
        }
        return flights;        
    }
    
    /**
     * Inserts the flights located in the databdase in an ArrayList setting also the seats.
     * 
     * @param resultQuery Result of the query.
     * @return List of all flights found in the database.
     * @throws SQLException if occurs a database access error or other errors.
     */
    public static Flight parseFlight(ResultSet resultQuery) throws SQLException{
        if(resultQuery.next()){
            String code = resultQuery.getString("COD_VOLO");
            String departureCity = resultQuery.getString("CITTAPARTENZA");
            String departureAirport = resultQuery.getString("AEROPORTOPARTENZA");
            String destinationCity = resultQuery.getString("CITTAARRIVO");
            String destinationAirport = resultQuery.getString("AEROPORTOARRIVO");
            String departureDate  = resultQuery.getString("DATAPARTENZA"); 
            String departureTime = resultQuery.getString("ORAPARTENZA");
            String destinationDate  = resultQuery.getString("DATAARRIVO"); 
            String destinationTime = resultQuery.getString("ORAARRIVO");
            double price = resultQuery.getDouble("PREZZO");
            
            Route r = new Route(departureAirport, destinationAirport, departureCity, destinationCity);
            Calendar departureCalendar = ParserSQL.returnCalendar(departureDate, departureTime);
            Calendar destinationCalendar = ParserSQL.returnCalendar(destinationDate, destinationTime);
            Flight flight = new Flight(code,r,departureCalendar,destinationCalendar,price); 
            
            ArrayList<Seat> seats = new ArrayList<>();
            int numberSeat = resultQuery.getInt("NUMERO");
            int classSeat = resultQuery.getInt("Classe");
            String ticket = resultQuery.getString("PASSEGGERO"); //è null se il ticket nel db è NULL 
            seats.add(new Seat(numberSeat,classSeat,ticket));
      
            while (resultQuery.next()) {
                numberSeat = resultQuery.getInt("NUMERO");
                classSeat = resultQuery.getInt("Classe");
                ticket = resultQuery.getString("PASSEGGERO");
                seats.add(new Seat(numberSeat,classSeat,ticket));
            }            
            flight.setSeats(seats);        
            return flight;
        }
        else{
            return null;
        }
    }
    /**
     * Inserts the reservation located in the databdase in an ArrayList.
     * 
     * @param resultQuery Result of the query.
     * @return List of all reservation found in the database.
     * @throws SQLException if occurs a database access error or other errors.
     */
    public static Reservation parseReservation(ResultSet resultQuery) throws SQLException{
        if(resultQuery.next()){
            int codeReservation = resultQuery.getInt("COD_PRENOTAZIONE");
            String codeFlight = resultQuery.getString("VOLO");
            String email = resultQuery.getString("EMAIL");
            String number = resultQuery.getString("NUMERO");
            Reservation res = new Reservation(codeReservation, email, number, codeFlight);
            
            ArrayList<Ticket> ticket = new ArrayList<>();
            String codeTicket = resultQuery.getString("COD_TICKET");
            ticket.add(new Ticket(codeTicket));
            
            while (resultQuery.next()){
                codeTicket = resultQuery.getString("COD_TICKET");
                ticket.add(new Ticket(codeTicket));
            }
            
            res.setTickets(ticket);
            return res;
        }
        else{
            return null;
        }
    }
 
    public static double parseFunctionDoubleSQL(ResultSet resultQuery, String value) throws SQLException{ //SINGOLI MAX,MIN,COUNT...
        resultQuery.next();
        return resultQuery.getDouble(value);
    }
    
    public static String parseFunctionStringSQL(ResultSet resultQuery, String value) throws SQLException{ //SINGOLI MAX,MIN,COUNT...
        resultQuery.next();
        return resultQuery.getString(value);
    }
    
    /**
     * Inserts the cities located in the databdase in an ArrayList.
     * 
     * @param resultQuery Result of the query.
     * @return List of all cities found in the database.
     * @throws SQLException if occurs a database access error or other errors.
     */
    public static ArrayList<String> parseCitis(ResultSet resultQuery) throws SQLException{
        ArrayList<String> citys = new ArrayList<>();
        while (resultQuery.next()) {
            citys.add(resultQuery.getString("NOME"));
        }
        return citys;
    }
    
    /**
     * Inserts the seats located in the databdase in an ArrayList.
     * 
     * @param resultQuery Result of the query.
     * @return List of all seats found in the database.
     * @throws SQLException if occurs a database access error or other errors.
     */
    public static ArrayList<Seat> parseSeats(ResultSet resultQuery) throws SQLException {
        ArrayList<Seat> seats = new ArrayList<>();
        while (resultQuery.next()) {
            int num = Integer.parseInt(resultQuery.getString("NUMERO"));
            int classe = Integer.parseInt(resultQuery.getString("Classe"));
            String ticket = resultQuery.getString("PASSEGGERO");
            seats.add(new Seat(num, classe, ticket));
        }
        return seats;    
    }
    /**
     * Inserts the meals located in the databdase in an ArrayList.
     * 
     * @param resultQuery Result of the query;
     * @return List of all meals found in the database.
     * @throws SQLException if occurs a database access error or other errors.
     */
    public static ArrayList<Meal> parseMeals(ResultSet resultQuery) throws SQLException{
        ArrayList<Meal> meals = new ArrayList<>();
        while (resultQuery.next()) {
            String code = resultQuery.getString("COD_PASTO");
            String name = resultQuery.getString("NOME");
            double price = resultQuery.getDouble("PREZZO");
            int timeMeal = resultQuery.getInt("TEMPOVIAGGIO");
            meals.add(new Meal(code,name,price,timeMeal));
        }
        return meals;   
    } 
    
    /**
     * Inserts the hold luggages located in the databdase in an ArrayList.
     * 
     * @param resultQuery Result of the query.
     * @return List of all hold luggages found in the database.
     * @throws SQLException if occurs a database access error or other errors.
     */
    public static ArrayList<HoldLuggage> parseHoldLuggages(ResultSet resultQuery) throws SQLException{
        ArrayList<HoldLuggage> holdLuggages = new ArrayList<>();
        while (resultQuery.next()) {
            String code = resultQuery.getString("COD_BAGAGLIO");
            double kg = resultQuery.getDouble("KG");
            double price = resultQuery.getDouble("PREZZO");
            String description = resultQuery.getString("DESCRIZIONE");
            holdLuggages.add(new HoldLuggage(code,kg,price,description));
        }
        return holdLuggages; 
    }
    
    /**
     * Inserts the insurances located in the databdase in an ArrayList.
     * 
     * @param resultQuery Result of the query.
     * @return List of all insurances found in the database.
     * @throws SQLException if occurs a database access error or other errors.
     */
    public static ArrayList<Insurance> parseInsurances(ResultSet resultQuery) throws SQLException{
        ArrayList<Insurance> insurances = new ArrayList<>();
        while (resultQuery.next()) {
            String code = resultQuery.getString("COD_ASSICURAZIONE");
            String name = resultQuery.getString("NOME");
            double price = resultQuery.getDouble("PREZZO");
            String description = resultQuery.getString("DESCRIZIONE");
            insurances.add(new Insurance(code,name,price,description));
        }
        return insurances; 
    }
    
    /**
     * Inserts the tickets located in the databdase in an ArrayList.
     * 
     * @param resultQuery Result of the query.
     * @return List of all tickets found in the database.
     * @throws SQLException if occurs a database access error or other errors.
     */
    public static Ticket parseTicketPassenger(ResultSet resultQuery) throws SQLException{
        if(resultQuery.next()){
            String codeTicket = resultQuery.getString("COD_TICKET");
            double priceFlight = resultQuery.getDouble("PREZZO");
            int codeReservation = resultQuery.getInt("COD_PRENOTAZIONE");
            String ID = resultQuery.getString("ID");
            String name = resultQuery.getString("NOME");
            String surname = resultQuery.getString("COGNOME");
            String codeFlight = resultQuery.getString("VOLO");
            int nseat = resultQuery.getInt("NPOSTO");
            int classe = resultQuery.getInt("CLASSE");
            boolean cheakIn  = resultQuery.getBoolean("CHECKIN");
            //TicketPassenger(String code, String name, String surname, String codeFlight, int nseat, int codeReservation, int classe, boolean checkIn) {
            Ticket tp = new Ticket(codeTicket, priceFlight, ID, name, surname, codeFlight, nseat, codeReservation, classe, cheakIn);
            return tp;
        }
        else{
            return null;
        }
    }
    
    public static Boolean parseCheckLogin(ResultSet resultQuery) throws SQLException{
        if(resultQuery.next()){
            return true;
        }
        else{
            return false;
        }
    }
        
    //METODI GENERICI
    
    /**
     * Converts the string date/time in a date in GregorianCalendar format.
     * 
     * @param stringDate Date in String format.
     * @param stringTime Time in String format.
     * @return Date in GregorianCalendar format.
     */
    public static Calendar returnCalendar(String stringDate, String stringTime){
        String[] vetDate = stringDate.split("-");
        int year = Integer.parseInt(vetDate[0]);
        int month = Integer.parseInt(vetDate[1])-1;
        int day = Integer.parseInt(vetDate[2]);
        String[] vetTime = stringTime.split(":");
        int hour = Integer.parseInt(vetTime[0]);
        int minute = Integer.parseInt(vetTime[1]);
        GregorianCalendar date = new GregorianCalendar(year,month,day,hour,minute);
        return date;
    }
    
    /**
     * Converts a date in Gregorian calendar format in a String.
     * 
     * @param date Date in GregorianCalendar format.
     * @return Date in String format.
     */
    public static String stringDate(GregorianCalendar date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String d = sdf.format(date.getTime());
        String[] vet = d.split("/");
        return vet[2]+"-"+vet[1]+"-"+vet[0];
    }
    /**
     * Converts a time in Gregorian calendar format in a String.
     * 
     * @param date Time in GregorianCalendar format.
     * @return Time in String format.
     */
    public static String stringTime(GregorianCalendar date){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String d = sdf.format(date.getTime());
        String[] vet = d.split(":");
        return vet[0]+":"+vet[1]+":00";
    }
    
    

    
}
