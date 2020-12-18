import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    private static BufferedImage img;
    private static File currFile;

    private static void saveImage(String name, int x, int y, int w, int h) throws IOException {
        BufferedImage b1 = img.getSubimage(x, y,w, h); //- взятие области в картинке
        ImageIO.write(b1, "png", new File("c:\\1\\" + name + ".png")); //- запись картинки в файл
    }

    public static void main(String[] args) throws IOException {
        String[] images;
        if (args.length ==0 ) {
            System.out.println("Файл run.bat параметром принимает путь до папки с картинками");
        }
        String path = args[0];

        File dir = new File(path); //path указывает на директорию
        if (dir.listFiles() ==null || dir.listFiles().length ==0) {
            System.out.println("No files found in path: " + path);
            System.exit(1);
        }
        List<File> lst = new ArrayList<>();

        for ( File file : dir.listFiles() ){
            if ( file.isFile() )
                lst.add(file);
       }
        //for (File f:lst)
        currFile = lst.get(0);
        {
            img = ImageIO.read(currFile); //зачитка картинки из файла
            saveImage("0image", 0,0,img.getWidth(), img.getHeight());
            saveImage("a1",145, 591,34, 24); //- взятие области в картинке
            saveImage("a2",169, 634,33, 33); //- взятие области в картинке

            saveImage("b1",217, 591,34, 24); //- взятие области в картинке
            //saveImage("a2",217, 591,34, 24); //- взятие области в картинке

            saveImage("c1",289, 591,34, 24); //- взятие области в картинке
            //saveImage("a3",289, 591,34, 24); //- взятие области в картинке
            saveImage("d1",361, 591,34, 24); //- взятие области в картинке
            saveImage("e1",433, 591,34, 24); //- взятие области в картинке

            //saveImage("b1",152, 591,17, 24); //- взятие области в картинке
            //saveImage("b2",224, 591,34, 24); //- взятие области в картинке
            //saveImage("b3",289, 591,34, 24); //- взятие области в картинке

        }

        System.out.println("path=" + path+ ", found files: " + lst.size());

    }
}