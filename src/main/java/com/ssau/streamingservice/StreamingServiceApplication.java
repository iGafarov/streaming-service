package com.ssau.streamingservice;

import com.ssau.streamingservice.services.StreamingService;

import java.io.IOException;

public class StreamingServiceApplication {

	public static void main(String[] args) throws IOException {
		StreamingService.startWebcamStreaming(54339);
	}

}
