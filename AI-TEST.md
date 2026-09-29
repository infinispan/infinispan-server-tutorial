# Testing Instructions

## Test Framework
* **Framework:** JUnit 5 (Jupiter)
* **Server test driver:** `infinispan-server-testdriver-core` with `InfinispanServerExtension`
* **Requires:** Docker (tests use testcontainers to spin up an Infinispan server)

## Running Tests
* **All tests:** `mvn clean verify`
* **Skip tests:** `mvn clean install -DskipTests=true`

## Test Structure

Tests live in `src/test/java/org/infinispan/tutorial/services/temperature/`:
* `TemperatureLoaderTest` — verifies temperature loading via Hot Rod
* `LocationWeatherLoaderTest` — verifies weather data loading with Protobuf serialization

## Writing Tests

Use the `InfinispanServerExtension` to get a testcontainer-backed server:

```java
@RegisterExtension
static InfinispanServerExtension infinispanServerExtension = InfinispanServerExtensionBuilder.server();
```

Create a `RemoteCacheManager` from the extension and register the ProtoStream serialization context:

```java
private RemoteCacheManager createRemoteCacheManager() {
    ConfigurationBuilder clientConfiguration = new ConfigurationBuilder();
    RemoteCacheManager remoteCacheManager = infinispanServerExtension.hotrod()
        .withClientConfiguration(clientConfiguration).createRemoteCacheManager();
    SerializationContext serCtx = MarshallerUtil.getSerializationContext(remoteCacheManager);
    LocationWeatherSchema schema = new LocationWeatherSchemaImpl();
    schema.registerSchema(serCtx);
    schema.registerMarshallers(serCtx);
    return remoteCacheManager;
}
```

## Branch Considerations

* On `solution`: tests contain full implementations.
* On `main`: `TemperatureLoaderTest` is a skeleton with `// STEP` placeholder. `LocationWeatherLoaderTest` does not exist (users create it).
