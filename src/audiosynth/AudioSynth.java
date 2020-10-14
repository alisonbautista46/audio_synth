package audiosynth;

import audiosynth.waveform.SineWave;
import audiosynth.waveform.SquareWave;
import audiosynth.waveform.TriangularWave;

/**
 * Generates and visualizes music.
 */
public class AudioSynth {
    public static void main(String[] args) {
        String fileName = "kondo.csv";
        new SongReader()
            .readSong(fileName)
            .renderAudio()
            .play();
    }
}
