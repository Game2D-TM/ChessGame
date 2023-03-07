package chessgame.gui;

import chessgame.Game;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class ImageManager {
    public Map<String, BufferedImage> images = new HashMap<>();
    private static ImageManager instance;
    public static ImageManager getInstance() {
        if(instance == null) instance = new ImageManager();
        return instance;
    }
    private String DecodingImage(File file, String name, String format) {
        try {
            FileInputStream imgStream = new FileInputStream(file.getAbsolutePath());
            byte[] byteStream = Base64.getDecoder().decode(new String(imgStream.readAllBytes()));
            String imagePath = file.getParent() + "\\" + name + format;
            FileOutputStream fos = new FileOutputStream(imagePath);
            fos.write(byteStream);
            fos.close();
            return imagePath;
        } catch (Exception e) {
            return null;
        }
    }
    private boolean deleteImageFile(String path) {
        try {
            if (path != null) {
                File file = new File(path);
                file.delete();
            } else {
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("Error at: " + e.toString());
        }
        return false;
    }
    public void loadFolderFile(File folder) {
        File[] files = folder.listFiles();
        if(files != null && files.length > 0) {
            for(File file : files) {
                if(!file.getName().contains(".dat")) continue;
                String filename = file.getName().replace(".dat", "");
                String format = ".png";
                if(filename.equals("menu-background")) {
                    format = ".jpg";
                }
                String imagePath = DecodingImage(file, filename, format);
                images.put(filename, loadImage(imagePath));
                deleteImageFile(imagePath);
            }
        }
    }
    
    private ImageManager() {
        loadFolderFile(new File("res/images"));
    }
    
    public BufferedImage loadImage(String imageUrl) {
        try {
            return ImageIO.read(new File(imageUrl));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
