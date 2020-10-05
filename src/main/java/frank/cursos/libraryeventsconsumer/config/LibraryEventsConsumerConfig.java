package frank.cursos.libraryeventsconsumer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@Configuration
//Configura el ContainerListenerFactory, FactoryConsummer
//lee propiedades de application.properties para hacer las cpnfiguraciones entre otras configuraciones
@EnableKafka
public class LibraryEventsConsumerConfig {
}
