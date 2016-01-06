package com.mobarenas.mavanity.utils;

import com.mobarenas.mavanity.MaVanity;
import org.bukkit.Color;
import org.bukkit.util.Vector;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP1 on 1/1/2016.
 */
public class WingHelper {

    private static MaVanity plugin;
    private static List<Vector> wingPoints;

    public WingHelper(MaVanity plugin) {
        this.plugin = plugin;
        this.wingPoints = new ArrayList<>();
        this.initialise();
    }

    public static void initialise() {
        try {
            File localFile = new File(plugin.getDataFolder() + "/Images/WingsPixels.png");
            localFile.getParentFile().mkdirs();
            if (!localFile.exists()) {
                localFile.createNewFile();
                InputStream stream = plugin.getResource("Resources/Wings.png");
                byte[] arrayOfByte = new byte[(stream).available()];
                stream.read(arrayOfByte);
                stream.close();
                FileOutputStream localFileOutputStream = new FileOutputStream(localFile);
                localFileOutputStream.write(arrayOfByte);
                localFileOutputStream.close();
            }
            BufferedImage bufferedImage = ImageIO.read(localFile);
            int i;
            double d = 0.03;
            for (int j = 0; j < (bufferedImage).getHeight(); j++) {
                for (int k = 0; k < (bufferedImage).getWidth(); k++) {
                    i = (bufferedImage).getRGB(k, j);
                    if ((Color.WHITE.asRGB() == i) || (i == -16777216)) {
                        Vector localVector = new Vector(k * d, j * d + 0.92, 0.0D);
                        wingPoints.add(localVector);
                        wingPoints.add(rotateAroundAxisY(localVector.clone(), 3.141592741012573D));
                    }
                }
            }
        } catch (IOException localIOException) {
            localIOException.printStackTrace();
        }
    }

    private static Vector rotateAroundAxisY(Vector vec, double d) {
        double var7 = Math.cos(d);
        double var9 = Math.sin(d);
        double var3 = vec.getX() * var7 + vec.getZ() * var9;
        double var5 = vec.getX() * -var9 + vec.getZ() * var7;
        return vec.setX(var3).setZ(var5);
    }

    public static List<Vector> getWingPoints() {
        return wingPoints;
    }
}
