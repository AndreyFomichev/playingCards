import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;

public class Cards {
    private static int cntBg1, maxErrors = 20, bgColor, xx, yy ;
    private static String encoded = "", replacementStr = "";
    private static BufferedImage img, img_clone;
    private static String[][] symbols = {{ "2",  "3",  "4",  "5",  "6",  "7",  "8",  "9", "1",  "A",  "J",  "Q", "K",  "♠", "♣", "♥", "♦" },
                                         {"20", "10", "20", "10", "20", "15", "14", "20", "14", "20", "11", "20", "15", "s50", "c50", "h50", "d30"  } };

    private static File dir;

    private static int pixelsCount(BufferedImage image){
        int cnt = 0;
        for (int x = 0; x <= 60; x++)
            for (int y = 0; y <= 82; y++)
                if (image.getRGB(xx + x, yy + y) == bgColor)
                    cnt++;
        return  cnt;
    }

    private static String findSymbol(String symbol, int num) {
        int cntBg2;
        for (int deltax = 0; deltax <= 24; deltax++) {
            Graphics2D g = img_clone.createGraphics();
            g.drawImage(img, 0, 0, null);
            //g.setFont(g.getFont().deriveFont(32f)); //Arson-Pro-Medium-otf
            g.setFont(new Font("TJesterday Demo Regular", Font.PLAIN, (replacementStr.length() == 0 ? 30 : 46)));
            g.setColor(Color.GREEN);
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g.drawString(String.valueOf(symbol), xx - 2 + deltax + (replacementStr.length() == 0 ? 0 : 22)
                    , yy + 23 + (replacementStr.length() == 0 ? 0 : 54));
            g.dispose();
            cntBg2 = pixelsCount(img_clone);
            if (Math.abs(cntBg1 - cntBg2) <= maxErrors && cntBg1 > 500) {
                encoded = encoded + (replacementStr.length() == 0 ? symbol : replacementStr);
                System.out.println("FOUND symbol=" + symbol + ", num = " + num + ", xx=" + (xx + deltax) + ", cntBg1=" + cntBg1 + ", cntBg2=" + cntBg2 + ", diff=" + (cntBg1 - cntBg2) + ", bgcolor=" + new Color(bgColor).toString() + ", deltax=" + deltax);
                //..File f = new File(dir + "\\buf"+symbol+".png");
                //ImageIO.write(img_clone, "png", f);
                //      Desktop dt = Desktop.getDesktop();
                //  dt.open(f);
                //Thread.sleep(1000);
                return symbol;

            } else {
                //if (String.valueOf(symbol).equals("♣") ) {
                if ( //replacementStr.length() > 0  //||
                        symbol.equals("0")
                                || symbol.equals("1")
                ) {
                    System.out.println("symbol=" + symbol + ", num = " + num + ", xx=" + (xx + deltax) + ", cntBg1=" + cntBg1 + ", cntBg2=" + cntBg2 + ", diff=" + (cntBg1 - cntBg2) + ", bgcolor=" + new Color(bgColor).toString() + ", deltax=" + deltax);
                    //     File f = new File(dir + "\\buf.png");
                    //     ImageIO.write(img_clone, "png", f);
                    //     Desktop dt = Desktop.getDesktop();
                    //     dt.open(f);
                    //     Thread.sleep(1000);
                }

            }
        }
        return "";
    }

    private static void decodeCard(int num) {
        encoded = encoded.concat("/");
        xx = 143 + 72 * num;
        yy = 591;
        int symbolsFound = 0;
        bgColor = img.getRGB(xx + 3, yy + 10);
        Color bgC = new Color(bgColor);
        cntBg1 = pixelsCount(img);
        for (int a = 0; a < symbols[0].length; a++) {
            String symbol = symbols[0][a];
            if (symbols[1][a].matches("[-+]?\\d+")) {
                maxErrors = Integer.parseInt(symbols[1][a]);
                replacementStr = "";
            } else {
                replacementStr = symbols[1][a].substring(0, 1);
                if (symbols[1][a].substring(1).matches("[-+]?\\d+"))
                    maxErrors = Integer.parseInt(symbols[1][a].substring(1));
            }
            if ((symbolsFound >= 1 && replacementStr.length() == 0) || symbolsFound >= 2)
                continue;
            String s = findSymbol(symbol, num);
            if (s.equals("1"))
                encoded = encoded.concat("0");
            if (s.length() > 0 )
                symbolsFound++;

        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length ==0 ) {
            System.out.println("Файл run.bat параметром принимает путь до папки с картинками");
        }
        String path = args[0];
        dir = new File(path); //path указывает на директорию
        if (dir.listFiles() == null || dir.listFiles().length ==0) {
            System.out.println("No files found in path: " + path);
            System.exit(1);
        }
        List<File> lst = new ArrayList<>();

        for ( File file : dir.listFiles() )
            if ( file.isFile() )
                lst.add(file);

        for (File currFile:lst) {
            if (!currFile.getName().equals("6s5h10h.png")) continue;
            encoded = "";
            img = ImageIO.read(currFile); //зачитка картинки из файла
            img_clone = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

            decodeCard(0);
            decodeCard(1);
            decodeCard(2);
            //decodeCard(3);
            //decodeCard(4);
            System.out.println(currFile.getName() + " - " + encoded);
        }
    }
}