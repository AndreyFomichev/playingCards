import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Cards {
/*//- BufferedImage img = ImageIO.read(f); - зачитка картинки из файла
//- ImageIO.write(img, "png", f); - запись картинки в файл
//- img.getWidth(); img.getHeight(); - рамеры картинки
//- BufferedImage img1 = img.getSubimage(x, y, w, h); - взятие области в картинке
//- img.getRGB(x, y); - взятие цвета точки по координате
//- Color c = new Color(img.getRGB(x, y)); c.getRed(); c.getGreen(); c.getBlue(); c.equals(c1) - работа с цветом точки
Если нужно ставить масть в HTML коде, то комбинация будет следующей:
    Для пики - &#9824;
    Для черви - &#9829;
    Для бубны - &#9830;
    Для трефы - &#9827;
*/
    private BufferedImage img;

    private void saveImage(File f, String name, int x, int y, int w, int h) throws IOException {
        img = ImageIO.read(f);  //- зачитка картинки из файла
        ImageIO.write(img, "png", new File("c:\\1\\b.png")); //- запись картинки в файл

    }

    public static void main(String[] args) throws IOException {
        String[] images;
        if (args.length ==0 ) {
            System.out.println("Файл run.bat параметром принимает путь до папки с картинками");
        }
        String path = args[0];

        File dir = new File(path); //path указывает на директорию
        List<File> lst = new ArrayList<>();
        for ( File file : dir.listFiles() ){
            if ( file.isFile() )
                lst.add(file);

        }
        //for (File f:lst)
        File f = lst.get(0);
        {
            saveImage(f, )
            BufferedImage b1 = img.getSubimage(152, 591,32, 24); //- взятие области в картинке
            ImageIO.write(b1, "png", new File("c:\\1\\b1.png")); //- запись картинки в файл
            BufferedImage b1 = img.getSubimage(152, 591,32, 24); //- взятие области в картинке



            BufferedImage b2 = img.getSubimage(224, 591,32, 24); //- взятие области в картинке
            ImageIO.write(b2, "png", new File("c:\\1\\b2.png")); //- запись картинки в файл
            BufferedImage b3 = img.getSubimage(289, 591,32, 24); //- взятие области в картинке
            ImageIO.write(b3, "png", new File("c:\\1\\b3.png")); //- запись картинки в файл

        }

        System.out.println("path=" + path+ ", found files: " + lst.size());

    }
}