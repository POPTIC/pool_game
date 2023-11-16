package org.items;

import javax.swing.plaf.SpinnerUI;

public interface MovingProcess {
    public void bound_collision();
    public void ball_collision(int i, int j);
    public boolean hit_detect(int i, int j);
}
