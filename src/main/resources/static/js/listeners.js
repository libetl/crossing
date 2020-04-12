document.addEventListener('DOMContentLoaded', function () {
    subscribe(new TrafficSubscriber())
    subscribe(new TrafficLightsSubscriber())
    connect().then(getTrafficLights).then(getTraffic)
})
