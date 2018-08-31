package test.dao;

import org.springframework.stereotype.Component;

@Component
public class English implements Speaker {

	public String say() {
		return "Hello";
	}
}
