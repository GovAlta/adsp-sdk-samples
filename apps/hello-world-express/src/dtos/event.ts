import { DomainEvent, User } from '@abgov/adsp-service-sdk';

export const HelloWorldEventName = 'hello-world-event';
export const createHelloEvent = (
  from: User,
  message: string,
  response: string
): DomainEvent => ({
  name: HelloWorldEventName,
  timestamp: new Date(),
  payload: {
    fromUserId: from.id,
    fromUser: from.name,
    message,
    response
  },
});
