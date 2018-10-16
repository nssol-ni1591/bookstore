package bookstore.util;

import javax.annotation.Priority;
import javax.enterprise.context.Dependent;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import bookstore.annotation.WithEntityTx;


@Interceptor	// �C���^�[�Z�v�^�[�̐錾
@Dependent
@WithEntityTx		// �o�C���h�p�A�m�e�[�V����
@Priority(Interceptor.Priority.APPLICATION) // �D��x
public class WithEntityTxInterceptor {

	/**
	 * �C���^�[�Z�v�^�[�̃��\�b�h
	 * 
	 * @param ic ���s�R���e�L�X�g - �{�����s����鏈���B
	 * @return �{�����s����鏈���̖߂�l
	 * @throws Exception ���炩�̗�O
	 */
	@AroundInvoke
	public Object invoke(InvocationContext ic) throws Exception {
		Object target = ic.getTarget();
		if (target instanceof EntityTx) {
			EntityTx et = (EntityTx)target;
			Object rc = null;
			try {
				et.startEntityTx();
				// ���\�b�h�̎��s
				rc = ic.proceed();
				return rc;
			}
			finally {
				et.stopEntityTx();
			}
		}
		throw new IllegalArgumentException("not implemments ExtityTx");
	}

}
