import io.perezalcolea.hollow.ConsumerHandler
import io.perezalcolea.hollow.ConsumerModule
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ratpack.health.HealthCheckHandler
import ratpack.rx.RxRatpack
import ratpack.service.Service
import ratpack.service.StartEvent

import static ratpack.groovy.Groovy.ratpack

final Logger logger = LoggerFactory.getLogger(Ratpack.class)

ratpack {
    bindings {
        module new ConsumerModule()
        bindInstance Service, new Service() {
            @Override
            void onStart(StartEvent event) throws Exception {
                logger.info "Initializing RX"
                RxRatpack.initialize()
            }
        }
    }

    handlers {
        get("health-check/:name?", new HealthCheckHandler())
        all chain(registry.get(ConsumerHandler))
    }
}