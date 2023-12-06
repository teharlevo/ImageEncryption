import main.Assets;
import main.Entity;
import main.Scene;
import main.Window;
import modeling.Model;
import render.FrameBuffer;
import render.Texture;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class ImageEncryption extends Scene{

    public static int w,h,seed;

    public static String pasteFromClipboard() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        DataFlavor flavor = DataFlavor.stringFlavor;
        String text = "500,500,4056741";
        if (clipboard.isDataFlavorAvailable(flavor)) {
            try {
                text = (String) clipboard.getData(flavor);
                System.out.println(text);
            } catch (UnsupportedFlavorException e) {
                System.out.println(e);
            } catch (IOException e) {
                System.out.println(e);
            }
          }
        return text;
    }

    public static void copy(String text) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection selection = new StringSelection(text);
        clipboard.setContents(selection, null);
    }
    
    public static void main(String[] args)  {
        Window.scenes = new Scene[]{new ImageEncryption()};
        new Window(5000,5000, "ImageEncryption",false);
    }
    
    
    public void init() {
        System.out.println(pasteFromClipboard());
        Texture tex = Assets.getTexture("unEncryption");
        FrameBuffer fb = new FrameBuffer(tex.width(),tex.height());
        new Entity().addComponent(new Model(tex, 0, 0, -1));
        fb.bind();
        render();
        fb.unbind();
        fb.getTexturex().saveImage("new", "png");
        Window.stop();
    }
    
    public void update(float dt) {}
}
