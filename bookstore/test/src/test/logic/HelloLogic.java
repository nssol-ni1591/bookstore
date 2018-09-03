package test.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import test.dao.Speaker;

//@Component
// applicationContext.xmlに指定するのでAnnotationは必要なし
public class HelloLogic { //implements Hello {

	/* bean名の省略はできない
	 * なぜならば、Speakerを継承したサブクラスは3つ存在する
	 * 
	 * org.springframework.beans.factory.NoUniqueBeanDefinitionException: 
	 *  No qualifying bean of type [test.dao.Speaker] is defined: expected single matching bean but found 3:
	 *  ChaineseBId,english,JapaneseBId
	 */
	//@Autowired Speaker speaker1;

	// デフォルトのbean名参照:
	@Autowired @Qualifier("english") Speaker speaker1;

	// Autowiredを指定しない場合:
	/*
	 * 本来ならば、HelloLogicはSpring制御のクラスなので、そのインスタンス変数はAutowiredであるべきで、
	 * 以下のような使い方は使わない。※ここではテストのため
	 * speaker2ではautowiredを指定していないため、aplicationContext.xmlにDI指定が必要となり、
	 * 結果として、このクラスをapplicationContext.xmlに定義する必要がある
	 * xmlに定義すると、参照側からはdefaultのbean名を指定しても別定義と認識されるため、
	 * 参照側（この例ではHelloLogic）にはQualifierの指定が必要になってしまう
	 * 
	 * defaultのbean名を指定した場合に発生する例外：
	 * org.springframework.beans.factory.NoUniqueBeanDefinitionException:
 	 *  No qualifying bean of type [test.logic.HelloLogic] is defined: expected single matching bean but found 2:
 	 *  helloLogic,hellologic
	 */
	Speaker speaker2;

	// applicationContext.xmlに定義したbean名で参照
	@Autowired @Qualifier("ChaineseBId") Speaker speaker3;

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
