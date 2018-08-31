package test.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import test.dao.Speaker;

@Component
public class HelloLogic implements Hello {

	@Autowired Speaker speaker;

	public HelloLogic() {
		System.out.println("called HelloLogic<init>");
	}
	public String hello() {
		return speaker.say();
	}

	public void setSpeaker(Speaker sp) {
		System.out.println("called setSpeaker: speaker=" + speaker.say());
		speaker = sp;
	}
}
