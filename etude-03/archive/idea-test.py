import sys
import matplotlib.pyplot as plt
import numpy as np
from numpy.linalg import norm
import math
from scipy.optimize import fsolve
import time
import random


def solve_circle(t, u, v):
    # print(f"{t=}, {u=}, {v=}")
    mat = np.array([[1, ((t[1]-u[1])/(t[0]-u[0]))],
                    [((t[0]-v[0])/(t[1]-v[1])), 1]])
    vec = np.array([(t[0]**2 - u[0]**2 + t[1]**2 - u[1]**2)/(2*(t[0]-u[0])), 
                    ((t[1]**2 - v[1]**2 + t[0]**2 - v[0]**2)/(2*(t[1]-v[1])))])
    s = np.linalg.solve(mat, vec)
    rad = norm(t-s)
    return s, rad

def get_norm_dist(a, b):
    return math.sqrt((a[0]-b[0])**2 + (a[1]-b[1])**2)

def count_points(circle_o, circle_r, points):
    n_points = 0
    for p in points:
        if get_norm_dist(p, circle_o) <= circle_r:
            n_points += 1
    return n_points

def get_wrong_node(bs, new_p, n_points):
    for i in range(len(bs)):
        o2, r2 = solve_circle(bs[i%3], bs[(i+1)%3], new_p)
        if count_points(o2, r2, ps) == n_points-1:
            return (i+2)%3
    return -1


coords = []
for line in sys.stdin:
    coords.append(np.array(list(map(float, line.strip().split(", ")))))
points = coords
random.shuffle(points)


b1 = points.pop()
b2 = points.pop()
b3 = points.pop()
bs = [b1, b2, b3]
b_array = np.array(bs)

points_ar = np.array(points)

figure, axes = plt.subplots()

axes.set_xlim(-30, 30)
axes.set_ylim(-30, 30)
axes.set_aspect('equal')

for _ in range(8):
    origin, radius = solve_circle(bs[0], bs[1], bs[2])
    circle_1 = plt.Circle((origin[0],  origin[1]), radius=radius, fill=False, color='black')
    axes.add_artist(circle_1)   

    
    # figure.set_size_inches((35, 35))
    ps = []
    pds = []
    for p in points:
        dist = get_norm_dist(p, origin)
        if dist < radius:
            # p_distances.append((p, dist))
            ps.append(p)
            pds.append(dist)

    ps_mod = [p for p in ps]
    # print(p_distances)
    # print(f"{radius=}")

    # p_distances.sort(key= lambda x: -x[1])
    number_of_points = count_points(origin, radius, points)
    # num_points = number_of_points

    while True:
        far_point = ps_mod.pop(pds.index(max(pds)))
        # print(len(ps_mod))
        index_of_worst_node = get_wrong_node(bs, far_point, number_of_points) 

        if index_of_worst_node != -1:
            bs[index_of_worst_node] = far_point
            break
        # rads.append(r2)
        # print(f"{r2}")
        # circle = 
        # axes.add_artist(plt.Circle((o2[0],  o2[1]), radius=r2, fill=False, color='red'))
        # break
    # print(f"worst b: {bs[(rads.index(min(rads))+2)%3]}")
    # print(f"{p_distances[0][0]}")
    # bs[(rads.index(min(rads))+2)%3] = p_distances[0][0]

# print(p_distances)


# coords = [[11.0, 12.0],[11.2, 10.0],[15.0, 11.0]]
# coords = np.array(coords)
# print(coords[0][0])



# circle_1 = plt.Circle((origin[0],  origin[1]), radius=radius, fill=False, color='black')


# for x in np.linspace(11.5, 13, 10):
#     coords = [[11.0, 12.0],[11.2, 10.0],[x, 11.0]]
#     coords = np.array(coords)
#     origin, radius = solve_circle(coords[0], coords[1], coords[2])
#     circle_1 = plt.Circle((origin[0],  origin[1]), radius=radius, fill=False, color='red')
#     axes.add_artist(circle_1)
#     plt.plot([origin[0], x], [origin[1], 11], c='orange')
#     plt.plot([origin[0], 11], [origin[1], 12], c = 'green')





# circle_2 = plt.Circle((-15.905990501764084, 6.146935209547194), radius=25.9387164966376, fill=False, color='red')


axes.scatter(points_ar[:, 0], points_ar[:, 1])
axes.scatter(b_array[:, 0], b_array[:, 1], marker='x', c='blue')


# axes.add_artist(circle_2)
# for c in circles:
    # print(f"{c=}")
    # drawing_circle = plt.Circle(c[0], c[1], fill=False)
    # axes.add_artist(drawing_circle)
# circle = plt.Circle(, lim_enc[0][1], fill=False, color='red')
# axes.add_artist(circle)
plt.show()