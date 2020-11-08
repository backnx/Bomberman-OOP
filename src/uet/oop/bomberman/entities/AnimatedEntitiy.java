package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

/**
 * Class quản lý các Entity có hiệu ứng hoạt hình.
 */

public abstract class AnimatedEntitiy extends Entity {
    protected int _animate = 0;
    protected final int MAX_ANIMATE = 7500;

    public AnimatedEntitiy(int x, int y, Image img) {
        super(x, y, img);
    }

    protected void animate() {
        if (_animate < MAX_ANIMATE) _animate++;
        else _animate = 0;
    }
}
