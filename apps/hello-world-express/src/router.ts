import { EventService, UnauthorizedUserError } from '@abgov/adsp-service-sdk';
import { Router } from 'express';
import { HelloWorldConfiguration } from './dtos/configuration';
import { createHelloEvent } from './dtos/event';
import { ServiceRoles } from './types';

// Inject event service via creator function.
export const createHelloRouter = (eventService: EventService): Router => {
  const router = Router();
  router.post('/hello', async (req, res, next) => {
    try {
      // Retrieve user information from the request.
      if (!req.user?.roles?.includes(ServiceRoles.HelloWorlder)) {
        throw new UnauthorizedUserError('post hello', req.user);
      }

      const { message } = req.body;
      
      // Retrieve configuration from the request; configuration of this service for current tenant is returned.
      const configuration = await req.getConfiguration<
        HelloWorldConfiguration,
        HelloWorldConfiguration
      >();
      const response = configuration?.responses?.[message] || 'Hello World!';

      // Send a domain event via the event service.
      eventService.send(createHelloEvent(req.user, message, response));
      res.send(response);
    } catch (err) {
      next(err);
    }
  });

  return router;
};
