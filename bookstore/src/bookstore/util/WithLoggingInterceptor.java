package bookstore.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Priority;
import javax.enterprise.context.Dependent;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import bookstore.annotation.WithLogging;


@Interceptor	// インターセプターの宣言
@Dependent
@WithLogging		// バインド用アノテーション
@Priority(Interceptor.Priority.APPLICATION) // 優先度
public class WithLoggingInterceptor {

	/**
	 * インターセプターのメソッド
	 * 
	 * @param ic 実行コンテキスト - 本来実行される処理。
	 * @return 本来実行される処理の戻り値
	 * @throws Exception 何らかの例外
	 */
	@AroundInvoke
	public Object invoke(InvocationContext ic) throws Exception {
		// ターゲットは、CDIのクライアントプロキシなので、スーパークラスを取得。
		String className = ic.getTarget().getClass().getSuperclass().getName();
		String methodName = ic.getMethod().getName();
		Logger log = Logger.getLogger(className);
		log.log(Level.INFO, "target: {0} # {1}", new Object[] { className, methodName });
		// メソッドの実行
		return ic.proceed();
	}

}
