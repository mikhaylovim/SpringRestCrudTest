# ENV
    # for local testing, add sys envs:
        jasypt.encryptor.password=""
        -ea -Dfile.encoding=UTF-8 -Djasypt.encryptor.password=""

# Actuator
    http://localhost:8080/actuator/info
    http://localhost:8080/actuator/health
    http://localhost:8080/actuator/metrics
    http://localhost:8080/actuator/metrics/jvm.memory.used

# OpenAPI
    http://localhost:8080/swagger-ui.html
    http://localhost:8080/v3/api-docs.yaml
    http://localhost:8080/v3/api-docs
    http://localhost:8080/api-docs