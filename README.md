# SolutionChecker
This is a solution checker I use often, how about making it public?

How to use it?
1. add the solution in a.cpp (Note: Please do not write return statement in your main function of your solution "a.cpp")
2. add the input testcases in checker/input/ folder (Note: Use of fixed format: input00.txt,input01.txt,...)
3. add the output testcases in checker/output/ folder (Note: Use of fixed format: input00.txt,input01.txt,...)
4. Just run "java checker timeLimit(floating point value) wC(withCompile)" on your terminal (Note: Use this exact format)
    a. timeLimit is a command line argument to be passed, it is a floating point value
    b. wC is an optional command line argument, if included, the checker compiles the code and then checks the solution.
    
Examples of Usage ::
  E1. java checker 1.5 wC // this will check the solution a.cpp under timeLimit Constraint = 1.5s after compiling it.
  E2. java checker 2      // this will check the solution a.cpp under timeLimit Constraint = 2s. Make sure a.out is present.

Verdicts?
1. AC 
2. WA
3. TLE
4. RE (Segmentation Fault,Floating Point Exception)

A peaceful working of it 
<pre><font color="#55FF55"><b>silver_buzz@PC</b></font>:<font color="#5555FF"><b>~/checker/checker</b></font>$ java checker 1 wC
Successful Compilation
AC on testcase #0
AC on testcase #1
AC on testcase #2
AC on testcase #3
AC on testcase #4
AC on testcase #5
AC on all the testcases</pre>
