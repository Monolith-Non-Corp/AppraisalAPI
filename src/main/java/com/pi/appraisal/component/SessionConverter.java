package com.pi.appraisal.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdConverter;
import com.pi.appraisal.util.Credentials;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SessionConverter extends StdConverter<String, Credentials> {

	@Override
	public Credentials convert(String value) {
		ObjectMapper mapper = new ObjectMapper();
		Credentials credentials;
		try {
			credentials = mapper.readValue(value, Credentials.class);
		} catch (IOException e) {
			e.printStackTrace();
			credentials = new Credentials() {
				@Override
				public boolean isExpired() {
					return true;
				}
			};
		}
		return credentials;
	}
}
