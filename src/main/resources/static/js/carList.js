const TrafficSubscriber = class TrafficSubscriber {
    constructor() {
        this.jsonViewerEast = new JSONViewer()
        this.jsonViewerNorth = new JSONViewer()
        this.jsonViewerSouth = new JSONViewer()
        this.jsonViewerWest = new JSONViewer()
        this.cars = []
        document.querySelector("#cars-east").appendChild(this.jsonViewerEast.getContainer())
        document.querySelector("#cars-north").appendChild(this.jsonViewerNorth.getContainer())
        document.querySelector("#cars-south").appendChild(this.jsonViewerSouth.getContainer())
        document.querySelector("#cars-west").appendChild(this.jsonViewerWest.getContainer())
    }

    sortCars(a, b) {
        return a.carId.localeCompare(b.carId)
    }

    receive(data) {
        this.cars = JSON.parse(data).cars || this.cars
        const carsEast = this.cars.filter(c => c.cameFrom === 'E')
        const carsNorth = this.cars.filter(c => c.cameFrom === 'N')
        const carsSouth = this.cars.filter(c => c.cameFrom === 'S')
        const carsWest = this.cars.filter(c => c.cameFrom === 'W')
        const sortedEast = [...carsEast].sort((a, b) =>
            b.situation.position.x - a.situation.position.x)
        const sortedNorth = [...carsNorth].sort((a, b) =>
            b.situation.position.y - a.situation.position.y)
        const sortedSouth = [...carsSouth].sort((a, b) =>
            a.situation.position.y - b.situation.position.y)
        const sortedWest = [...carsWest].sort((a, b) =>
            a.situation.position.x - b.situation.position.x)
        carsEast.sort(this.sortCars)
        carsNorth.sort(this.sortCars)
        carsSouth.sort(this.sortCars)
        carsWest.sort(this.sortCars)
        const consolidatedEast = carsEast.map(o => ({order: sortedEast.indexOf(o), ...o}))
        const consolidatedNorth = carsNorth.map(o => ({order: sortedNorth.indexOf(o), ...o}))
        const consolidatedSouth = carsSouth.map(o => ({order: sortedSouth.indexOf(o), ...o}))
        const consolidatedWest = carsWest.map(o => ({order: sortedWest.indexOf(o), ...o}))
        this.jsonViewerEast.showJSON(consolidatedEast)
        this.jsonViewerNorth.showJSON(consolidatedNorth)
        this.jsonViewerSouth.showJSON(consolidatedSouth)
        this.jsonViewerWest.showJSON(consolidatedWest)
    }
}


const TrafficLightsSubscriber = class TrafficLightsSubscriber {
    constructor(){
        this.trafficLights = []
    }
    receive(data){
        this.trafficLights = JSON.parse(data).newStatuses || this.trafficLights
    }
}