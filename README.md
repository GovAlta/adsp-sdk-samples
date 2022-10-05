# ADSP SDK Samples

This is monorepo of minimal hello world backend services using variants of the [ADSP Service SDK](https://github.com/GovAlta/adsp-monorepo).

This project uses [Nx](https://nx.dev).

## Prerequisites
- ASP.NET Core variant 
  - requires .NET 6 SDK
  - nuget.config with GitHub credentials to download package from nuget source: https://nuget.pkg.github.com/GovAlta/index.json (See [GitHub docs](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-nuget-registry))
- Spring Boot variant 
  - requires JDK 11
  - Maven settings.xml with GitHub credentials to download package from maven repository: https://maven.pkg.github.com/GovAlta/adsp-monorepo (See [GitHub docs](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-apache-maven-registry))
- ADSP Tenant environment
- Confidential client with the Client roles:
  - urn:ads:tenant-service -> platform-service
  - urn:ads:configuration-service -> configured-service
  - urn:ads:event-service -> event-sender



## Running services

Set the configuration values via an appropriate mechanism for the variant:
- app settings in ASP.NET Core variant
- properties in Spring Boot variant
- environment in Express variant

Install dependencies using: `npm i -D`

Build specific app like: `npx nx build hello-world-express`

Run specific app like `npx nx serve hello-world-express`
