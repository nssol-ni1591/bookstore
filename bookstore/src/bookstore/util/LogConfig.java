package bookstore.util;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LogConfig {

	private static final Logger log = Logger.getLogger(LogConfig.class.getName());

	public LogConfig() {
		init();
	}

	private void init() {
		try {
			LogManager.getLogManager().readConfiguration(getClass().getResourceAsStream("/META-INF/logging.properties"));
			// ���̃N���X�Ɠ����p�b�P�[�W�ł͖����ꍇ�� /myapp/logging.properties �Ȃǐ�΃p�X�w��
		} catch (IOException e) {
			log.log(Level.SEVERE, "��O", e);
		}
	}

}
