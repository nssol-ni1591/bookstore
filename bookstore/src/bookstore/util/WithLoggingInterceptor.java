package bookstore.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Priority;
import javax.enterprise.context.Dependent;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import bookstore.annotation.WithLogging;


@Interceptor	// �C���^�[�Z�v�^�[�̐錾
@Dependent
@WithLogging		// �o�C���h�p�A�m�e�[�V����
@Priority(Interceptor.Priority.APPLICATION) // �D��x
public class WithLoggingInterceptor {

	/**
	 * �C���^�[�Z�v�^�[�̃��\�b�h
	 * 
	 * @param ic ���s�R���e�L�X�g - �{�����s����鏈���B
	 * @return �{�����s����鏈���̖߂�l
	 * @throws Exception ���炩�̗�O
	 */
	@AroundInvoke
	public Object invoke(InvocationContext ic) throws Exception {
		// �^�[�Q�b�g�́ACDI�̃N���C�A���g�v���L�V�Ȃ̂ŁA�X�[�p�[�N���X���擾�B
		String className = ic.getTarget().getClass().getSuperclass().getName();
		String methodName = ic.getMethod().getName();
		Logger log = Logger.getLogger(className);
		log.log(Level.INFO, "target: {0} # {1}", new Object[] { className, methodName });
		// ���\�b�h�̎��s
		return ic.proceed();
	}

}
