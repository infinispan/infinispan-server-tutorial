## Tech Stack
* **Java Version:** 17+ (builds with JDK 17 through 25)
* **Build Tool:** Maven (single module)
* **Infinispan Version:** 16.2.x (set via `version.infinispan` property in pom.xml)
* **Serialization:** ProtoStream (annotation processor generates `*Impl` classes at compile time)
* **Key Frameworks:** Infinispan Hot Rod Client, ProtoStream, JUnit 5

## Project Structure

The tutorial builds a Weather System with four applications:
* `TemperatureLoaderApp` — loads temperature data into the `temperature` cache
* `TemperatureMonitorApp` — monitors temperature changes via client listeners
* `WeatherLoaderApp` — loads weather data (complex objects) into the `weather` cache
* `WeatherFinderApp` — searches weather data using Ickle queries and continuous queries

### Source Layout
* `src/main/java/org/infinispan/tutorial/client/` — application entry points
* `src/main/java/org/infinispan/tutorial/data/` — data model (`LocationWeather` record, `WeatherCondition` enum)
* `src/main/java/org/infinispan/tutorial/db/` — data source connector and ProtoStream schema definition
* `src/main/java/org/infinispan/tutorial/services/` — service implementations (loaders, monitors, search)
* `src/main/resources/` — cache configuration XML (`temperatureCacheConfig.xml`, `weatherCacheConfig.xml`)
* `README.adoc` — the tutorial documentation (AsciiDoc)

## Build Commands
* **Build (skip tests):** `mvn clean install -DskipTests=true`
* **Run temperature loader:** `mvn exec:java -Dexec.mainClass=org.infinispan.tutorial.client.temperature.TemperatureLoaderApp`
* **Run temperature monitor:** `mvn exec:java -Dexec.mainClass=org.infinispan.tutorial.client.temperature.TemperatureMonitorApp`
* **Run weather loader:** `mvn exec:java -Dexec.mainClass=org.infinispan.tutorial.client.weather.WeatherLoaderApp`
* **Run weather finder:** `mvn exec:java -Dexec.mainClass=org.infinispan.tutorial.client.weather.WeatherFinderApp`
* **Run tests (requires Docker):** `mvn clean verify`

## Key Concepts
* **ProtoStream:** annotation-based Protobuf serialization. `@Proto` on records, `@ProtoSchema` on schema interfaces. The `protostream-processor` generates `*Impl` classes at compile time via `annotationProcessorPaths` in the compiler plugin.
* **Hot Rod URI:** connection string format is `hotrod://user:password@host:port`
* **Cache configuration:** XML files in `src/main/resources/` define distributed caches with protobuf encoding.
* **Schema upload:** `remoteCacheManager.administration().schemas().createOrUpdate()` uploads Protobuf schemas to the server.
* **Ickle queries:** SQL-like query language for Infinispan (FROM, SELECT, WHERE with named parameters).

## Branch Workflow
* **`main`** — skeleton code with `// STEP` placeholder comments for users to fill in.
* **`solution`** — completed implementation.

When upgrading versions or modifying infrastructure:
1. Make changes on the `solution` branch first (complete implementation).
2. Apply the same version/dependency/doc changes to `main`, keeping skeleton code with `// STEP` placeholders intact.

## Version Upgrades
When upgrading Infinispan:
1. Update `version.infinispan` in `pom.xml`.
2. Update `version.protostream` to match (check `build/bom/pom.xml` in the infinispan repo at the target tag).
3. Check if `infinispan-server-testdriver-*` artifact name changed.
4. Check if test extension package moved (e.g., `junit5` → `jupiter`).
5. Update container image tags in `README.adoc`.
6. Update example output version strings in `README.adoc` (search for `ISPN004021`).
7. Build and verify: `mvn clean install -DskipTests=true`.

## Related Projects
* **Infinispan:** `../infinispan` — main project (check tags for version-specific API changes)
* **Simple Tutorials:** `../infinispan-simple-tutorials` — additional tutorial examples
* **Console:** `../infinispan-console` — web console UI
* **Server Images:** `../infinispan-images` — container image definitions
