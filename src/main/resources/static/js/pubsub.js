const subscribers = []
const subscribe = subscriber => subscribers.push(subscriber)
const emit = data => subscribers.forEach(subscriber => subscriber.receive(data))
