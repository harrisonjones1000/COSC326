# 2025-04-17
# COSC326 - Etude 6
# Oliver Hurst, Harrison Jones, Jack Bredenbeck, Ben Darlington

import numpy as np
from scipy.io import wavfile
from scipy.fft import rfft
from scipy.signal import find_peaks
from scipy.signal import ShortTimeFFT
import warnings
import matplotlib.pyplot as plt

warnings.filterwarnings("ignore")

def get_key_number(frequency):
    return round(12*np.log2(frequency/440)+49)

def get_note(key_number):
    keys = {0: 'G#',
            1: 'A',
            2: 'A#',
            3: 'B',
            4: 'C',
            5: 'C#',
            6: 'D',
            7: 'D#',
            8: 'E',
            9: 'F',
            10: 'F#',
            11: 'G'}
    key_name = keys[key_number%12]
    octave = (key_number+8) // 12
    return f"{key_name}{octave}"

def sliding_window(song_name):
    sample_rate, data = wavfile.read(song_name)
    # print(f"{len(data)=}")
    # s_audio = len(data)/sample_rate
    w = round(sample_rate/2) # samples per 500ms
    # print(f"{w=}")
    n_steps = 100
    i = round((len(data)-w)/(n_steps-1))
    # print(f"{i=}")

    sub_arrays = []
    notes = []
    male_notes = 0
    female_notes = 0
    for n in range(n_steps):
        # if n > 3:
            # exit()
        # print(f"{n*i=}:{n*i+w=}")
        # Take sliding windows of length w
        # Sliding by i each iteration, 100 times
        # Then take fft of slice
        transformed_slice = rfft(data[n*i:n*i+w])
        transformed_slice = abs(transformed_slice)[:801]
        # peaks, properties = find_peaks(transformed_slice, prominence=np.max(transformed_slice)*3/4)
        mode = np.argmax(transformed_slice)
        # fig, ax = plt.subplots()
        # ax.plot(transformed_slice)
        # ax.hlines(y=peak, xmin=0, xmax=len(transformed_slice))
        # ax.scatter(y=transformed_slice[peaks], x=peaks,marker='x')
        # plt.show()
        # Frequency of peak
        # mode = np.argmax(np.abs(transformed_slice))
        key_n = get_key_number(mode)
        if key_n >= 20 and key_n <= 58:
            male_notes += 1
        if key_n >= 34 and key_n<= 64:
            female_notes += 1
        note = get_note(key_n)
        notes.append(note)
    print("-".join(notes))
    # print(f"{sub_arrays[-1]}")
    # print(f"{len(sub_arrays[-1])=}")
     
    filtered_notes = [None]
    last_note = None
    for note in notes:
        if note == last_note and note != filtered_notes[-1]:
            filtered_notes.append(note)
        last_note = note
    filtered_notes.pop(0)
    print("filtered notes")
    print("-".join(filtered_notes))

    filtered_new = [None]

    for a, b, c in zip(notes, notes[1:], notes[2:]):
        if a == b == c and a != filtered_new[-1]:
            filtered_new.append(a)
    filtered_new.pop(0)
    print()
    print("-".join(filtered_new))

def new_results(song_name):
    sample_rate, data = wavfile.read(song_name)
    SFT = ShortTimeFFT(data, hop=1000, fs=1/sample_rate)
    Sx = SFT.stft(data)

    print(f"{len(data)=}")

    print(f"{Sx=}")
    plt.plot(Sx)
    plt.show()


def print_song_results(song_name):
    sample_rate, data = wavfile.read(song_name)

    s_audio = len(data)/sample_rate
    # print(f"{s_audio=}")
    notes = []
    male_notes = 0
    female_notes = 0
    # Change this into sliding window
    sub_arrays = np.array_split(data, round(s_audio))
    for arr in sub_arrays:
        t_arr = rfft(arr)
        mode = np.argmax(t_arr)
        key_number = get_key_number(mode)
        if key_number >= 20 and key_number <= 58:
            male_notes += 1
        if key_number >= 34 and key_number <= 64:
            female_notes += 1
        note = get_note(key_number)
        notes.append(note)
  
    print(f"Song: {song_name}")
    print("-".join(notes))
    if male_notes > female_notes:
        print("Male voice")
    else:
        print("Female voice")

# new_results('song1.wav')
# print_song_results('song1.wav')
# print()
# print_song_results('song2.wav')
sliding_window('song2.wav')

