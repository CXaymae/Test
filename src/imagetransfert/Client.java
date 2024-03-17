package imagetransfert;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;



public class Client {
    public static void main(String[] args) {
        try {
            // Create a file chooser
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Choose an image to transfer");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif"));

            // Show the file chooser dialog
            int returnValue = fileChooser.showOpenDialog(null);

            // If the user selects a file
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                // Get the selected file
                Path selectedFilePath = fileChooser.getSelectedFile().toPath();

                // Read the bytes of the selected image file
                byte[] imageData = Files.readAllBytes(selectedFilePath);

                // Establish connection to the server
                Socket socket = new Socket("localhost", 1234);
                OutputStream outputStream = socket.getOutputStream();

                // Write the image data to the server
                outputStream.write(imageData);
                outputStream.flush();
                outputStream.close();
                socket.close();
                System.out.println("Image sent successfully.");
            } else {
                System.out.println("No file selected.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

