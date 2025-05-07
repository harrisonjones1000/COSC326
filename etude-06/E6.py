# 2025-04-17
# COSC326 - Etude 6
# Oliver Hurst, Harrison Jones, Jack Bredenbeck, Ben Darlington

import numpy as np
from scipy.io import wavfile
from scipy.fft import rfft
import warnings
import sys

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

def print_notes(song_name):
    sample_rate, data = wavfile.read(song_name)
    w = round(sample_rate/2) # width, 500ms
    n_steps = round(len(data)/(sample_rate/10)) # number of steps, 10 per second
    i = round((len(data)-w)/(n_steps-1)) # amount to move window by
    resolution_of_fft = sample_rate/w
    # print(f"{resolution_of_fft=} Hz")

    notes = []
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
        # cepstrum = np.abs(irfft(np.log(np.abs(rfft(data[n*i:n*i+w])))))#[60:801]
        # cepstrum = np.reshape(cepstrum, (-1, 1))
        # ix = np.arange(len(cepstrum))
        # ix = np.reshape(ix, (-1, 1))
        # print(ix.shape)
        # cepstrum = np.vstack([cepstrum, ix]).T

        # l_bound = sample_rate/800
        # u_bound = sample_rate/60

        # print(f"{l_bound=}")
        # print(f"{u_bound=}")

        # print(f"{cepstrum[:, 1]=}")
        # print(f"{u_bound=}")

        # print(f"{len(cepstrum)=}")

        # condition = np.all([cepstrum[:, 1] > l_bound, cepstrum[:, 1] < u_bound], axis=0)
        # cepstrum = cepstrum[condition, :]
        # cepstrum = cepstrum[cepstrum[:, 1] < u_bound]

        # quefrency = cepstrum[cepstrum[:, 0].argmax(), 1]
        # frequency = sample_rate/quefrency
        # key_n = get_key_number(frequency)
        # note2 = get_note(key_n)
        # print(f"{note=}")
        # print(f"{note2=}")

        # print(f"{quefrency=}")
        # print(f"{sample_rate/quefrency=}")





        # print(cepstrum)
        # print(f"{cepstrum.shape=}")

        # print(ix)
        # plt.scatter(y=cepstrum[:, 0], x=cepstrum[:, 1])
        # plt.show()
        # exit()

    # print(f"{'-'.join(notes)}")

    filtered_notes = [None]

    for a, b, c in zip(notes, notes[1:], notes[2:]):
        if a == b == c and a != filtered_notes[-1]:
            filtered_notes.append(a)
    filtered_notes.pop(0)

    # print(f"Song: {song_name}")
    print("-".join(filtered_notes))

    # if male_notes > female_notes:
    #     print("Male voice")
    # else:
    #     print("Female voice")

path_to_song = sys.argv[1]

# print(f"{path_to_song=}")
print_notes(path_to_song)
# print()
# print_notes('song2.wav')


