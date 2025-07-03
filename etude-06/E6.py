# 2025-04-17
# COSC326 - Etude 6
# Oliver Hurst, Harrison Jones, Jack Bredenbeck, Ben Darlington

import sys
import numpy as np
from scipy.io import wavfile
import warnings

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

def filter_keys(keys):
    filtered_keys = [None]
    for a, b in zip(keys, keys[1:]):
        if a == b and a != filtered_keys[-1]:
            filtered_keys.append(a)
    filtered_keys.pop(0)
    return filtered_keys

def key_in_male_range(key):
    return key <= 52

def key_in_female_range(key):
    return key >= 33

def print_autocorr_notes(song_name):
    sample_rate, data = wavfile.read(song_name)
    w = round(sample_rate/7) # width, 1/7 s
    n_steps = round(len(data)/(sample_rate/9)) # number of steps, 9 per second
    i = round((len(data)-w)/(n_steps-1)) # amount to move window by
    notes = []
    keys = []
    for n in range(n_steps):

        slice = data[n*i:n*i+w]
        ts = np.linspace(1/800, 1/65, num=480)
        ks = [round(t * sample_rate) for t in ts]

        amdfs = [AMDF(slice, k) for k in ks]
        
        amdf_arr = np.array(amdfs)
        min_k_i = np.argmin(amdf_arr)
        min_k = ks[min_k_i]
        min_t = min_k / sample_rate
        freq = 1/min_t
        if freq > 370 or freq < 140:
            continue
        key_n = get_key_number(freq)
        keys.append(key_n)
        note = get_note(key_n)
        notes.append(note)

    filtered_keys = filter_keys(keys)

    m_k = 0
    f_k = 0

    for key in filtered_keys:
        if key_in_male_range(key):
            m_k += 1
        if key_in_female_range(key):
            f_k += 1

    filt_k_notes = [get_note(k) for k in filtered_keys]
    print(f"{'-'.join(filt_k_notes)}")
    if f_k < m_k:
        print(f"male")
    else:
        print(f"female")

path_to_song = sys.argv[1]

print_autocorr_notes(path_to_song)

