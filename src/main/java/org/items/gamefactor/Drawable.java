package org.items.gamefactor;

import javafx.collections.ObservableList;
import javafx.scene.Node;

/**
 * @param
 * @return
 */
public interface Drawable {
    // Implementations show recursively add their child drawables to group
    void addToGroup(ObservableList<Node> group);
}
