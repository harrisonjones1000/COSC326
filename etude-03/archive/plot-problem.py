import matplotlib.pyplot as plt
import sys
import numpy as np


coords = []
for line in sys.stdin:
    coords.append(np.array(list(map(float, line.strip().split(", ")))))
c_a = np.array(coords)

circle_1 = plt.Circle((-1.59059905e1,  6.14693521), radius=2.59387165e+01, fill=False, color='blue')
# circle_2 = plt.Circle((-15.905990501764084, 6.146935209547194), radius=25.9387164966376, fill=False, color='red')

figure, axes = plt.subplots()
axes.set_aspect('equal')
axes.scatter(c_a[:, 0], c_a[:, 1])

axes.add_artist(circle_1)
# axes.add_artist(circle_2)
# for c in circles:
    # print(f"{c=}")
    # drawing_circle = plt.Circle(c[0], c[1], fill=False)
    # axes.add_artist(drawing_circle)
# circle = plt.Circle(, lim_enc[0][1], fill=False, color='red')
# axes.add_artist(circle)
plt.show()