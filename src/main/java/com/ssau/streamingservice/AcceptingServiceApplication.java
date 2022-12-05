package com.ssau.streamingservice;

import com.ssau.streamingservice.services.AcceptingService;
import com.ssau.streamingservice.services.VideoConverterService;
import com.ssau.streamingservice.services.VideoMakerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

public class AcceptingServiceApplication {

	public static void main(String[] args) throws IOException, InterruptedException {
		AcceptingService.startAccept(54339);
	}

}
