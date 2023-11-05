package Calculator;

import java.util.ArrayList;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;


// Created by MJ
public class Calculator {
	private JFrame frame;
	private static JTextField textfield;
    private JPanel panel;
    private static ArrayList<Double> numbers = new ArrayList<>();
    private static String operator;
    private static String plus = "+";
    private static String minus = "-";
    private static String mult = "*";
    private static String div = "/";
    private static String dot = ".";
	private static String clear = "C";
	private static String equal = "=";
	private static String acceptedChars = "0123456789.+-*/=";
	public Calculator() {
		initialize();
	}

	public void initialize() {
		frame = new JFrame("Calculator");
		frame.setSize(340, 600);
		textfield = new JTextField();
		textfield.setLayout(new BorderLayout());
		textfield.setPreferredSize(new Dimension(100, 50));
		

		// Add all buttons
		ArrayList<JButton> buttons = buttons();
		panel = new JPanel(new GridLayout(5, 4));
		
		for (JButton b : buttons) {
			b.setFocusable(false);

			panel.add(b);	
		}
		
				
		buttonClicked(buttons, textfield);
		keyTyped(textfield);
		enterClicked(textfield);		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(textfield, BorderLayout.NORTH);
		frame.add(panel, BorderLayout.CENTER);
		frame.setVisible(true);
	}
	
	public static ArrayList<JButton> buttons() {
		ArrayList<JButton> buttons = new ArrayList<>();
		
		
		buttons.add(new JButton(String.valueOf(1)));
		buttons.add(new JButton(String.valueOf(2)));
		buttons.add(new JButton(String.valueOf(3)));
		buttons.add(new JButton(plus));
		buttons.add(new JButton(String.valueOf(4)));
		buttons.add(new JButton(String.valueOf(5)));
		buttons.add(new JButton(String.valueOf(6)));
		buttons.add(new JButton(minus));
		buttons.add(new JButton(String.valueOf(7)));
		buttons.add(new JButton(String.valueOf(8)));
		buttons.add(new JButton(String.valueOf(9)));
		buttons.add(new JButton(mult));
		buttons.add(new JButton(dot));
		buttons.add(new JButton(String.valueOf(0)));
		buttons.add(new JButton(equal));
		buttons.add(new JButton(div));
		buttons.add(new JButton(clear));

		return buttons;
	}
	
	public static void buttonClicked(ArrayList<JButton> buttons, JTextField textfield) {
		for (JButton button : buttons) {
			String buttonText = button.getText();
				button.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// Check if last input was not from keyboard
	                    String currentText = textfield.getText();
	                    
	                    handle(buttonText, currentText);
					}
				});
		}
	}
	
	public static void keyTyped(JTextField textfield) {
		textfield.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
                String key = String.valueOf((char) e.getKeyChar());
	            if (acceptedChars.contains(key)) {
	                String currentText = textfield.getText();

                	handle(key, currentText);

                	e.consume();
	            } else {
	            	
	            	e.consume();
	            	
	            }
			}
		});
	}
	
	public static void handle(String input, String currentText) {
		int len = currentText.length();

		if (isDigit(currentText) && "+-*/=".contains(input)) {
			numbers.add(Double.parseDouble(currentText));

			if ("+-*/".contains(input)) {
				operator = input;
			}
		} 
		
		if (len > 0 && ("+-*/".contains(input) || "+-*/".contains(String.valueOf(currentText.charAt(len - 1))))) {
			textfield.setText(input);
		} else if (input.equals(equal)) {
			calculate(numbers);
		} else if (input.equals(clear)) {
			textfield.setText("");
			numbers.clear();
		} else {
			textfield.setText(currentText + input);
		}
		
			
	}
	
	public static void calculate(ArrayList<Double> numbers) {		
		double result = 0.0;
	    double operand1 = numbers.get(0);
	    double operand2 = numbers.get(1);
	    boolean canDivide = true;
		if (operator.equals(plus)) {
			result = operand1 + operand2;
		} else if (operator.equals(minus)) {
			result = operand1 - operand2;
		} else if (operator.equals(div)) {
			if (operand2 != 0) {
				result = operand1 / operand2;
			} else {
				canDivide = false;
			}
		} else if (operator.equals(mult)) {
			result = operand1 * operand2;
		} 
		
		if (canDivide == true) {
			if (result == (int) result) {
				textfield.setText(Integer.toString((int) result));
				} else {
					textfield.setText(String.valueOf(result));
				}
				numbers.clear();
		} else {
			textfield.setText("Cannot divide by zero");
			numbers.clear();

		}
		
		
		 
			
	}
	
	public static void enterClicked(JTextField textfield) {
		 textfield.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	handle(equal, textfield.getText());
	            }
	        });
	}
	
	public static boolean isDigit(String input) {
		return input.length() > 0 && Character.isDigit(input.charAt(input.length()- 1));
	}


}