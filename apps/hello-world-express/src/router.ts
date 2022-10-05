import { EventService, UnauthorizedUserError } from '@abgov/adsp-service-sdk';
import { Router } from 'express';
import { HelloWorldConfiguration } from './dtos/configuration';
import { createHelloEvent } from './dtos/event';
import { ServiceRoles } from './types';

export const createHelloRouter = (eventService: EventService): Router => {
  const router = Router();
  router.post('/hello', async (req, res, next) => {
    try {
      if (!req.user?.roles?.includes(ServiceRoles.HelloWorlder)) {
        throw new UnauthorizedUserError('post hello', req.user);
      }

      const { message } = req.body;
      const configuration = await req.getConfiguration<HelloWorldConfiguration,HelloWorldConfiguration>();
      const response = configuration?.responses?.[message] || 'Hello World!';

      eventService.send(createHelloEvent(req.user, message, response));
      res.send(response);
    } catch (err) {
      next(err);
    }
  });

  return router;
};
