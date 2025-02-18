import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Paths;
import java.util.Scanner;

public class FileDownloader {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(" !!================Welcome to Download Manager================!!");
        System.out.println();

        while (true) {
            System.out.println("1. Download ");
            System.out.println("2. Exit");

            System.out.print("Choose Your Option:- ");
            int value = sc.nextInt();

            switch (value) {
                case 1:
                    System.out.println();
                    downlaodHelper();
                    break;
                case 2:
                    System.out.println("Exiting ... ");
                    sc.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println(" ");
            }
        }
    }

    public static String getFileNameFromURL(String fileURL) {
        return Paths.get(fileURL).getFileName().toString(); // Extracts the file name from the URL
    }

    public static void downlaodHelper() {
        System.out.println("Enter the File Url To Download :- ");
        String File_Url = sc.next();
        String currentDirectory = System.getProperty("user.dir");

        // adding the directory in downloads folder
        String destinationPath = currentDirectory + "/download" + File.separator + getFileNameFromURL(File_Url);
        System.out.println();
        // downlaoding Starts Here !!
        try {
            System.out.println("Downloading..");
            downlaod(File_Url, destinationPath);
            System.out.println();
            System.out.println("Download Completed !");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void downlaod(String filename, String destinationAddress) throws IOException {
        @SuppressWarnings("deprecation")
        URL url = new URL(filename);
        URLConnection connection = url.openConnection();

        // setting up connection Timeouts
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);


        int fileSize = connection.getContentLength(); // length of Download File

        int filesizeinMb = fileSize / (1024 * 1024);

        int totalBytesRead = 0;

        try {
            InputStream stream = connection.getInputStream(); // taking input

            try (FileOutputStream output = new FileOutputStream(destinationAddress)) {
                byte[] buffer = new byte[4096];
                int byteRead;
                while ((byteRead = stream.read(buffer)) != -1) {
                    totalBytesRead += byteRead;
                    output.write(buffer, 0, byteRead);
                    progressBar(totalBytesRead, filesizeinMb);
                }
            }
        } catch (Exception e) {
            System.out.println("Error Downlaoding :- " + e.getMessage());
        }
    }

    public static void progressBar(int downlaodedBytes, int totalsize) {
        // calculate the progress up till now

        double downloadedinMb = downlaodedBytes / (1024 * 1024);

        int progress = (int) (downloadedinMb / totalsize * 100);

        StringBuilder progressBar = new StringBuilder("[");

        int barlength = 50;
        int progresstillnow = (int) ((double) barlength * progress / 100);

        for (int i = 0; i < barlength; i++) {
            if (i < progresstillnow) {
                progressBar.append("=");
            } else {
                progressBar.append(" ");
            }
        }
        progressBar.append("] ");

        // Print the progress bar
        System.out.print("\r" + progressBar.toString() + progress + " % " + " ( " + downloadedinMb + " MB / "
                + totalsize + " MB ) ");

    }
}

// downlaod
// https://github.com/obsidianmd/obsidian-releases/releases/download/v1.8.4/Obsidian-1.8.4.exe
