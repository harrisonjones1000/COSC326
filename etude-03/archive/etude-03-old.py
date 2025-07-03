import sys
import matplotlib.pyplot as plt
import numpy as np
from numpy.linalg import norm
import math
from scipy.optimize import fsolve
import time

start_time = time.time()
coords = []
for line in sys.stdin:
    coords.append(np.array(list(map(float, line.strip().split(", ")))))
points = np.array(coords)

comb = []
for_start = time.time()

for xi in range(len(points)):
    for yi in range(xi+1, len(points)):
        for zi in range(yi+1, len(points)):
            comb.append(np.array([points[xi], points[yi], points[zi]]))

comb = np.array(comb)

print(f"Loop took: {time.time()-for_start} seconds")

solver_st = time.time()

# find all possible circles
circle_coords = []
circle_rads = []

for e in comb:
    t, u, v = e
    mat = np.array([[1, ((t[1]-u[1])/(t[0]-u[0]))],
                    [((t[0]-v[0])/(t[1]-v[1])), 1]])
    vec = np.array([(t[0]**2 - u[0]**2 + t[1]**2 - u[1]**2)/(2*(t[0]-u[0])), 
                    ((t[1]**2 - v[1]**2 + t[0]**2 - v[0]**2)/(2*(t[1]-v[1])))])
    s = np.linalg.solve(mat, vec)
    rad = norm(t-s)

    circle_coords.append(s)
    circle_rads.append(rad)

circle_coords = np.array(circle_coords)
circle_rads = np.array(circle_rads)

# circles = np.column_stack([circle_coords, circle_rads])

# sorted_circles = circles[circles[:, 2].argsort()[::-1]]

# exit()

print(f"Solver took {time.time()-solver_st} seconds")


norm_st = time.time()
# distance from p0 to all circles, distance from p1 to all circles, ...
norms = []
for p in points:
    norms.append(norm(circle_coords - p, axis=1))

norms = np.array(norms)

print(f"Norms took: {time.time()-norm_st} seconds")

find_circle_st = time.time()

circle_rads_tol = np.abs(circle_rads) + 0.0000000001
circle_rads_tol_rep = np.tile(circle_rads_tol, len(points))
circle_rads_tol_rep = np.reshape(circle_rads_tol_rep, (len(points), len(circle_coords)))


# each row is whether a point is contained within the circle of that column

# each column is associated with a circle, each row with a point
# True if point is in circle (therefore row sum to get number of points by circle)
point_in_circle = norms <= circle_rads_tol_rep
counts = point_in_circle.sum(axis=0)
contains_twelve = counts == 12
i_circle_ans = circle_rads[contains_twelve].argmin()

print(f"Finding circle took: {time.time()-find_circle_st} seconds")
print(f"Time taken: {time.time()-start_time}")
print(f"Circle: {circle_coords[contains_twelve][i_circle_ans]}, {circle_rads[contains_twelve][i_circle_ans]}")

# print(norms)
# print()
# print(circle_rads_tol_rep)
# print()
# print(point_in_circle)
# print()
# print(counts)
# print()
# print(contains_twelve)
# print()
# print(circle_coords[contains_twelve])
# print()
# print(circle_rads[contains_twelve][i_circle_ans])
# print()
# print(i_circle_ans)
# print(contains_twelve[i])