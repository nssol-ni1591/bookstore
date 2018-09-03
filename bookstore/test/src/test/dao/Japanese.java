package test.dao;

import org.springframework.context.annotation.Scope;

@Scope("prototype")
public class Japanese implements Speaker {

	public String say() {
		return "おはよう";
	}
}
