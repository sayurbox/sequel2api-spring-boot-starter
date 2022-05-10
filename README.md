### sequel2api-spring-boot [WIP]
---------
Creating HTTP rest api just from writing SQL query into a yaml file. Highly recommended for simple api which doesn't require any business logic inside. For example: API to showing list of master data. And avoid from making boilerplate codes in spring-boot. (eg: create repository, controller, service class etc)
Very inspired from `Oracle Netsuite` https://timdietrich.me/blog/netsuite-suiteql-query-api/

Example:
```yaml
api:
  - name: user_account_user_id
    url: '/api/users/account/{id}'
    singleResult: false
    query: |
      SELECT *
      FROM accounts
      WHERE user_id = :user_id
      OR username = :name
 ......     
```
will be converted into:
`[GET] http://localhost:8080/api/users/account/1?name=test`
```json
[
  {
    "address": "jakarta",
    "created_on": "2022-04-27T23:40:50Z",
    "email": "test@email.com",
    "employee_id": 1,
    "id": 1,
    "is_active": true,
    "last_login": "2022-04-27T23:37:10Z",
    "salary": 100.58,
    "user_name": "test"
  }
]
```
#### How to Use
---
1. Add `jitpack` repository to gradle
2. Add below dependencies (`spring-jpa, jdbc, web, jetty`) to your existing spring-boot project
```groovy
repositories {
    .......
    maven { url 'https://jitpack.io' }
}
dependencies {
	// add the library
    implementation('com.github.sayurbox:sequel2api-spring-boot-starter:0.0.1')
    // required dependecies for spring-boot
    implementation('org.springframework.boot:spring-boot-starter-data-jdbc')
    implementation('org.springframework.boot:spring-boot-starter-data-jpa')
    implementation('org.springframework.boot:spring-boot-starter-web')
    implementation('org.springframework.boot:spring-boot-starter-jetty') // if you use jetty for web-server
```
3. Add yaml configuration file location into `application.properties`
```
sequel2api.config.yamlLocation=/location/path/config.yaml
```

#### YAML Configuration
---
[TBD] will explain later, for now look at the example in the demo project https://github.com/sayurbox/sequel2api-spring-boot-starter/blob/main/demo/src/main/resources/api.yaml

#### Limitation
1. [TBD]

#### Disclaimer
---
This is still WIP project.
