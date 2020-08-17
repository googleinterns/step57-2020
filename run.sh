#!/bin/bash
#
# Script to setup and run Kronos on AppEngine with proper environment variables for OAuth


if [ "$DOMAIN" = "" ]
then
  # if you're running on Cloudshell, the domain name will change frequently
  export DOMAIN=http://localhost:8080
fi

mvn package appengine:run
