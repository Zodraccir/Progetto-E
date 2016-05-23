/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import objects.Flight;
import objects.Reservation;
import objects.Route;

/**
 *
 * @author Giovanni
 */
public class AdapterSQL {
    
    ConnectionSQL SQL;
    

    public AdapterSQL() {
        SQL = new ConnectionSQL();
        SQL.startConnection();
        
    } 
    
    public ArrayList<Route> searchRoutes() throws SQLException{
        ArrayList<Route> routes;
        String query = 
        "SELECT A1.CITTA \"CITTAPARTENZA\", A1.NOME \"AEROPORTOPARTENZA\", A2.CITTA \"CITTAARRIVO\", A2.NOME \"AEROPORTOARRIVO\"\n" +
        "FROM Rotta R, Aeroporto A1, Aeroporto A2\n" +
        "WHERE R.AEROPORTOPARTENZA = A1.COD_AEROPORTO AND R.AEROPORTOARRIVO=A2.COD_AEROPORTO";
        ResultSet resultQuery = SQL.queryRead(query);
        routes = ParserSQL.parseRoutes(resultQuery);
        resultQuery.close();
        return routes;        
    }
    
    public ArrayList<Flight> searchFlights(String departure, String destination, String date) throws SQLException{
        ArrayList<Flight> flights;
        String query=
        "SELECT V.COD_VOLO, A1.CITTA \"CITTAPARTENZA\", A1.NOME \"AEROPORTOPARTENZA\", A2.CITTA \"CITTAARRIVO\", A2.NOME \"AEROPORTOARRIVO\", V.DATAPARTENZA, V.ORAPARTENZA, V.DATAARRIVO, V.ORAARRIVO, V.PREZZO "+
        "FROM Rotta R, Aeroporto A1, Aeroporto A2, Volo V "+
        "WHERE R.AEROPORTOPARTENZA = A1.COD_AEROPORTO AND R.AEROPORTOARRIVO=A2.COD_AEROPORTO AND R.COD_ROTTA=V.ROTTA AND A1.CITTA='"+departure+"' AND A2.CITTA ='"+destination+"' AND V.DATAPARTENZA = '"+date+"'";
        ResultSet resultQuery = SQL.queryRead(query);
        flights = ParserSQL.parseFlights(resultQuery);
        resultQuery.close();
        return flights;  
    }  
    
    public Reservation searchReservation(int code) throws SQLException{
        Reservation reservation;
        String query=
        "SELECT COD_PRENOTAZIONE, VOLO, EMAIL, NUMERO\n" +
        "FROM Prenotazione\n" +
        "WHERE COD_PRENOTAZIONE = "+code;
        ResultSet resultQuery = SQL.queryRead(query);
        reservation = ParserSQL.parseReservation(resultQuery);
        resultQuery.close();
        return reservation;
    }
    
    public Reservation makeReservation(String codeFlight, String email, String number) throws SQLException{
        Reservation reservation;
        String query =
        "SELECT MAX(COD_PRENOTAZIONE) AS MAXCOD\n" +
        "FROM Prenotazione";        
        ResultSet resultQuery = SQL.queryRead(query);
        int code = ParserSQL.parseFunctionSQL(resultQuery, "MAXCOD");
        code++;
        query="INSERT INTO Prenotazione VALUES ('"+code+"', '"+codeFlight+"', '"+email+"', '"+number+"')";
        SQL.queryWrite(query);
        reservation = new Reservation(code,codeFlight,email,number);
        return reservation;
    }
}

