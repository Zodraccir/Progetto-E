/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controller.FacadeControllerClient;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import objects.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Michele
 */
public class RiassuntoPanel extends JPanel {

    private HomeFrame home;
    private FacadeControllerClient controller;
    private ArrayList<Ticket> passengers;
    private Reservation reservation;
    private Flight flight;

    JPanel infoGenerali = new JPanel();
    JLabel email = new JLabel();
    JLabel telefono= new JLabel();
    JLabel codice = new JLabel();
    JLabel andata = new JLabel();
    JLabel ritorno = new JLabel();
    JLabel data = new JLabel();
    JLabel orario = new JLabel();
    JLabel prezzototale = new JLabel();

    JPanel info = new JPanel();

    JLabel classe = new JLabel();

    JLabel prezzo = new JLabel();
    JLabel numPosto = new JLabel();
    JComboBox passeggeri = new JComboBox();
    JTextArea riassunto = new JTextArea(30, 10);
    JScrollPane riassuntoS = new JScrollPane(riassunto);

    JButton conferma = new JButton("Conferma");

    public RiassuntoPanel(HomeFrame home, FacadeControllerClient controller) throws IOException {
        this.home = home;
        passengers = home.getPassengers();
        home.getReservation().setTickets(passengers);
        reservation = home.getReservation();
        this.controller = controller;
        flight = controller.searchFlights(new Flight(home.getCodeflight()))[0];
        this.setVisible(true);
        setOpaque(false);
        addComponents(this);

        this.makeComponentsTrasparent();
        passeggeri.setForeground(Color.black);
    }

    private void addComponents(Container pane) throws IOException {

        pane.setLayout(new BorderLayout());
        initInfoGenerali();
        pane.add(infoGenerali, BorderLayout.NORTH);
        initInfoPasseggero();
        pane.add(info, BorderLayout.CENTER);

        pane.add(conferma, BorderLayout.SOUTH);

        //LISTENER CONFERMA!
        conferma.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                reservation = controller.makeReservation(home.getReservation());
                flight = controller.searchFlights(flight)[0];
                for (Ticket tick : reservation.getPassengers()) {
                    if (tick.getNseat() == -1) {
                        String[] options = {"esci."};
                        FinalControlPanel finale = new FinalControlPanel(home, controller, reservation, tick, flight);
                        JOptionPane.showOptionDialog(home, finale, "BENVENUTI IN THETABLUEAIRLINE", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    }
                }
                reservation = controller.getReservation(reservation);
                String s = "Codice prenotazione: " + reservation.getCode() + "\nCodice biglietto:\n";
                for (Ticket t : reservation.getPassengers()) {
                    s += t.getName() + " " + t.getSurname() + " " + t.getCode() + "\n";
                }
                controller.sendMail(reservation.getEmail(), "ACQUISTO BIGLIETTO", reservation.printReservation("&%") + "&%" + reservation.printTickets("&%"));
                JOptionPane.showMessageDialog(home, "Congratulazioni!\n" + "Prenotazione Effettuata\n" + s + "\nLe è stata inviata un'email di recupero.");
                System.out.println("OK FUNZIONA");
                home.returnHome();
            }
        });

    }

    private void initInfoGenerali() {
        infoGenerali.setLayout(new GridLayout(3, 3));
        infoGenerali.setOpaque(false);
        codice.setText(reservation.getCodeFlight());
        infoGenerali.add(codice);
        andata.setText(flight.getRoute().getDepartureCity() + " " + flight.getRoute().getDeparutreAirport());
        infoGenerali.add(andata);
        ritorno.setText(flight.getRoute().getDestinationCity() + " " + flight.getRoute().getDestinationAirport());
        infoGenerali.add(ritorno);
        
        SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
        String d = format1.format(flight.getDateDeparture().getTime());
        data.setText(d);
        infoGenerali.add(data);
        SimpleDateFormat format2 = new SimpleDateFormat("hh:mm a");
        String d2 = format2.format(flight.getDateDeparture().getTime());
        orario.setText(d2);
        infoGenerali.add(orario);
        prezzototale.setText("Prezzo totale: " + reservation.getTotalPrice() + "€");
        infoGenerali.add(prezzototale);
        email.setText(reservation.getEmail());
        infoGenerali.add(email);
        telefono.setText(reservation.getNumber());
        infoGenerali.add(new JLabel(""));
        infoGenerali.add(telefono);

    }

    private void initInfoPasseggero() throws IOException {

        info.setLayout(new BorderLayout());
        info.setOpaque(false);
        info.add(passeggeri, BorderLayout.NORTH);
        for (int i = 0; i < passengers.size(); i++) {
            passeggeri.addItem(passengers.get(i).getName() + " " + passengers.get(i).getSurname());
        }
        passeggeri.addActionListener(PassengerListener());

        riassunto.setText("");
        riassunto.append(passengers.get(0).printTicketWithoutCode("\n"));
        riassunto.append("\n\nATTENZIONE: il codice biglietto sarà valido solo al momento del check-in!");
        info.add(riassunto, BorderLayout.CENTER);
        riassunto.setEditable(false);
    }

    private void makeComponentsTrasparent() {
        home.trasparentButton(conferma);
        home.setallFont(conferma);
        riassuntoS.getViewport().setOpaque(false);
        home.setallFont(riassuntoS.getViewport());
        home.setallFont(infoGenerali);
        home.setallFont(info);
        riassunto.setOpaque(false);
        riassunto.setFont(new Font("Helvetica", Font.BOLD, 15));
    }

    private ActionListener PassengerListener() {
        ActionListener evento = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                riassunto.setText(passengers.get(passeggeri.getSelectedIndex()).printTicketWithoutCode("\n"));
                riassunto.append("\n\nATTENZIONE: codice biglietto verrà erogato solo al momento del check-in!");
            }
        };
        return evento;
    }
}
