import random 
import sys 
print("Telephone Sites:", file=sys.stdout)
for i in range(int(sys.argv[1])):     
    print(f"{random.uniform(-10, 10)} {random.uniform(-10, 10)}", file=sys.stdout)