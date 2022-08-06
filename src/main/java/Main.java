import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import java.io.File;
import java.net.URL;
import java.util.List;

public class Main {
    private static final String imageFolder = "data/images/";
    private static final String imagePattern = "[^\\s]+(\\.(?i)(jpe?g|png|gif|bmp))$";

    public static void main(String[] args) {
        try {
            List<String> src = Jsoup.connect("https://ismet.kz").get().getElementsByTag("img").stream()
                    .filter(e -> e.absUrl("src").matches(imagePattern))
                    .map(e -> e.absUrl("src").trim())
                    .toList();
            src.forEach(i -> System.out.println("Картинка найдена. URL: " + i));
            downloadImage(src);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void downloadImage(List<String> imageLink) throws Exception {
        for (String element : imageLink) {
            String imageName = element.substring(element.lastIndexOf("/") + 1 );
            FileUtils.copyURLToFile(new URL(element), new File(imageFolder + imageName), 5000, 5000);
            System.out.println("Качаем картинку - " + imageName + " | URL - " + element);
        }
    }
}