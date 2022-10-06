/**
 * This is not a production server yet!
 * This is only a minimal backend to get started.
 */
import { AdspId, initializeService } from '@abgov/adsp-service-sdk';
import * as express from 'express';
import * as passport from 'passport';
import { environment } from './environments/environment';
import { createHelloRouter } from './router';
import { ServiceRoles } from './types';

async function initializeApp(): Promise<express.Application> {
  const app = express();
  app.use(express.json({ limit: '1mb' }));

  // Initialization of ADSP SDK components...
  // 1. Initialize service to access ADSP components.
  const { configurationHandler, eventService, tenantStrategy } = await initializeService(
    {
      // 2. Configure ADSP via options properties.
      accessServiceUrl: new URL(environment.ACCESS_SERVICE_URL),
      realm: environment.REALM,
      directoryUrl: new URL(environment.DIRECTORY_URL),
      // 3. Provide configuration of the service.
      // 3a. Configure basic service information.
      serviceId: AdspId.parse(environment.CLIENT_ID),
      clientSecret: environment.CLIENT_SECRET,      
      displayName: 'Hello world service',
      description: 'Hello world sample for Express with ADSP SDK.',
      // 3b. Register configuration definition.
      configuration: {
        description: 'Configuration of the hello world sample service.',
        schema: {
          type: 'object',
          properties: {
            responses: {
              type: 'object',
              additionalProperties: { type: 'string' },
            },
          },
        },
      },
      combineConfiguration: (tenant) => tenant,
      // 3c. Register service roles.
      roles: [
        {
          role: ServiceRoles.HelloWorlder,
          description:
            'Hello worlder role that allows user to post a message to the API.',
        },
      ],
      // 3d. Register domain events.
      events: [
        {
          name: 'hello-world-event',
          description:
            'Signalled when a hello world message is posted to the API.',
          payloadSchema: {
            type: 'object',
            properties: {
              fromUserId: { type: 'string' },
              fromUser: { type: 'string' },
              message: { type: 'string' },
              response: { type: 'string' },
            },
          },
        },
      ],
    },
    {
      logLevel: 'debug',
    }
  );

  // 4. Add ADSP middleware to routes.
  passport.use('tenant', tenantStrategy);

  app.use(passport.initialize());
  const apiRouter = createHelloRouter(eventService);
  app.use(
    '/hello-world/v1',
    passport.authenticate('tenant', { session: false }),
    configurationHandler,
    apiRouter
  );

  return app;
}

initializeApp().then((app) => {
  const port = process.env.port || 3333;
  const server = app.listen(port, () => {
    console.log(`Listening at http://localhost:${port}/api`);
  });
  server.on('error', console.error);
});
