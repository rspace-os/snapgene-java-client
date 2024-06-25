# Packer build

## Docker

This builds a docker image containing only the Snapgene daemon and a valid license.
The license should be supplied as a resource to the build

The Docker image exit immediately after running the entry point, so it has to be run manually.

You can launch then go into running container to start the daemon
e.g.

    docker run --entrypoint /bin/sh -itd -p5556:5556 IMAGE
    docker exec -it CONTAINER /bin/bash

Once in the container, start it by:

    /opt/gslbiotech/snapgene-server/tools/start

and check it's up:

    /opt/gslbiotech/snapgene-server/tools/info.py


