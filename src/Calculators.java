/**
 * 
 * @author chinazom
 *
 */



import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;
import java.awt.BorderLayout;

public class Calculators extends JFrame {
	private static final long serialVersionUID = 1L;
public static void main (String[]args) {
		// create the frame and set the frame properties
	JFrame calculatorFrame = new JFrame();
	 calculatorFrame.setTitle("Calculator");
	 calculatorFrame.setSize(600, 350);
	 calculatorFrame.setLocation(200,100);
	 calculatorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 calculatorFrame.setVisible(true);
	 calculatorFrame.setBackground(Color.DARK_GRAY);
	 
	
	 
	 // creates five panels and add the panel to a parent panel
	 JPanel calculatorPanel = new JPanel ();
	 JPanel displayPanel = new JPanel ();
	//where initialization occurs:
	 JTextArea display = new JTextArea(5,5);
	 display.setColumns(50);
	String R = display.getText();
	 displayPanel.add(display);
	 
	 //String text=display.getText().toString();
	 //System.out.println(R);
	 
	 
	// displayPanel.setLayout(new GridLayout(5, 1));
	 JPanel numberPanel = new JPanel ();
	 numberPanel.setSize(200,300);
	 numberPanel.setLayout(new GridLayout(4, 3));
	 JPanel mathsPanel = new JPanel ();
	 mathsPanel.setLayout(new GridLayout(5, 2));
	 JPanel symbolPanel = new JPanel ();
	 symbolPanel.setLayout(new GridLayout(4, 3));
	 calculatorPanel.setLayout(new BorderLayout(20,10));
	 calculatorPanel.add(displayPanel,BorderLayout.NORTH);
	 calculatorPanel.add(numberPanel, BorderLayout.CENTER);
	 calculatorPanel.add(mathsPanel, BorderLayout.EAST);
	 calculatorPanel.add(symbolPanel, BorderLayout.WEST);
	 ButtonGroup buttons = new ButtonGroup();
	 
	
	// Add buttons to the number panel and get the string value
		  for (int i = 1; i <= 9; i++) {
			  JButton num = new JButton("" + i);
			  //,String.valueOf(i))
			  buttons.add(num);
			  numberPanel.add(num);
			 
			  num.addActionListener(new ActionListener()
			     {
			         public void actionPerformed(ActionEvent evt)
			         {
			        	 String val = ((JButton)evt.getSource()).getText();
			             display.setText(display.getText()+val);
			         }
			     });
		  }
		 JButton zero = new JButton("" + 0);
		 zero.addActionListener(new ActionListener()
	     {
	         public void actionPerformed(ActionEvent evt)
	         {
	        	String val = ((JButton)evt.getSource()).getText();
	            display.setText(display.getText()+val);
	         }
	     });
		 JButton equalTo = new JButton("=");
		  symbolPanel.add(equalTo);
		  equalTo.addActionListener(new ActionListener()
		     {
		         public void actionPerformed(ActionEvent evt)
		         {
		        	 
		        	 
		        	 //String val = (display.getText());//
		        	 
		        	
		        	 String val = "23+4*3";
		        	 
		        	 
		             
		        	 display.setText("");
		        	 
		        	 String input = val;
		        	 
		        	 System.out.println();
		        	 
		        	 
		        	 /*while(val.indexOf("*") != -1) {
		            	 
		            	 
		            	 int currentOperatorIndex = val.indexOf("*");
		            	 
		            	 System.out.println(arg0);
		            	 
		            	 double leftValue = Double.parseDouble(val.substring(currentOperatorIndex - 1, currentOperatorIndex)); 
		            	 double rightValue = Double.parseDouble(val.substring(currentOperatorIndex + 1)); 
		            	 
		            	 
		            	 double result = leftValue * rightValue;
		            	 
		            	 // Test for left side
		            	 String leftSide = "";
		            	 String righSide = "";
		            	 try {
		            		 leftSide = val.substring(0, currentOperatorIndex - 2);
		            		 righSide = val.substring(currentOperatorIndex + 2, val.length() - 1);
		            	 } catch (Exception e) {
							// TODO: handle exception
		            		 System.out.println("No left side");
		            	 }
		            	 
		            	 String newVal = leftSide + String.valueOf(result) + righSide;
		            	 
		            	 System.out.println(newVal);
		            	 
		            	 
		            	 
		            	 //String leftSide = val.substring(0, currentOperatorIndex - 2);
		            	 //String rightSide = val.
		            	 val = newVal;
		            	 
		             }*/
		        	 
		        	 
		        	 
		             /*
		             String[] operators =input.split("[a-zA-Z_0-9]");
		             String[] numbers =input.split("[^a-zA-Z_0-9]");
		             
		             
		             ArrayList<String> operatorsList = new ArrayList<>(Arrays.asList(operators));
		             ArrayList<Double> numbersList = new ArrayList<Double>();
		             
		             for(int i = 0; i < numbers.length; i++) {
		            	 double value = Double.parseDouble(numbers[i]);
		            	 numbersList.add(value);
		             }
		             
		             
		             
		             
		             while(operatorsList.indexOf("*") != -1) {
		            	 
		            	 
		            	 
		            	 
		            	 
		             }
		             */
		             
		             
		        	 
		        	 
		        	 /*
		             String val = (display.getText());
		             display.setText("");
		             System.out.println (val);
		             
		             String input = val;
		             String[] operators =input.split("[a-zA-Z_0-9]");
		             
		             String[] num =input.split("[^a-zA-Z_0-9]");

		             Double[] numval = new Double[num.length];
		             //String[] opval;

		             ArrayList<String> ops = new ArrayList<>(Arrays.asList(operators));

		             for (int i= 0; i < num.length; i++ ) {
		             numval[i]= Double.parseDouble(num[i]);
		             System.out.println(num[i]);
		             System.out.println(operators[i]);
		             }

		             ArrayList<Double> numva = new ArrayList<>(Arrays.asList(numval));
		             System.out.println((numva));
		             System.out.println((ops));
		             System.out.println(val);
		             double result = 0;


		             while(ops.indexOf("*")!=-1){
		                 int ind = ops.indexOf("*");
		                 ops.remove(ind);
		              result = (numva.get(ind-1) * numva.get(ind));
		             numva.set(ind, result);
		             numva.remove(ind-1);

		             }

		             while(ops.indexOf("+")!=-1){
		                 int ind = ops.indexOf("+");
		                 ops.remove(ind);
		              result = (numva.get(ind-1) + numva.get(ind));
		             numva.set(ind, result);
		             numva.remove(ind-1);

		             }
		             while(ops.indexOf("-")!=-1){
		                 int ind = ops.indexOf("-");
		                 ops.remove(ind);
		              result = (numva.get(ind-1) - numva.get(ind));
		             numva.set(ind, result);
		             numva.remove(ind-1);

		             }

		             while(ops.indexOf("/")!=-1){
		                 int ind = ops.indexOf("/");
		                 ops.remove(ind);
		              result = (numva.get(ind-1) / numva.get(ind));
		             numva.set(ind, result);
		             numva.remove(ind-1);

		             }
		             display.setText(Double.toString(result));
		             System.out.println(result);
		             */
		         }
		     });
			  
		 JButton point = new JButton(".");
		point.addActionListener(new ActionListener()
	     {
	         public void actionPerformed(ActionEvent evt)
	         {
	        	
	             String val = ((JButton)evt.getSource()).getText();
	             display.setText(display.getText()+val);
	             point.setEnabled(false);
	            
	             /*if (evt.getSource() == equalTo) {
	            	 point.setEnabled(true);
	             }**/
	         }
	     });
		 JButton C =new JButton("C");
		 C.addActionListener(new ActionListener()
	     {
	         public void actionPerformed(ActionEvent evt)
	         {
	        	display.setText("");
	        	SwingUtilities.updateComponentTreeUI(calculatorFrame);
	        	calculatorFrame.repaint();
	         }
	     });
		 
		  buttons.add(zero);
		  buttons.add(point);
		  buttons.add(C);
		  numberPanel.add(zero);
		  numberPanel.add(point);
		  numberPanel.add(C);
		   
		// Add buttons to the symbol panel
		  JButton division =new JButton("/");
		  division.addActionListener(new ActionListener()
		     {
		         public void actionPerformed(ActionEvent evt)
		         {
		        	
		             String val = ((JButton)evt.getSource()).getText();
		             display.setText(display.getText()+val);
		             String s = evt.getActionCommand();
		             System.out.println(s);
		         }
		     });
		  symbolPanel.add(division);
		  
		  JButton openbracket =new JButton("(");
		  symbolPanel.add(openbracket);
		  openbracket.addActionListener(new ActionListener()
		     {
		         public void actionPerformed(ActionEvent evt)
		         {
		        	
		             String val = ((JButton)evt.getSource()).getText();
		             display.setText(display.getText()+val);
		         }
		     });
		  JButton multiplication = new JButton ("*");
		  multiplication.addActionListener(new ActionListener()
		     {
		         public void actionPerformed(ActionEvent evt)
		         {
		        	
		             String val = ((JButton)evt.getSource()).getText();
		             display.setText(display.getText()+val);
		         }
		     });
		  symbolPanel.add(multiplication);
		  JButton closebracket =new JButton(")");
		  symbolPanel.add(closebracket);
		  closebracket.addActionListener(new ActionListener()
		     {
		         public void actionPerformed(ActionEvent evt)
		         {
		        	
		             String val = ((JButton)evt.getSource()).getText();
		             display.setText(display.getText()+val);
		         }
		     });
		  
		  JButton subtraction =new JButton("-");
		  subtraction.addActionListener(new ActionListener()
		     {
		         public void actionPerformed(ActionEvent evt)
		         {
		        	
		             String val = ((JButton)evt.getSource()).getText();
		             display.setText(display.getText()+val);
		         }
		     });
		  symbolPanel.add(subtraction);
		  JButton addition =new JButton("+");
		  symbolPanel.add(addition);
		  addition.addActionListener(new ActionListener()
		     {
		         public void actionPerformed(ActionEvent evt)
		         {
		        if (evt.getSource() == addition) {
		        	
		        	point.setEnabled(true);
		        	
		        	
		        	
		        	
		        	  String val = ((JButton)evt.getSource()).getText();
		        	  String vall = display.getText();	
		        	  display.setText(vall + val);
		        	  //System.out.println(val);
	            
		        	  String[] valueinArray =  vall.split("\\+");
		        	  
		        	  //System.out.println(vall + valueinArray.length);
		       /* for (int i = 0; i < valueinArray.length; i++) {
	            	 //System.out.print(valueinArray[i] + " ");
		        	
	            	 double valll= Double.parseDouble(valueinArray[i]);
	            	 System.out.println(valll);
	            	 	 
	            	
	            	 }**/
		        	  /*double [] numbers = new double[valueinArray.length];
		              for(int i = 0; i < valueinArray.length; i++){
		                  numbers[i] = Double.parseDouble(valueinArray[i]);
		                
		                  //System.out.println(numbers[i]);
		                  }	
			  
		       double value = 0;
	              for(int i = 0; i < valueinArray.length; i++){
	                   value += numbers[i];
	                
	                 }	
	              System.out.println(value); */
		  }
		        
		        
		         }
		     });
		  /*if (!display.getText().isEmpty()) {
			  double value = Double.parseDouble(display.getText());
			  System.out.println(value);
			 **/
		  
		  JButton Sin = new JButton ("sin");
		  JButton ln = new JButton ("ln");
		  JButton cos = new JButton ("cos");
		  JButton log = new JButton ("log");
		  JButton tan = new JButton ("tan");
		  JButton e = new JButton ("e");
		  JButton pi = new JButton ("\u03C0");
		  JButton raisetoPower = new JButton ("R");
		  JButton factoria = new JButton ("!");
		  JButton squareroot = new JButton ("sq");
		  
		  mathsPanel.add(Sin);
		  mathsPanel.add(ln);
		  mathsPanel.add(cos);
		  mathsPanel.add(log);
		  mathsPanel.add(tan);
		  mathsPanel.add(e);
		  mathsPanel.add(pi);
		  mathsPanel.add(raisetoPower);
		  mathsPanel.add(factoria);
		  mathsPanel.add(squareroot); 
		  calculatorFrame.add(calculatorPanel);
		 
		 
	}
}


