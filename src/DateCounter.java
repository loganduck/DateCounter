import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class DateCounter {
	private static JFrame frame;
	private static JTextArea startDate, endDate;
	private JButton button;
	private static JLabel label;
	
	private String currentMonth = (Calendar.getInstance().get(Calendar.MONTH) + 1) + "";
	private String currentDayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "";
	private String currentYear = (Calendar.getInstance().get(Calendar.YEAR) + "").substring(2);
	
	private Font font = new Font("Calibri", Font.BOLD, 20);
	
	public DateCounter() {
		frame = new JFrame();
		frame.setLayout(null);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(500, 150));
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		
		startDate = new JTextArea();
		startDate.setName("startDate");
		startDate.setLineWrap(false);
		startDate.setFont(font);
		startDate.setBounds(10, 10, 120, 25);
		frame.add(startDate);
		
		endDate = new JTextArea();
		endDate.setName("endDate");
		endDate.setLineWrap(false);
		endDate.setFont(font);
		endDate.setText(currentMonth + "/" + currentDayOfMonth + "/" + currentYear);
		endDate.setBounds(10, 45, 120, 25);
		frame.add(endDate);
		
		button = new JButton("Check");
		button.setFont(new Font("Calibri", Font.BOLD, 17));
		button.setBounds(20, 80, 98, 30);
		button.setHorizontalAlignment(SwingUtilities.CENTER);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (!verifiedDateFormat(startDate) || ! verifiedDateFormat(endDate)) {
					System.out.println("Incorrect Format: MM/dd/yyyy");
					System.exit(1);
				}
				
				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
				Date initDate = null;
				Date checkDate = null;
				
				try {
					initDate = formatter.parse(startDate.getText());
					checkDate = formatter.parse(endDate.getText());
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				
				Calendar start = Calendar.getInstance();
				start.setTime(initDate);
				Calendar end = Calendar.getInstance();
				end.setTime(checkDate);

				int distance = 0;
				for (; start.before(end); start.add(Calendar.DATE, 1), start.getTime()) {
					distance++;
				}
				
				label.setText(distance + "");
			}
			
		});
		frame.add(button);
		
		label = new JLabel("0");
		label.setFont(new Font("Calibri", Font.BOLD, 90));
		label.setBounds(140, 10, 280, 78);
		label.setForeground(Color.WHITE);
		frame.add(label);
		
		frame.setVisible(true);
	}
	
	/**
	 * Most format checks are complete, just don't be silly and enter a date that does not even exist.
	 * @param date - format to be checked.
	 * @return whether the format of the text area is acceptable.
	 */
	public static boolean verifiedDateFormat(JTextArea date) {
		if (date.getText().equals("")) {
			return false;	
		}
		
		String dateMonth = date.getText().substring(0, 2);
		String dateDayOfMonth = date.getText().substring(3, 5);
		String dateYear = date.getText().substring(6, 8);

		if (date.getText().length() != 8) {
			System.out.println(1);
			return false;
		}
		
		if (!dateMonth.matches("^[0-9][0-9]") || !dateDayOfMonth.matches("^[0-9][0-9]") || !dateYear.matches("^[0-9][0-9]") || dateMonth.equals("00")) {
			System.out.println(2);
			return false;
		}
		
		if (!date.getText().substring(2, 3).equals("/") || !date.getText().substring(5, 6).equals("/")) {
			System.out.println(3);
			return false;
		}
		
		if (Integer.parseInt(dateMonth) > 12) {
			System.out.println(5);
			return false;
		}
		
		return true;
	}
}