# 2025-04-17
# COSC326 - Etude 6
# Oliver Hurst, Harrison Jones, Jack Bredenbeck, Ben Darlington

import numpy as np
from scipy.io import wavfile
from scipy.fft import rfft
import warnings

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

def print_notes(song_name):
    sample_rate, data = wavfile.read(song_name)
    w = round(sample_rate/2) # width, 500ms
    n_steps = round(len(data)/(sample_rate/10)) # number of steps, 10 per second
    i = round((len(data)-w)/(n_steps-1)) # amount to move window by

    notes = []
    # male_notes = 0
    # female_notes = 0
    for n in range(n_steps):
        # Take sliding windows of length w
        # Sliding by i each iteration, 100 times
        # Then take fft of slice
        transformed_slice = rfft(data[n*i:n*i+w])
        transformed_slice = abs(transformed_slice)[:801]
        mode = np.argmax(transformed_slice)

        key_n = get_key_number(mode)
        # if key_n >= 20 and key_n <= 58:
        #     male_notes += 1
        # if key_n >= 34 and key_n<= 64:
        #     female_notes += 1
        note = get_note(key_n)
        notes.append(note)

    # print(f"{'-'.join(notes)}")

    filtered_notes = [None]

    for a, b, c in zip(notes, notes[1:], notes[2:]):
        if a == b == c and a != filtered_notes[-1]:
            filtered_notes.append(a)
    filtered_notes.pop(0)

    print(f"Song: {song_name}")
    print("-".join(filtered_notes))

    # if male_notes > female_notes:
    #     print("Male voice")
    # else:
    #     print("Female voice")

print_notes('song1.wav')
print()
print_notes('song2.wav')


