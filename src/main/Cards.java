import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Pixels {

    public Pixels(int[] whites, int[] blacks) {
        this.whites = whites;
        this.blacks = blacks;
    }
    public int[] getWhites() { return whites; }
    //public void setWhites(int[] whites) { this.whites = whites;  }
    public int[] getBlacks() { return blacks; }
    //public void setBlacks(int[] blacks) { this.blacks = blacks; }
    int[] whites;
    int[] blacks;
}

public class Cards {
    /* BufferedImage img = ImageIO.read(f); - зачитка картинки из файла
       ImageIO.write(img, "png", f); - запись картинки в файл
       img.getWidth(); img.getHeight(); - рамеры картинки
       BufferedImage img1 = img.getSubimage(x, y, w, h); - взятие области в картинке
       img.getRGB(x, y); - взятие цвета точки по координате
       Color c = new Color(img.getRGB(x, y)); c.getRed(); c.getGreen(); c.getBlue(); c.equals(c1) - работа с цветом точки
    Если нужно ставить масть в HTML коде, то комбинация будет следующей:
        Для пики - &#9824;
        Для черви - &#9829;
        Для бубны - &#9830;
        Для трефы - &#9827;
    */
    private static String encoded = " ";
    private static BufferedImage img;
    private static File currFile;
    private static Pixels pixels;
    private static Map<String, Pixels> symbols = new HashMap<>() ;
    private static File dir;

    private static void decodeImage(String name, int x, int y, int w, int h) throws IOException {
        int color;
        boolean founded;
        BufferedImage buf = img.getSubimage(x, y,w, h); //- взятие области в картинке
        ImageIO.write(buf, "png", new File(dir + "\\buf.png")); //- запись картинки в файл
        for (String symbol : symbols.keySet()) {
            founded = true;
            pixels = symbols.get (symbol);
            for (int q = 0; q < pixels.getWhites().length; q += 4) {
                for (int i = pixels.getWhites()[q]; i <= pixels.getWhites()[q] + pixels.getWhites()[q + 2]; i++) {
                    for (int j = pixels.getWhites()[q + 1]; j <= pixels.getWhites()[q + 1] + pixels.getWhites()[q + 3]; j++) {
                         try {
                             int col  = buf.getRGB(i, j);
                             Color c = new Color(buf.getRGB(i, j));
                             int red = c.getRed();
                             int green = c.getGreen();
                             int blue = c.getBlue();
                             if (c.getRed() < 240 || c.getGreen() < 240 || c.getBlue() < 240)  {
                                 founded = false;
                                 break;
                             }
                         }catch(Exception e) {
                             System.out.println(symbol + ", q=" +q + ", i="+i+",j="+j + ": " + e.getMessage());
                         }
                    }

                }
            }
            if (founded) {
                encoded = encoded.concat(symbol);
                //System.out.println("founded=" + founded);
                return;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        symbols.put("6",  new Pixels(new int[]{6,0,0,9,   6,11,0,12, 7,0,0,6,   7,18,0,5, 12,8,20,0}, new int[]{}));
        symbols.put("Q",  new Pixels(new int[]{0,0,6,24, 12,8,12,3, 14,12,3,5, 15,5,6,2, 0,24,30,0, 30,0,2,24  }, new int[]{}));
        symbols.put("10", new Pixels(new int[]{6,5,0,17,  12,0,0,7,  12,14,0,9 }, new int[]{8,0,0,21}));
        symbols.put("7",  new Pixels(new int[]{5,0,0,23,  5,5,9,0,   23,0,0,23, 16,16,0,7}, new int[]{}));
        symbols.put("K",  new Pixels(new int[]{6,0,0,23,  11,0,0,8,  11,17,0,6, 20,8,0,5}, new int[]{}));
        symbols.put("s",  new Pixels(new int[]{1,0,0,15,  2,0,0,13,  3,0,0,12,  4,0,0,11, 5,0,0,10,  6,0,0,9, 7,0,0,8, 8,0,0,7, 0,28,12,0, 18,28,13,0, 23,0,9,7 }, new int[]{}));

        if (args.length ==0 ) {
            System.out.println("Файл run.bat параметром принимает путь до папки с картинками");
        }
        String path = args[0];

        dir = new File(path); //path указывает на директорию
        if (dir.listFiles() ==null || dir.listFiles().length ==0) {
            System.out.println("No files found in path: " + path);
            System.exit(1);
        }
        List<File> lst = new ArrayList<>();

        for ( File file : dir.listFiles() )
            if ( file.isFile() )
                lst.add(file);

        for (File currFile:lst) {
            String s= currFile.getName();
            if (!currFile.getName().equals("10.png")) continue;
            encoded = "";
            img = ImageIO.read(currFile); //зачитка картинки из файла
            decodeImage("a1",145, 591,34, 33);
            decodeImage("a2",145 + 24, 591 + 43,33, 33);

            decodeImage("b1",217, 591,34, 33);
            decodeImage("b2",217 + 24, 591 + 43,33, 33);

            decodeImage("c1",289, 591,34, 33);
            decodeImage("c2",289 + 24, 591 + 43,33, 33);

            decodeImage("d1",361, 591,34, 33);
            decodeImage("d2",361 + 24, 591 + 43,33, 33);

            decodeImage("e1",433, 591,34, 33);
            decodeImage("e2",433 + 24, 591 + 43,33, 33);
            System.out.println(currFile.getName() + " - " + encoded);
        }
    }
}