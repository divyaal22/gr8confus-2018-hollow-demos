package io.perezalcolea.hollow

import com.google.inject.AbstractModule
import com.google.inject.Scopes

class ConsumerModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(HollowConsumerService).in(Scopes.SINGLETON)
        bind(ConsumerHandler).in(Scopes.SINGLETON)
    }
}
