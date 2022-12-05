package com.ssau.streamingservice.services;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class AcceptingService {

    private static final String FILE_PATH = "tmp\\";
    private static final int FRAMES_COUNT = 200;

    public static void startAccept(int port) throws IOException, InterruptedException {

        Socket socket = new Socket("127.0.0.1", port);

        List<File> files = Arrays.stream(Objects.requireNonNull(new File("tmp").listFiles())).toList();
        for (File file : files) {
            file.delete();
        }

        JFrame mainWindow = getJFrame();
        JPanel rootPanel = new JPanel();
        mainWindow.add(rootPanel);

        JPanel imagePanel = new JPanel();
        rootPanel.add(imagePanel);

        try (DataInputStream rcv = new DataInputStream(new BufferedInputStream(socket.getInputStream()))) //never use DataStreams without buffering, too slow
        {
            int iteration = 0;
            for (int currentFrameNum = 0; currentFrameNum >= 0; ++currentFrameNum) {
                int frameWidth = rcv.readInt();     //read image with
                int frameHeight = rcv.readInt();    //read image height

                int[] pixelData = new int[frameWidth * frameHeight];
                for (int i = 0; i < pixelData.length; i++) {
                    pixelData[i] = rcv.readInt();   //read pixel data
                }

                BufferedImage frame = new BufferedImage(frameWidth, frameHeight, BufferedImage.TYPE_INT_RGB); //create image
                frame.setRGB(0, 0, frameWidth, frameHeight, pixelData, 0, frameWidth);  //set pixel data
                File outputFile = new File(FILE_PATH + "image" + iteration++ + ".jpg");
                ImageIO.write(frame, "jpg", outputFile);

                if (currentFrameNum > 0) {
                    File old = new File("tmp/image" + (currentFrameNum - 1) + ".jpg");
                    old.delete();
                }
                Thread.sleep(60);
                imagePanel.removeAll();

                JLabel imageHolder = new JLabel();
                imageHolder.setIcon(makeImageIcon(currentFrameNum));

                imagePanel.add(imageHolder);

                imagePanel.repaint();

                mainWindow.add(imagePanel);
                mainWindow.setVisible(true);
            }
        }
    }

    private static JFrame getJFrame(){
        JFrame jFrame = new JFrame(){};
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        int width = 1000;
        int height = 700;
        jFrame.setBounds(dimension.width/2 - width/2, dimension.height/2 - height/2, width, height);
        jFrame.setTitle("STREAMIKS");
        return jFrame;
    }

    private static ImageIcon makeImageIcon(int iteration) {
        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(new File("tmp/image" + iteration + ".jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ImageIcon(myPicture);
    }
}
