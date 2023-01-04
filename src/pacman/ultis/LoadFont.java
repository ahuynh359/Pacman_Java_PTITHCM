package pacman.ultis;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class LoadFont {
    public static Font loadFont() {

        InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("res/font/font.ttf");
        Font font;
        try {
            assert stream != null;
            font = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(10f);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
        return font;

    }
}
