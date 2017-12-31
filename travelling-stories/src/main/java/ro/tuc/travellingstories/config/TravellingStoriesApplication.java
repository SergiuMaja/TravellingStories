package ro.tuc.travellingstories.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import ro.tuc.travellingstories.listener.EmailSendListener;

@SpringBootApplication
@ComponentScan(basePackages = "ro.tuc.travellingstories")
@EnableJpaRepositories(basePackages = "ro.tuc.travellingstories.repositories")
@EnableTransactionManagement
@EntityScan(basePackages = "ro.tuc.travellingstories.entities")
public class TravellingStoriesApplication {

	public static final String SEND_EMAIL_MESSAGE_QUEUE = "email-sending-queue";
	
	@Bean
	Queue emailQueue() {
		return new Queue(SEND_EMAIL_MESSAGE_QUEUE, false);
	}
	
	@Bean
	TopicExchange exchange() {
		return new TopicExchange("spring-boot-exchange");
	}
	
	@Bean
	Binding bindingSend(TopicExchange exchange, Queue emailQueue) {
		return BindingBuilder.bind(emailQueue).to(exchange).with(SEND_EMAIL_MESSAGE_QUEUE);
	}
	
	@Bean
	SimpleMessageListenerContainer containerSend(ConnectionFactory connectionFactory, MessageListenerAdapter sendListenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(SEND_EMAIL_MESSAGE_QUEUE);
		container.setMessageListener(sendListenerAdapter);
		return container;
	}
	
	@Bean
	MessageListenerAdapter sendListenerAdapter(EmailSendListener consumer) {
		return new MessageListenerAdapter(consumer, "receiveMessage");
	}
	
	public static void main(String[] args) {
		SpringApplication.run(TravellingStoriesApplication.class, args);
	}
}
