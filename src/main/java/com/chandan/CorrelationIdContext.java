package com.chandan;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class CorrelationIdContext {

	private CorrelationIdContext() {

	}

	public static final InheritableThreadLocal<Map<String, String>> correlationIdContext = new InheritableThreadLocal<Map<String, String>>() {
		@Override
		protected java.util.Map<String, String> initialValue() {
			return new ConcurrentHashMap();
		};
	};

	public static boolean hasCorrelationId(String headerKey) {
		return correlationIdContext.get().containsKey(headerKey);
	}

	public static void set(String headerKey, String headerValue) {
		correlationIdContext.get().put(headerKey, headerValue);
	}

	public static String get(String headerName) {
		return correlationIdContext.get().get(headerName);
	}

	public static Set<String> getHeaderNames() {
		return correlationIdContext.get().keySet();
	}
	public static String clear(String headerName) {
		return correlationIdContext.get().remove(headerName);
	}
}
