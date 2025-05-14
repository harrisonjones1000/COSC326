# 2025-04-17
# COSC326 - Etude 6
# Oliver Hurst, Harrison Jones, Jack Bredenbeck, Ben Darlington

import sys
import numpy as np
from scipy.io import wavfile
import warnings

# import matplotlib.pyplot as plt

warnings.filterwarnings("ignore")

def get_key_number(frequency):
    return round(12*np.log2(frequency/440)+49)

def get_note(key_number):
    keys = {0: 'g#',
            1: 'a',
            2: 'a#',
            3: 'b',
            4: 'c',
            5: 'c#',
            6: 'd',
            7: 'd#',
            8: 'e',
            9: 'f',
            10: 'f#',
            11: 'g'}
    key_name = keys[key_number%12]
    octave = (key_number+8) // 12
    return f"{key_name}{octave}"

def AMDF(s, k):
    a_s = s[0:len(s)-k]
    b_s = s[k:len(s)]

    res = np.stack([a_s, b_s], axis=1)
    amdf = np.mean(np.abs(res[:, [0]] - res[:, [1]]))

    return amdf

def filter_keys_0(keys):
    filtered_keys = [None]

    for a, b, c, d in zip(keys, keys[1:], keys[2:], keys[3:]):
        if max([a, b, c, d]) - min([a, b, c, d]) <= 1 and round(np.mean([a, b, c, d])) != filtered_keys[-1]:
            filtered_keys.append(round(np.mean([a, b, c, d])))
    filtered_keys.pop(0)
    return filtered_keys

def filter_keys_1(keys):
    filtered_keys = [None]
    for key in keys:
        if key != filtered_keys[-1]:
            filtered_keys.append(key)
    filtered_keys.pop(0)
    return filtered_keys

def filter_keys_2(keys):
    filtered_keys = [None]
    for a, b in zip(keys, keys[1:]):
        if a == b and a != filtered_keys[-1]:
            filtered_keys.append(a)
    filtered_keys.pop(0)
    return filtered_keys

def filter_keys_3(keys):
    filtered_keys = [None]
    for a, b, c in zip(keys, keys[1:], keys[2:]):
        # if max([a, b, c]) - min([a, b, c]) <= 2 and round(np.mean([a, b, c])) != filtered_keys[-1]:
        if abs(a-b) <= 1 and abs(b-c) <= 1 and abs(a-c) <= 1 and b != filtered_keys[-1]:
            # filtered_keys.append(round(np.mean([a, b, c])))
            filtered_keys.append(b)
    filtered_keys.pop(0)
    return filtered_keys

std_lim = 1.3
def filter_keys_4(keys):
    filtered_keys = [None]
    for a, b, c in zip(keys, keys[1:], keys[2:]):
        avg = np.mean([a, b, c])
        if np.std([a, b, c]) < std_lim and filtered_keys[-1] != round(avg):
            filtered_keys.append(round(avg))
    filtered_keys.pop(0)
    return filtered_keys

def filter_keys_5(keys):
    filtered_keys = [None]
    for a, b, c, d in zip(keys, keys[1:], keys[2:], keys[3:]):
        avg = np.mean([a, b, c, d])
        if np.std([a, b, c, d]) < std_lim and filtered_keys[-1] != round(avg):
            filtered_keys.append(round(avg))
    filtered_keys.pop(0)
    return filtered_keys

def print_autocorr_notes(song_name):
    sample_rate, data = wavfile.read(song_name)
    abs_data = np.abs(data)
    # plt.plot(abs_data)
    # plt.hlines(y=np.mean(abs_data), xmin=0, xmax=len(abs_data), color='red')
    # plt.show()
    # exit()
    # data_mean = np.mean(abs_data)
    # print(f"{data_mean=}")
    # data_std = np.std(np.abs(data))
    # print(f"{data_std=}")
    # denom = 6
    w = round(sample_rate/7) # width, 250ms
    n_steps = round(len(data)/(sample_rate/9)) # number of steps
    i = round((len(data)-w)/(n_steps-1)) # amount to move window by
    notes = []
    keys = []
    for n in range(n_steps):

        slice = data[n*i:n*i+w]
        slice_average = np.mean(np.abs(slice))
        # print(f"{slice_average=}")
        # print(f"{data_mean=}")
        # print(f"{data_std=}")
        # if slice_average < 0.1*data_mean: # minimum threshold must be <= 0.5 to detect notes (f#3) at end of song 9
        #     continue
        # slice = slice[slice >= mean - 2*std]
        # print(f"{slice=}")
        ts = np.linspace(1/800, 1/65, num=480)
        ks = [round(t * sample_rate) for t in ts]


        amdfs = [AMDF(slice, k) for k in ks]
        
        amdf_arr = np.array(amdfs)
        min_k_i = np.argmin(amdf_arr)
        min_k = ks[min_k_i]
        min_t = min_k / sample_rate
        freq = 1/min_t
        # Highest note in song 11 is c4
        # Lowest not in song 11 is d#3 (156Hz)
        # Hightest note in song 9 is f#4 (370Hz)
        # Lowest note in song 9 is f#3 (185Hz)

        # Notes from d#3 to f#4 (156Hz to 370Hz)
        if freq > 370 or freq < 140:
            continue
        key_n = get_key_number(freq)
        keys.append(key_n)
        note = get_note(key_n)
        notes.append(note)

    # print("All notes:")
    # print(f"{'-'.join(notes)}")

    # filtered_keys = [None]

    # for a, b, c, d in zip(keys, keys[1:], keys[2:], keys[3:]):
    #     if max([a, b, c, d]) - min([a, b, c, d]) <= 2 and round(np.mean([a, b, c, d])) != filtered_keys[-1]:
    #         filtered_keys.append(round(np.mean([a, b, c, d])))
    # filtered_keys.pop(0)
    # filtered_keys = filter_keys_0(keys)
    # filt_k_notes = [get_note(k) for k in filtered_keys]
    # print("Filtered notes: ", end="")
    # print(f"{'-'.join(filt_k_notes)}", end=" ")
    # print(f"Filtered length:{len(filt_k_notes)}")

    # filtered_keys = filter_keys_1(keys)
    # filt_k_notes = [get_note(k) for k in filtered_keys]
    # print("Filtered notes 1:", end="")
    # print(f"{'-'.join(filt_k_notes)}")

    filtered_keys = filter_keys_2(keys)
    filt_k_notes = [get_note(k) for k in filtered_keys]
    # print("Filtered notes 2:", end="")
    print(f"{'-'.join(filt_k_notes)}")
    # print(f"{len(filt_k_notes)}")

    # filtered_keys = filter_keys_3(keys)
    # filt_k_notes = [get_note(k) for k in filtered_keys]
    # print("Filtered notes 3:")
    # print(f"{'-'.join(filt_k_notes)}", end=" ")
    # print(f"{len(filt_k_notes)}")

    # filtered_keys = filter_keys_4(keys)
    # filt_k_notes = [get_note(k) for k in filtered_keys]
    # print("Filtered notes 4:")
    # print(f"{'-'.join(filt_k_notes)}", end=" ")
    # print(f"{len(filt_k_notes)}")

    # filtered_keys = filter_keys_5(keys)
    # filt_k_notes = [get_note(k) for k in filtered_keys]
    # print("Filtered notes 5:")
    # print(f"{'-'.join(filt_k_notes)}", end=" ")
    # print(f"{len(filt_k_notes)}")

path_to_song = sys.argv[1]

print_autocorr_notes(path_to_song)

# if path_to_song == "song9.wav":

#     song9_ans = "f#4-e4-f4-d#4-c#4-b3-c#4-f#4-f#3-c#4-d4-c4-b3-a#3-b3-a3-g3-f#3-g3"
#     print("Correct notes: ", song9_ans, end=" ")
#     print(f"Correct length: {len(song9_ans.split('-'))}")
# elif path_to_song == "song11.wav":
#     song11_ans = "b3-c4-a3-g#3-f3-c4-a3-g#3-f3-d#3"
#     print("Correct notes: ", song11_ans, end=" ")
#     print(f"orrect length: {len(song11_ans.split('-'))}")

