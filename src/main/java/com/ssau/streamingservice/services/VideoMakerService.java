package com.ssau.streamingservice.services;

import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public abstract class VideoMakerService {

    private static final int FRAMES_COUNT = 200;
    private static final Dimension SCREEN_BOUNDS = new Dimension(640, 480);
    private static final String OUTPUT_FILE = "tmp\\movie.avi";
    private static final String FILE_PATH = "tmp\\";


    public static void makeVideo() throws IOException {

        final IMediaWriter writer = ToolFactory.makeWriter(OUTPUT_FILE);
        writer.addVideoStream(0, 0, ICodec.ID.CODEC_ID_MPEG4,
                SCREEN_BOUNDS.width / 2, SCREEN_BOUNDS.height / 2);
        long startTime = System.nanoTime();

        for (int currentFrameNum = 0; currentFrameNum < FRAMES_COUNT; ++currentFrameNum) {

            File currentImage = new File(FILE_PATH + "image" + currentFrameNum++ + ".jpg");
            BufferedImage bgrScreen = ImageIO.read(currentImage);
            System.out.println("time stamp = "+ (System.nanoTime() - startTime));
            bgrScreen = convertToType(bgrScreen, BufferedImage.TYPE_3BYTE_BGR);
            writer.encodeVideo(0, bgrScreen, System.nanoTime() - startTime,
                    TimeUnit.NANOSECONDS);
            // sleep for frame rate milliseconds
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // ignore
            }
        }
        writer.close();
    }

    public static BufferedImage convertToType(BufferedImage sourceImage,
                                              int targetType) {

        BufferedImage image;

        // if the source image is already the target type, return the source
        // image
        if (sourceImage.getType() == targetType) {
            image = sourceImage;
        }
        // otherwise create a new image of the target type and draw the new
        // image
        else {
            image = new BufferedImage(sourceImage.getWidth(),
                    sourceImage.getHeight(), targetType);
            image.getGraphics().drawImage(sourceImage, 0, 0, null);
        }

        return image;

    }

}
