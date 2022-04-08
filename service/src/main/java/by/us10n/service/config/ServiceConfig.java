package by.us10n.service.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"by.us10n.service", "by.us10n.repository"})
public class ServiceConfig {
}
