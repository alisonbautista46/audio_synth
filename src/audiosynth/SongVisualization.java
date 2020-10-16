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
    private double pixelsPerSecond;
    private double pixelsPerSemitone;
    // other instance variables?

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
        add(visualizer);
    }

    /**
     * Shows the notes of the given song, removing any song already present.
     */
    public void showSong(Song song) {
        
        visualizer.removeAll();
        List<Note> notes = song.getNotes();
        for (Note note:notes) {
            Rectangle pixel = new Rectangle(note.getStartTime(), MAX_PITCH - note.getPitch(), pixelsPerSecond, pixelsPerSemitone);
            pixel.setStrokeWidth(0.5); 
            pixel.setFilled(true);
            pixel.setFillColor(getNoteColor(note));
            visualizer.add(pixel);
        }
    }
    // Remove all existing graphics
    //
    // For each note:
    //
    //    Create a rectangle using the pixelsPerSecond, pixelsPerSemitone,
    //    and the note's data to compute the coordinates.
    //
    //    Tip: Use (MAX_PITCH - note.getPitch()) to make higher notes
    //    be higher on the screen instead of lower.
    //
    //    Set the rectangle’s fill color, then add it to your graphics group.


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
