package presentation;


import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;

import businessLogic.FlightManager;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.border.EmptyBorder;

import domain.ConcreteFlight;

public class FlightBooking23 extends JFrame {
	

	private static final long serialVersionUID = 1L;
	private JPanel contentPane= null;
	private JLabel lblDepartCity = new JLabel("Departing city:");
	private JLabel lblArrivalCity = new JLabel("Arrival City");
	private JLabel lblYear = new JLabel("Year:");
	private JLabel lblRoomType = new JLabel("Seat Type:");
	private JLabel lblMonth = new JLabel("Month:");
	private JLabel lblDay = new JLabel("Day:");;
	private JLabel jLabelResult = new JLabel();
	private JLabel searchResult =   new JLabel();
	private JTextField day = null;
	
	
	
	private JComboBox<String> departComboBox;
	private JComboBox<String> arrivalComboBox;

	private JComboBox<String> months = null;
	
	private DefaultComboBoxModel<String> departCities = new DefaultComboBoxModel<String>();
	private DefaultComboBoxModel<String> arrivalCities = new DefaultComboBoxModel<String>();
	private DefaultComboBoxModel<String> monthNames = new DefaultComboBoxModel<String>();

	private JTextField year = null;
	
	private JRadioButton bussinesTicket = null;
	private JRadioButton firstTicket = null;
	private JRadioButton touristTicket = null;

	private ButtonGroup fareButtonGroup = new ButtonGroup();  
	
	private JButton lookforFlights = null;
	private DefaultListModel<ConcreteFlight> flightInfo = new DefaultListModel<ConcreteFlight>();

	
	private JList<ConcreteFlight> flightList = null;
	private JButton bookFlight = null;
	
	

	
	private Collection<ConcreteFlight> concreteFlightCollection;
	
	private FlightManager businessLogic;  //  @jve:decl-index=0:
	private JScrollPane flightListScrollPane = new JScrollPane();;
	
	
	private ConcreteFlight selectedConcreteFlight;
	
	

	/**
	 * Launch the application.  
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FlightBooking23 frame = new FlightBooking23(new FlightManager());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	//Custom operations
	public void setBusinessLogic(FlightManager g) {businessLogic = g;}
	
	private Date newDate(int year,int month,int day) {

	     Calendar calendar = Calendar.getInstance();
	     calendar.set(year, month, day,0,0,0);
	     calendar.set(Calendar.MILLISECOND, 0);

	     return calendar.getTime();
	}
	/**
	 * Create the frame
	 * 
	 * @return void
	 */
	private  FlightBooking23(FlightManager g) {
		
		businessLogic = g;
		
		setTitle("Book Flight");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblDepartCity.setBounds(21, 11, 103, 16);
		contentPane.add(lblDepartCity);
		
		departComboBox = new JComboBox();
		departComboBox.setBounds(109, 7, 243, 27);
		contentPane.add(departComboBox);
		departCities.addAll(businessLogic.getAllDepartingCities());
		departComboBox.setModel(departCities);
		
		lblYear.setBounds(21, 62, 33, 16);
		contentPane.add(lblYear);
		
		lblMonth.setBounds(117, 62, 50, 16);
		contentPane.add(lblMonth);
	    
		months = new JComboBox<String>();
		months.setBounds(163, 58, 116, 27);
		contentPane.add(months);
		months.setModel(monthNames);
		
		monthNames.addElement("January");
		monthNames.addElement("February");
		monthNames.addElement("March");
		monthNames.addElement("April");
		monthNames.addElement("May");
		monthNames.addElement("June");
		monthNames.addElement("July");
		monthNames.addElement("August");
		monthNames.addElement("September");
		monthNames.addElement("October");
		monthNames.addElement("November");
		monthNames.addElement("December");
		months.setSelectedIndex(1);
		
		lblDay.setBounds(291, 62, 38, 16);
		contentPane.add(lblDay);
		
		day = new JTextField();
		day.setText("23");
		day.setBounds(331, 57, 50, 26);
		contentPane.add(day);
		day.setColumns(10);
		
		lblRoomType.setBounds(21, 90, 84, 16);
		contentPane.add(lblRoomType);
		
		
		
		bussinesTicket = new JRadioButton("Business");
		bussinesTicket.setSelected(true);
		fareButtonGroup.add(bussinesTicket);
		bussinesTicket.setBounds(99, 86, 101, 23);
		contentPane.add(bussinesTicket);
		
		firstTicket = new JRadioButton("First");
		fareButtonGroup.add(firstTicket);
		firstTicket.setBounds(192, 86, 77, 23);
		contentPane.add(firstTicket);
		
		touristTicket = new JRadioButton("Tourist");
		fareButtonGroup.add(touristTicket);
		touristTicket.setBounds(271, 86, 77, 23);
		contentPane.add(touristTicket);
		
		lookforFlights = new JButton("Look for Concrete Flights");
		lookforFlights.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bookFlight.setEnabled(true);
				flightInfo.clear();
				bookFlight.setText("");
				
				java.util.Date date =newDate(Integer.parseInt(year.getText()),months.getSelectedIndex(),Integer.parseInt(day.getText()));
				 
				concreteFlightCollection=businessLogic.getConcreteFlights(departCity.getText(),arrivalCity.getText(),date);
				for (ConcreteFlight f : concreteFlightCollection) { 
					System.out.println(f.toString());
					flightInfo.addElement(f); 
				}
				if (concreteFlightCollection.isEmpty()) searchResult.setText("No flights in that city in that date");
				else searchResult.setText("Choose an available flight in this list:");
			}
		});
		lookforFlights.setBounds(86, 118, 261, 40);
		contentPane.add(lookforFlights);	
		
		jLabelResult = new JLabel("");
		jLabelResult.setBounds(109, 180, 243, 16);
		contentPane.add(jLabelResult);
		
		flightList = new JList<ConcreteFlight>();
		flightList.setModel(flightInfo);
		flightList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
			public void valueChanged(javax.swing.event.ListSelectionEvent e) {
				if (e.getValueIsAdjusting()) return; // El evento se dispara dos veces: antes de cambiar el valor y una vez cambiado
													 // Interesa s�lo actuar una vez cambiado
				if (!flightList.isSelectionEmpty()){ // A este m�todo se le llama tambi�n cuando se hace un clear del JList, 
													 // por lo que podr�a estar la selecci�n vac�a y dar un error
					selectedConcreteFlight = (ConcreteFlight) flightList.getSelectedValue();
					bookFlight.setEnabled(true);
					bookFlight.setText("Book this flight: "+selectedConcreteFlight);  // TODO Auto-generated Event stub valueChanged()
				}
			}
		});
		
		flightListScrollPane.setBounds(new Rectangle(64, 199, 336, 71));
		flightListScrollPane.setViewportView(flightList);
		contentPane.add(flightListScrollPane);
		
		
		bookFlight = new JButton("");
		bookFlight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int num=0;
				boolean error=false;
				if (bussinesTicket.isSelected()) { 
					num=selectedConcreteFlight.getBusinessNumber();
					if (num>0) selectedConcreteFlight.setBusinessNumber(num-1); else error=true; 
				}
				else if (firstTicket.isSelected()) {
					num=selectedConcreteFlight.getFirstNumber();
					if (num>0) selectedConcreteFlight.setFirstNumber(num-1); else error=true;
				}
				else if (touristTicket.isSelected()) {
					num=selectedConcreteFlight.getTouristNumber();
					if (num>0) selectedConcreteFlight.setTouristNumber(num-1); else error=true;
				}
				if (error) bookFlight.setText("Error: There were no seats available!");
				else bookFlight.setText("Booked. #seat left: "+(num-1));
				bookFlight.setEnabled(false);
			}
		});
		bookFlight.setBounds(21, 282, 399, 40);
		contentPane.add(bookFlight);

		year = new JTextField();
		year.setText("2022");
		year.setBounds(57, 57, 50, 26);
		contentPane.add(year);
		year.setColumns(10);
		
		lblArrivalCity.setBounds(21, 39, 84, 16);
		contentPane.add(lblArrivalCity);
		
		searchResult.setBounds(86, 170, 314, 16);
		contentPane.add(searchResult);
		
		
		
		arrivalComboBox = new JComboBox();
		arrivalComboBox.setBounds(99, 35, 243, 27);
		contentPane.add(arrivalComboBox);
	}
}  //  @jve:decl-index=0:visual-constraint="18,9"

