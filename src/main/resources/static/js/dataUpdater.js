const socket = new SockJS('/websock-js')
const stompClient = Stomp.over(socket)
stompClient.debug = null

const onTrafficLightsUpdate = data => emit(data.body)
const onTrafficUpdate = data => emit(data.body)

const connect = () => new Promise(resolve => stompClient.connect({}, () => {
    stompClient.subscribe('/topics/trafficLights', onTrafficLightsUpdate)
    stompClient.subscribe('/topics/currentTraffic', onTrafficUpdate)
    resolve()
}))

const getTrafficLights = () => stompClient.send('/app/getTrafficLights', {}, '')
const getTraffic = () => stompClient.send('/app/getCurrentTraffic', {}, '')
