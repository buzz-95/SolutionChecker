import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;
class timeMeasure implements Runnable{
	double timeLimit;
	int testcase;
	boolean isRunning;
	timeMeasure(double inputTimeLimit, int testcaseNum){
		timeLimit = inputTimeLimit + 0.25; //0.25 seconds for safety
		testcase = testcaseNum;
		isRunning = true;
	}
	public void run(){
		long startTime = System.nanoTime();
		while(isRunning){
			long currTime = System.nanoTime();
			long timeElapsed = (currTime - startTime) / (long)(1e6);
			if(timeElapsed <= timeLimit){
				try{
					TimeUnit.MILLISECONDS.sleep(450);
				}
				catch(Exception e){
					System.out.println("Exception : " + e);
				}
			}
			else{
				break;
			}
		}
		if(isRunning){
			cleanUp();
			System.out.println("Time Limit Exceeded on testcase #" + 
			Integer.toString(testcase));
			System.exit(0);
		}/*
		else{
			long currTime = System.nanoTime();
			long timeElapsed = (currTime - startTime) / (long)(1e6);
			System.out.println("Time taken by testcase #" + 
			Integer.toString(testcase) + " : " + Long.toString(timeElapsed));
		}*/
	}
	public static void cleanUp(){
		String cleanUpBash = "cleanUp.sh";
		Process CleanUpOp;
		try{
			CleanUpOp = Runtime.getRuntime().exec(
				"sh " + cleanUpBash);
			CleanUpOp.waitFor();
		}
		catch(Exception e){
			System.out.println("Exception : " + e);
		}
	}
	public void kill(){
		isRunning = false;
	}
}
public class checker{
	public static void compile(){
		String compileBash = "compile.sh";
		Process compilation;
		try{
			compilation = Runtime.getRuntime().exec(
				"sh " + compileBash);
			BufferedReader output = new BufferedReader(
				new InputStreamReader(compilation.getInputStream()));
			BufferedReader error = new BufferedReader(
				new InputStreamReader(compilation.getErrorStream()));
			compilation.waitFor();
			output.close();
			error.close();

			if(compilation.exitValue() == 0){
				System.out.println("Successful Compilation");
				return;
			}
			System.out.println("Compilation Failed");
			System.exit(0);
		}
		catch(Exception e){
			System.out.println("Exception : " + e);
		}
	}
	public static int findNumOfTestcases(){
		String numTcsBash = "numOfTestcases.sh";
		Process findTcs;
		Integer numOfTestcases = 0;
		try{
			findTcs = Runtime.getRuntime().exec("sh " + numTcsBash);
			BufferedReader output = new BufferedReader(
				new InputStreamReader(findTcs.getInputStream()));
			BufferedReader error = new BufferedReader(
				new InputStreamReader(findTcs.getErrorStream()));
			String numOfTestcasesStr = output.readLine();
			findTcs.waitFor();
			if(numOfTestcasesStr == null || findTcs.exitValue() != 0){
				System.out.println("Some Error Occured");
				System.exit(0);
			}
			numOfTestcases = Integer.parseInt(numOfTestcasesStr);
			output.close();
			error.close();			
		}
		catch(Exception e){
			System.out.println("Exception : " + e);
		}
		return numOfTestcases;
	}
	public static String intToStr(Integer num){
		String str = Integer.toString(num);
		if(num < 10) str = "0" + str;
		return str;
	}
	public static void generateOpFor(Integer index){
		String genOpBash = "genOp.sh";
		Process generateOp;
		try{
			generateOp = Runtime.getRuntime().exec(
				"sh " + genOpBash + " input" + intToStr(index)
				 + ".txt uoutput" + intToStr(index) + ".txt");
			BufferedReader output = new BufferedReader(
				new InputStreamReader(generateOp.getInputStream()));
			BufferedReader error = new BufferedReader(
				new InputStreamReader(generateOp.getErrorStream()));
			generateOp.waitFor();
			if(generateOp.exitValue() != 0){
				cleanUp();
				String s = new String("");
				while((s = error.readLine()) != null)
					System.out.println("Runtime Error -> " + s);
				System.exit(0);
			}
			output.close();
			error.close();
		}
		catch(Exception e){
			System.out.println("Exception : " + e);
		}
	}
	public static void checkOutputFor(Integer index){
		String checkOpBash = "checkOp.sh";
		Process checkOp;
		try{
			checkOp = Runtime.getRuntime().exec(
				"sh " + checkOpBash + " output" + intToStr(index) + ".txt");
			BufferedReader output = new BufferedReader(
				new InputStreamReader(checkOp.getInputStream()));
			BufferedReader error = new BufferedReader(
				new InputStreamReader(checkOp.getErrorStream()));
			checkOp.waitFor();
			String verdict = new String("");
			verdict = output.readLine();
			if(checkOp.exitValue() != 0 || verdict == null){
				System.out.println("Some Error Occured");
				System.exit(0);
			}
			if(verdict.equals("AC")){
				System.out.println("AC on testcase #" + Integer.toString(index));
				return;
			}
			else if(verdict.equals("WA")){
				System.out.println("WA on testcase #" + Integer.toString(index));
				System.exit(0);
			}
			else{
				System.out.println("Something's wrong! Contact buzz-95 for this");	
				System.exit(0);
			}
		}
		catch(Exception e){
			System.out.println("Exception : " + e);
		}
	}
	public static void cleanUp(){
		String cleanUpBash = "cleanUp.sh";
		Process CleanUpOp;
		try{
			CleanUpOp = Runtime.getRuntime().exec(
				"sh " + cleanUpBash);
			CleanUpOp.waitFor();
		}
		catch(Exception e){
			System.out.println("Exception : " + e);
		}
	}
	public static void main(String args[]){
		if(args.length > 2 || args.length <= 0){
			System.out.println("Command Line Arguments Count Error");
			return;
		}
		else if(args.length == 2){
			if(args[1].equals("wC")){
				compile();
			}
		}
		double timeLimit = Double.parseDouble(args[0]);
		int numOfTestcases = findNumOfTestcases();
		for(int i = 0;i < numOfTestcases;i++){
			timeMeasure tM = new timeMeasure(timeLimit,i);
			Thread thr = new Thread(tM);
			thr.start();
			generateOpFor(i);
			tM.kill();
			checkOutputFor(i);
			cleanUp();
		}
		System.out.println("AC on all the testcases");
	}
}
