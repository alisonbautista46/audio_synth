package audiosynth;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import audiosynth.waveform.Waveform;

import java.awt.Color;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Rectangle;

/**
 * Visualizes a Song as a collection of multicolored rectangles.
 */
public class SongVisualization extends GraphicsGroup {
    public static final int MAX_PITCH = 120;

    private final Map<Waveform,Color> waveformColors = new HashMap<>();
    private GraphicsGroup visualizer;
    private GraphicsGroup noteVisualizer;
    private double pixelsPerSecond;
    private double pixelsPerSemitone;
    List<Note> notes;

    /**
     * Creates an empty song visualization.
     *
     * @param pixelsPerSecond   Horizontal distance of one second
     * @param pixelsPerSemitone Number of pixels per pitch unit
     */
    public SongVisualization(double pixelsPerSecond, double pixelsPerSemitone) {
        this.pixelsPerSecond = pixelsPerSecond;
        this.pixelsPerSemitone = pixelsPerSemitone;
        visualizer = new GraphicsGroup();
        noteVisualizer = new GraphicsGroup();
        add(visualizer);
        add(noteVisualizer);
        visualizer.add(noteVisualizer);
    }

    /**
     * Shows the notes of the given song, removing any song already present.
     */
    public void showSong(Song song) {
        
        visualizer.removeAll();
        this.notes = song.getNotes();
        for (Note note:this.notes) {
            Rectangle pixel = new Rectangle(note.getStartTime() * pixelsPerSecond, (MAX_PITCH - note.getPitch())* pixelsPerSemitone, note.getDuration() * pixelsPerSecond, pixelsPerSemitone);
            pixel.setStrokeWidth(0.5); 
            pixel.setFilled(true);
            pixel.setFillColor(getNoteColor(note));
            noteVisualizer.add(pixel);
        }
    }
    /**
     * Moves the visualization to show that the given time is the current time.
     *
     * @param seconds Time from the beginning of the song
     * @param done    True if the song is done playing
     */
    public void setTime(double seconds, boolean done) {
        noteVisualizer.setPosition(pixelsPerSecond*-seconds, noteVisualizer.getY()); 
    }

    /**
     * A helper method you can use to generate different colors for different
     * notes based on their waveform. (Study this. How does it work?)
     */
    private Color getNoteColor(Note note) {
        Waveform waveform = note.getWaveform();
        Color color = waveformColors.get(waveform);
        if (color == null) {
            color = Color.getHSBColor(waveformColors.size() * 0.382f % 1, 1, 0.6f);
            waveformColors.put(waveform, color);
        }
        return color;
    }

}
