#!/bin/bash
set -x
## This differs from AMI script in several way:
# - the license is included as a file rather than retrieved (this is faster but also licenseUtility script doesn't seem to 
#  work when provisioning on docker
# Root user runs daemon 

## This performs a full installation on base openjdk-11 image

echo "installing dependencies"
DEBIAN_FRONTEND=noninteractive apt-get -y update && apt-get install -y xvfb && apt-get install -y python-zmq

SNAPGENE_DOWNLOAD_PKG=snapgene_server_2.0.34_linux.deb
SNAPGENE_DAEMON_HOME=/opt/gslbiotech/snapgene-server
SNAPGENE_DOWNLOAD_URL=https://cdn.snapgene.com/downloads/SnapGeneServer/2.x/2.0/2.0.34/$SNAPGENE_DOWNLOAD_PKG


function install_Snapgene {
  echo "installing Snapgene"
  wget $SNAPGENE_DOWNLOAD_URL
  dpkg -i  $SNAPGENE_DOWNLOAD_PKG

  ### into install folder
  INSTALL_UBUNTU_SCRIPT=$SNAPGENE_DAEMON_HOME/install/install_ubuntu
   ##  to make every install  command non-interactive
   sed -i.bac "s:\(apt-get*\):DEBIAN_FRONTEND=noninteractive \1:" $INSTALL_UBUNTU_SCRIPT
   sed -i.bac "s:\(apt-transport\):-y \1:" $INSTALL_UBUNTU_SCRIPT
   sed -i.bac "s:\(ttf-mscorefonts\):-y \1:" $INSTALL_UBUNTU_SCRIPT
   sed -i.bac "s:\(fonts-roboto\):-y \1:" $INSTALL_UBUNTU_SCRIPT

   $INSTALL_UBUNTU_SCRIPT 
 }
 
 function add_license {
   echo "Adding license"
   mkdir /etc/snapgene-server
   cp /snapgene-license.xml /etc/snapgene-server/.klf  
 }
 ## starts, tests and stops snapgene daemon
 function start_and_test_daemon {
   sed -i.bk "s/sudo -u snapuser//" $SNAPGENE_DAEMON_HOME/tools/start
   sed -i.bk "s/sudo //" $SNAPGENE_DAEMON_HOME/tools/stop-one
   $SNAPGENE_DAEMON_HOME/tools/start
   echo "info..."
   $SNAPGENE_DAEMON_HOME/tools/info.py
   echo "Testing reportFeatures is working..."
   $SNAPGENE_DAEMON_HOME/tools/request.py -c'{"request": "reportFeatures", "inputFile": "/opt/gslbiotech/snapgene-server/resources/pIB2-SEC13-mEGFP.dna"}'\
      -v2
   echo "Stopping"
   $SNAPGENE_DAEMON_HOME/tools/stop
   echo "stopped"
 }

install_Snapgene;
add_license
start_and_test_daemon


chmod 755 docker-start.sh;
