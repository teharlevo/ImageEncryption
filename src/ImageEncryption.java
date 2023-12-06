import main.Assets;
import main.Entity;
import main.Scene;
import main.Window;
import modeling.Mash;
import modeling.Model;
import render.FrameBuffer;
import render.Texture;

public class ImageEncryption extends Scene{
    public static void main(String[] args)  {
        Window.scenes = new Scene[]{new ImageEncryption()};
        new Window(700, 700, "ImageEncryption",false);
    }
    
    public void init() {
        Texture tex = Assets.getTexture("4");
        FrameBuffer fb = new FrameBuffer(tex.width(),tex.height());
        new Entity().addComponent(new Model(tex, 0, 0, 1));
        fb.bind();
        render();
        fb.unbind();
        tex.saveImage("new", "png");
        Window.stop();
    }
    
    public void update(float dt) {}
}
