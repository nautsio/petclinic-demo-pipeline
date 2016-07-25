# Demo pipeline

This is a demo CD pipeline using docker to run everything.

## Running

```
$ docker-compose up
```

## What does this pipeline contain?

- Jenkins 2.x
  - Initially there is an `admin/admin` user created
  - Plugins are installed and plugin dependencies are automatically managed by the provided `install-plugins.sh` script in the jenkins container
  - Maven 3.3.9 is installed as a jenkins tool and is given the name ``M3``
  - Initally two demo pipelines are generated using groovy scripts that are loaded at startup
- Docker registry for storing docker images
- Nexus 2.x for storing java apps
- Selenium grid with two nodes for testing from a browser
  - Chrome
  - Firefox
- SonarQube for doing static analysis on code


### Jenkins 2.x pipelines as code

The preferred way of building pipelines in Jenkins 2.x is using the `pipeline-plugin` that gives users a semi-friendly DSL to work with. Jenkins 2.x offers "Pipeline" as a new type of job.

Pipeline scripts can also be retrieved from your git repo. Using a `Jenkinsfile` in your git root with the pipeline description allows for a multibranch pipeline.

Basic syntax is as follows:

```
node {
 stage 'Name of stage'
 git <your git repo url>

 stage '...'
 sh "<some command>"
}
```

Any commands are executed from the current workspace, meaning the git repo root.

Have a look at https://jenkins.io/doc/pipeline/ for more information on pipelines in Jenkins 2.x.
For more info on multibranch pipelines, visit https://jenkins.io/blog/2015/12/03/pipeline-as-code-with-multibranch-workflows-in-jenkins/
