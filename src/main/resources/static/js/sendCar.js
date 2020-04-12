const socket = new SockJS('/websock-js')
const stompClient = Stomp.over(socket)
stompClient.debug = null

const connect = () => new Promise(resolve => stompClient.connect({}, () => {
    resolve()
}))

function uuid() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        var r = Math.random() * 16 | 0, v = c === 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}

const sendCar = ({comingFrom, behavior}) => stompClient.send('/topics/sendCar', {},
    JSON.stringify({carId: uuid(), cameFrom: comingFrom, behavior}))

setTimeout(() =>
    document.querySelector('#sendNewCar').addEventListener('click', (e) => {
        sendCar({comingFrom: document.querySelector('input[name="comingFrom"]:checked').value,
            behavior: document.querySelector('select[name="behavior"]').value})
        e.preventDefault()
        return false
    })
, 200);