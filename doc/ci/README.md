# Continuous Integration

CI (Continuous Integration) is the mechanism that performs a set of actions on every commit.
Currently, our pipeline contains the following stages:
* build
* test
* check
* archive

## build stage
The build stage tries to build the whole project. If this fails, the whole pipeline exits
immediately.

## test stage
The test stage runs `mvn test` on each individual module (client and server).

## check stage
The check stage currently only runs checkstyle checks, but should also be configured to
check the test coverage statistics.

## archive stage
The archive stage only runs for commits on the `master` branch, and compiles and packages
the whole project, and archives it.

## How it works
The CI setup is defined in `.gitlab-ci.yml`. The setup uses some (bash) scripts to make
the CI configuration simpler. These scripts are in the `.ci_scripts/` folder.
