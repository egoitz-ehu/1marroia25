package presentation;


import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

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
import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;

public class FlightBooking extends JFrame {
	

	private static final long serialVersionUID = 1L;
	private JPanel contentPane= null;
	private JLabel lblDepartCity = new JLabel("Departing city:");
	private JLabel lblArrivalCity = new JLabel("Arrival City");
	private JLabel lblYear = new JLabel("Year:");
	private JLabel lblRoomType = new JLabel("Room Type:");
	private JLabel lblMonth = new JLabel("Month:");
	private JLabel lblDay = new JLabel("Day:");;
	private JLabel jLabelResult = new JLabel();
	private JLabel searchResult =   new JLabel();
	private JTextField day = null;
	private JComboBox<String> months = null;
	private DefaultComboBoxModel<String> monthNames = new DefaultComboBoxModel<String>();

	private JTextField year = null;
	
	private JRadioButton bussinesTicket = null;
	private JRadioButton firstTicket = null;
	private JRadioButton touristTicket = null;

	private ButtonGroup fareButtonGroup = new ButtonGroup();  
	
	private JButton lookforFlights = null;
	
	//private JList<ConcreteFlight> flightList = null;
	private JButton bookFlight = null;
	
	
	private Collection<ConcreteFlight> concreteFlightCollection;
	
	private FlightManager businessLogic;  //  @jve:decl-index=0:;
	
	
	private ConcreteFlight selectedConcreteFlight;
	
	
	private JComboBox<String> comboBoxDeparting = null;
	private DefaultComboBoxModel<String> departingCitiesModel = new DefaultComboBoxModel<String>();
	
	private JComboBox<String> comboBoxArrival = null;
	private DefaultComboBoxModel<String> arrivalCitiesModel = new DefaultComboBoxModel<String>();
	
	private JComboBox<ConcreteFlight> comboBoxFlights = null;
	private DefaultComboBoxModel<ConcreteFlight> flightInfoModel = new DefaultComboBoxModel<ConcreteFlight>();
	

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FlightBooking frame = new FlightBooking();
					frame.setBusinessLogic(new FlightManager());
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
	private  FlightBooking() {
		
		setTitle("Book Flight");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 353);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblDepartCity = new JLabel("Depart City");
		lblDepartCity.setBounds(21, 11, 103, 16);
		contentPane.add(lblDepartCity);
		
		
		lblYear = new JLabel("Year:");
		lblYear.setBounds(21, 62, 33, 16);
		contentPane.add(lblYear);
		
		lblMonth = new JLabel("Month:");
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
		
		lblDay = new JLabel("Day:");
		lblDay.setBounds(291, 62, 38, 16);
		contentPane.add(lblDay);
		
		day = new JTextField();
		day.setText("23");
		day.setBounds(331, 57, 50, 26);
		contentPane.add(day);
		day.setColumns(10);
		
		lblRoomType = new JLabel("Seat Type:");
		lblRoomType.setBounds(21, 242, 84, 16);
		contentPane.add(lblRoomType);
		
		
		bussinesTicket = new JRadioButton("Business");
		fareButtonGroup.add(bussinesTicket);
		bussinesTicket.setBounds(99, 238, 101, 23);
		contentPane.add(bussinesTicket);
		bussinesTicket.setEnabled(false);
		
		firstTicket = new JRadioButton("First");
		fareButtonGroup.add(firstTicket);
		firstTicket.setBounds(202, 238, 77, 23);
		contentPane.add(firstTicket);
		firstTicket.setEnabled(false);
		
		touristTicket = new JRadioButton("Tourist");
		fareButtonGroup.add(touristTicket);
		touristTicket.setBounds(278, 238, 77, 23);
		contentPane.add(touristTicket);
		touristTicket.setEnabled(false);
		
		comboBoxDeparting = new JComboBox<String>();
		comboBoxDeparting.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				arrivalCitiesModel.removeAllElements();
				arrivalCitiesModel.addAll(businessLogic.getArrivalCitiesFrom(departingCitiesModel.getSelectedItem().toString()));
				if(arrivalCitiesModel.getSize()>0) {
					comboBoxArrival.setSelectedIndex(0);
				}
			}
		});
		comboBoxDeparting.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				if(departingCitiesModel.getSize()==0)
					departingCitiesModel.addAll(businessLogic.getAllDepartingCities());
			}
		});
		comboBoxDeparting.setBounds(99, 8, 243, 22);
		contentPane.add(comboBoxDeparting);
		comboBoxDeparting.setModel(departingCitiesModel);
		
		comboBoxArrival = new JComboBox<String>();
		comboBoxArrival.setBounds(99, 29, 243, 22);
		contentPane.add(comboBoxArrival);
		comboBoxArrival.setModel(arrivalCitiesModel);
		
		comboBoxFlights = new JComboBox<ConcreteFlight>();
		comboBoxFlights.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(flightInfoModel.getSelectedItem()!=null) {
					selectedConcreteFlight = (ConcreteFlight) flightInfoModel.getSelectedItem();
					fareButtonGroup.clearSelection();
					bussinesTicket.setEnabled(selectedConcreteFlight.getBussinesNumber()>0);
					firstTicket.setEnabled(selectedConcreteFlight.getFirstNumber()>0);
					touristTicket.setEnabled(selectedConcreteFlight.getTouristNumber()>0);
					bookFlight.setText("");
					bookFlight.setEnabled(false);
				}
			}
		});
		comboBoxFlights.setBounds(57, 174, 324, 22);
		contentPane.add(comboBoxFlights);
		comboBoxFlights.setModel(flightInfoModel);
		
		lookforFlights = new JButton("Look for Concrete Flights");
		lookforFlights.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//bookFlight.setEnabled(true);
				//flightInfo.clear();
				flightInfoModel.removeAllElements();
				bookFlight.setText("");
				
				fareButtonGroup.clearSelection();
				bussinesTicket.setEnabled(false);
				firstTicket.setEnabled(false);
				touristTicket.setEnabled(false);
				bookFlight.setEnabled(false);
				
				try {
					int dayMaxNumber;
					switch(months.getSelectedIndex()) {
						case 0: case 2: case 4: case 6: case 7: case 9: case 11: 
							dayMaxNumber=31;
							break;
						case 1:
							if(Integer.parseInt(year.getText()) % 4 == 0 && ((Integer.parseInt(year.getText()) % 100 != 0) || (Integer.parseInt(year.getText()) % 400 == 0)))
								dayMaxNumber=29;
							else
								dayMaxNumber=28;
							break;
						case 3: case 5: case 8: case 10:
							dayMaxNumber=30;
							break;
						default:
							dayMaxNumber=0;
								
					}
					if(Integer.parseInt(day.getText())<1 || Integer.parseInt(day.getText())>dayMaxNumber || Integer.parseInt(year.getText())<0)
							throw new IllegalArgumentException("The year or the day is invalid.");
					
					java.util.Date date =newDate(Integer.parseInt(year.getText()),months.getSelectedIndex(),Integer.parseInt(day.getText()));
					 
					concreteFlightCollection=businessLogic.getConcreteFlights(departingCitiesModel.getSelectedItem().toString(),arrivalCitiesModel.getSelectedItem().toString(),date);
					Iterator<ConcreteFlight> flights=concreteFlightCollection.iterator();
					while (flights.hasNext()) 
						flightInfoModel.addElement(flights.next()); 
					if (concreteFlightCollection.isEmpty()) searchResult.setText("No flights in that city in that date");
					else {
						searchResult.setText("Choose an available flight in this list:");
						comboBoxFlights.setSelectedIndex(0);
					}
					searchResult.setForeground(Color.BLACK);
				} catch (NumberFormatException e1) {
					searchResult.setText("The day and year must be whole numbers");
					searchResult.setForeground(Color.RED);
					System.out.println("The day and year must be whole numbers");
				} catch (NullPointerException e2) {
					searchResult.setText("It is necessary to select a departing and arrival city");
					searchResult.setForeground(Color.RED);
					System.out.println("It is necessary to select a departing and arrival city");
				} catch (IllegalArgumentException e3) {
					searchResult.setText(e3.getMessage());
					searchResult.setForeground(Color.RED);
					System.out.println(e3.getMessage());
				}
			}
		});
		lookforFlights.setBounds(81, 90, 261, 40);
		contentPane.add(lookforFlights);	
		
		jLabelResult = new JLabel("");
		jLabelResult.setBounds(99, 287, 243, 16);
		contentPane.add(jLabelResult);
		
		
		bookFlight = new JButton("");
		bookFlight.setEnabled(false);
		bookFlight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int num=0;
				boolean error=false;
				if (bussinesTicket.isSelected()) { 
				    num=selectedConcreteFlight.getBussinesNumber();
					if (num>0) selectedConcreteFlight.setBusinessNumber(num-1); else error=true; 
				}
				if (firstTicket.isSelected()) {
					num=selectedConcreteFlight.getFirstNumber();
					if (num>0) selectedConcreteFlight.setFirstNumber(num-1); else error=true;
				}
				if (touristTicket.isSelected()) {
					num=selectedConcreteFlight.getTouristNumber();
					if (num>0) selectedConcreteFlight.setTouristNumber(num-1); else error=true;
				}
				if (error) bookFlight.setText("Error: There were no seats available!");
				else bookFlight.setText("Booked. #seat left: "+(num-1));
				bookFlight.setEnabled(false);
				fareButtonGroup.clearSelection();
				bussinesTicket.setEnabled(false);
				firstTicket.setEnabled(false);
				touristTicket.setEnabled(false);
				flightInfoModel.removeAllElements();
			}
		});
		
		ActionListener radioButtonListner = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bookFlight.setEnabled(true);
				bookFlight.setText("Book: "+selectedConcreteFlight);
			}
		};
		
		bussinesTicket.addActionListener(radioButtonListner);
		firstTicket.addActionListener(radioButtonListner);
		touristTicket.addActionListener(radioButtonListner);
		
		
		bookFlight.setBounds(31, 273, 399, 40);
		contentPane.add(bookFlight);

		year = new JTextField();
		year.setText("2025");
		year.setBounds(57, 57, 50, 26);
		contentPane.add(year);
		year.setColumns(10);
		
		lblArrivalCity.setBounds(21, 39, 84, 16);
		contentPane.add(lblArrivalCity);
		
		searchResult.setBounds(57, 130, 314, 16);
		contentPane.add(searchResult);
	}
}  //  @jve:decl-index=0:visual-constraint="18,9"
