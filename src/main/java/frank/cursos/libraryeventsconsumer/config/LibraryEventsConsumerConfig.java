package frank.cursos.libraryeventsconsumer.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

@Configuration
//Configura el ContainerListenerFactory, FactoryConsummer
//lee propiedades de application.properties para hacer las cpnfiguraciones entre otras configuraciones
@EnableKafka
public class LibraryEventsConsumerConfig {

    /**
     * Sobrescribe el ConcurrentKafkaListenerContainerFactory bean, en la propiedad setAckMode
     * para que el commit del offset sea manual y no batch
     *
     * Mediante el metodo factory.setConcurrency, permite atender de manera paralela por cada thread indicado una particion
     * Si se esta corriendo la applicacion en multiples nodos, no es necesaria esta opcion ya que las particiones se distribuiran en los nodos
    * */
    @Bean
    @ConditionalOnMissingBean(name = "kafkaListenerContainerFactory")
    ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
            ObjectProvider<ConsumerFactory<Object, Object>> kafkaConsumerFactory,
            KafkaProperties properties) {
        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        configurer.configure(factory, kafkaConsumerFactory
                .getIfAvailable(() -> new DefaultKafkaConsumerFactory<>(properties.buildConsumerProperties())));
        factory.setConcurrency(3);
        //factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        return factory;
    }
}
