package com.chandan;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class CorrelationIdValve extends ValveBase {

	private final Logger log = LoggerFactory.getLogger(CorrelationIdValve.class);
	public final String key ="CORRELATION-ID";
	
	@Override
	public void invoke(Request request, Response response) throws IOException, ServletException {
		try {
			String requestCorrelationId = request.getHeader(key);
			if(!Strings.isNotEmpty(requestCorrelationId)) {
				String randomId = UUID.randomUUID().toString().replace("-", "").substring(0,24);
				requestCorrelationId = randomId;
				log.trace("new id generated {} - {}",key, randomId);
			}
			CorrelationIdContext.set(key, requestCorrelationId);
			response.setHeader(key, requestCorrelationId);
			MDC.put("CORRELATION", requestCorrelationId);
		} catch(Exception e) {
			log.info(e.getMessage());
		} finally {
			try {				
				getNext().invoke(request, response);
			}finally {
				try {
					String crId = CorrelationIdContext.clear(key);
					MDC.remove("CORRELATION");
					log.trace("clear correlationid {} - {}",key,crId);
				}catch(Exception e) {
					log.info(e.getMessage());
				}
			}
		}
		
	}

}
