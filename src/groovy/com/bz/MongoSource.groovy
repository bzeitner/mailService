package com.bz

import com.mongodb.Mongo
import com.mongodb.MongoOptions
import com.mongodb.ServerAddress
import org.springframework.data.authentication.UserCredentials
import org.springframework.data.mongodb.core.MongoTemplate

/**
 * Mongo datasource bean
 */
class MongoSource {

  String dbName = "mail"

  // Server address
  String host = "localhost"
  int port = 27017

  // MongoOptions
  int connectionsPerHost = 10
  int threadsAllowedToBlockForConnectionMultiplier = 5
  int maxWaitTime = 120000
  int connectTimeout = 0
  int socketTimeout = 0
  boolean socketKeepAlive = false
  boolean autoConnectRetry = false
  long maxAutoConnectRetryTime = 0  // 15000 if autoConnectRetry is TRUE
  boolean safe = false
  int w = 0
  int wtimeout = 0
  boolean fsync = false
  boolean j = false


  private MongoTemplate mongoTemplate

  public MongoSource()
  {
    Mongo mongo = new Mongo(new ServerAddress(host, port), mongoOptions())
    //Mongo is set up to only allow connetions from localhost anyways....
    //So no account credentials required

    mongoTemplate = new MongoTemplate(mongo, dbName, null)
  }



  public MongoTemplate mongoTemplate()
  {
    return mongoTemplate
  }


  private MongoOptions mongoOptions()
  {
    MongoOptions mongoOptions = new MongoOptions()
    mongoOptions.connectionsPerHost = connectionsPerHost
    mongoOptions.threadsAllowedToBlockForConnectionMultiplier = threadsAllowedToBlockForConnectionMultiplier
    mongoOptions.maxWaitTime = maxWaitTime
    mongoOptions.connectTimeout = connectTimeout
    mongoOptions.socketTimeout = socketTimeout
    mongoOptions.socketKeepAlive = socketKeepAlive
    mongoOptions.autoConnectRetry = autoConnectRetry
    mongoOptions.maxAutoConnectRetryTime = (autoConnectRetry && maxAutoConnectRetryTime == 0) ? 15000 : maxAutoConnectRetryTime
    mongoOptions.safe = safe
    mongoOptions.w = w
    mongoOptions.wtimeout = wtimeout
    mongoOptions.fsync = fsync
    mongoOptions.j = j
    return mongoOptions
  }

}
