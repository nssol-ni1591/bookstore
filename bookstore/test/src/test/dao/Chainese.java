package test.dao;

import org.springframework.stereotype.Component;

@Component("ChaineseBId")
public class Chainese implements Speaker {

	public String say() {
		return "你好";
	}
}
