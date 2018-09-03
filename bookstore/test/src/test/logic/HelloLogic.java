package test.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import test.dao.Speaker;

@Component
public class HelloLogic { //implements Hello {

	/*
	 * org.springframework.beans.factory.NoUniqueBeanDefinitionException: 
	 *  No qualifying bean of type [test.dao.Speaker] is defined: expected single matching bean but found 3:
	 *  ChaineseBId,english,JapaneseBId
	 */
	//@Autowired Speaker speaker1;
	@Autowired @Qualifier("english") Speaker speaker1;
	Speaker speaker2;
	@Autowired @Qualifier("ChaineseBId") Speaker speaker3;
	//@Autowired Hello logic;

	public HelloLogic() {
		System.out.println("called HelloLogic<init>");
	}
	public String hello1() {
		return speaker1 == null ? "null" : speaker1.say();
	}
	public String hello2() {
		return speaker2 == null ? "null" : speaker2.say();
	}
	public String hello3() {
		return speaker3 == null ? "null" : speaker3.say();
	}

	public void setSpeaker2(Speaker sp) {
		System.out.println("called setSpeaker: speaker2=" + sp);
		this.speaker2 = sp;
	}
}
