package audiosynth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A collection of notes that (perhaps) make music together. A newly created song starts out empty —
 * no notes.
 */
public class Song {
    List<Note> notes = new ArrayList<>();
    /**
     * Adds the given note to the notes already in the piece.
     */
    public void addNote(Note note) {
        notes.add(note);
    }

    /**
     * Returns an unmodifiable collection of all the notes in the piece.
     */
    public List<Note> getNotes() {
        return Collections.unmodifiableList(notes);
    }

    /**
     * Returns maximum end time of any note in the song. Returns 0 if the song is empty.
     */
    public double getDuration() {
        return notes.stream().mapToDouble(Note::getEndTime).max().orElse(0);
    }

    /**
    * Renders all the notes in the piece to a normalized audio buffer.
    */
    public AudioBuffer renderAudio() {
        AudioBuffer audio = new AudioBuffer(Utils.convertSecondsToSamples(getDuration()));
            for (Note note : notes) {
                Signal signal = note.getWaveform().createSignal(Utils.convertPitchToWavelength(note.getPitch()));
                int duration = Utils.convertSecondsToSamples(note.getDuration());
                int offset = Utils.convertSecondsToSamples(note.getStartTime());
                audio.mix(signal, offset, duration);
            }
            audio.normalize();
            return audio;  
    }
    // Sketch of the method:
    //
    // Create a new AudioBuffer. Compute the size of the buffer using:
    //
    //     Utils.covertSecondsToSamples(getDuration())
    //
    // For each note:
    //
    //     Create a signal for the note by converting its pitch a wavelength.
    //
    //     Call mix() on your audio buffer to mix the signal into the buffer
    //     at the correct time and for the correct duration.
    //  
    //     Methods that will help you:
    //  
    //     - Utils.convertPitchToWavelength()
    //     - Utils.covertSecondsToSamples()
    //     - note.get_______()
    //
    // Call normalize() on your audio buffer. This prevents the mixed audio
    // from being too loud and clipping.
    //
    // Return the audio buffer. 
}