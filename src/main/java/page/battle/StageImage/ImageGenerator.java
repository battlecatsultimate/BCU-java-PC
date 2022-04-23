package page.battle.StageImage;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public interface ImageGenerator {
    int space = 30;
    int xGap = 5;
    int yGap = 2;

    BufferedImage generateRealImage(String message);
}