package gui;

import controller.FacadeControllerClient;
import java.awt.*;
import com.toedter.calendar.JDateChooser;

import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import objects.Flight;
import objects.HoldLuggage;
import objects.Insurance;
import objects.Meal;
import objects.Reservation;
import objects.Ticket;

/**
 *
 * @author andreacavagna
 */
public class HomeFrame extends JFrame {

    private final int WEIGHT = 800;
    private final int HEIGHT = 600;

    private FacadeControllerClient controller;
    private int npasseggeri;
    private String codeflight;
    private double priceflight;
    private ArrayList<Meal> meals;
    private ArrayList<Insurance> insurances;
    private ArrayList<HoldLuggage> luggages;
    private ArrayList<Ticket> passengers;
    private Meal[] allmeals;
    private Insurance[] allinsurances;
    private HoldLuggage[] allholdluggages;
    private Reservation reservation;
    private Flight flighttmp;

    private final String DEFAULT = "Benvenuti in Blue Airline";
    final boolean shouldFill = true;
    final boolean RIGHT_TO_LEFT = false;

    JPanel panel2 = new JPanel();
    JButton checkin = new JButton("Check in");
    JButton amministrazione = new JButton("Amministrazione");
    JLabel titolo = new JLabel();
    JLabel logo = new JLabel();
    JLabel sfondo;
    JLabel notifiche;
    HomeFrame frame;
    JLabel a1 = new JLabel("\t\t");

    JLabel c1 = new JLabel("\t\t");
    JButton modificavolo = new JButton("Modifica volo");
    

    JButton order = new JButton("Prenota Volo");
    JLabel mb1 = new JLabel("\t\t");

    JLabel scritta = new JLabel("BENVENUTO IN BLUEAIRLINE");

    public HomeFrame(String title, FacadeControllerClient c) throws HeadlessException {
        this.frame = this;
        this.controller = c;
        setTitle(title);
        setSize(WEIGHT, HEIGHT);
        setMinimumSize(new Dimension(WEIGHT, HEIGHT));
        this.setResizable(false);
        initComponents();
        
        this.passengers = new ArrayList<>();
        this.allmeals = this.controller.getAllMeals();
        this.allholdluggages = this.controller.getAllHoldLuggages();
        this.allinsurances = this.controller.getAllInsurances();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(sfondo);
        notifiche.setFont(new Font("Helvetica", Font.BOLD, 15));
        notifiche.setForeground(Color.white);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = this.getSize();
        this.setLocation((screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2); 
        this.setVisible(true);
    }

    private void initComponents() {

        ImageIcon immagine = new ImageIcon("immagini/Sfondo.jpeg");
        sfondo = new JLabel(scalaImmagine(immagine, WEIGHT + 3, HEIGHT + 3));
        sfondo.setLayout(new BorderLayout());
        sfondo.add(homePanel());
        notifiche = new JLabel("Home Page", SwingConstants.CENTER);

        frame.setallFont(notifiche);

        sfondo.add(notifiche, BorderLayout.SOUTH);
        sfondo.add(Menu(), BorderLayout.NORTH);
        add(sfondo);
        sfondo.addMouseMotionListener(new MouseAdapter() {

            @Override
            public void mouseMoved(MouseEvent e) {

                notifiche.setText(DEFAULT);
            }

        });

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    private JPanel homePanel() {

        JPanel homepanel = new JPanel();

        homepanel.setOpaque(false);

        homepanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        homepanel.add(scritta, new GridBagConstraints(3, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 300, 40));
        scritta.setFont(new Font("Papyrus", Font.BOLD, 45));
        scritta.setForeground(Color.blue);
        homepanel.add(order, new GridBagConstraints(3, 2, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 200, 40));
        homepanel.add(a1, new GridBagConstraints(2, 2, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 200, 40));
        homepanel.add(modificavolo, new GridBagConstraints(3, 6, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 200, 40));
        homepanel.add(mb1, new GridBagConstraints(2, 4, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 200, 40));
        homepanel.add(checkin, new GridBagConstraints(3, 4, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 225, 40));
        homepanel.add(c1, new GridBagConstraints(2, 6, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 200, 40));
        this.trasparentButton(order);
        this.setallFont(order);
        modificavolo.setVisible(false);
        this.trasparentButton(modificavolo);
        this.setallFont(modificavolo);
        this.trasparentButton(checkin);
        this.setallFont(checkin);

        order.addActionListener(nuovaPrenotazione());
        order.addMouseMotionListener(new MouseAdapter() {

            @Override
            public void mouseMoved(MouseEvent e) {

                notifiche.setText("Clicca per effettuare una nuova prenotazione.");
            }

        });

        checkin.addMouseMotionListener(new MouseAdapter() {

            @Override
            public void mouseMoved(MouseEvent e) {

                notifiche.setText("Clicca per effettuare il check in del tuo volo.");
            }
            
        });

        checkin.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String[] options = {"Check-in"};
                JPanel panel = new JPanel();
                JLabel lbl = new JLabel("Insert the Ticket code: ");
                JTextField txt = new JTextField(10);
                panel.add(lbl);
                panel.add(txt);
                ImageIcon immagine = new ImageIcon("immagini/logo2.png");
                immagine = scalaImmagine(immagine, 60, 60);
                int selectedOption = JOptionPane.showOptionDialog(null, panel, "EFFETTUA IL CHECK-IN", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, immagine, options, options[0]);

                if (selectedOption == 0) {
                    String codeTicket = txt.getText();
                    Ticket tp = new Ticket(codeTicket);
                    tp = controller.getTicket(tp);
                    if (tp != null) {
                        String[] option = {"o"};
                        if (controller.isCheckIn(tp)) {
                            JOptionPane.showConfirmDialog(panel, "Il check-in è stato già effettuato.", null, JOptionPane.CLOSED_OPTION);
                        } else {
                            tp = controller.checkIn(tp);
                            JOptionPane.showConfirmDialog(panel, "Check-in effettuato. Riceverà una mail con la conferma del check-in.\n" + tp.printTicketPassenger("\n"), null, JOptionPane.CLOSED_OPTION);
                            Ticket tpt=controller.getTicket(tp);
                            Reservation t=controller.getReservation(new Reservation(tpt.getCodeReservation()));
                            controller.sendMail(t.getEmail(), "CHECK-IN EFFETTUATO",  tpt.printTicketPassenger("&%"));
              
                        }
                    } else {
                        JOptionPane.showConfirmDialog(panel, "Errore inserimento codice biglietto", null, JOptionPane.CLOSED_OPTION);
                    }
                    notifiche.setText(DEFAULT);
                } else {
                    notifiche.setText("Operazione cancellata dall'utente");
                }
            }

        });

        homepanel.setOpaque(false);

        return homepanel;
    }

    public ImageIcon scalaImmagine(ImageIcon immagine, int lunghezza, int altezza) {
        return new ImageIcon(immagine.getImage().getScaledInstance(lunghezza, altezza, java.awt.Image.SCALE_SMOOTH));
    }

    private JMenuBar Menu() {
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;
        JMenuItem contattaci;

        menuBar = new JMenuBar();

        menu = new JMenu("Home");

        menu.getAccessibleContext().setAccessibleDescription("Return to the Home Page");
        menuBar.add(menu);

        menuItem = new JMenuItem("Home", KeyEvent.VK_H);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("Return to the Home Page");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dialogResult = JOptionPane.showConfirmDialog(frame, "Proseguendo potresti perdere\n" + "i dati inseriti fino ad ora.\n" + "Vuoi proseguire?", "Avvertenza", JOptionPane.YES_NO_OPTION);
                if (dialogResult == 0) {
                    returnHome();
                    notifiche.setText(DEFAULT);
                } else {
                    notifiche.setText("Operazione cancellata dall'utente");
                }
            }

        });
        menuItem.addMouseMotionListener(new MouseAdapter() {

            @Override
            public void mouseMoved(MouseEvent e) {

                notifiche.setText("Premere Home per tornare alla Home Page (ATTENZIONE, possibile perdita dati).");
            }

        });
        menu.add(menuItem);

        contattaci = new JMenuItem("Contattaci", KeyEvent.VK_C);
        contattaci.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        contattaci.getAccessibleContext().setAccessibleDescription("Contattaci");
        contattaci.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] options = {"SEND"};
                JPanel panel = new JPanel();
                JLabel lbl = new JLabel("Send your opinion here : ");
                JTextArea txt = new JTextArea(20, 30);
                panel.add(lbl);
                panel.add(txt);
                ImageIcon immagine = new ImageIcon("immagini/logo2.png");
                immagine = scalaImmagine(immagine, 150, 150);
                int selectedOption = JOptionPane.showOptionDialog(null, panel, "CONTATTACI", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, immagine, options, options[0]);

                if (selectedOption == 0) {
                    controller.sendMail("thetablueairline@gmail.com", "OPINIONE ANONIMA", txt.getText());
                    //inserire qui quello che deve fare in caso di contattaci!
                    //frame.returnHome();//Eliminare il ritorno a HOme Frame o no?
                    notifiche.setText(DEFAULT);
                } else {
                    notifiche.setText("Operazione cancellata dall'utente");
                }
            }

        });
        contattaci.addMouseMotionListener(new MouseAdapter() {

            @Override
            public void mouseMoved(MouseEvent e) {

                notifiche.setText("Premere Contattaci per inviarci una mail(ATTENZIONE, possibile perdita dati).");
            }

        });
        menu.add(contattaci);

        return menuBar;
    }

    private ActionListener nuovaPrenotazione() {
        ActionListener evento = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshGUI(new ReservationPanel(controller, frame));
                notifiche.setText("Aggiungi una nuova Prenotazione: ");
            }
        };
        return evento;
    }

    public void returnHome() {
        passengers.clear();
        refreshGUI(homePanel());
    }

    public void setNpasseggeri(int npasseggeri) {
        this.npasseggeri = npasseggeri;
    }

    public void setCodeflight(String codeflight) {
        this.codeflight = codeflight;
    }

    public int getNpasseggeri() {
        return npasseggeri;
    }

    public String getCodeflight() {
        return codeflight;
    }

    public ArrayList<Ticket> getPassengers() {
        return passengers;
    }

    public void addPassenger(String ID, String name, String surname, int nseat, int classe, String codflight, double priceticket) {
        Ticket t = new Ticket(ID, name, surname, nseat, classe, codflight, priceticket, meals, insurances, luggages);
        this.passengers.add(t);
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    protected void refreshGUI(final JPanel displayPanel) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ImageIcon immagine = new ImageIcon("immagini/Sfondo.jpeg");
                sfondo.removeAll();
                sfondo.setIcon(scalaImmagine(immagine, WEIGHT + 3, HEIGHT + 3));
                sfondo.add(notifiche, BorderLayout.SOUTH);
                sfondo.add(Menu(), BorderLayout.NORTH);
                sfondo.add(displayPanel, BorderLayout.CENTER);
                sfondo.revalidate();
                sfondo.repaint();

            }
        });
    }

    public void trasparentButton(final JButton b) {
        b.setOpaque(false);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        final Color color = Color.white;
        b.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                b.setForeground(Color.BLUE);
            }

            public void mouseReleased(MouseEvent e) {
                b.setForeground(color);
            }
        });
    }

    public void trasparentButton(final JButton b, String icon) {
        b.setOpaque(false);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        final ImageIcon im = (ImageIcon) b.getIcon();
        final ImageIcon img = new ImageIcon(icon);
        final Color color = b.getForeground();
        b.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                b.setForeground(Color.BLUE);
                b.setIcon(img);

            }

            public void mouseReleased(MouseEvent e) {
                b.setForeground(Color.white);
                b.setIcon(im);
                b.setForeground(color);
            }
        });
    }

    public void trasparentTextField(final JTextField t) {
        t.setBackground(new Color(0, 0, 0, 0));
        t.setOpaque(false);
    }

    public void trasparentJDateChooser(final JDateChooser d) {
        d.setBackground(new Color(0, 0, 0, 0));
        d.setOpaque(false);
    }

    public void setallFont(final JComponent c) {

        if (c instanceof JTextField) {
            JTextFieldSelected((JTextField) c);
        }
        if (c.getComponentCount() == 0) {
            if (!(c instanceof JComboBox)) {
                if (c instanceof JLabel || c instanceof JButton) {
                    c.setFont(new Font("Helvetica", Font.BOLD, 25));
                } else {
                    c.setFont(new Font("Helvetica", Font.BOLD, 15));

                }
            }
        }

        for (Component tmp : c.getComponents()) {
            if (tmp instanceof JTextField) {
                JTextFieldSelected((JTextField) tmp);
            }
            if (tmp instanceof JLabel || tmp instanceof JButton) {
                tmp.setFont(new Font("Helvetica", Font.BOLD, 25));
            } else {
                tmp.setFont(new Font("Helvetica", Font.BOLD, 15));
            }
            if (tmp instanceof JPanel) {
                this.setallFont((JComponent) tmp);
            }
            tmp.setForeground(Color.white);
        }
        c.setForeground(Color.white);
    }

    public boolean PassengerMeno() {
        this.npasseggeri--;
        return npasseggeri != 0;
    }

    public void setPriceflight(double priceflight) {
        this.priceflight = priceflight;
    }

    public double getPriceflight() {
        return priceflight;
    }

    public Flight getFlighttmp() {
        return flighttmp;
    }

    public void setFlighttmp(Flight flighttmp) {
        this.flighttmp = flighttmp;
    }

    public void setMeals(ArrayList<Meal> meals) {
        this.meals = meals;
    }

    public void setInsurances(ArrayList<Insurance> insurances) {
        this.insurances = insurances;
    }

    public void setLuggages(ArrayList<HoldLuggage> luggages) {
        this.luggages = luggages;
    }

    public ArrayList<Meal> getMeals() {
        return meals;
    }

    public ArrayList<Insurance> getInsurances() {
        return insurances;
    }

    public ArrayList<HoldLuggage> getLuggages() {
        return luggages;
    }

    public Meal[] getAllmeals() {
        return allmeals;
    }

    public Insurance[] getAllinsurances() {
        return allinsurances;
    }

    public HoldLuggage[] getAllholdluggages() {
        return allholdluggages;
    }

    public void addMeal(String v) {
        for (Meal m : allmeals) {
            if (m.getCode().equals(v)) {
                meals.add(m);
                break;
            }
        }
    }

    public void addInsurance(String v) {
        for (Insurance i : allinsurances) {
            if (i.getCode().equals(v)) {
                insurances.add(i);
                break;
            }
        }
    }

    public void addHoldLuggage(String v) {
        for (HoldLuggage h : allholdluggages) {
            if (h.getCode().equals(v)) {
                luggages.add(h);
                break;
            }
        }
    }

    public void JTextFieldSelected(final JTextField f) {
        f.addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {
                f.setSelectionStart(0);
                f.setSelectionEnd(f.getText().length());
            }

        });

    }

    public void noMinorZero(final JTextField t, final JButton meno, final JButton piu) {
        t.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (Integer.parseInt(t.getText()) == 0) {
                    meno.setEnabled(false);
                } else {
                    meno.setEnabled(true);
                }
            }
        });
        if (meno.isSelected() && Integer.parseInt(t.getText()) == 1) {
            meno.setEnabled(false);
        }
        if (piu.isSelected() && Integer.parseInt(t.getText()) == 0) {
            meno.setEnabled(true);
        }

    }

}
