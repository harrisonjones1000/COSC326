# 2025-04-17
# COSC326 - Etude 6
# Oliver Hurst, Harrison Jones, Jack Bredenbeck, Ben Darlington

import sys
import numpy as np
from scipy.io import wavfile
import warnings

from scipy.fft import rfft
from scipy.signal import stft
from scipy.signal import ShortTimeFFT
import matplotlib.pyplot as plt

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
        if max([a, b, c, d]) - min([a, b, c, d]) <= 2 and round(np.mean([a, b, c, d])) != filtered_keys[-1]:
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
        if abs(a-b) <= 1 and abs(b-c) <= 1 and b != filtered_keys[-1]:
            # filtered_keys.append(round(np.mean([a, b, c])))
            filtered_keys.append(b)
    filtered_keys.pop(0)
    return filtered_keys

def print_autocorr_notes(song_name):
    sample_rate, data = wavfile.read(song_name)
    # denom = 6
    w = round(sample_rate/6) # width, 250ms
    n_steps = round(len(data)/(sample_rate/10)) # number of steps
    i = round((len(data)-w)/(n_steps-1)) # amount to move window by
     # window = hamming(w, sym=True)
    # SFT = ShortTimeFFT(window, i, sample_rate)
    f, t, Z = stft(data, sample_rate, window='hann', nperseg=w, noverlap=w//2)
    print(f"{f.shape=}")
    print(f"{t.shape=}")
    print(f"{Z.shape=}")
    keys = []
    # plt.pcolormesh(t, f, np.abs(Z), vmin=0, vmax=8, shading='gouraud')
    # plt.plot(Z[:, 50])
    for j in range(Z.shape[1]):
        keys.append(get_key_number(np.argmax(Z[60:801, j])+60))
    notes = [get_note(k) for k in keys]
    print(f"{'-'.join(notes)=}")
    exit()
    plt.ylabel('Frequency [Hz]')
    plt.xlabel('Time [sec]')
    plt.show()
    exit()
    # for i in range(result.shape[1]):
    #     mode = np.argmax(result[:, i])
    #     key_n = get_key_number(mode)
    #     note = get_note(key_n)

    #     # print(f"{mode=}")
    #     # print(f"{key_n=}")

    notes = []
    # keys_2 = []
    notes_2 = []
    keys = []
    for n in range(n_steps):

        slice = data[n*i:n*i+w]
        ts = np.linspace(1/800, 1/65, num=120)
        ks = [round(t * sample_rate) for t in ts]

        res = rfft(slice)[60:801]
        peak_freq = np.argmax(res) + 60
        notes_2.append(get_note(get_key_number(peak_freq)))

        amdfs = [AMDF(slice, k) for k in ks]
        
        amdf_arr = np.array(amdfs)
        min_k_i = np.argmin(amdf_arr)
        min_k = ks[min_k_i]
        min_t = min_k / sample_rate
        freq = 1/min_t
        key_n = get_key_number(freq)
        keys.append(key_n)
        note = get_note(key_n)
        notes.append(note)

    print("All notes:")
    print(f"{'-'.join(notes)}")

    print("FFT notes:")
    print(f"{'-'.join(notes_2)}")

    # filtered_keys = [None]

    # for a, b, c, d in zip(keys, keys[1:], keys[2:], keys[3:]):
    #     if max([a, b, c, d]) - min([a, b, c, d]) <= 2 and round(np.mean([a, b, c, d])) != filtered_keys[-1]:
    #         filtered_keys.append(round(np.mean([a, b, c, d])))
    # filtered_keys.pop(0)
    filtered_keys = filter_keys_0(keys)
    filt_k_notes = [get_note(k) for k in filtered_keys]
    print("Filtered notes 0:")
    print(f"{'-'.join(filt_k_notes)}")

    # filtered_keys = filter_keys_1(keys)
    # filt_k_notes = [get_note(k) for k in filtered_keys]
    # print("Filtered notes 1:")
    # print(f"{'-'.join(filt_k_notes)}")

    filtered_keys = filter_keys_2(keys)
    filt_k_notes = [get_note(k) for k in filtered_keys]
    print("Filtered notes 2:")
    print(f"{'-'.join(filt_k_notes)}")

    filtered_keys = filter_keys_3(keys)
    filt_k_notes = [get_note(k) for k in filtered_keys]
    print("Filtered notes 3:")
    print(f"{'-'.join(filt_k_notes)}")

path_to_song = sys.argv[1]

print_autocorr_notes(path_to_song)

