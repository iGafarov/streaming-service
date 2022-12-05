package com.ssau.streamingservice;

import com.ssau.streamingservice.services.AcceptingService;
import java.io.IOException;

public class AcceptingServiceApplication {

	public static void main(String[] args) throws IOException, InterruptedException {
		AcceptingService.startAccept(54339);
	}

}
