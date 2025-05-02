# 2025-03-19
# Harrison Jones and Oliver Hurst
# COSC362 Etude 8 - User filenames

import sys
import os
import re

def int_in_range(string_num, min_v, max_v):
    int_num = int(string_num)
    if int_num < min_v or int_num > max_v:
        return False
    return True

def get_processed_fname(fname):
    pat = re.compile("\d", flags=re.ASCII)
    digits = re.findall(pat, fname)
    if len(digits) != 6:
        return None
    
    job_site = f"{digits[0]}{digits[1]}"
    lab_desk = f"{digits[2]}{digits[3]}"
    job_num = f"{digits[4]}{digits[5]}"

    if not int_in_range(job_site, 1, 5):
        return None

    if not int_in_range(lab_desk, 1, 25):
        return None

    if not int_in_range(job_num, 1, 99):
        return None

    processed_tup = (job_site, lab_desk, job_num)
    return processed_tup

dir = sys.argv

list_paths = []

for dirpath, dirnames, filenames in os.walk(dir[1]):
    for filename in filenames:
        list_paths.append((dirpath, filename))

valid_list = []
invalid_list = []

for p in list_paths:
    p_tup = get_processed_fname(p[1])
    if p_tup is not None:
        valid_list.append((p_tup, p))
    else:
        invalid_list.append(p)

valid_list.sort(key=lambda x: (x[0][0], x[0][1], x[0][2]))

all_list = [t[1] for t in valid_list] + invalid_list

with open("filename.txt", 'w') as write_fn_f:
    for t in all_list:
        write_fn_f.write(t[1] + "\n")

with open("result.txt", 'w') as write_r_f:
    for t in all_list:
        file_path = os.path.join(t[0], t[1])
        with open(file_path, 'r') as read_f:
            lines = read_f.readlines()
            write_r_f.writelines(lines)
