import matplotlib.pyplot as plt
import sys
import numpy as np


coords = []
for line in sys.stdin:
    coords.append(np.array(list(map(float, line.strip().split(", ")))))
c_a = np.array(coords)

circle_1 = plt.Circle((-7.9783441, -7.10660146), radius=0.12988796, fill=False, color='blue')
# circle_2 = plt.Circle((0.483, -0.698), radius=9.030, fill=False, color='red')

figure, axes = plt.subplots()
axes.set_aspect('equal')
axes.add_artist(circle_1)
axes.scatter(c_a[:, 0], c_a[:, 1], s=1, c='red', marker="s")

# axes.add_artist(circle_2)
# for c in circles:
    # print(f"{c=}")
    # drawing_circle = plt.Circle(c[0], c[1], fill=False)
    # axes.add_artist(drawing_circle)
# circle = plt.Circle(, lim_enc[0][1], fill=False, color='red')
# axes.add_artist(circle)
plt.show()