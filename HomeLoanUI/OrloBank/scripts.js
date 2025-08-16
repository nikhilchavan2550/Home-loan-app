  const custId = localStorage.getItem("custId");
  const navLinks = document.getElementById("navLinks");

  if (custId) {
    // User is logged in: show Dashboard
    navLinks.innerHTML = `
      <a href="Dashboard/dashboard.html" class="bg-sky-700 text-white px-4 py-2 rounded-full text-sm hover:bg-sky-800">Open Dashboard</a>
    `;
  } else {
    // User is not logged in: show Login & Open Account
    navLinks.innerHTML = `
      <a href="index.html" class="bg-sky-700 text-white px-4 py-2 rounded-full text-sm hover:bg-sky-800">Features</a>
      <a href="Login/customer_login.html" class="text-sky-700 hover:text-sky-900">Login</a>
      <a href="Register/create_account.html" class="text-sky-700 hover:text-sky-900">Open Account</a>
    `;
  }

const canvas = document.getElementById('globeCanvas');
  const scene = new THREE.Scene();
  const camera = new THREE.PerspectiveCamera(60, canvas.clientWidth / canvas.clientHeight, 0.1, 1000);
  const renderer = new THREE.WebGLRenderer({ canvas, alpha: true, antialias: true });

  function resizeRenderer() {
    const width = canvas.clientWidth;
    const height = canvas.clientHeight;
    renderer.setSize(width, height, false);
    camera.aspect = width / height;
    camera.updateProjectionMatrix();
  }

  resizeRenderer();
  window.addEventListener('resize', resizeRenderer);
  camera.position.z = 5;

  const textures = {
    earthDay: 'https://clouds.matteason.co.uk/images/4096x2048/earth.jpg',
    cloudAlpha: 'https://clouds.matteason.co.uk/images/4096x2048/clouds-alpha.png',
    specular: 'https://clouds.matteason.co.uk/images/4096x2048/specular.jpg'
  };

  const loader = new THREE.TextureLoader();
  const loaded = {};
  let loadedCount = 0;
  const total = Object.keys(textures).length;

  Object.entries(textures).forEach(([key, url]) => {
    loader.load(url, texture => {
      loaded[key] = texture;
      loadedCount++;
      if (loadedCount === total) initScene();
    });
  });

  function initScene() {
    // Earth
    const earthGeo = new THREE.SphereGeometry(2, 64, 64);
    const earthMat = new THREE.MeshPhongMaterial({
      map: loaded.earthDay,
      specularMap: loaded.specular,
      specular: new THREE.Color('grey'),
      shininess: 10
    });
    const earth = new THREE.Mesh(earthGeo, earthMat);
    scene.add(earth);

    // Cloud Layer
    const cloudGeo = new THREE.SphereGeometry(2.02, 64, 64);
    const cloudMat = new THREE.MeshLambertMaterial({
      map: loaded.cloudAlpha,
      transparent: true,
      opacity: 0.4
    });
    const clouds = new THREE.Mesh(cloudGeo, cloudMat);
    scene.add(clouds);

    // Lighting
    const ambientLight = new THREE.AmbientLight(0xffffff, 0.5);
    const sunLight = new THREE.DirectionalLight(0xffffff, 1);
    sunLight.position.set(5, 3, 5);
    scene.add(ambientLight, sunLight);

    // Animation
    function animate() {
      requestAnimationFrame(animate);
      earth.rotation.y += 0.001;
      clouds.rotation.y += 0.0012;
      renderer.render(scene, camera);
    }

    animate();
  }