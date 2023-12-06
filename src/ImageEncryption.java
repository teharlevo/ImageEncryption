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

    public static int w,h,seed,encoded;
    
    public static void main(String[] args)  {
        Window.scenes = new Scene[]{new ImageEncryption()};
        String pfc = pasteFromClipboard();
        int eol = pfc.indexOf(",");
        w = Integer.parseInt(pfc.substring(0, eol).trim());

        int index = pfc.indexOf(",",eol) + 1;
        eol = pfc.indexOf(",",index);
        h = Integer.parseInt(pfc.substring(index, eol).trim());

        index = pfc.indexOf(",",eol) + 1;
        eol = pfc.indexOf(",",index);
        seed = Integer.parseInt(pfc.substring(index, eol).trim());

        index = pfc.indexOf(",",eol) + 1;
        encoded = Integer.parseInt(pfc.substring(index).trim());
        new Window(w,h, "ImageEncryption",false);
    }
    
    
    public void init() {
        getRenderer().getRIH().setIntsNames(new String[]{"CR"});
        getRenderer().getRIH().setInts(new int[]{encoded});
        getRenderer().getRIH().setFloatsNames(new String[]{"seed"});
        getRenderer().getRIH().setFloats(new float[]{seed/1000.0f});
        Texture tex = Assets.getTexture("img");
        FrameBuffer fb = new FrameBuffer(w,h);
        new Entity().addComponent(new Model(tex, 0, 0, -1));
        fb.bind();
        render();
        fb.unbind();
        fb.getTexturex().saveImage("newImg", "png");
        Window.stop();
    }
    
    public void update(float dt) {}


    public static String pasteFromClipboard() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        DataFlavor flavor = DataFlavor.stringFlavor;
        String text = "540,552,405671,1";
        //540,552,405671,-1
        if (clipboard.isDataFlavorAvailable(flavor)) {
            try {
                text = (String) clipboard.getData(flavor);
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
}
