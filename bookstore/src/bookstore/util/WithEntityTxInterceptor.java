package bookstore.util;

import javax.annotation.Priority;
import javax.enterprise.context.Dependent;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import bookstore.annotation.WithEntityTx;


@Interceptor	// インターセプターの宣言
@Dependent
@WithEntityTx		// バインド用アノテーション
@Priority(Interceptor.Priority.APPLICATION) // 優先度
public class WithEntityTxInterceptor {

	/**
	 * インターセプターのメソッド
	 * 
	 * @param ic 実行コンテキスト - 本来実行される処理。
	 * @return 本来実行される処理の戻り値
	 * @throws Exception 何らかの例外
	 */
	@AroundInvoke
	public Object invoke(InvocationContext ic) throws Exception {
		Object target = ic.getTarget();
		if (target instanceof EntityTx) {
			EntityTx et = (EntityTx)target;
			Object rc = null;
			try {
				et.startEntityTx();
				// メソッドの実行
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
