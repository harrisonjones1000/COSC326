# 2025-04-17
# COSC326 - Etude 6
# Oliver Hurst, Harrison Jones, Jack Bredenbeck, Ben Darlington

import numpy as np
from scipy.io import wavfile
from scipy.fft import rfft
import warnings
warnings.filterwarnings("ignore")
# women are typically in the soprano to contralto range,
# this includes notes from F#3 (key 34) to C#6 (key 64),
# men are typically in the countertenor to bass range,
# this includes notes from E2 (key 20) to F#5 (key 58)

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

def print_song_results(song_name):
    sample_rate, data = wavfile.read(song_name)

    s_audio = len(data)/sample_rate
    notes = []
    male_notes = 0
    female_notes = 0
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

    print("-".join(notes))
    if male_notes > female_notes:
        print("Male voice")
    else:
        print("Female voice")
    print()

print_song_results('song1.wav')
print_song_results('song2.wav')

