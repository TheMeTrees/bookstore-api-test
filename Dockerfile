# Use a base image with JDK 21
FROM eclipse-temurin:21-jdk

# Set environment variables
ENV MAVEN_VERSION=3.9.6
ENV ALLURE_VERSION=2.24.0
ENV MAVEN_HOME=/opt/maven
ENV PATH=$MAVEN_HOME/bin:$PATH
ENV ALLURE_HOME=/opt/allure
ENV PATH=$ALLURE_HOME/bin:$PATH

RUN apt-get update && \
    apt-get install -y curl unzip git && \
    rm -rf /var/lib/apt/lists/*

RUN curl -fsSL https://archive.apache.org/dist/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
    | tar -xz -C /opt && \
    ln -s /opt/apache-maven-${MAVEN_VERSION} $MAVEN_HOME

RUN curl -fsSL https://github.com/allure-framework/allure2/releases/download/${ALLURE_VERSION}/allure-${ALLURE_VERSION}.zip \
    -o allure.zip && \
    unzip allure.zip -d /opt && \
    mv /opt/allure-${ALLURE_VERSION} $ALLURE_HOME && \
    rm allure.zip

WORKDIR /app

COPY . /app

CMD rm -rf target/allure-report && \
    mvn clean test || true && \
    allure generate target/allure-results --clean -o target/allure-report && \
    echo "Allure report generated at /BookstoreApiTest/target/allure-report"
