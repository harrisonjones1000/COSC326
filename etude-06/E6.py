# 2025-04-17
# COSC326 - Etude 6
# Oliver Hurst, Harrison Jones, Jack Bredenbeck, Ben Darlington

import numpy as np
from scipy.io import wavfile
from scipy.fft import rfft
import warnings
import sys

from scipy.signal import ShortTimeFFT
from scipy.signal.windows import hamming
import matplotlib.pyplot as plt 
import seaborn as sns

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

def get_hamming_stfft_notes(sample_rate, data):
    w = round(sample_rate/2) # width, 500ms
    n_steps = round(len(data)/(sample_rate/10)) # number of steps, 10 per second
    i = round((len(data)-w)/(n_steps-1)) # amount to move window by
    # resolution_of_fft = sample_rate/w
    # print(f"{resolution_of_fft=} Hz")
    # print(f"{n_steps=}")

    window = hamming(w, sym=True)
    SFT = ShortTimeFFT(window, hop=i, fs=sample_rate, scale_to='magnitude')
    Sx = SFT.stft(data)
    # print(f"{Sx.shape=}")
    freq_l_bound = 60
    Sx = Sx[freq_l_bound:800, :]
    # print(f"{Sx.shape=}")

    notes = []
    for i in range(Sx.shape[1]):
        slice = Sx[:, i]
        mode = slice.argmax()
        key_n = get_key_number(mode+freq_l_bound)
        note = get_note(key_n)
        notes.append(note)
    
    # print(f"{'-'.join(notes)}")
    # print()
    
    filtered_notes = [None]

    for a, b, c in zip(notes, notes[1:], notes[2:]):
        if a == b == c and a != filtered_notes[-1]:
            filtered_notes.append(a)
    filtered_notes.pop(0)

    # print(f"Song: {song_name}")
    print("hamming notes:")
    print("-".join(filtered_notes))

# def AMDF(s, k):
#     return np.mean([np.abs(s[n] - s[n+k]) for n in range(len(s) - k)])

def AMDF(s, k):
    a_s = s[0:len(s)-k]
    b_s = s[k:len(s)]

    a_s = a_s.reshape(-1, 1)
    b_s = b_s.reshape(-1, 1)

    res = np.hstack([a_s, b_s])
    amdfs = np.mean(np.abs(res[:, [0]] - res[:, [1]]))

    return amdfs


def get_autocorrelation_notes(sample_rate, data):
    w = round(sample_rate/4) # width, 250ms
    n_steps = round(len(data)/(sample_rate/7)) # number of steps
    i = round((len(data)-w)/(n_steps-1)) # amount to move window by
    
    notes = []
    keys = []
    for n in range(n_steps):

        slice = data[n*i:n*i+w]
        ts = np.linspace(1/800, 1/65, num=120)
        ks = [round(t * sample_rate) for t in ts]

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

        verbose = False

        if verbose:
            print(f"{amdf_arr.shape=}")
            print(f"{min_k_i=}")
            print(f"{min_k=}")
            print(f"{min_t=}")
            print(f"{freq=}")
            print(f"{key_n=}")
            print(f"{note=}")

        plotting = False
        if plotting:
            sns.lineplot(amdf_arr)
            plt.show()
    print(f"{'-'.join(notes)=}")
    # print()
    # print(f"{'-'.join(list(map(str, keys)))=}")

    

    # filtered_keys = [None]
    # last_five = []
    # for i, key in enumerate(keys):
    #     if len(last_five) == 4:
    #         if min(last_five) >= max(last_five) - 2:
    #             best_key = round(np.mean(last_five))
    #             if filtered_keys[-1] != best_key:
    #                 filtered_keys.append(best_key)

            
    #         last_five.pop(0)

    #     last_five.append(key)
    # filtered_keys.pop(0)

    filtered_keys = [None]

    for a, b, c in zip(keys, keys[1:], keys[2:]):
        if max([a, b, c]) - min([a, b, c]) <= 2 and round(np.mean([a, b, c])) != filtered_keys[-1]:
            filtered_keys.append(round(np.mean([a, b, c])))
    filtered_keys.pop(0)

    filt_k_notes = [get_note(k) for k in filtered_keys]

    # print(f"Song: {song_name}")
    print("filtered key notes 1:")
    print(f"{'-'.join(filt_k_notes)}")

    filtered_keys = [None]

    for a, b, c, d in zip(keys, keys[1:], keys[2:], keys[3:]):
        if max([a, b, c, d]) - min([a, b, c, d]) <= 2 and round(np.mean([a, b, c, d])) != filtered_keys[-1]:
            filtered_keys.append(round(np.mean([a, b, c, d])))
    filtered_keys.pop(0)

    filt_k_notes = [get_note(k) for k in filtered_keys]

    # print(f"Song: {song_name}")
    print("filtered key notes 2:")
    print(f"{'-'.join(filt_k_notes)}")



    # print()
    # print(f"{'-'.join(list(map(str, filtered_keys)))=}")

    # filt_k_notes = [get_note(k) for k in filtered_keys]

    # print()
    # print("-".join(filt_k_notes))
        
            
        

    filtered_notes = [None]

    for a, b, c in zip(notes, notes[1:], notes[2:]):
        if a == b == c and a != filtered_notes[-1]:
            filtered_notes.append(a)
    filtered_notes.pop(0)

    # print(f"Song: {song_name}")
    # print("filtered notes:")
    # print("-".join(filtered_notes))

       


def print_notes(song_name):
    sample_rate, data = wavfile.read(song_name)
    w = round(sample_rate/2) # width, 500ms
    n_steps = round(len(data)/(sample_rate/10)) # number of steps, 10 per second
    i = round((len(data)-w)/(n_steps-1)) # amount to move window by

    get_autocorrelation_notes(sample_rate, data)

    # get_hamming_stfft_notes(sample_rate, data)
   

    # print(f"{Sx=}")
    # print(f"{Sx.shape=}")
    # print(Sx[:, 0])
    # print(f"{SFT.f=}")
    # print(f"{key_n=}")
    # print(f"{note=}")
    # print(f"{mode=}")
    # plt.plot(Sx[:, 0])
    # plt.show()
    # print("Song 11 answer:")
    # print("b3-c4-a3-g#3-f3-c4-a3-g#3-f3-d#3")

    exit()

    notes = []
    for n in range(n_steps):
        # Take sliding windows of length w
        # Sliding by i each iteration, 100 times
        # Then take fft of slice

        transformed_slice = rfft(data[n*i:n*i+w])
        transformed_slice = abs(transformed_slice)[60:800]
        mode = np.argmax(transformed_slice) + 60
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
        # exit(
    print(f"{'-'.join(notes)}")
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


