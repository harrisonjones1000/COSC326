# COSC326 Etude 03

### External Libraries:
We used the Apache Commons Math library to solve a simple set of linear equations. While we could written the code ourselves, we decided to use the library and focus our attention on the more interesting parts of this problem.

### How to run:
1. Clone the repo.  
2. Navigate to the project level directory `/etude-03` (the directory that contains `/src`, `/lib` as direct descendents)
<!-- Key: unix | windows
```
python3 -m venv venv | python -m venv C:\path\to\new\virtual\environment
source venv/bin/activate | C:\> <venv>\Scripts\activate.bat
pip install -r requirements.txt
python random-points.py {n-points} > input.txt
python etude-03.py < input.txt
``` -->

3. Compile: 
```
javac -cp lib/commons-math3-3.6.1-bin/commons-math3-3.6.1/commons-math3-3.6.1.jar -d bin src/TelephoneImproved.java
```
4. Run:
```
java -cp bin:lib/commons-math3-3.6.1-bin/commons-math3-3.6.1/commons-math3-3.6.1.jar TelephoneImproved < input_data.txt 
 ```