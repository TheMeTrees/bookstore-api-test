FROM eclipse-temurin:21-jdk
WORKDIR /app
RUN apt-get update && apt-get install -y maven curl unzip && apt-get clean
COPY . .

RUN curl -L -o allure.tgz https://github.com/allure-framework/allure2/releases/download/2.27.0/allure-2.27.0.tgz \
    && tar -xzf allure.tgz && mv allure-2.27.0 /opt/allure && ln -s /opt/allure/bin/allure /usr/bin/allure \
    && rm allure.tgz

CMD mvn clean test -Denv=dev -Dapi.version=/api/v1 || true && \
    allure generate target/allure-results --clean -o allure-report --single-file
