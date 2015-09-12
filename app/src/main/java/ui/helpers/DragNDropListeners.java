package ui.helpers;

/**
 * Created by Жамбыл on 13.09.2015.
 */
public interface DragNDropListeners {

    public void onDrag(float x, float y);

    /**
     * Event fired when an item is picked.
     * position[0] - group position
     * position[1] - child position
     * @param position
     */
    public void onPick(int[] position);

    /**
     * Event fired when an item is dropped.
     * from - position from where item is picked.
     * to - position at which item is dropped.
     * from[0] - group position
     * from[1] - child position
     */
    public void onDrop(int[] from, int[] to);
}

