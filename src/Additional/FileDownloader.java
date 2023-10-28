package Additional;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.*;

public class FileDownloader implements Runnable{
    String url;


    public FileDownloader(String url) {
        this.url = url;
    }

    @Override
    public void run() {
        URL ul;
        URLConnection connection;
        try {
            ul = new URL(url);
            connection = ul.openConnection();
            connection.setConnectTimeout(5000); // 5 seconds timeout for connection
            connection.setReadTimeout(10000);   // 10 seconds timeout for reading data
            connection.setRequestProperty("User-Agent", "MyJavaApp");

            String outputPath = "C:\\Users\\KarlB\\OneDrive\\Рабочий стол\\2 курс- 4семестр\\down.png";

            FileOutputStream outputStream = new FileOutputStream(outputPath);
            InputStream inputStream = connection.getInputStream();
            byte[] buffer = new byte[4096]; // You can adjust the buffer size as needed
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            inputStream.close();
            outputStream.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}

class MultiThreadedDownloader{
    public static void main(String[] args) {

        ExecutorService service = Executors.newFixedThreadPool(3);

        Runnable fileDownloader = new FileDownloader("https://techmonitor.ai/wp-content/uploads/sites/4/2017/02/shutterstock_552493561.webp");


        Runnable fileDownloader1 = new FileDownloader("https://inbusiness.kz/uploads/37/images/ctaXESBu.png");


        service.submit(fileDownloader);

        service.submit(fileDownloader1);

        service.shutdown();

        try {
            service.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            System.err.println("Error waiting for tasks to complete: " + e.getMessage());
        }

        System.out.println("All downloads are completed.");
    }

}
