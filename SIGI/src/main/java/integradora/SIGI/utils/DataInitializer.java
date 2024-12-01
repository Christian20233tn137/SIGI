package integradora.SIGI.utils;

import integradora.SIGI.categoria.model.Categoria;
import integradora.SIGI.categoria.model.CategoriaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    CommandLineRunner initDatabase(CategoriaRepository categoriaRepository) {
        return args -> {
            // Crear y guardar categorías iniciales si no existen
            if (categoriaRepository.findByNameIgnoreCase("Electrónica").isEmpty()) {
                Categoria categoriaElectronica = new Categoria("Electrónica", "Categoría de productos electrónicos", true);
                categoriaRepository.save(categoriaElectronica);
                logger.info("Categoría 'Electrónica' creada");
            }

            if (categoriaRepository.findByNameIgnoreCase("Ropa").isEmpty()) {
                Categoria categoriaRopa = new Categoria("Ropa", "Categoría de prendas de vestir", true);
                categoriaRepository.save(categoriaRopa);
                logger.info("Categoría 'Ropa' creada");
            }

            if (categoriaRepository.findByNameIgnoreCase("Hogar").isEmpty()) {
                Categoria categoriaHogar = new Categoria("Hogar", "Categoría de artículos para el hogar", true);
                categoriaRepository.save(categoriaHogar);
                logger.info("Categoría 'Hogar' creada");
            }
        };
    }
}
