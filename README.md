# ioetExercise

How to run:  
I made this project with eclipse, so just create a new folder beside the src folder called 'input'.  
Put the ioet package in the src folder of a project.  
The 'input' folder is needed for the input.txt file, because in the 'AcmeCalculation' class is a relative path used for the input.  

Design:  
It starts with an FileInput and check if the input has enough sets of data.  
Then there is String splitting used to get the datas needed like the days (MO, TU...) and the worked hours (12:00-14:00...).  
Then the days and worked hours pairs are saved with a new class called 'DataHelper'.  
It was used in combination with an ArrayList.  
To calculate the wage there is even more String splitting used and then there are a few if...else statements to get the correct pay for the given hour and day.  
I created 2 Exception classes:
- TooLessDataSetsException is used when there are under 5 sets of data.
- WrongInputSyntaxException is used when the syntax in the input.txt is wrong. This is checked with an RegExp.
