# Build and Deploy Config Service
custom_build(
    ref = 'config-service',
    command = './mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=$EXPECTED_REF',
    deps = ['config-server/pom.xml', 'config-server/src']
)

# Deploy Config Service
k8s_yaml(['config-server/k8s/deployment.yml', 'config-server/k8s/service.yml'])
k8s_resource('config-service', port_forwards=['8888'])

# Build and Deploy Product Service
custom_build(
    ref = 'product-service',
    command = './mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=$EXPECTED_REF',
    deps = ['product/pom.xml', 'product/src']
)

# Deploy Product Service
k8s_yaml(['product/k8s/deployment.yml', 'product/k8s/service.yml'])
k8s_resource('product-service', port_forwards=['3000'])
