package br.com.fiap.fiap_tcp5_ecommerce_payment_ms;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(FiapTcp5EcommercePaymentMsApplication.class);
	}

}
