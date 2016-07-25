import jenkins.model.Jenkins
import org.jenkinsci.plugins.workflow.job.WorkflowJob
import org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition
import org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject
import jenkins.branch.BranchSource
import jenkins.plugins.git.GitSCMSource
import jenkins.branch.DefaultBranchPropertyStrategy
import jenkins.branch.BranchProperty


/*
 * Example of a pipeline defined in a job
 */
def project = Jenkins.instance.createProject(WorkflowJob.class, 'pipeline-local')

def pipeline = """
  node {
   stage 'Checkout'
   git url: 'https://github.com/pgoultiaev/spring-petclinic.git'

   // Get the maven tool.
   // ** NOTE: This 'M3' maven tool must be configured
   // **       in the global configuration.
   def mvnHome = tool 'M3'

   // Mark the code build 'stage'....
   stage 'unit test'
   sh "\${mvnHome}/bin/mvn test"

   stage 'sonar'
   sh "\${mvnHome}/bin/mvn sonar:sonar -Dsonar.host.url=http://sonar:9000"

   stage 'build'
   sh "\${mvnHome}/bin/mvn clean package"

   stage 'deploy to repo'
   sh "\${mvnHome}/bin/mvn -X -s /var/maven/settings.xml deploy:deploy-file \
   -DgroupId=nl.somecompany \
   -DartifactId=petclinic \
   -Dversion=1.0.0-SNAPSHOT \
   -DgeneratePom=true \
   -Dpackaging=war \
   -DrepositoryId=nexus \
   -Durl=http://nexus:8081/content/repositories/snapshots \
   -Dfile=target/petclinic.war"

  }
"""

project.definition = new CpsFlowDefinition(pipeline, true)

/*
 * Example of a pipeline defined in a jenkinsfile in a git repository
 */
def mp = Jenkins.instance.createProject(WorkflowMultiBranchProject.class, "pipeline-jenkinsfile")
mp.getSourcesList().add(new BranchSource(new GitSCMSource(null, 'https://github.com/kishorebhatia/pipeline-as-code-demo', '', '*', "", false), new DefaultBranchPropertyStrategy(new BranchProperty[0])));
