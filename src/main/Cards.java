import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;

public class Cards {
    private static int cntBg1, maxErrors = 20, bgColor, xx, yy = 591;
    private static String encoded = "", replacementStr = "", path = "";
    private static BufferedImage img, img_clone;
    private static String[][] symbols = {{ "2", "6", "8",   "3",  "4",  "5",  "7",  "9",  "1",  "A", "J",  "Q",  "K",  "♦",   "♣",   "♠",   "♥", },
                                         {"15", "8", "17", "17", "20", "12", "10", "12", "12", "20", "8", "25", "15", "d9", "c50", "s46", "h50",  } };

    private static int pixelsCount(BufferedImage image) {
        int cnt = 0;
        for (int x = 0; x <= 60; x++)
            for (int y = 0; y <= 82; y++)
                if (image.getRGB(xx + x, yy + y) == bgColor)
                    cnt++;
        return cnt;
    }

    private static String findSymbol(String symbol, int num) throws IOException, InterruptedException {
        int cntBg2;
        for (int deltaY = -2; deltaY <= +1; deltaY++) {
            for (int deltaX = 0; deltaX <= 15; deltaX++) {
                Graphics2D g = img_clone.createGraphics();
                g.drawImage(img, 0, 0, null);
                g.setFont(new Font("Jesterday Demo Regular", Font.PLAIN, (replacementStr.length() == 0 ? 30 : 46)));
                g.setColor(Color.BLACK);
                g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g.drawString(String.valueOf(symbol), xx - 2 + deltaX + (replacementStr.length() == 0 ? 0 : 22)
                        , yy + 23 + deltaY + (replacementStr.length() == 0 ? 0 : 54));
                g.dispose();
                cntBg2 = pixelsCount(img_clone);
                if (Math.abs(cntBg1 - cntBg2) <= maxErrors && cntBg1 > 500) {
                    encoded = encoded + (replacementStr.length() == 0 ? symbol : replacementStr);
                    return symbol;
                }
            }
        }
        return "";
    }

    private static void decodeCard(int num) throws IOException, InterruptedException {
        xx = 143 + 72 * num;
        int symbolsFound = 0;
        bgColor = img.getRGB(xx + 3, yy + 10);
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
            if (s.length() > 0)
                symbolsFound++;
        }
    }

    public static void main(String[] args) throws Exception {
        List<File> lst = new ArrayList<>();
        for (File file : new File(args[0]).listFiles())
            if (file.isFile())
                lst.add(file);
        for (File currFile : lst) {
            encoded = "";
            img = ImageIO.read(currFile); //зачитка картинки из файла
            img_clone = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
            for(int z=0; z<=4; z++)
                decodeCard(z);
            System.out.println(currFile.getName() + " - " + encoded + (currFile.getName().substring(0, currFile.getName().indexOf('.')).equals(encoded) ? " (+)" : ""));
        }
    }
}