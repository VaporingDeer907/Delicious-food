import javax.imageio.ImageIO;
import java.io.File;

public class PngTest {
    public static void main(String[] args) throws Exception {
        for (String a : args) {
            try {
                var img = ImageIO.read(new File(a));
                System.out.println((img == null ? "DECODE-NULL" : "OK " + img.getWidth() + "x" + img.getHeight()) + "  " + new File(a).getName());
            } catch (Exception e) {
                System.out.println("EXCEPTION " + e.getClass().getSimpleName() + "  " + new File(a).getName());
            }
        }
    }
}