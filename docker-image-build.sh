mvn clean install -Denvironment=ossdocker
cp target/eusurvey.war docker/server/dist/eusurvey.war
cd docker
docker rmi ghcr.io/circabc/tomcat-eusurvey-server-k8s            
docker rmi eusurvey-tomcat   
docker-compose build
docker tag eusurvey-tomcat ghcr.io/circabc/tomcat-eusurvey-server-k8s:latest
docker push  ghcr.io/circabc/tomcat-eusurvey-server-k8s:latest
docker images 
minikube image load ghcr.io/circabc/tomcat-eusurvey-server-k8s:latest
minikube ssh -- docker images
