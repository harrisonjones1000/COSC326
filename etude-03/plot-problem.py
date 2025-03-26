import matplotlib.pyplot as plt
import sys
import numpy as np


coords = []
for line in sys.stdin:
    coords.append(np.array(list(map(float, line.strip().split(", ")))))
c_a = np.array(coords)

# circle_1 = plt.Circle((5.363, -5.639), radius=4.150, fill=False, color='blue')
circle_2 = plt.Circle((-2.429, 0.350), radius=1.600, fill=False, color='red')

figure, axes = plt.subplots()
axes.set_aspect('equal')
axes.scatter(c_a[:, 0], c_a[:, 1])

# axes.add_artist(circle_1)
axes.add_artist(circle_2)
# for c in circles:
    # print(f"{c=}")
    # drawing_circle = plt.Circle(c[0], c[1], fill=False)
    # axes.add_artist(drawing_circle)
# circle = plt.Circle(, lim_enc[0][1], fill=False, color='red')
# axes.add_artist(circle)
plt.show()