package pl.smartexplorer.scribe.services.mailer.broker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author
 * Karol Meksuła
 * 04-11-2018
 * */

@Component
public class MailBrokerFactory {

    @Value("${mail.broker}")
    private String brokerName;

    @Bean
    public MailBroker mailBroker() {
        if (brokerName.equals("rabbitmq")) {
            return new RabbitMailBroker();
        }

        return new SimpleMailBroker();
    }

}
