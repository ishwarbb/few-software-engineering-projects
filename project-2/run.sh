export JAVA_HOME=/home/ishwar/Downloads/jdk-8u391-linux-i586/jdk1.8.0_391

cd books-parent
mvn clean -DskipTests install
cd ..

cd books-web
mvn jetty:run -e
cd ..
