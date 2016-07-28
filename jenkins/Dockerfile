FROM jenkinsci/jenkins

COPY *.groovy /usr/share/jenkins/ref/init.groovy.d/

# COPY plugins.txt /var/jenkins_home/plugins.txt
# RUN /usr/local/bin/plugins.sh /var/jenkins_home/plugins.txt

USER root
WORKDIR /usr/share/jenkins/ref/plugins/
RUN install-plugins.sh workflow-aggregator \
                       docker-workflow \
                       pipeline-utility-steps	\
                       pipeline-stage-view \
                       git \
                       antisamy-markup-formatter \
                       matrix-auth \
                       sonar

RUN apt-get update \
    && apt-get install -y apache2-utils \
    && apt-get install -y sudo \
    && rm -rf /var/lib/apt/lists/*
RUN echo "jenkins ALL=NOPASSWD: ALL" >> /etc/sudoers

# See JENKINS-34035 - disable upgrade wizard
RUN echo -n false > /usr/share/jenkins/ref/jenkins.install.runSetupWizard
RUN echo -n 2.0 > /usr/share/jenkins/ref/jenkins.install.UpgradeWizard.state  && \
    echo -n 2.0 > /usr/share/jenkins/ref/jenkins.install.InstallUtil.lastExecVersion

USER jenkins

COPY settings.xml /var/maven/settings.xml
