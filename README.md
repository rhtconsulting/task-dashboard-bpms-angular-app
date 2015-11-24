task-dashboard-bpms-angular-app
==============

## Modules:

1. bpms-angularjs-tasklist: A angularjs 2 app wired together using require.js, package in a Java WAR. It provides a dashboard of human tasks managed in the BPM Suite runtime by integrating with the [runtime task query API](https://access.redhat.com/documentation/en-US/Red_Hat_JBoss_BPM_Suite/6.1/html/Development_Guide/chap-Remote_API.html#sect-The_REST_Query_API)
2. bpms-remote-client: A simple usage of the [jbpm remote java client](https://access.redhat.com/documentation/en-US/Red_Hat_JBoss_BPM_Suite/6.1/html/Development_Guide/sect-Remote_Java_API.html)

## Deployment

The deployment of this project is described in a [Dockerfile](https://github.com/sherl0cks/docker-task-dashboard-bpms-angular) and which has been built and pushed to [Docker hub](https://hub.docker.com/r/sherl0cks/task-dashboard-bpms-angular/).

Thanks for [Becca Kreuter](https://github.com/downquark) for developing much of the JS and [Matyas Danter](https://github.com/mdanter/angularjs-jbpm) for some of the original code to get this bootstraped.

## Related Work

1. [bpms-trader-knowledge](https://github.com/rhtconsulting/task-dashboard-bpms-angular-knowledge) - The module containing rules and processes to be deployed in the bpm runtime for this demo
2. Red Hat Consulting Business Automation Practice [Standard project structure](http://redhat.slides.com/jholmes/bxms-standard-project-structures)

## Product Version

Currently built against BPM Suite 6.1 update 4 (maven version 6.2.0.Final-redhat-13)
