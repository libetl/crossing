document.addEventListener('DOMContentLoaded', function () {
    subscribe(new Traffic3DSubscriber())
    subscribe(new TrafficLights3DSubscriber())
    connect().then(getTrafficLights).then(getTraffic)
})
