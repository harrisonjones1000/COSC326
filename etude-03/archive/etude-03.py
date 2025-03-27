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


print(f"Solver took {time.time()-solver_st} seconds")
find_circle_st = time.time()

circles = np.column_stack([circle_coords, circle_rads])

# print(circles)
# exit()
sorted_circles = circles[circles[:, 2].argsort()]

def get_smallest_circle(sorted_circles):
    for i in range(len(sorted_circles)):
        n_points = (norm(points - sorted_circles[i, 0:2], axis=1) <= sorted_circles[i, 2] + 1e-5).sum()
        if n_points > 11:
            return i

index = get_smallest_circle(sorted_circles)

print(f"Finding circle took: {time.time()-find_circle_st} seconds")
print(f"Circle: {sorted_circles[index]}")
print(f"Total time taken: {time.time()-start_time}")