# 2025-03-19
# Harrison Jones and Oliver Hurst
# COSC362 Etude 8 - User filenames

import sys
import os

dir = sys.argv
print(dir[1])

list_paths = []

for dirpath, dirnames, filenames in os.walk(dir[1]):
    # print(f"{dirpath=}, {dirnames=}, {filenames=}")
    for filename in filenames:
        list_paths.append((filename, dirpath))

for p in list_paths:
    print(p)

list_paths.sort(key=lambda x: (x[0][0:2], x[0][3:5], x[0][6:8]))

print("***")
for p in list_paths:
    print(p)