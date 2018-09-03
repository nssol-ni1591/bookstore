package test.dao;

//@Component("ChaineseBId")
// applicationContext.xmlに定義されているので必要なし
public class Chainese implements Speaker {

	public String say() {
		return "你好";
	}
}
