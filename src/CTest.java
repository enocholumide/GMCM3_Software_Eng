
public class CTest {
	
	String[] operators;
	String[] numbers;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String val = "234*63*6/9+2-2";
	   	
		//val = processOperator("")
		
		val = processOperator("*", val);
		val = processOperator("/", val);
		val = processOperator("+", val);
		val = processOperator("-", val);
		
		System.out.println("New equation " + val);
		
		   	
	}


	private static String processOperator(String op, String val) {
		
		
		while(val.indexOf(op) != -1) {
		   	
			int index = val.indexOf(op);
		   	
		   	String leftValue = getLeftValue(index, val);
		   	String rightValue = getRightValue(index, val);
		   	
		   	double left = Double.parseDouble(leftValue);
		   	double right = Double.parseDouble(rightValue);
		   	
		   	double result = 0.0;
		   	if(op.equals("*")) {
		   		result = left * right;
		   	}
		   	
		   	else if (op.equals("/")) {
		   		result = left / right;
		   	}
		   	
		   	else if(op.equals("+")) {
		   		result = left + right;
		   	}
		   	
		   	else if(op.equals("-")) {
		   		result = left - right;
		   	}
		   	
		   	
		   	System.out.println("Result: " + result);
		   	
		   	String procesed = leftValue + "*" + rightValue;
		   	
		   	
		   	int leftStop = index - leftValue.length();
		   	int rightStart = index + rightValue.length() + 1;
		   	
		   	
		   	if(leftStop < 0) {
		   		leftStop = 0;
		   	}
		   	
		   	if(rightStart > val.length()) {
		   		rightStart = val.length();
		   	}
		   	
		   	
		   	String allLeftSide = val.substring(0, leftStop);
		   	String allRightSide = val.substring(rightStart, val.length());
		   	
		   	System.out.println("Left side " + allLeftSide);
		   	System.out.println("Right side " + allRightSide);
		   	
		   	val = allLeftSide + String.valueOf(result) + allRightSide;
		   	
		   	
		}
		
		
		
		return val;
		
	}


	private static String getRightValue(int index, String val) {
		
		// Form the value to return
		String right = "";
		
		int length = val.length();
		
		// Loop forward on the supplied string starting from the index supplied
		for(int i = index + 1; i <= length; i++) {
			
			// Get
			int front = i + 1;
			if(front > length) {
				front = length;
			}
			String value = val.substring(i, front);
			
			if(!value.equals(".")) {
				
				try {
					int v = Integer.parseInt(value);
					right = right + value;
					
				} catch (Exception e) {
					return right;
				}	
			} else {
				right = right + "." ;
			}

		}
		
		return null;
	}


	private static String getLeftValue(int index, String val) {
		
		// Form the value to return
		String left = "";
		
		
		// Loop backwards on the supplied string starting from the index supplied
		for(int i = index; i >= 0; i--) {
			
			// Get
			int back = i - 1;
			if(back < 0) {
				back = 0;
			}
			String value = val.substring(back, i);
			
			if(!value.equals(".")) {
				
				try {
					int v = Integer.parseInt(value);
					left = value + left;
					
				} catch (Exception e) {
					return left;
				}	
			} else {
				left = "." + left;
			}

		}
		
		return null;
	}

	

}
