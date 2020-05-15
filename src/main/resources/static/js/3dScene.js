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

const trafficLight1MaskRed = new THREE.Mesh( new THREE.PlaneGeometry( 1.7, 1.3, 1.7, 1.7 ),
    new THREE.MeshBasicMaterial( {color: 0x000000} ) )
trafficLight1MaskRed.position.x = -8.7
trafficLight1MaskRed.position.y = 10.8
trafficLight1MaskRed.position.z = 22.5
trafficLight1MaskRed.rotation.y = 0
trafficLight1MaskRed.rotation.x = 0

const trafficLight1MaskOrange = new THREE.Mesh( new THREE.PlaneGeometry( 1.7, 1.3, 1.7, 1.7 ),
    new THREE.MeshBasicMaterial( {color: 0x000000} ) )
trafficLight1MaskOrange.position.x = -8.7
trafficLight1MaskOrange.position.y = 9.5
trafficLight1MaskOrange.position.z = 22.5
trafficLight1MaskOrange.rotation.y = 0
trafficLight1MaskOrange.rotation.x = 0

const trafficLight1MaskGreen = new THREE.Mesh( new THREE.PlaneGeometry( 1.7, 1.3, 1.7, 1.7 ),
    new THREE.MeshBasicMaterial( {color: 0x000000} ) )
trafficLight1MaskGreen.position.x = -8.7
trafficLight1MaskGreen.position.y = 8.2
trafficLight1MaskGreen.position.z = 22.5
trafficLight1MaskGreen.rotation.y = 0
trafficLight1MaskGreen.rotation.x = 0

const trafficLight1BigMaskRed = new THREE.Mesh( new THREE.PlaneGeometry( 2.7, 2.3, 2.7, 2.7 ),
    new THREE.MeshBasicMaterial( {color: 0x000000} ) )
trafficLight1BigMaskRed.position.x = 2.7
trafficLight1BigMaskRed.position.y = 26.8
trafficLight1BigMaskRed.position.z = 23.8
trafficLight1BigMaskRed.rotation.y = 0
trafficLight1BigMaskRed.rotation.x = 0

const trafficLight1BigMaskOrange = new THREE.Mesh( new THREE.PlaneGeometry( 2.7, 2.3, 2.7, 2.7 ),
    new THREE.MeshBasicMaterial( {color: 0x000000} ) )
trafficLight1BigMaskOrange.position.x = 2.7
trafficLight1BigMaskOrange.position.y = 24.5
trafficLight1BigMaskOrange.position.z = 23.8
trafficLight1BigMaskOrange.rotation.y = 0
trafficLight1BigMaskOrange.rotation.x = 0

const trafficLight1BigMaskGreen = new THREE.Mesh( new THREE.PlaneGeometry( 2.7, 2.3, 2.7, 2.7 ),
    new THREE.MeshBasicMaterial( {color: 0x000000} ) )
trafficLight1BigMaskGreen.position.x = 2.7
trafficLight1BigMaskGreen.position.y = 22.2
trafficLight1BigMaskGreen.position.z = 23.8
trafficLight1BigMaskGreen.rotation.y = 0
trafficLight1BigMaskGreen.rotation.x = 0

const trafficLight2MaskRed = new THREE.Mesh( new THREE.PlaneGeometry( 1.7, 1.3, 1.7, 1.7 ),
    new THREE.MeshBasicMaterial( {color: 0x000000} ) )
trafficLight2MaskRed.position.x = -12.5
trafficLight2MaskRed.position.y = 10.8
trafficLight2MaskRed.position.z = -9
trafficLight2MaskRed.rotation.y = 4.71
trafficLight2MaskRed.rotation.x = 0

const trafficLight2MaskOrange = new THREE.Mesh( new THREE.PlaneGeometry( 1.7, 1.3, 1.7, 1.7 ),
    new THREE.MeshBasicMaterial( {color: 0x000000} ) )
trafficLight2MaskOrange.position.x = -12.5
trafficLight2MaskOrange.position.y = 9.5
trafficLight2MaskOrange.position.z = -9
trafficLight2MaskOrange.rotation.y = 4.71
trafficLight2MaskOrange.rotation.x = 0

const trafficLight2MaskGreen = new THREE.Mesh( new THREE.PlaneGeometry( 1.7, 1.3, 1.7, 1.7 ),
    new THREE.MeshBasicMaterial( {color: 0x000000} ) )
trafficLight2MaskGreen.position.x = -12.5
trafficLight2MaskGreen.position.y = 8.2
trafficLight2MaskGreen.position.z = -9
trafficLight2MaskGreen.rotation.y = 4.71
trafficLight2MaskGreen.rotation.x = 0

const trafficLight2BigMaskRed = new THREE.Mesh( new THREE.PlaneGeometry( 2.7, 2.3, 2.7, 2.7 ),
    new THREE.MeshBasicMaterial( {color: 0x000000} ) )
trafficLight2BigMaskRed.position.x = -13.85
trafficLight2BigMaskRed.position.y = 26.8
trafficLight2BigMaskRed.position.z = 3
trafficLight2BigMaskRed.rotation.y = 4.71
trafficLight2BigMaskRed.rotation.x = 0

const trafficLight2BigMaskOrange = new THREE.Mesh( new THREE.PlaneGeometry( 2.7, 2.3, 2.7, 2.7 ),
    new THREE.MeshBasicMaterial( {color: 0x000000} ) )
trafficLight2BigMaskOrange.position.x = -13.85
trafficLight2BigMaskOrange.position.y = 24.5
trafficLight2BigMaskOrange.position.z = 3
trafficLight2BigMaskOrange.rotation.y = 4.71
trafficLight2BigMaskOrange.rotation.x = 0

const trafficLight2BigMaskGreen = new THREE.Mesh( new THREE.PlaneGeometry( 2.7, 2.3, 2.7, 2.7 ),
    new THREE.MeshBasicMaterial( {color: 0x000000} ) )
trafficLight2BigMaskGreen.position.x = -13.85
trafficLight2BigMaskGreen.position.y = 22.2
trafficLight2BigMaskGreen.position.z = 3
trafficLight2BigMaskGreen.rotation.y = 4.71
trafficLight2BigMaskGreen.rotation.x = 0

const trafficLight3MaskRed = new THREE.Mesh( new THREE.PlaneGeometry( 1.7, 1.3, 1.7, 1.7 ),
    new THREE.MeshBasicMaterial( {color: 0x000000} ) )
trafficLight3MaskRed.position.x = 19
trafficLight3MaskRed.position.y = 10.8
trafficLight3MaskRed.position.z = -12.5
trafficLight3MaskRed.rotation.y = 3.14
trafficLight3MaskRed.rotation.x = 0

const trafficLight3MaskOrange = new THREE.Mesh( new THREE.PlaneGeometry( 1.7, 1.3, 1.7, 1.7 ),
    new THREE.MeshBasicMaterial( {color: 0x000000} ) )
trafficLight3MaskOrange.position.x = 19
trafficLight3MaskOrange.position.y = 9.5
trafficLight3MaskOrange.position.z = -12.5
trafficLight3MaskOrange.rotation.y = 3.14
trafficLight3MaskOrange.rotation.x = 0

const trafficLight3MaskGreen = new THREE.Mesh( new THREE.PlaneGeometry( 1.7, 1.3, 1.7, 1.7 ),
    new THREE.MeshBasicMaterial( {color: 0x000000} ) )
trafficLight3MaskGreen.position.x = 19
trafficLight3MaskGreen.position.y = 8.2
trafficLight3MaskGreen.position.z = -12.5
trafficLight3MaskGreen.rotation.y = 3.14
trafficLight3MaskGreen.rotation.x = 0

const trafficLight3BigMaskRed = new THREE.Mesh( new THREE.PlaneGeometry( 2.7, 2.3, 2.7, 2.7 ),
    new THREE.MeshBasicMaterial( {color: 0x000000} ) )
trafficLight3BigMaskRed.position.x = 7
trafficLight3BigMaskRed.position.y = 26.8
trafficLight3BigMaskRed.position.z = -13.88
trafficLight3BigMaskRed.rotation.y = 3.14
trafficLight3BigMaskRed.rotation.x = 0

const trafficLight3BigMaskOrange = new THREE.Mesh( new THREE.PlaneGeometry( 2.7, 2.3, 2.7, 2.7 ),
    new THREE.MeshBasicMaterial( {color: 0x000000} ) )
trafficLight3BigMaskOrange.position.x = 7
trafficLight3BigMaskOrange.position.y = 24.5
trafficLight3BigMaskOrange.position.z = -13.88
trafficLight3BigMaskOrange.rotation.y = 3.14
trafficLight3BigMaskOrange.rotation.x = 0

const trafficLight3BigMaskGreen = new THREE.Mesh( new THREE.PlaneGeometry( 2.7, 2.3, 2.7, 2.7 ),
    new THREE.MeshBasicMaterial( {color: 0x000000} ) )
trafficLight3BigMaskGreen.position.x = 7
trafficLight3BigMaskGreen.position.y = 22.2
trafficLight3BigMaskGreen.position.z = -13.88
trafficLight3BigMaskGreen.rotation.y = 3.14
trafficLight3BigMaskGreen.rotation.x = 0

const trafficLight4MaskRed = new THREE.Mesh( new THREE.PlaneGeometry( 1.7, 1.3, 1.7, 1.7 ),
    new THREE.MeshBasicMaterial( {color: 0x000000} ) )
trafficLight4MaskRed.position.x = 17.5
trafficLight4MaskRed.position.y = 10.8
trafficLight4MaskRed.position.z = 19
trafficLight4MaskRed.rotation.y = -4.71
trafficLight4MaskRed.rotation.x = 0

const trafficLight4MaskOrange = new THREE.Mesh( new THREE.PlaneGeometry( 1.7, 1.3, 1.7, 1.7 ),
    new THREE.MeshBasicMaterial( {color: 0x000000} ) )
trafficLight4MaskOrange.position.x = 17.5
trafficLight4MaskOrange.position.y = 9.5
trafficLight4MaskOrange.position.z = 19
trafficLight4MaskOrange.rotation.y = -4.71
trafficLight4MaskOrange.rotation.x = 0

const trafficLight4MaskGreen = new THREE.Mesh( new THREE.PlaneGeometry( 1.7, 1.3, 1.7, 1.7 ),
    new THREE.MeshBasicMaterial( {color: 0x000000} ) )
trafficLight4MaskGreen.position.x = 17.5
trafficLight4MaskGreen.position.y = 8.2
trafficLight4MaskGreen.position.z = 19
trafficLight4MaskGreen.rotation.y = -4.71
trafficLight4MaskGreen.rotation.x = 0

const trafficLight4BigMaskRed = new THREE.Mesh( new THREE.PlaneGeometry( 2.7, 2.3, 2.7, 2.7 ),
    new THREE.MeshBasicMaterial( {color: 0x000000} ) )
trafficLight4BigMaskRed.position.x = 18.86
trafficLight4BigMaskRed.position.y = 26.8
trafficLight4BigMaskRed.position.z = 7
trafficLight4BigMaskRed.rotation.y = -4.71
trafficLight4BigMaskRed.rotation.x = 0

const trafficLight4BigMaskOrange = new THREE.Mesh( new THREE.PlaneGeometry( 2.7, 2.3, 2.7, 2.7 ),
    new THREE.MeshBasicMaterial( {color: 0x000000} ) )
trafficLight4BigMaskOrange.position.x = 18.86
trafficLight4BigMaskOrange.position.y = 24.5
trafficLight4BigMaskOrange.position.z = 7
trafficLight4BigMaskOrange.rotation.y = -4.71
trafficLight4BigMaskOrange.rotation.x = 0

const trafficLight4BigMaskGreen = new THREE.Mesh( new THREE.PlaneGeometry( 2.7, 2.3, 2.7, 2.7 ),
    new THREE.MeshBasicMaterial( {color: 0x000000} ) )
trafficLight4BigMaskGreen.position.x = 18.86
trafficLight4BigMaskGreen.position.y = 22.2
trafficLight4BigMaskGreen.position.z = 7
trafficLight4BigMaskGreen.rotation.y = -4.71
trafficLight4BigMaskGreen.rotation.x = 0

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

const TrafficSubscriber = class TrafficSubscriber {
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
            carInstance.position.x =
                car.situation.position.x === 0 &&
                car.cameFrom === 'N' ? 8 :
                    car.situation.position.x === 0 &&
                    car.cameFrom === 'S' ? -3 : car.situation.position.x
            carInstance.position.y = 5.0
            carInstance.position.z =
                car.situation.position.y === 0 &&
                car.cameFrom === 'E' ? 0.5 :
                    car.situation.position.y === 0 &&
                    car.cameFrom === 'W' ? 10 : car.situation.position.y
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

const TrafficLightsSubscriber = class TrafficLightsSubscriber {
    constructor(){
        this.trafficLights = []
    }
    receive(data){
        this.trafficLights = JSON.parse(data).newStatuses || this.trafficLights
        const northStatus = this.trafficLights.find(status => status.side === 'N').newStatus
        const eastStatus = this.trafficLights.find(status => status.side === 'E').newStatus
        const southStatus = this.trafficLights.find(status => status.side === 'S').newStatus
        const westStatus = this.trafficLights.find(status => status.side === 'W').newStatus
        if (northStatus === 'GREEN'){
            scene.remove(trafficLight1MaskGreen)
            scene.remove(trafficLight1BigMaskGreen)
            scene.add(trafficLight1MaskRed)
            scene.add(trafficLight1MaskOrange)
            scene.add(trafficLight1BigMaskRed)
            scene.add(trafficLight1BigMaskOrange)
        }
        if (northStatus === 'RED'){
            scene.remove(trafficLight1MaskRed)
            scene.remove(trafficLight1BigMaskRed)
            scene.add(trafficLight1MaskGreen)
            scene.add(trafficLight1MaskOrange)
            scene.add(trafficLight1BigMaskGreen)
            scene.add(trafficLight1BigMaskOrange)
        }
        if (northStatus === 'ORANGE'){
            scene.remove(trafficLight1MaskOrange)
            scene.remove(trafficLight1BigMaskOrange)
            scene.add(trafficLight1MaskGreen)
            scene.add(trafficLight1MaskRed)
            scene.add(trafficLight1BigMaskGreen)
            scene.add(trafficLight1BigMaskRed)
        }
        if (eastStatus === 'GREEN'){
            scene.remove(trafficLight4MaskGreen)
            scene.remove(trafficLight4BigMaskGreen)
            scene.add(trafficLight4MaskRed)
            scene.add(trafficLight4MaskOrange)
            scene.add(trafficLight4BigMaskRed)
            scene.add(trafficLight4BigMaskOrange)
        }
        if (eastStatus === 'RED'){
            scene.remove(trafficLight4MaskRed)
            scene.remove(trafficLight4BigMaskRed)
            scene.add(trafficLight4MaskGreen)
            scene.add(trafficLight4MaskOrange)
            scene.add(trafficLight4BigMaskGreen)
            scene.add(trafficLight4BigMaskOrange)
        }
        if (eastStatus === 'ORANGE'){
            scene.remove(trafficLight4MaskOrange)
            scene.remove(trafficLight4BigMaskOrange)
            scene.add(trafficLight4MaskGreen)
            scene.add(trafficLight4MaskRed)
            scene.add(trafficLight4BigMaskGreen)
            scene.add(trafficLight4BigMaskRed)
        }
        if (southStatus === 'GREEN'){
            scene.remove(trafficLight3MaskGreen)
            scene.remove(trafficLight3BigMaskGreen)
            scene.add(trafficLight3MaskRed)
            scene.add(trafficLight3MaskOrange)
            scene.add(trafficLight3BigMaskRed)
            scene.add(trafficLight3BigMaskOrange)
        }
        if (southStatus === 'RED'){
            scene.remove(trafficLight3MaskRed)
            scene.remove(trafficLight3BigMaskRed)
            scene.add(trafficLight3MaskGreen)
            scene.add(trafficLight3MaskOrange)
            scene.add(trafficLight3BigMaskGreen)
            scene.add(trafficLight3BigMaskOrange)
        }
        if (southStatus === 'ORANGE'){
            scene.remove(trafficLight3MaskOrange)
            scene.remove(trafficLight3BigMaskOrange)
            scene.add(trafficLight3MaskGreen)
            scene.add(trafficLight3MaskRed)
            scene.add(trafficLight3BigMaskGreen)
            scene.add(trafficLight3BigMaskRed)
        }
        if (westStatus === 'GREEN'){
            scene.remove(trafficLight2MaskGreen)
            scene.remove(trafficLight2BigMaskGreen)
            scene.add(trafficLight2MaskRed)
            scene.add(trafficLight2MaskOrange)
            scene.add(trafficLight2BigMaskRed)
            scene.add(trafficLight2BigMaskOrange)
        }
        if (westStatus === 'RED'){
            scene.remove(trafficLight2MaskRed)
            scene.remove(trafficLight2BigMaskRed)
            scene.add(trafficLight2MaskGreen)
            scene.add(trafficLight2MaskOrange)
            scene.add(trafficLight2BigMaskGreen)
            scene.add(trafficLight2BigMaskOrange)
        }
        if (westStatus === 'ORANGE'){
            scene.remove(trafficLight2MaskOrange)
            scene.remove(trafficLight2BigMaskOrange)
            scene.add(trafficLight2MaskGreen)
            scene.add(trafficLight2MaskRed)
            scene.add(trafficLight2BigMaskGreen)
            scene.add(trafficLight2BigMaskRed)
        }
    }
}

render()
animate()
controls.addEventListener( 'change', render )
