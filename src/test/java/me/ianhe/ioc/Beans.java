package me.ianhe.ioc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by iHelin on 17/5/3 22:25.
 */
@Configuration
public class Beans {

    @Bean(name = "car")
    public Car buildCar() {
        Car car = new Car();
        car.setBrand("红旗CA72");
        car.setSpeed(200);
        return car;
    }
}
