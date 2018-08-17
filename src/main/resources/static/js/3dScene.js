const scene = new THREE.Scene()
const aspect = window.innerWidth / window.innerHeight
const camera = new THREE.PerspectiveCamera( 45, aspect, 1, 10000 )
const renderer = new THREE.WebGLRenderer()
renderer.setSize( window.innerWidth, window.innerHeight )
document.body.appendChild( renderer.domElement )

const grassTexture = new THREE.TextureLoader().load( 'images/negy.png', texture  => {
    texture.wrapS = texture.wrapT = THREE.RepeatWrapping;
    texture.offset.set( 0, 0 );
    texture.repeat.set( 500, 500 );

} )
const roadTexture = new THREE.TextureLoader().load( 'images/road.png', texture  => {
    texture.wrapS = texture.wrapT = THREE.RepeatWrapping;
    texture.offset.set( 0, 0 );
    texture.repeat.set( 1, 500 );

})
const grassMaterial = new THREE.MeshBasicMaterial( { color: 0x50e048, map: grassTexture } )
const grassGeometry = new THREE.BoxGeometry( 10000, 1, 10000 )
const terrain = new THREE.Mesh( grassGeometry, grassMaterial )
terrain.rotation.x = 0

scene.background = new THREE.CubeTextureLoader()
    .setPath( 'images/' )
    .load([ 'posx.png', 'negx.png',
        'posy.png', 'negy.png',
        'posz.png', 'negz.png' ])
camera.position.set( 80, 40, 20 )
camera.lookAt( new THREE.Vector3( 0, 0, 0 ) )

const road1 = new THREE.Mesh( new THREE.PlaneGeometry( 20, 5000, 20, 5000 ),
    new THREE.MeshBasicMaterial( {map: roadTexture} ) )
road1.rotation.z = 4.71
road1.rotation.x = 4.71
road1.position.y = 3
road1.position.x = -2506
scene.add(road1)

const road2 = new THREE.Mesh( new THREE.PlaneGeometry( 20, 5000, 20, 5000 ),
    new THREE.MeshBasicMaterial( {map: roadTexture} ) )
road2.rotation.z = 4.71
road2.rotation.x = 4.71
road2.position.y = 3
road2.position.x = 2512
road2.position.z = 13
scene.add(road2)

const road3 = new THREE.Mesh( new THREE.PlaneGeometry( 20, 5000, 20, 5000 ),
    new THREE.MeshBasicMaterial( {map: roadTexture} ) )
road3.rotation.z = 0
road3.rotation.x = 4.71
road3.position.y = 9
road3.position.x = 2
road3.position.z = 2515
scene.add(road3)

const road4 = new THREE.Mesh( new THREE.PlaneGeometry( 20, 5000, 20, 5000 ),
    new THREE.MeshBasicMaterial( {map: roadTexture} ) )
road4.rotation.z = 0
road4.rotation.x = 4.71
road4.position.y = -3
road4.position.x = 4
road4.position.z = -2502
scene.add(road4)

const crossing = new THREE.Mesh( new THREE.PlaneGeometry( 20, 20, 20, 20 ),
    new THREE.MeshBasicMaterial( {color: 0x242024} ) )
crossing.rotation.z = 0
crossing.rotation.x = 4.71
crossing.position.y = 3
crossing.position.x = 2
crossing.position.z = 7
scene.add(crossing)

const trafficLight1 = new THREE.ObjectLoader().parse(trafficLight3DObject)
trafficLight1.position.x = -10
trafficLight1.position.z = -10
scene.add(trafficLight1)

const trafficLight2 = new THREE.ObjectLoader().parse(trafficLight3DObject)
trafficLight2.position.x = 20
trafficLight2.position.z = -10
trafficLight2.rotation.y = 4.71
scene.add(trafficLight2)

const trafficLight3 = new THREE.ObjectLoader().parse(trafficLight3DObject)
trafficLight3.position.x = 15
trafficLight3.position.z = 20
trafficLight3.rotation.y = 3.14
scene.add(trafficLight3)

const trafficLight4 = new THREE.ObjectLoader().parse(trafficLight3DObject)
trafficLight4.position.x = -10
trafficLight4.position.z = 20
trafficLight4.rotation.y = -4.71
scene.add(trafficLight4)

scene.add( terrain )
const carsParent = new THREE.Object3D()
scene.add(carsParent)

const controls = new THREE.OrbitControls( camera )
Object.assign(controls, {
    rotateSpeed : 1.0,
    zoomSpeed : 1.2,
    panSpeed : 0.8,
    noZoom : false,
    noPan : false,
    staticMoving : true,
    dynamicDampingFactor : 0.3,
    keys : [ 65, 83, 68 ]
})

function animate() {
    requestAnimationFrame( () => {animate();render();} )
    controls.update()
}

function render() {
    renderer.render( scene, camera )
}

const Traffic3DSubscriber = class Traffic3DSubscriber {
    constructor(){
        this.cars = []
    }
    receive(data){
        const newCars = JSON.parse(data).cars || this.cars
        newCars.forEach(car => {
            const existingCar = carsParent.getObjectByName(car.carId)
            const carInstance = existingCar ||
                new THREE.ObjectLoader().parse(car3DObject)
            carInstance.name = car.carId
            carInstance.position.x = car.situation.position.x
            carInstance.position.y = 2.6
            carInstance.position.z = car.situation.position.y
            carInstance.rotation.y =
                car.cameFrom === 'N' ? 3.14 :
                    car.cameFrom === 'E' ? 4.71 :
                        car.cameFrom === 'S' ? 0 :
                            car.cameFrom === 'W' ? -4.71 : 0
            carInstance.position.needsUpdate = true
            if (!existingCar) carsParent.add(carInstance)
        })
        const removedCars = this.cars.filter(car =>
            !newCars.map(newCar => newCar.carId).includes(car.carId))
        removedCars.forEach(removedCar => carsParent.remove(scene.getObjectByName(removedCar.carId)))
        this.cars = newCars
    }
}

const TrafficLights3DSubscriber = class TrafficLights3DSubscriber {
    constructor(){
        this.trafficLights = []
    }
    receive(data){
        this.trafficLights = JSON.parse(data).newStatuses || this.trafficLights
    }
}

render()
animate()
controls.addEventListener( 'change', render )
