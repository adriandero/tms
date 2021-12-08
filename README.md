# tms

## Directory Structure

### `camel-demo/`
A simple demo with the [camel framework](https://camel.apache.org/) showing how a project is supposed to be
set up.

### `software/`
Folder that contains all source code files.

## Build
### Run everything (recommended)
> Runs the software and database.

When in the project root use `docker-compose up --build tms`.
### Only Image
Build & run the docker image with `sh software/build.sh run`.
