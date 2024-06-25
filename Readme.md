Java code to implement RSpace FileStore operations to Egnyte

## Functionality

This library uses Spring Rest Template client to wrap calls to Egnyte File and Search API.
All calls require a valid access token.

## Getting a personal access token for development

* Get an API key
* Get access token using call like:

    curl --request POST -H "Content-Type: application/x-www-form-urlencoded" -d grant_type=password -d username=<username> -d 'password=<password>' -d client_id=<api key> https://apprspace.egnyte.com/puboauth/token
    
 which should return a value like:

    {"access_token":"<token>","token_type":"bearer","expires_in":-1}

## Notes

This is a separate,  module independent of RSpace code,  but as it will eventually be part of RSpace webapp - there is a dependency on parent pom.xml to keep consistent library versions and avoid classpath hell at runtime.

## Running tests

Acceptance tests make real calls to Egnyte. A proxy class `APiExecutor` that wraps test API call limits calls to once per 750 millis, which should work as the documented limit is 2 per second.

1. Edit `test.properties` so that the value of `egnyte.url` is the URL of your Egnyte instance. Make sure the URL ends with a trailing slash, e.g. `https://apprspace.egnyte.com/`.
2. Set your access token as a system property using a -D option to your test runner, e.g. if using Maven

    mvn clean test -DaccessToken=abcdefghijk
    
## Usage

See acceptance tests in package `com.researchspace.egnyte.api2` for usage examples.
 
