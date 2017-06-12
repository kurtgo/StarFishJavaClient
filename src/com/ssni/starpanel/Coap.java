package com.ssni.starpanel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.Utils;


public class Coap {

	/*
	 * Application entry point.
	 * 
	 */	
	public static void get(URI uri, String outfile) {
		
		CoapClient client = new CoapClient(uri);

		CoapResponse response = client.get();
		
		if (response!=null) {
			
			System.out.println(response.getCode());
			System.out.println(response.getOptions());
			if (outfile != null) {
				try (FileOutputStream out = new FileOutputStream(outfile)) {
					out.write(response.getPayload());
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println(response.getResponseText());
				
				System.out.println(System.lineSeparator() + "ADVANCED" + System.lineSeparator());
				// access advanced API with access to more details through
				// .advanced()
				System.out.println(Utils.prettyPrint(response));
			}
		} else {
			System.out.println("No response received.");
		}
	}

}
