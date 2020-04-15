let currentStatus = []

const changeTrafficLight = ({side, newStatus}) => stompClient.send('/topics/trafficLights', {},
    JSON.stringify({newStatuses: [...currentStatus.filter(traficLight => traficLight.side !== side), {side, newStatus}]}))

const TrafficSubscriber = class TrafficSubscriber {
    receive(data) {}
}

const TrafficLightsSubscriber = class TrafficLightsSubscriber {
    constructor() {}

    receive(data) {
        const traficLights = JSON.parse(data).newStatuses
        if (!traficLights) return
        currentStatus = traficLights
        document.querySelector('#currentStatus').innerHTML = `<ul>${Object.entries(traficLights).map(
            traficLight => `<li>${traficLight[1].side} : ${traficLight[1].newStatus}</li>`).join('')}</ul>`
    }
}

setTimeout(() =>
    document.querySelector('#changeTrafficLight').addEventListener('click', (e) => {
        changeTrafficLight({side: document.querySelector('input[name="side"]:checked').value,
        newStatus: document.querySelector('select[name="newStatus"]').value})
        e.preventDefault()
        return false
    })
, 200);