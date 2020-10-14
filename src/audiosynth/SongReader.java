package audiosynth;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import audiosynth.waveform.Noise;
import audiosynth.waveform.SawtoothWave;
import audiosynth.waveform.SineWave;
import audiosynth.waveform.SquareWave;
import audiosynth.waveform.TriangularWave;
import audiosynth.waveform.Waveform;

/**
 * A utility for reading Songs from text files. The text file consists of the following four values
 * in this order:
 *
 * - One of the following waveform names: sine tri square saw noise - pitch in MIDI units - start
 * time in seconds - duration in seconds
 *
 * ...repeated for every note in the song. Values may be separated by commas, spaces, or newlines.
 * The order of notes does not matter.
 */

public class SongReader{
    private Map<String, Waveform> waveforMap = new HashMap<>();
    /**
     * Creates a new song reader with the following name-to-waveform mappings:
     *
     * - "sine"   → SineWave
     * - "tri"    → TriangularWave
     * - "square" → SquareWave
     * - "saw"    → SawtoothWave
     * - "noise"  → Noise
     */
    public SongReader() {
        waveforMap.put("sine", new SineWave());
        waveforMap.put("tri", new TriangularWave());
        waveforMap.put("square", new SquareWave());
        waveforMap.put("saw", new SawtoothWave());
        waveforMap.put("noise", new Noise());
        // Hint: use a Map instance variable to track the name → waveform mappings.
        //
        // Later you will get waveforms out of this map and use them to create Notes.
        //
        // Look at the `AudioSynth.main()` test code from the previous step
        // for a reminder of how to make an object that represents a waveform
        // of a particular type.
    }
    /**
     * Reads song data from the given project resource.
     */
    public Song readSong(String resourceName) {
        return readSong(getClass().getResourceAsStream("/" + resourceName));
    }

    /**
     * Reads song data from an arbitrary input stream. The song data should be
     * comma-separated text, one note per line, with the following format:
     *
     *     [waveform name], [pitch], [start time], [duration]
     *
     * For example, here are three notes:
     *
     *     sine, 61, 0.6, 0.2
     *     tri, 50, 0.8, 0.2
     *     square, 50, 1.6, 0.2
     */
    public Song readSong(InputStream in) {
        // Delimiter allows values to be separated by commas, spaces, or line breaks
        Scanner scanner = new Scanner(in).useDelimiter("\\s*,\\s*|\\s+");

        Song song = new Song();
        while(scanner.hasNext()) {
            String waveformName = scanner.next();
            Waveform waveform = waveforMap.get(waveformName);
            double pitch = scanner.nextDouble();
            double startTime = scanner.nextDouble();
            double duration = scanner.nextDouble();
            // Use scanner.next() and scanner.nextDouble() to read the following
            // things, in this order:
            //
            // - waveform name
            // - pitch
            // - start time
            // - duration
            //
            // ...then use that data to create a new Note and add it to the song.
            // (Look at the Note constructor to see what types you need in what order.)
            Note note = new Note(waveform, pitch, startTime, duration);
            song.addNote(note);
        }
        return song;
    }
}