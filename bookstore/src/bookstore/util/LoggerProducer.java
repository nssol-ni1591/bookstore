package bookstore.util;

import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import bookstore.annotation.SuperclassLog;

@Dependent
public class LoggerProducer {

	@Produces
	@Default
	public Logger getLogger(InjectionPoint ip) {
		return Logger.getLogger(ip.getMember().getDeclaringClass().getName());
	}

	@Produces
	@SuperclassLog
	public Logger getLoggerSuperclass(InjectionPoint ip) {
		return Logger.getLogger(ip.getMember().getDeclaringClass().getName());
	}

}
