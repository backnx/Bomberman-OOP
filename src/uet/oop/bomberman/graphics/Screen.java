package uet.oop.bomberman.graphics;


public class Screen {
    protected int _width, _height;
    private int _transparentColor = 0xffff00ff;
    public static int xOffset = 0, yOffset = 0;
    public int[] _pixels;

    public Screen(int width, int height) {
        _width = width;
        _height = height;
        _pixels = new int[width * height];
    }

    public void clear() {
        for (int i = 0; i < _pixels.length; i++) {
            _pixels[i] = 0;
        }
    }

    public static void setxOffset(int x0) {
        xOffset = x0;
    }

    public static void setyOffset(int y0) {
        yOffset = y0;
    }

    public static void setOffset(int xO, int yO) {
        xOffset = xO;
        yOffset = yO;
    }

    public int get_width() {
        return _width;
    }

    public int get_height() {
        return _height;
    }
}
