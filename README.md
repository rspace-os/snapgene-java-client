### Background

Java wrapper for sending and receiving requests to SnapgeneServer via ZeroMQ message 
requests. This can be part of a small webapp so that client code doesn't have to communicate with ZMQ directly. 

## Contents

- object models for the response/request objects. These are simple pojos with validation
- a ZMQ message client for communicating with Snapgene daemon
- Packer build script for building a Docker image of the Snapgene daemon.

Note this project doesn't contain the Snapgene daemon itself, just Java classes to invoke Snapgene commands
and build scripts to build a Docker image.

### To run tests

You'll need access to a running Snapgene server instance.
Jenkins has access to AWS instance.
To run on another server alter properties in `ZMQSnapgeneTest`

To see how to launch / invoke the Daemon see `build/packer/README.md`.

If it's running on localhost then all tests can be run using

    mvn clean test -Dsnapgene.host=localhost

### Design

Spring-5-test project is added for testing only, for easy configuration of properties and wiring objects for testing.