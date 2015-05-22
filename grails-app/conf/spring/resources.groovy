import com.bz.MongoSource

// Place your Spring DSL code here
beans = {

    mongoSource(MongoSource) {
        host = application.config.mongo.host ?: "localhost"
        port = application.config.mongo.port ?: 27017
        connectionsPerHost = application.config.mongo.connections_per_host ?: 10
        threadsAllowedToBlockForConnectionMultiplier = application.config.mongo.threads_allowed_to_block_for_connection_multiplier ?: 5
        maxWaitTime = application.config.mongo.max_wait_time ?: 120000
        connectTimeout = application.config.mongo.connect_timeout ?: 0
        socketTimeout = application.config.mongo.socket_timeout ?: 0
        socketKeepAlive = application.config.mongo.socket_keep_alive ?: false
        autoConnectRetry = application.config.mongo.auto_connect_retry ?: false
        maxAutoConnectRetryTime = application.config.mongo.max_Auto_connect_retry_time ?: 0
        safe = application.config.mongo.safe ?: false
        w = application.config.mongo.w ?: 0
        wtimeout = application.config.mongo.wtimeout ?: 0
        fsync = application.config.mongo.fsync ?: false
        j = application.config.mongo.j ?: false
    }
}
