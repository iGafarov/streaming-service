package com.ssau.streamingservice.services;

import ws.schild.jave.*;

import java.io.File;
import java.io.IOException;

public abstract class VideoConverterService {

    private static final VideoSize VIDEO_SIZE = new VideoSize(640, 480);

    private static final String CODEC = "h264";
    private static final String TARGET_FORMAT = "mp4";
    private static final int BIT_RATE = 160000;
    private static final int FRAME_RATE = 15;

    private static final String FILE_PATH = "tmp\\";
    private static final String RESULT_PATH = "src\\main\\resources\\static\\";


    public static void convertVideo() throws IOException {

        File source = new File(FILE_PATH + "movie.avi");
        File target = new File(RESULT_PATH + "movie.mp4");

        /* Set Video Attributes for conversion*/
        VideoAttributes video = new VideoAttributes();
        video.setCodec(CODEC);
        video.setX264Profile(VideoAttributes.X264_PROFILE.BASELINE);
        // Here 160 kbps video is 160000
        video.setBitRate(BIT_RATE);
        // More the frames more quality and size, but keep it low based on devices like mobile
        video.setFrameRate(FRAME_RATE);
        video.setSize(VIDEO_SIZE);

        /* Step 4. Set Encoding Attributes*/
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat(TARGET_FORMAT);
        attrs.setVideoAttributes(video);

        try {
            Encoder encoder = new Encoder();
            MultimediaObject obj = new MultimediaObject(source);
            encoder.encode(obj, target, attrs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
